package org.MetaCutSheet;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenFinalPDF {

    // Open PDF after creation (successful)
    public static void openPDF(String filePath) {
        if (Desktop.isDesktopSupported()) {
            try {
                File pdfFile = new File(filePath);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                    System.out.println("PDF opened successfully." + "\n");
                } else {
                    System.out.println("PDF file does not exist." + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to open PDF." + "\n");
            }
        } else {
            System.out.println("Desktop is not supported. Cannot open PDF." + "\n");
        }
    }


}
