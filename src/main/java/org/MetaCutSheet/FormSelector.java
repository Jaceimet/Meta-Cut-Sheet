package org.MetaCutSheet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormSelector {

    static String userSelection;

//        static File userTemplateFile;

//        static RandomAccessReadBufferedFile userTemplateStream;

//    static String devicePath = "src/main/resources/Template_Device.pdf";
//    static String fixturePath = "src/main/resources/Template_Fixture.pdf";

    public static String displayForm() {
        // Create a frame
        JFrame frame = new JFrame("Select Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 75);
        frame.setLocationRelativeTo(null);


        // Create buttons
        JButton deviceButton = new JButton("Device Form");
        JButton fixtureButton = new JButton("Fixture Form");

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.add(deviceButton);
        panel.add(fixtureButton);

        // Add action listeners to buttons
        deviceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userSelection = "Device Form";
                frame.dispose(); // Close the frame
            }
        });

        fixtureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userSelection = "Fixture Form";
                frame.dispose(); // Close the frame
            }
        });


        // Add the panel to the frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);

        // Wait for the frame to close
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Modify user return data
        if (userSelection == "Device Form") {
//                userSelection = "C:\\Computer Programming Projects\\Udemy\\Data Structures and Algorithms\\PDF_Express_2\\src\\main\\resources\\Template_Device.pdf";
            ///Functional absolute path
//                userSelection = "C:\\Computer Programming Projects\\GitHub\\Meta-Cut-Sheet\\Meta-Cut-Sheet\\src\\main\\resources\\Template_Device.pdf";
//            userSelection = "C:\\Computer Programming Projects\\GitHub\\Meta-Cut-Sheet\\Meta-Cut-Sheet\\Template_Device.pdf";
            /// non-functional
//            userSelection = "src/main/resources/Template_Device.pdf";
            userSelection = "Template_Device.pdf";
//            userSelection = "resources/Template_Device.pdf";



//                userTemplateFile = new File( "src/main/Templates/Template_Device.pdf");
//                userTemplateStream = FormSelector.class.getResourceAsStream("/src/main/Templates/Template_Device.pdf");
//                try {
//                    userTemplateStream = new RandomAccessReadBufferedFile("Template_Device.pdf");
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }

//                try (RandomAccessReadBufferedFile devicePdf = new RandomAccessReadBufferedFile(devicePath)) {
//                    if (devicePdf == null) {
//                        System.out.println("PDF file not found.");
////                        return inputStream;
//                    }}catch (Exception e) {
//                        e.printStackTrace();}

        } else {
//                userSelection = "C:\\Computer Programming Projects\\Udemy\\Data Structures and Algorithms\\PDF_Express_2\\src\\main\\resources\\Template_Fixture.pdf";
//                userSelection = "C:\\Computer Programming Projects\\GitHub\\Meta-Cut-Sheet\\Meta-Cut-Sheet\\src\\main\\resources\\Template_Fixture.pdf";
            userSelection = "src/main/resources/Template_Fixture.pdf";
//                userTemplateFile = new File( "src/main/Templates/Template_Fixture.pdf");
//                userTemplateStream = FormSelector.class.getResourceAsStream("/src/main/Templates/Template_Fixture.pdf");
//                try {
//                    userTemplateStream = new RandomAccessReadBufferedFile("Template_Fixture.pdf");
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }


            // Return the user's selection
            System.out.println("Template chosen: " + userSelection);
//            return userTemplateStream;
        }
        return userSelection;
    }


}
