package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ImageScalar {

     float mediaBoxWidth;
     float mediaBoxHeight;
     float mediaBoxBottomLeftX;
     float mediaBoxBottomLeftY;
     float imageWidth;
     float imageHeight;

     float scale;

    public ImageScalar(float mediaBoxWidth, float mediaBoxHeight, float mediaBoxBottomLeftX,
                       float mediaBoxBottomLeftY, PDImageXObject pdImage) {
        this.mediaBoxWidth = mediaBoxWidth;
        this.mediaBoxHeight = mediaBoxHeight;
        this.mediaBoxBottomLeftX = mediaBoxBottomLeftX;
        this.mediaBoxBottomLeftY = mediaBoxBottomLeftY;

        // Get image dimensions from pdImage2
        this.imageWidth = pdImage.getWidth();
        this.imageHeight = pdImage.getHeight();

        // Get scale of image
        this.scale = Math.min(mediaBoxWidth / imageWidth, mediaBoxHeight / imageHeight);
    }

    public float getScaledWidth() {
        // Scale fit image to media box
        return imageWidth * scale;
    }

    public float getScaledHeight() {
        // Scale fit image to media box
        return imageHeight * scale;
    }

    public float getAdjustedX() {
        // Scale fit image to media box
        float scaledWidth = imageWidth * scale;

        // Dynamically adjust x to center the image in the media box
        return (mediaBoxWidth - scaledWidth) / 2 + mediaBoxBottomLeftX;
    }

    public float getAdjustedY() {
        // Scale fit image to media box
        float scaledHeight = imageHeight * scale;

        // Dynamically adjust y to center the image in the media box
        return (mediaBoxHeight - scaledHeight) / 2 + mediaBoxBottomLeftY;
    }


}
