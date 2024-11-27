package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BatchForm {

    static String type;
    static String manufacturer;
    static String modelNumber;
    static String partNumber;
    static String description;
    static String wattage;
    static String voltage;
    static String control;
    static String dimmable;
    static String date;
    static String revision;
    static String approvedBy;
    static String ld;
    static String designFirm;
    static String projectName;
    static String projectLocation;
    static String notes;

    public static PDDocument batchForm(PDDocument existingDocument){

//https://www.youtube.com/watch?v=ipjl49Hgsg8&list=PLUDwpEzHYYLsN1kpIjOyYW6j_GLgOyA07
        //https://www.youtube.com/watch?v=JAyJSffgm7c

        //hashmap data processing
        //https://www.youtube.com/watch?v=rJ3-94VjWMg


        try {
            String excelFilePath = "src/main/resources/Cut Sheet Express excel.xlsx";

            FileInputStream inputStream = new FileInputStream(excelFilePath);

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            XSSFSheet sheet = workbook.getSheet("sheet1"); //XSSFSheet sheet = workbook.getSheet("sheet1")



//            int rowCount = sheet.getLastRowNum();
//            int rowCount = sheet.getLastRowNum() ; //- sheet.getFirstRowNum()
//            System.out.println("row count : " + rowCount);


// example code (current state reads correct direction but wrong value)

            List<Map<String,String>> mapLists = new ArrayList<>();

            int rows = sheet.getLastRowNum();
            System.out.println("rows: " + rows);

            XSSFRow row = sheet.getRow(0);
            System.out.println("Last Cell num: " + row.getLastCellNum());

            for(int j = 0; j < row.getLastCellNum(); j++){

                Map<String, String > myMap = new HashMap<>();


                for (int i = 1; i < rows + 1; i++){

                    Row r = CellUtil.getRow(0, sheet);
                    String key = CellUtil.getCell(r,0).toString();
                    String value = CellUtil.getCell(r,j).toString();

                    myMap.put(key,value);


                }

                mapLists.add(myMap);
            }


            //Current inprogress code
            // note code reads top to bottom, needs left to right
//            for (int i = 0; i <= rowCount; i++){ //+1
//
//                XSSFRow row = sheet.getRow(i);
//
////                int columnCount = row.getPhysicalNumberOfCells();
//
//                Map<String,String> myMap = new HashMap<>();
//
//                for(int j =0; j< row.getPhysicalNumberOfCells(); j++){
//
////                    Row r = CellUtil.getRow(i,sheet);
//                    String key = CellUtil.getCell(row,0).toString();
//                    String value = CellUtil.getCell(row,j).toString();
//
//                    myMap.put(key,value);
//
//                }
//                mapList.add(myMap);
//            }
//
            System.out.println(mapLists);

            inputStream.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

//         original version
//        for (int i = 1; i < rowCount +1; i++){
//
//            Map<String,String> myMap = new HashMap<>();
//
//            for(int j =1; j< row.getLastCellNum(); j++){
//
//                Row r = CellUtil.getRow(i,sheet);
//                String key = CellUtil.getCell(r,0).toString();
//                String value = CellUtil.getCell(r,j).toString();
//
//                myMap.put(key,value);
//
//            }
//            mapList.add(myMap);
//        }
//
//        System.out.println(mapList);
//
//        inputStream.close();
//
//    } catch (IOException e) {
//        System.err.println(e.getMessage());
//    }

//            // this code only prints out data, does not store
//            try {
//                String excelFilePath = "src/main/resources/Cut Sheet Express excel.xlsx";
//
//                System.out.println(excelFilePath);
//
//                FileInputStream inputStream = new FileInputStream(excelFilePath);
//
//                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//
//                XSSFSheet sheet = workbook.getSheetAt(0); //XSSFSheet sheet = workbook.getSheet("sheet1")
//
//                int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
//                System.out.println("row count : " + rowCount);
//
//                for (int i = 0; i <= rowCount; i++) {
//                    XSSFRow row = sheet.getRow(i);
//
//                    //fails here, need if statement?
//                    int columnCount = row.getPhysicalNumberOfCells();
//                    System.out.println("Cell count: " + columnCount);
//
////                    if (columnCount != 1) {
//
//                        for (int j = 0; j < columnCount; j++) {
//
//                            //needs better logic to skip blank cells
//                            if (row.getCell(j) != null && columnCount > 1){
//
//                            XSSFCell cell = row.getCell(j);
//
//
////                    String cellValue = getCellValue(cell);
//                            switch (cell.getCellType()) {
//                                case STRING:
//                                    System.out.println(cell.getStringCellValue());
//                                    break;
//                                case NUMERIC:
//                                    System.out.println(cell.getNumericCellValue());
//                                    break;
//                                case BOOLEAN:
//                                    System.out.println(cell.getBooleanCellValue());
//                                    break;
//                                case FORMULA:
//                                    System.out.println(cell.getDateCellValue());
//                                    break;
//                                case _NONE:
//                                    System.out.println("none");
//                                    break;
//                                case ERROR:
//                                    System.out.println("Error");
//                                    break;
//                                case BLANK:
//                                    System.out.println("Blank");
//                                    break;
//                                default:
//                                    throw new IllegalStateException("Unexpected value: " + cell.getCellType());
//                            }
//                        }else {
//                                ++columnCount;
//                                ++j;
////                                i=rowCount;
//                            }
//
//                    }
//                    System.out.println();
//                }
//                } catch(IOException e){
//                    System.err.println(e.getMessage());
//                }



        Map<String, String> fields = new HashMap<>();

        fields.put("type", type);
        fields.put("manufacturer", manufacturer);
        // incorrect format?
        fields.put("modelNumber", modelNumber);
        fields.put("partNumber", partNumber);
        fields.put("description", description);
        // incorrect format?
        fields.put("wattage", wattage);
        fields.put("voltage", voltage);
        fields.put("control", control);
        fields.put("dimmable", dimmable);
        // // incorrect format?
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        fields.put("date", dtf.format(today)); //("123").formatted()
        fields.put("revision", revision);
        fields.put("approvedBy", approvedBy);
        fields.put("ld", ld);
        fields.put("designFirm", designFirm);
        fields.put("projectName", projectName);
        fields.put("projectLocation", projectLocation);
        fields.put("notes", notes);
        PDDocumentCatalog docCatalog = existingDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        acroForm.setCacheFields(true);

        Iterator var9 = fields.entrySet().iterator();

        while(var9.hasNext()) {
            Map.Entry<String, String> field = (Map.Entry)var9.next();
            try {
                acroForm.getField(field.getKey()).setValue(field.getValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Form filled");
        return existingDocument;
    }



}
