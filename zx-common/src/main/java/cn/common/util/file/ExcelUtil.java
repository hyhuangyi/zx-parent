package cn.common.util.file;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {
    /**
     * 将list<map>数据插入到execl 并下载
     *
     * @param response   返回请求信息
     * @param filedNames execl标题名
     * @param titleName  生成的execl文件的名称
     * @param list       插入的数据
     * @throws Exception 异常
     */
    public static void createExcelByListMap(HttpServletResponse response, String[] filedNames,
                                            String titleName, List<Map<String, Object>> list) throws Exception {
        List<LinkedHashMap> linkedHashMapList = new LinkedList<>();
        //转换
        for (Map<String, Object> map : list) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap = getMapValueForLinkedHashMap(map);
            linkedHashMapList.add(linkedHashMap);
        }
        exportToExcel(response, filedNames, titleName, linkedHashMapList);
    }


    /**
     * 将Map转换为LinkedHashMap（不带key）
     *
     * @param dataMap
     * @return
     */
    public static LinkedHashMap getMapValueForLinkedHashMap(Map dataMap) {
        LinkedHashMap returnMap = new LinkedHashMap();
        if (ObjectUtils.isEmpty(dataMap)) {
            return returnMap;
        }
        Iterator iterator = dataMap.keySet().iterator();
        while (iterator.hasNext()) {
            Object objKey = iterator.next();
            Object objValue = dataMap.get(objKey);
            if (objValue instanceof Map) {
                returnMap.put(objKey, getMapValueForLinkedHashMap((Map) objValue));
            } else {
                returnMap.put(objKey, objValue);
            }
        }
        return returnMap;
    }

    /**
     * 导出Excel.
     *
     * @param response
     * @param filedNames  excel标题&字段 此参数为map，实例为new LinkedHashMap<String, Object>();
     * @param titleName   导出文件名
     * @param filedParams 内容范例.
     */
    public static void exportToExcel(final HttpServletResponse response, final String[] filedNames,
                                     final String titleName, final List<LinkedHashMap> filedParams) {
        // 第一步，创建一个webbook，对应一个Excel文件
        final XSSFWorkbook wb = new XSSFWorkbook();
        // HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        final XSSFSheet sheet = wb.createSheet(titleName);
        // 设置表格默认列宽度为25个字节XSSFWorkbook
        sheet.setDefaultColumnWidth(18);
        sheet.setDefaultRowHeight((short) (1 * 256));
        //sheet.autoSizeColumn(1);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头设置表头居中
        final XSSFCellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        //数据样式居中
        style.setAlignment(HorizontalAlignment.LEFT);
        // 声明列对象
        XSSFCell cell = null;
        Iterator<LinkedHashMap> it = filedParams.iterator();
        try {
            int i = 0;
            // 创建标题
            for (String filed : filedNames) {
                cell = row.createCell(i);
                cell.setCellValue(filed);
                cell.setCellStyle(style);
                i++;
            }

            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                Map<String, Object> data = it.next();
                int j = 0;
                //设置序号
                /**
                 if (!data.isEmpty()) {
                 cell = row.createCell(j);
                 cell.setCellStyle(style);
                 cell.setCellValue(index);
                 j++;
                 }**/
                for (String key : data.keySet()) {
                    cell = row.createCell(j);
                    cell.setCellStyle(style);
                    XSSFRichTextString text = new XSSFRichTextString(data.get(key) + "");
                    cell.setCellValue(text);
                    j++;
                }
            }
            // 文件名
            String fileName;
            final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            final Date date = new Date();
            final String time = format.format(date);
            // 输出的文件名+以毫秒为单位返回当前时间
            // ISO8859-1不能改为UTF-8,否则文件名是乱码
            fileName = URLEncoder.encode(titleName + time + ".xlsx", "UTF-8");
            // application应用；octet-stream八进制；charset字符集(请求应用)
            response.setContentType("application/octet-stream;charset=UTF-8");
            // Content-Disposition内容配置；attachment附件；(下载完成提示)
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            final OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param is       文件流
     * @param rowStart 从第几行开始读取（不包含）
     * @param colStart 从第几列开始读取（不包含）
     * @return List<Map < String, Object>>
     * @Description：
     */
    public static List<Map<String, Object>> readXls(InputStream is, int numSheet, int rowStart, int colStart) throws Exception {
        Workbook hssfWorkbook = WorkbookFactory.create(is);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // Read the Sheet
        Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
        if (hssfSheet == null) {
            return null;
        }
        List<List<Map<String, String>>> headers = new ArrayList<>();
        //read the sheet header
        for (int i = 0; i < rowStart; i++) {
            List<Map<String, String>> headerList = new ArrayList<>();
            Row headerRow = hssfSheet.getRow(i);
            for (int j = colStart; j < headerRow.getLastCellNum(); j++) {
                Map<String, String> map = new HashMap<>();
                map.put("cellVal", headerRow.getCell(j) == null ? "" : headerRow.getCell(j).toString());
                Map<String, String> mergedMap = isMergedRegion(hssfSheet, i, j);
                if (mergedMap.get("isMerged").equals("yes")) {
                    map.put("group", mergedMap.get("index"));
                }
                headerList.add(map);
            }
            if (i == rowStart - 1) {
                headers.add(headerList);
            } else {
                headers.add(dealList(headerList));
            }
        }
        //拼接表头
        List<String> keys = joinHeader(headers);
        //System.out.println(keys);
        for (int rowNum = rowStart; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            Row hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow != null) {
                Map<String, Object> map = new LinkedHashMap<>();
                for (int i = colStart, j = hssfRow.getLastCellNum(); i < j; i++) {
                    map.put(keys.get(i - colStart), hssfRow.getCell(i));
                }
                list.add(map);
            }
        }
        hssfWorkbook.close();
        return list;
    }

    //判断是否是合并单元格，并返回sheet中该合并单元格的索引
    public static Map<String, String> isMergedRegion(Sheet sheet, int row, int column) {
        Map<String, String> map = new HashMap<>();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    map.put("isMerged", "yes");
                    map.put("index", i + "");
                    return map;
                }
            }
        }
        map.put("isMerged", "no");
        return map;
    }

    /**
     * @param
     * @return List<String>
     * @Description：
     */
    public static List<Map<String, String>> dealList(List<Map<String, String>> headerList) {
        if (headerList != null && !headerList.isEmpty()) {
            for (int i = 1; i < headerList.size(); i++) {
                Map<String, String> map = headerList.get(i);
                Map<String, String> map1 = headerList.get(i - 1);
                if (StringUtils.isEmpty(map.get("cellVal")) && map1.containsKey("group") && map.containsKey("group") && map.get("group").equals(map1.get("group"))) {
                    headerList.set(i, headerList.get(i - 1));
                }
            }
            return headerList;
        }
        return null;
    }

    public static List<String> joinHeader(final List<List<Map<String, String>>> headers) {
        final List<Iterator<Map<String, String>>> it = new LinkedList<>();
        for (List<Map<String, String>> l : headers) {
            it.add(l.iterator());
        }

        final List<String> combined = new ArrayList<>();
        int index = 1;
        boolean flag = false;
        outer:
        while (true) {
            final StringBuilder sb = new StringBuilder();
            if (flag) {
                index = 1;
            }
            for (final Iterator<Map<String, String>> i : it) {
                if (!i.hasNext()) {
                    break outer;
                }
                Map<String, String> map = i.next();
                sb.append(map.get("cellVal") + "-");
            }
            String str = sb.toString();
            str = str.replaceAll("-{1,}$", "");
            if (combined.contains(str)) {
                str = str + "-" + index;
                index++;
                flag = false;
            } else {
                flag = true;
            }
            combined.add(str);
        }
        for (final Iterator<Map<String, String>> i : it) {
            if (i.hasNext()) {
                throw new IllegalArgumentException("Lists not the same length.");
            }
        }
        return combined;
    }
}
