package cn.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import com.beust.jcommander.internal.Lists;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class ExcelEventParser {

    private InputStream inputStream;
    private SheetContentsHandler handler;

    public ExcelEventParser(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public ExcelEventParser setHandler(SheetContentsHandler handler) {
        this.handler = handler;
        return this;
    }

    public void parse(){
        OPCPackage pkg = null;
        InputStream sheetInputStream = null;

        try {
            pkg = OPCPackage.open(inputStream);
            XSSFReader xssfReader = new XSSFReader(pkg);
            StylesTable styles = xssfReader.getStylesTable();
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
            sheetInputStream = xssfReader.getSheetsData().next();
            processSheet(styles, strings, sheetInputStream);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            if(sheetInputStream != null){
                try {
                    sheetInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            if(pkg != null){
                try {
                    pkg.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    private void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws SAXException, ParserConfigurationException, IOException{
        XMLReader sheetParser = SAXHelper.newXMLReader();

        if(handler != null){
            sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, handler, false));
        }else{
            sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, new SimpleSheetContentsHandler(), false));
        }

        sheetParser.parse(new InputSource(sheetInputStream));
    }

    public static class SimpleSheetContentsHandler implements SheetContentsHandler{
        protected List<String> row = new LinkedList<>();

        @Override
        public void startRow(int rowNum) {
            row.clear();
        }

        @Override
        public void endRow(int rowNum) {
            System.err.println(rowNum + " : " + row);
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            row.add(formattedValue);
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {

        }
    }

    public static void main(String[] args) throws Exception{
        File file=new File("C:\\Users\\86187\\Desktop\\aa.xlsx");
        long start = System.currentTimeMillis();

        List<List<String>> table = new LinkedList<>();
        new ExcelEventParser(new FileInputStream(file)).setHandler(new ExcelEventParser.SimpleSheetContentsHandler(){

            private List<String> fields;

            @Override
            public void endRow(int rowNum) {
                if(rowNum == 0){
                    // 第二行字段名
                    fields = row;
                }else {
                    // 数据
                    table.add(Lists.newArrayList(row));
                }
            }
        }).parse();

        long end = System.currentTimeMillis();
        System.out.println(table);
        System.err.println(table.size());
        System.err.println(end - start);
    }
}
