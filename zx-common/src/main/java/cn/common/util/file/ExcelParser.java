package cn.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.beust.jcommander.internal.Lists;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
/**
 * 解析大数据量Excel工具类
 */
@Component
public class ExcelParser {
    private static final Logger logger = LoggerFactory.getLogger(ExcelParser.class);
    /**
     * 表格默认处理器
     */
    private ISheetContentHandler contentHandler = new DefaultSheetHandler();
    /**
     * 读取数据
     */
    private List<List<String>> datas = new ArrayList<>();

    /**
     * 转换表格,默认为转换第一个表格
     * @param stream
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     * @throws ParseException
     */
    public ExcelParser parse(InputStream stream)
            throws InvalidFormatException, IOException, ParseException {
        return parse(stream, 1);
    }

    /**
     *
     * @param stream
     * @param sheetId:为要遍历的sheet索引，从1开始
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     * @throws ParseException
     */
    public synchronized ExcelParser parse(InputStream stream, int sheetId)
            throws InvalidFormatException, IOException, ParseException {
        // 每次转换前都清空数据
        datas.clear();
        // 打开表格文件输入流
        OPCPackage pkg = OPCPackage.open(stream);
        try {
            // 创建表阅读器
            XSSFReader reader;
            try {
                reader = new XSSFReader(pkg);
            } catch (OpenXML4JException e) {
                logger.error("读取表格出错");
                throw new ParseException(e.fillInStackTrace());
            }

            // 转换指定单元表
            InputStream shellStream = reader.getSheet("rId" + sheetId);
            try {
                InputSource sheetSource = new InputSource(shellStream);
                StylesTable styles = reader.getStylesTable();
                ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
                getContentHandler().init(datas);// 设置读取出的数据
                // 获取转换器
                XMLReader parser = getSheetParser(styles, strings);
                parser.parse(sheetSource);
            } catch (SAXException e) {
                logger.error("读取表格出错");
                throw new ParseException(e.fillInStackTrace());
            } finally {
                shellStream.close();
            }
        } finally {
            pkg.close();

        }
        return this;

    }

    /**
     * 获取表格读取数据,获取数据前，需要先转换数据<br>
     * 此方法不会获取第一行数据
     *
     * @return 表格读取数据
     */
    public List<List<String>> getDatas() {
        return getDatas(true);
    }

    /**
     * 获取表格读取数据,获取数据前，需要先转换数据
     *
     * @param dropFirstRow
     *            删除第一行表头记录
     * @return 表格读取数据
     */
    public List<List<String>> getDatas(boolean dropFirstRow) {
        if (dropFirstRow && datas.size() > 0) {
            datas.remove(0);// 删除表头
        }
        return datas;

    }

    /**
     * 获取读取表格的转换器
     *
     * @return 读取表格的转换器
     * @throws SAXException
     *             SAX错误
     */
    protected XMLReader getSheetParser(StylesTable styles, ReadOnlySharedStringsTable strings) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, getContentHandler(), false));
        return parser;
    }

    public ISheetContentHandler getContentHandler() {
        return contentHandler;
    }

    public void setContentHandler(ISheetContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    /**
     * 表格转换错误
     */
    public class ParseException extends Exception {
        private static final long serialVersionUID = -2451526411018517607L;

        public ParseException(Throwable t) {
            super("表格转换错误", t);
        }

    }

    public interface ISheetContentHandler extends SheetContentsHandler {

        /**
         * 设置转换后的数据集，用于存放转换结果
         *
         * @param datas
         *            转换结果
         */
        void init(List<List<String>> datas);
    }

    /**
     * 默认表格解析handder
     */
    class DefaultSheetHandler implements ISheetContentHandler {
        /**
         * 读取数据
         */
        private List<List<String>> datas=new LinkedList<>();
        // 读取行信息
        private List<String> readRow=new LinkedList<>();

        @Override
        public void init(List<List<String>> datas) {
            this.datas = datas;
        }

        @Override
        public void startRow(int rowNum) {
           readRow.clear();
        }

        @Override
        public void endRow(int rowNum) {
            if(rowNum == 0){
                // 标题
                datas.add(Lists.newLinkedList(readRow));
            }else {
                // 数据
                datas.add(Lists.newArrayList(readRow));
            }
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            readRow.add(formattedValue);
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }
    }

    public static void main(String[] args) throws  Exception{
        long start = System.currentTimeMillis();
        ExcelParser excelParser=new ExcelParser();
        File file=new File("C:\\Users\\86187\\Desktop\\cc.xls");
        ExcelParser parser=excelParser.parse(new FileInputStream(file));
        List<List<String>>list=parser.getDatas();
        System.out.println(list);
        long end = System.currentTimeMillis();
        System.err.println(end - start);
    }
}