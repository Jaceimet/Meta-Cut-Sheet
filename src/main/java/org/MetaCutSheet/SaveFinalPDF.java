package org.MetaCutSheet;

import jnafilechooser.api.JnaFileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class SaveFinalPDF {

    public static String saveFinalPDF(PDDocument final_cs) throws IOException {


        String selectedSaveFilePath = null;

        JnaFileChooser jnaFileChooser = new JnaFileChooser();
        jnaFileChooser.setTitle("Choose Save Directory");
        jnaFileChooser.addFilter("PDF Documents (*.pdf)", "pdf");
        String testFile2 = "C:\\\\Users\\\\James\\\\Desktop\\\\temp";
        jnaFileChooser.setCurrentDirectory(testFile2);

        if (jnaFileChooser.showSaveDialog(null)) {
            File saveFileDir = jnaFileChooser.getSelectedFile();

            ///////
//            Need to save previous save location
//            use preferences class
            /////

            if (saveFileDir.getAbsolutePath().contains(".pdf")) {
                selectedSaveFilePath = saveFileDir.getAbsolutePath();
            } else {
                selectedSaveFilePath = saveFileDir.getAbsolutePath() + ".pdf";
            }

            System.out.println("\n" + "Selected save file: " + selectedSaveFilePath + "\n");

        } else {

            System.out.println("No file selected.");
        }

        final_cs.save(selectedSaveFilePath);

        System.out.println("Successfully saved to : " + selectedSaveFilePath + "\n");

        final_cs.close();

        return selectedSaveFilePath;

    }
}
