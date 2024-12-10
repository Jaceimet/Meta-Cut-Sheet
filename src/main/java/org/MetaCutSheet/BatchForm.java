package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class BatchForm {

//    static String type;
//    static String manufacturer;
//    static String modelNumber;
//    static String partNumber;
//    static String description;
//    static String wattage;
//    static String voltage;
//    static String control;
//    static String dimmable;
//    static String date;
//    static String revision;
//    static String approvedBy;
//    static String ld;
//    static String designFirm;
//    static String projectName;
//    static String projectLocation;
//    static String notes;

    public static PDDocument batchForm(PDDocument existingDocument){

        //look into linked hashmap?
        //Java collections overview: https://www.youtube.com/watch?v=viTHc_4XfCA

//https://www.youtube.com/watch?v=ipjl49Hgsg8&list=PLUDwpEzHYYLsN1kpIjOyYW6j_GLgOyA07
        //https://www.youtube.com/watch?v=JAyJSffgm7c

        //hashmap data processing
        //https://www.youtube.com/watch?v=rJ3-94VjWMg


        try {
            String excelFilePath = "src/main/resources/Cut Sheet Express excel.xlsx";

            //successfully open Excel spreadsheet
            if (Desktop.isDesktopSupported()) {
                try {
                    File excelFile = new File(excelFilePath);
                    if (excelFile.exists()) {
                        Desktop.getDesktop().open(excelFile);
                        System.out.println("Excel opened successfully." + "\n");
                    } else {
                        System.out.println("excel file does not exist." + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to open excel." + "\n");
                }
            } else {
                System.out.println("Desktop is not supported. Cannot open excel." + "\n");
            }

            FileInputStream inputStream = new FileInputStream(excelFilePath);

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            XSSFSheet sheet = workbook.getSheet("sheet1"); //XSSFSheet sheet = workbook.getSheet("sheet1")


            List<Map<String,String>> mapLists = new ArrayList<>();

            int rows = sheet.getLastRowNum();
            System.out.println("rows: " + rows);

            XSSFRow row = sheet.getRow(0);
            System.out.println("Last Cell num: " + row.getLastCellNum());

            // functional loop, each row is a map
            for(int i = 0; i < rows; i++){

                //attempt to remove empty maps unsuccessful so far
//                Row rowCheck = CellUtil.getRow(i+1, sheet);
                Row rowCheck = CellUtil.getRow(i,sheet);

//                if(rowCheck.iterator().hasNext()) {
                    LinkedHashMap<String, String> myMap = new LinkedHashMap<>();
//                }

                for (int j = 0; j < row.getLastCellNum(); j++){

                    Row keyRow = CellUtil.getRow(0,sheet);
                    Row valueRow = CellUtil.getRow(i+1, sheet);
                    String key = CellUtil.getCell(keyRow,j).toString();
                    String value = CellUtil.getCell(valueRow,j).toString();

                    if(value != "") {
                        myMap.put(key, value);

                    }

                }

                mapLists.add(myMap);

            }

//            System.out.println(mapLists);

            for (Map<String, String> map : mapLists){
//                map.get("Target PDF/Image Location");
//                System.out.println(map.get("Target PDF/Image Location:"));
                if (map.containsValue("C:\\Users\\James\\Desktop\\001")){
                    System.out.println(map);

                    Map<String, String> fields = new HashMap<>();

                    fields.put("type", map.get("Type:"));
                    fields.put("manufacturer", map.get("Manufacturer:"));
                    // incorrect format?
                    fields.put("modelNumber", map.get("Model #:"));
                    fields.put("partNumber", map.get("Part #:"));
                    fields.put("description", map.get("Description:"));
                    // incorrect format?
                    fields.put("wattage", map.get("Wattage:"));
                    fields.put("voltage", map.get("Voltage:"));
                    fields.put("control", map.get("Control:"));
                    fields.put("dimmable", map.get("Dimmable:"));
                    // // incorrect format?
                    LocalDate today = LocalDate.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    fields.put("date", dtf.format(today)); //("123").formatted()
                    fields.put("revision", map.get("Revision:"));
                    fields.put("approvedBy", map.get("Approved By:"));
                    fields.put("ld", map.get("LD:"));
                    fields.put("designFirm", map.get("Design Firm:"));
                    fields.put("projectName", map.get("Project Name:"));
                    fields.put("projectLocation", map.get("Project Location:"));
                    fields.put("notes", map.get("Notes:"));
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

                }

            }


            inputStream.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }



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



//        Map<String, String> fields = new HashMap<>();
//
//        fields.put("type", type);
//        fields.put("manufacturer", manufacturer);
//        // incorrect format?
//        fields.put("modelNumber", modelNumber);
//        fields.put("partNumber", partNumber);
//        fields.put("description", description);
//        // incorrect format?
//        fields.put("wattage", wattage);
//        fields.put("voltage", voltage);
//        fields.put("control", control);
//        fields.put("dimmable", dimmable);
//        // // incorrect format?
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        fields.put("date", dtf.format(today)); //("123").formatted()
//        fields.put("revision", revision);
//        fields.put("approvedBy", approvedBy);
//        fields.put("ld", ld);
//        fields.put("designFirm", designFirm);
//        fields.put("projectName", projectName);
//        fields.put("projectLocation", projectLocation);
//        fields.put("notes", notes);
//        PDDocumentCatalog docCatalog = existingDocument.getDocumentCatalog();
//        PDAcroForm acroForm = docCatalog.getAcroForm();
//        acroForm.setCacheFields(true);
//
//        Iterator var9 = fields.entrySet().iterator();
//
//        while(var9.hasNext()) {
//            Map.Entry<String, String> field = (Map.Entry)var9.next();
//            try {
//                acroForm.getField(field.getKey()).setValue(field.getValue());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }

        System.out.println("Form filled");
        return existingDocument;
    }



}
