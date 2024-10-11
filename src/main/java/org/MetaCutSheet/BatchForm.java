package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BatchForm {

    public static PDDocument batchForm(PDDocument existingDocument) {

        Map<String, String> fields = new HashMap<>();

        fields.put("type", "a");
        fields.put("manufacturer", "b");
//        fields.put("model#", "c");
//        fields.put("part#", "d");
        fields.put("description", "e");
        fields.put("wattage", "f");
        fields.put("voltage", "g");
        fields.put("control", "h");
        fields.put("dimmable", "i");
        // needs solution
////        LocalDate today = LocalDate.now();
////        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        fields.put("date", "j");
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
                acroForm.getField((String)field.getKey()).setValue((String)field.getValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Form filled");
        return existingDocument;
    }



}
