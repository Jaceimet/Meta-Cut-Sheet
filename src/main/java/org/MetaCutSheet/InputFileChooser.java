package org.MetaCutSheet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class InputFileChooser {




    static String userFile() {

        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();

        // Optionally, set the file chooser to select only files with a specific extension
        // need to figure out how to save last file path*********************

///////////////////////////// For Test Mode
        File testFile = new File("C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets");
/////////////////////////////

        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF & Image Files", "pdf", "jpg", "png");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Choose File to Convert");

        ///////////////////////////// For Test Mode
        fileChooser.setCurrentDirectory(testFile);
        ///////////////////////////////

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



    public static File[] multipleFileUserInput() throws IOException {

        // need to add muti-file option for images

        JFrame frame = new JFrame();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose multiple files to Convert");

        File testFile = new File("C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets");
        chooser.setCurrentDirectory(testFile);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png");
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(true);
        chooser.showOpenDialog(frame);



        File[] files = chooser.getSelectedFiles();


            //////////////////////
        return files;
        }


    }


