package cn.common.util.file;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
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
     * @param list   插入的数据
     * @throws Exception 异常
     */
    public static void createExcelByListMap(HttpServletResponse response, String[] filedNames,
                                       String titleName, List<Map<String, String>> list) throws Exception {
        List<LinkedHashMap> linkedHashMapList = new LinkedList<>();
        //转换
        for (Map<String, String> map : list) {
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
        sheet.setDefaultColumnWidth(25);
        sheet.autoSizeColumn(0);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头设置表头居中
        final XSSFCellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        //数据样式居中
        style.setAlignment(HorizontalAlignment.CENTER);
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
                if (!data.isEmpty()) {
                    cell = row.createCell(j);
                    cell.setCellStyle(style);
                    cell.setCellValue(index);
                    j++;
                }
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

}
