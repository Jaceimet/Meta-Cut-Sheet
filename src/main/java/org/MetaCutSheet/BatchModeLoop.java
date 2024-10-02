package org.MetaCutSheet;

import javax.swing.*;

public class BatchModeLoop {

    static int reply;


    public static int displayForm() {
        reply = JOptionPane.showConfirmDialog(null,
                "Would you like to process more documents?", "Batch Loop", JOptionPane.YES_NO_OPTION);

        System.out.println(reply);

        return reply;
    }

}
