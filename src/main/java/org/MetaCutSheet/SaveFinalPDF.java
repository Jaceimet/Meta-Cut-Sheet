package org.MetaCutSheet;

import jnafilechooser.api.JnaFileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class SaveFinalPDF {

    public static String saveFinalPDF(PDDocument final_cs) throws IOException {

        String selectedSaveFilePath = null;

        Preferences prefs = Preferences.userRoot().node("Save File Path");

        String testFile2;

        JnaFileChooser jnaFileChooser = new JnaFileChooser();
        jnaFileChooser.setTitle("Choose Save Directory");
        jnaFileChooser.addFilter("PDF Documents (*.pdf)", "pdf");
        if (prefs.get("Save File Path", "root") == null){
            testFile2 = "C:\\\\Users\\\\James\\\\Desktop";
        } else {
            testFile2 = prefs.get("Save File Path", "root");
        }
//        String testFile2 = prefs.get("Save File Path", "root");
//        String testFile2 = "C:\\\\Users\\\\James\\\\Desktop\\\\temp";
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

        prefs.put("Save File Path", selectedSaveFilePath );

        System.out.println(prefs.get("Save File Path", "root"));

        System.out.println("Successfully saved to : " + selectedSaveFilePath + "\n");

        final_cs.close();

        return selectedSaveFilePath;

    }
}
