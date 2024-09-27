package org.MetaCutSheet;

import jnafilechooser.api.JnaFileChooser;

import java.io.File;
import java.io.IOException;

public class InputFileChooser {

//    Customer requests easier access to onedrive, need to save file preference for future use?
//    https://www.youtube.com/watch?v=6y5vzp2qYik  (posible solution with mods)
//    https://www.youtube.com/watch?v=1GeVbi1uj_8

    static String userPDFFileInput() {

//         repository added possible solution to customer request. has issue with opening file 3 deep
        String selectedFilePath = null;

        JnaFileChooser jnaFileChooser = new JnaFileChooser();
        jnaFileChooser.addFilter("PDF Files", "pdf");
        jnaFileChooser.setTitle("Choose PDF File to Convert");
        String testFile2 = "C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets";
        jnaFileChooser.setCurrentDirectory(testFile2);

        if (jnaFileChooser.showOpenDialog(null)) {
            File file = jnaFileChooser.getSelectedFile();
            selectedFilePath = file.getAbsolutePath();

            System.out.println("\n" + "Selected input file: " + selectedFilePath + "\n");
        } else {
            System.out.println("No file selected.");
        }

        return selectedFilePath;
    }

    public static File[] multipleImageFileUserInput() throws IOException {


        String selectedFilePath;

        JnaFileChooser jnaFileChooser = new JnaFileChooser();
        jnaFileChooser.addFilter("Image Files", "jpg", "png");
        jnaFileChooser.setTitle("Choose Image File(s) to Convert");
        jnaFileChooser.setMultiSelectionEnabled(true);
        String testFile2 = "C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets";
        jnaFileChooser.setCurrentDirectory(testFile2);

        if (jnaFileChooser.showOpenDialog(null)) {
            File file = jnaFileChooser.getSelectedFile();
            selectedFilePath = file.getAbsolutePath();

            System.out.println("\n" + "Selected input file: " + selectedFilePath + "\n");
        } else {
            System.out.println("No file selected.");
        }

        return jnaFileChooser.getSelectedFiles();



//        try {
//            for (UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // need to add muti-file option for images
//
//        JFrame frame = new JFrame();
//        JFileChooser chooser = new JFileChooser();
//        chooser.setDialogTitle("Choose file(s) to Convert");
//
//        File testFile = new File("C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets");
//        chooser.setCurrentDirectory(testFile);
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png");
//        chooser.setFileFilter(filter);
//        chooser.setMultiSelectionEnabled(true);
//        chooser.showOpenDialog(frame);
//
//
//        //////////////////////
//        return chooser.getSelectedFiles();
    }


}


