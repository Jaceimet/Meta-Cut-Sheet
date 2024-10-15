package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BatchForm {

    public static PDDocument batchForm(PDDocument existingDocument){

//https://www.youtube.com/watch?v=ipjl49Hgsg8&list=PLUDwpEzHYYLsN1kpIjOyYW6j_GLgOyA07

        String excelFilePath = "src/main/resources/Cut Sheet Express excel.xlsx";

//        InputStream url = FormSelector.class.getResourceAsStream(excelFilePath);
//        existingDocument = Loader.loadPDF(PdfImageCreator.class.getResourceAsStream(excelFilePath).readAllBytes());


        try {

            System.out.println(excelFilePath);
            FileInputStream inputStream = new FileInputStream(excelFilePath);

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            XSSFSheet sheet = workbook.getSheetAt(0); //XSSFSheet sheet = workbook.getSheet("sheet1")

            int rows = sheet.getLastRowNum();
            int columns = sheet.getRow(1).getLastCellNum();

            for(int r =0; r<=rows;r++){

                XSSFRow row = sheet.getRow(r);

                // error row is null?
                for(int c = 0; c < columns; c++)
                {
                    XSSFCell cell = row.getCell(c);

                    switch(cell.getCellType())
                    {
                        case STRING: System.out.println(cell.getStringCellValue()); break;
                        case NUMERIC: System.out.println(cell.getNumericCellValue());break;
                        case BOOLEAN: System.out.println(cell.getBooleanCellValue()); break;
                    }

                }
                System.out.println();
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Map<String, String> fields = new HashMap<>();

        fields.put("type", "a");
        fields.put("manufacturer", "b");
        // incorrect format?
        fields.put("modelNumber", "3234");
        fields.put("partNumber", "d");
        fields.put("description", "e");
        // incorrect format?
//        fields.put("wattage", ("f").formatted());
        fields.put("voltage", "g");
        fields.put("control", "h");
        fields.put("dimmable", "i");
        // // incorrect format?
////        LocalDate today = LocalDate.now();
////        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        fields.put("date", ("123").formatted());
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
