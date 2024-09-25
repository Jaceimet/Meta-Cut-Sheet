package org.MetaCutSheet;

import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class InputFileChooser {

//    Customer requests easier access to onedrive, need to save file preference for future use?
//    https://www.youtube.com/watch?v=6y5vzp2qYik  (posible solution with mods)
//    https://www.youtube.com/watch?v=1GeVbi1uj_8

    static String userPDFFileInput() {

        /// failure due to multi-thread attept or can not return result in lambda form...

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().

                addAll(
                        new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(null);

        final String selectedFilePath = file.getAbsolutePath();

        return selectedFilePath;


    }



//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    //attempt to use javafx filechooser (failed)
//                    FileChooser fileChooser = new FileChooser();
//                    fileChooser.setTitle("Open Resource File");
//                    fileChooser.getExtensionFilters().
//
//                            addAll(
//                                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
//                                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
////                    new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
//                                    new FileChooser.ExtensionFilter("All Files", "*.*"));
//                    File selectedFile = fileChooser.showOpenDialog(null);
////            if (selectedFile != null) {
//////                mainStage.display(selectedFile);
////            }
//                }
//            });
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        // repository added possible solution to customer request. has issue with opening file 3 deep
//        JnaFileChooser fc = new JnaFileChooser();
//        fc.addFilter("PDF Files", "pdf");
//        String testFile2 = "C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets";
//        fc.setCurrentDirectory(testFile2);
//
//        if (fc.showOpenDialog(null)) {
//            File f = fc.getSelectedFile();
//            // do something with f
//        }


        // using jchooser/swing styling
//        try {
//            for (UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


//        // Create a file chooser
//        JFileChooser jFileChooser = new JFileChooser();
//
//        // Optionally, set the file chooser to select only files with a specific extension
//        // need to figure out how to save last file path*********************
//
/////////////////////////////// For Test Mode
//        File testFile = new File("C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets");
///////////////////////////////
//
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
//        jFileChooser.setFileFilter(filter);
//        jFileChooser.setDialogTitle("Choose File to Convert");
//
//        ///////////////////////////// For Test Mode
//        jFileChooser.setCurrentDirectory(testFile);
//        ///////////////////////////////
//
//        // Show the file chooser dialog
//        int result = jFileChooser.showOpenDialog(null);
//
//        //Get file path from user
//        String selectedFilePath = null;
//        // Check if a file was selected
//        if (result == JFileChooser.APPROVE_OPTION) {
//
//            // Get the selected file
//            File file = jFileChooser.getSelectedFile();
//            selectedFilePath = file.getAbsolutePath();
//
//            System.out.println("\n" + "Selected input file: " + file.getAbsolutePath() + "\n");
//        } else {
//            System.out.println("No file selected.");
//        }
//
//        return selectedFilePath;
//        return selectedFilePath;
//    }



    public static File[] multipleImageFileUserInput() throws IOException {

        try {
            for (UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // need to add muti-file option for images

        JFrame frame = new JFrame();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose file(s) to Convert");

        File testFile = new File("C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets");
        chooser.setCurrentDirectory(testFile);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png");
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(true);
        chooser.showOpenDialog(frame);


        //////////////////////
        return chooser.getSelectedFiles();
        }


    }


