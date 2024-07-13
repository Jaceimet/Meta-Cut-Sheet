package org.MetaCutSheet;

import java.awt.*;

public class ResizeImage {

    //(W.593, H.644 inside the box (Aspect ratio = 1.086003))
    //0.7071398 * 484,704

    //w=585.4515
    //h=827.9148


    //image aspect ratio: 1.4141475
    Dimension imgSize = new Dimension(500, 100);
    Dimension boundary = new Dimension(593, 644);

//    W.593, H.644 inside the box
public double getScaleFactor(int iMasterSize, int iTargetSize) {

    double dScale = 1;
    if (iMasterSize > iTargetSize) {

        dScale = (double) iTargetSize / (double) iMasterSize;

    } else {

        dScale = (double) iTargetSize / (double) iMasterSize;

    }

    return dScale;

}

    public double getScaleFactorToFit(Dimension original, Dimension toFit) {

        double dScale = 1d;

        if (original != null && toFit != null) {

            double dScaleWidth = getScaleFactor(original.width, toFit.width);
            double dScaleHeight = getScaleFactor(original.height, toFit.height);

            dScale = Math.min(dScaleHeight, dScaleWidth);

        }

        return dScale;

    }
//
//    double factor getScaledFactorToFit(new Dimension(image.getWidth(), image.getHeight()), getSize());
//
//    int scaledWidth = image.getWidth() * scale;
//    int scaledHeight *= image.getWidth() * scale;


}
