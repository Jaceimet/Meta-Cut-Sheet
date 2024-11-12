package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BatchForm {

    public static PDDocument batchForm(PDDocument existingDocument){

//https://www.youtube.com/watch?v=ipjl49Hgsg8&list=PLUDwpEzHYYLsN1kpIjOyYW6j_GLgOyA07
        //https://www.youtube.com/watch?v=JAyJSffgm7c


        String excelFilePath = "src/main/resources/Cut Sheet Express excel.xlsx";

//        InputStream url = FormSelector.class.getResourceAsStream(excelFilePath);
//        existingDocument = Loader.loadPDF(PdfImageCreator.class.getResourceAsStream(excelFilePath).readAllBytes());


//        try {
//
//            System.out.println(excelFilePath);
//
//            FileInputStream inputStream = new FileInputStream(excelFilePath);
//
//            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//
//            XSSFSheet sheet = workbook.getSheetAt(0); //XSSFSheet sheet = workbook.getSheet("sheet1")
//
//            int rowCount = sheet.getLastRowNum();
//            System.out.println("row count : " + rowCount);


//            for (int i =0; i <= rowCount; i++) {
//                    int columnCount = sheet.getRow(i).getLastCellNum();
//                System.out.println("Cell count: " + columnCount);
//
//                    for (int j = 0; j < columnCount; j++) {
//                        System.out.println(sheet.getRow(i).getCell(j).toString());
//                    }
//                    System.out.println();
//
////                }else {
////                    System.out.println("no more row to process");
////                }
//
//
//            }

            try {

                System.out.println(excelFilePath);

                FileInputStream inputStream = new FileInputStream(excelFilePath);

                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

                XSSFSheet sheet = workbook.getSheetAt(0); //XSSFSheet sheet = workbook.getSheet("sheet1")

                int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
                System.out.println("row count : " + rowCount);

                for (int i =0; i <= rowCount; i++) {
                    XSSFRow row = sheet.getRow(i);

                    //fails here, need if statement?
//                if (row != null) {
                    int columnCount = row.getPhysicalNumberOfCells();
                    System.out.println("Cell count: " + columnCount);

                    for (int j = 0; j < columnCount; j++) {
                        XSSFCell cell = row.getCell(j);
//                    String cellValue = getCellValue(cell);
                        switch (cell.getCellType()) {
                            case STRING:
                                System.out.println(cell.getStringCellValue());
                                break;
                            case NUMERIC:
                                System.out.println(cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                System.out.println(cell.getBooleanCellValue());
                                break;
                            case _NONE, ERROR, BLANK:
                                System.out.println("blank cell possible error");
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + cell.getCellType());
                        }

                    }
                    System.out.println();

//                }else {
//                    System.out.println("no more row to process");
//                }


                }



//            int rows = sheet.getLastRowNum();
//            System.out.println("Number of rows: " + rows);
//            int columns = sheet.getRow(1).getLastCellNum();
//            System.out.println("Number of Columns: " + columns);
//            for(int r = 0; r <= rows; r++){
//
//                XSSFRow row = sheet.getRow(r);
//
//                // error row is null?
//                for(int c = 0; c < columns; c++) {
//
//                    //fails here need fix
//                    XSSFCell cell = row.getCell(c);
//
//
//                        switch (cell.getCellType()) {
//                            case STRING:
//                                System.out.println(cell.getStringCellValue());
//                                break;
//                            case NUMERIC:
//                                System.out.println(cell.getNumericCellValue());
//                                break;
//                            case BOOLEAN:
//                                System.out.println(cell.getBooleanCellValue());
//                                break;
//                            case _NONE, ERROR, BLANK:
//                                System.out.println("blank cell possible error");
//                                break;
//                            default:
//                                throw new IllegalStateException("Unexpected value: " + cell.getCellType());
//                        }
//
//                    }
//                    System.out.println();
//
//            }


        } catch (IOException e) {
            System.err.println("Ran out of rows " + e.getMessage());
        }




        Map<String, String> fields = new HashMap<>();

        fields.put("type", "a");
        fields.put("manufacturer", "b");
        // incorrect format?
        fields.put("modelNumber", "3234");
        fields.put("partNumber", "d");
        fields.put("description", "e");
        // incorrect format?
        fields.put("wattage", ("f").formatted());
        fields.put("voltage", "g");
        fields.put("control", "h");
        fields.put("dimmable", "i");
        // // incorrect format?
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        fields.put("date", dtf.format(today).toString()); //("123").formatted()
        fields.put("revision", "k");
        fields.put("approvedBy", "l");
        fields.put("ld", "m");
        fields.put("designFirm", "n");
        fields.put("projectName", "o");
        fields.put("projectLocation", "p");
        fields.put("notes", "q");
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
