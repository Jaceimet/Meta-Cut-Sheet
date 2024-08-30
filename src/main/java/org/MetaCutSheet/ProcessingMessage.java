package org.MetaCutSheet;

import javax.swing.*;
import java.awt.*;

public class ProcessingMessage {

    public static void processingMessage() {
        JFrame frame = new JFrame();


            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 75);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            JLabel jlabel = new JLabel("Processing");
            jlabel.setFont(new Font("Verdana",1,20));
            panel.add(jlabel);
            frame.getContentPane().add(panel);
            frame.setVisible(true);

            try {
                Thread.sleep(3000);
                frame.dispose();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


    }
}
