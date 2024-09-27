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


        // cleanup
        // need to figure out how to save last file path*********************
        //String selectedSaveFilePath = null;
//        JFileChooser saveFileChooser = new JFileChooser();
//
//////////////////////////////// For test mode
//        File firstSaveDir = new File("C:\\Users\\James\\Desktop\\temp");
//        saveFileChooser.setCurrentDirectory(firstSaveDir);
////////////////////////////////
//
//        saveFileChooser.setDialogTitle("Choose Save Directory");
//
//        // Show the file chooser dialog
//        int userSaveDir = saveFileChooser.showSaveDialog(null);
//
//        //Get file path from user
//        String selectedSaveFilePath = null;
//
//        // Check if a file was selected
//        if (userSaveDir == JFileChooser.APPROVE_OPTION) {
//            // Get the selected file
//            java.io.File file = saveFileChooser.getSelectedFile();
//            //System.out.println("user chosen file path and name: " + file + "\n");
//
//            if (file.getAbsolutePath().contains(".pdf")) {
//                selectedSaveFilePath = file.getAbsolutePath();
//            }else {
//                selectedSaveFilePath = file.getAbsolutePath() + ".pdf";
//            }
//
//        } else {
//            System.out.println("No file selected." + "\n");
//        }
//
//        //need to change save location and name
//        final_cs.save(selectedSaveFilePath);
//
//        System.out.println("Successfully saved to : " + selectedSaveFilePath + "\n");
//
//        final_cs.close();

        return selectedSaveFilePath;

    }
}
