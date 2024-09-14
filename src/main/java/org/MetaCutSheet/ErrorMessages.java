package org.MetaCutSheet;

import javax.swing.*;

public class ErrorMessages {

    public static void templateCrash(){
        JFrame frame = new JFrame("Error");

        // Set the default operation for closing the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display an error message dialog
        JOptionPane.showMessageDialog(
                frame,
                "A template error occurred and the project will be shutting down.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

        // Optionally, you can include code to perform any cleanup operations here

        // Exit the application
        System.exit(1);

    }

    public static void imageCrash(){
        JFrame frame = new JFrame("Error");

        // Set the default operation for closing the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display an error message dialog
        JOptionPane.showMessageDialog(
                frame,
                "A image error occurred and the project will be shutting down.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

        // Optionally, you can include code to perform any cleanup operations here

        // Exit the application
        System.exit(1);

    }

}
