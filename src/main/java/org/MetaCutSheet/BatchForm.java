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


        String excelFilePath = "src/main/resources/Cut Sheet Express excel.xlsx";


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
                }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


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
