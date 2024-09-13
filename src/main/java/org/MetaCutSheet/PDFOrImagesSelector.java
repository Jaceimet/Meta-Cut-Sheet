package org.MetaCutSheet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PDFOrImagesSelector {

    static String userSelection;


    public static String displayPDFOrImageForm() {
        // Create a frame
        JFrame frame = new JFrame("Choose to Process PDF or Image(s)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 75);
        frame.setLocationRelativeTo(null);


        // Create buttons
        JButton deviceButton = new JButton("PDF");
        JButton fixtureButton = new JButton("Image(s)");

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.add(deviceButton);
        panel.add(fixtureButton);

        // Add action listeners to buttons
        deviceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userSelection = "PDF";
                frame.dispose(); // Close the frame
            }
        });

        fixtureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userSelection = "Image(s)";
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
       return userSelection;
    }


}
