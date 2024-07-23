package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ImageScaler {


    //image dimensions
    private float[] getImageDimensions(PDImageXObject pdImage2) {

        float[] imageDimensions = new float[2];
        imageDimensions[0] = pdImage2.getWidth();
        System.out.println("image width: " + imageDimensions[0] + "\n");
        imageDimensions[1] = pdImage2.getHeight();
        System.out.println("image height: " + imageDimensions[1] + "\n");
        return imageDimensions;
    }

//    //scale fit image to media box
//    float scale = Math.min(mediaBoxWidth / imageWidth, mediaBoxHeight/ imageHeight);
//                System.out.println("Scale : " + scale + "\n");
//    float scaledWidth = imageWidth * scale;
//                System.out.println("scaledWidth "+scaledWidth+ "\n");
//    float scaledHeight = imageHeight * scale;
//                System.out.println("scaledHeight "+scaledHeight+ "\n");
//
//    // dynamically adjust x,y to input
//    float x = (mediaBoxWidth - scaledWidth)/2 + mediaBoxBottomLeftX;
//                System.out.println("x= "+ x + "\n");
//    float y = (mediaBoxHeight - scaledHeight)/ 2 + mediaBoxBottomLeftY;
//                System.out.println("y= "+ y + "\n");



}
