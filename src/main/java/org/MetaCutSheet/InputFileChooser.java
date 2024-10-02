package org.MetaCutSheet;

import jnafilechooser.api.JnaFileChooser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class InputFileChooser {



//    Customer requests easier access to onedrive, need to save file preference for future use?
//    https://www.youtube.com/watch?v=6y5vzp2qYik  (posible solution with mods)
//    https://www.youtube.com/watch?v=1GeVbi1uj_8

    static String userPDFFileInput() {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e){

        }

//         https://github.com/steos/jnafilechooser/tree/master

        Preferences prefs = Preferences.userRoot().node("User File Path");

        String selectedFilePath = null;

        String testFile2;

        JnaFileChooser jnaFileChooser = new JnaFileChooser();
        jnaFileChooser.addFilter("PDF Files", "pdf");
        jnaFileChooser.setTitle("Choose PDF File to Convert");
        if (prefs.get("User File Path", "root") == null){
            testFile2 = "C:\\\\Users\\\\James\\\\Desktop";
        } else {
            testFile2 = prefs.get("User File Path", "root");
        }
//        String testFile2 = "C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets";
        jnaFileChooser.setCurrentDirectory(testFile2);

        if (jnaFileChooser.showOpenDialog(null)) {
            File file = jnaFileChooser.getSelectedFile();
            selectedFilePath = file.getAbsolutePath();

            System.out.println("\n" + "Selected input file: " + selectedFilePath + "\n");
        } else {
            System.out.println("No file selected.");
        }

        prefs.put("User File Path", selectedFilePath );

        return selectedFilePath;
    }

    public static File[] multipleImageFileUserInput() throws IOException {

        Preferences prefs = Preferences.userRoot().node("User File Path");

        String selectedFilePath = null;

        String testFile2;

        JnaFileChooser jnaFileChooser = new JnaFileChooser();
        jnaFileChooser.addFilter("Image Files", "jpg", "png");
        jnaFileChooser.setTitle("Choose Image File(s) to Convert");
        jnaFileChooser.setMultiSelectionEnabled(true);
        if (prefs.get("User File Path", "root") == null){
            testFile2 = "C:\\\\Users\\\\James\\\\Desktop";
        } else {
            testFile2 = prefs.get("User File Path", "root");
        }

//        String testFile2 = "C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets";
        jnaFileChooser.setCurrentDirectory(testFile2);

        if (jnaFileChooser.showOpenDialog(null)) {

            ///////
//            Need to save previous open location
//            https://www.youtube.com/watch?v=Uxe7ZkX_Msw
            /////

            File file = jnaFileChooser.getSelectedFile();
            selectedFilePath = file.getAbsolutePath();

            System.out.println("\n" + "Selected input file: " + selectedFilePath + "\n");
        } else {
            System.out.println("No file selected.");
        }

        prefs.put("User File Path", selectedFilePath );

        return jnaFileChooser.getSelectedFiles();

    }


}


