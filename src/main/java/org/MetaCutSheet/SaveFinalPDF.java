package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SaveFinalPDF {

    public static String saveFinalPDF(PDDocument final_cs) throws IOException {

        // cleanup
        // need to figure out how to save last file path*********************
        //String selectedSaveFilePath = null;
        JFileChooser saveFileChooser = new JFileChooser();

////////////////////////////// For test mode
        File firstSaveDir = new File("C:\\Users\\James\\Desktop\\temp");
        saveFileChooser.setCurrentDirectory(firstSaveDir);
//////////////////////////////

        saveFileChooser.setDialogTitle("Choose Save Directory");

        // Show the file chooser dialog
        int userSaveDir = saveFileChooser.showSaveDialog(null);

        //Get file path from user
        String selectedSaveFilePath = null;

        // Check if a file was selected
        if (userSaveDir == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            java.io.File file = saveFileChooser.getSelectedFile();
            //System.out.println("user chosen file path and name: " + file + "\n");

            if (file.getAbsolutePath().contains(".pdf")) {
                selectedSaveFilePath = file.getAbsolutePath();
            }else {
                selectedSaveFilePath = file.getAbsolutePath() + ".pdf";
            }

        } else {
            System.out.println("No file selected." + "\n");
        }

        //need to change save location and name
        final_cs.save(selectedSaveFilePath);

        System.out.println("Successfully saved to : " + selectedSaveFilePath + "\n");

        final_cs.close();

        return selectedSaveFilePath;

    }
}
