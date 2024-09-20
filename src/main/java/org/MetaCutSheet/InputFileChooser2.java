package org.MetaCutSheet;

import jnafilechooser.api.JnaFileChooser;

import java.io.File;

public class InputFileChooser2 {

    static String userPDFFileInput() {

        // possible solution to customer request. has issue with opening file 3 deep
        JnaFileChooser fc = new JnaFileChooser();
        fc.addFilter("PDF Files", "pdf");
        String testFile2 = "C:\\Computer Programming Projects\\Olivers PDF project\\sample cut sheets";
        fc.setCurrentDirectory(testFile2);

        if (fc.showOpenDialog(null)) {
            File f = fc.getSelectedFile();
            // do something with f
        }

        return null;
    }
}
