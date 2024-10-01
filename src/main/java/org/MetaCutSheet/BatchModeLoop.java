package org.MetaCutSheet;

import javax.swing.*;

public class BatchModeLoop {

    static int reply;


    public static int displayForm() {
        reply = JOptionPane.showConfirmDialog(null,
                "Would you like to process more documents?", "title", JOptionPane.YES_NO_OPTION);
//        if (reply == JOptionPane.YES_OPTION) {
//            JOptionPane.showMessageDialog(null, "HELLO");
//        } else {
//            JOptionPane.showMessageDialog(null, "GOODBYE");
//            System.exit(0);
//        }
        System.out.println(reply);

        return reply;
    }

}
