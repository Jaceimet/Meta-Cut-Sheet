package org.MetaCutSheet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class InputFileChooser {

    static String userFile() {

        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();

        // Optionally, set the file chooser to select only files with a specific extension
        // need to figure out how to save last file path*********************

        File testFile = new File("C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets");
//        File testFile = new File("C:\\Users\\Public\\Desktop"); (.exe version)
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF & Image Files", "pdf", "jpg", "png");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(testFile);
        fileChooser.setDialogTitle("Choose File to Convert");

        // Show the file chooser dialog
        int result = fileChooser.showOpenDialog(null);

        //Get file path from user
        String selectedFilePath = null;
        // Check if a file was selected
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            java.io.File file = fileChooser.getSelectedFile();
            selectedFilePath = file.getAbsolutePath();

            System.out.println("\n" + "Selected input file: " + file.getAbsolutePath() + "\n");
        } else {
            System.out.println("No file selected.");
        }

        return selectedFilePath;
    }

}
