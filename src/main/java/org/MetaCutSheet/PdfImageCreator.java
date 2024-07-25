package org.MetaCutSheet;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class PdfImageCreator {

    public static PDDocument pdfGenerator(String template, String inputUserFile) {

        PDDocument final_cs = new PDDocument();
        String type = null;
        PDPage temp_page = null;
        PDDocument existingDocument;
        PDImageXObject pdImage2;
        float scaledWidth = 0;
        float scaledHeight = 0;
        float adjustedX = 0;
        float adjustedY = 0;

        //media box dimensions
        float mediaBoxWidth = 593F; //638.0F
        float mediaBoxHeight = 642f; //644f, 641.48f, 638.6f
        float mediaBoxBottomLeftX = 9.95f;
        float mediaBoxBottomLeftY = 139f;

        // Load template
        try {
            existingDocument = Loader.loadPDF(new File(template));
            temp_page = existingDocument.getPage(0);

            //find template measurements
            PDRectangle pageSize = existingDocument.getPage(0).getMediaBox();
            float existingPageWidth = pageSize.getWidth();
            System.out.println("existingPageWidth "+existingPageWidth+ "\n");
            float existingPageHeight = pageSize.getHeight();
            System.out.println("existingPageHeight "+existingPageHeight+ "\n");

            //(W.593, H.644 inside the box (Aspect ratio = 1.086003))
            // box area = 381,892
            // aspect = 0.7071 (portrait? long side divided by the short side)
            // portrait aspect ratio = long side divided by the short side
        }catch (IOException e) {
        System.err.println("Error processing template: " + e.getMessage());
        }

        try{
            type = Files.probeContentType(Paths.get(inputUserFile));
        }catch (IOException e) {
            System.err.println("Error processing inputUserFile type: " + e.getMessage());
        }

        try {

            //image handling, needs refining

            if (type != null && type.equals("image/png") || type.equals("image/jpeg")) {

                System.out.println("File is an "+ type +" type\n");

                final_cs.addPage(temp_page);

                pdImage2 = PDImageXObject.createFromFile(inputUserFile, final_cs);

                imageProcessor(mediaBoxWidth, mediaBoxHeight, mediaBoxBottomLeftX, mediaBoxBottomLeftY,
                pdImage2);

                // needs to be a separate class?

                ///////////////////////////////////
//                //image dimensions
//                float imageWidth = pdImage2.getWidth();
//                System.out.println("image width: " + imageWidth+ "\n");
//                float imageHeight = pdImage2.getHeight();
//                System.out.println("image height: " + imageHeight+ "\n");
//
//                //scale fit image to media box
//                float scale = Math.min(mediaBoxWidth / imageWidth, mediaBoxHeight/ imageHeight);
//                System.out.println("Scale : " + scale + "\n");
//                float scaledWidth = imageWidth * scale;
//                System.out.println("scaledWidth "+scaledWidth+ "\n");
//                float scaledHeight = imageHeight * scale;
//                System.out.println("scaledHeight "+scaledHeight+ "\n");
//
//                // dynamically adjust x,y to input
//                float adjustedX = (mediaBoxWidth - scaledWidth)/2 + mediaBoxBottomLeftX;
//                System.out.println("x= "+ adjustedX + "\n");
//                float adjustedY = (mediaBoxHeight - scaledHeight)/ 2 + mediaBoxBottomLeftY;
//                System.out.println("y= "+ adjustedY + "\n");

                ////////////////////////////////////////////////////////

                //functional call to imagescalar
//                ImageScalar imageScalar = new ImageScalar(mediaBoxWidth, mediaBoxHeight, mediaBoxBottomLeftX,
//                        mediaBoxBottomLeftY, pdImage2);
//                float scaledWidth = imageScalar.getScaledWidth();
//                float scaledHeight = imageScalar.getScaledHeight();
//                float adjustedX = imageScalar.getAdjustedX();
//                float adjustedY = imageScalar.getAdjustedY();
                ////////////////////////////////////////////////////////



                // non-functional scale stretch to media box (optional calculation)
//                float scaledWidth2 = imageWidth / scaledWidth;
//                System.out.println("scaledWidth2 "+scaledWidth2);
//                float scaledHeight2 = imageHeight / scaledHeight;
//                System.out.println("scaledHeight2 "+scaledHeight2);


                PDPageContentStream contentStream = new PDPageContentStream(final_cs, temp_page,
                        PDPageContentStream.AppendMode.APPEND, false);

                boolean isLandscape = pdImage2.getHeight() < pdImage2.getWidth();


                if (isLandscape) {
                    System.out.println("image is in landscape orientation" + "\n");

                    contentStream.drawImage(pdImage2, mediaBoxBottomLeftX, adjustedY, scaledWidth, scaledHeight);

                    contentStream.close();
                }else {
                    System.out.println("image is in portrait orientation" + "\n");

                    // non-functional stretch to media box (optional implementation)
//                    contentStream.drawImage(pdImage2, 9.95f, 139f, scaledWidth2, scaledHeight2);

                    imageProcessor(mediaBoxWidth, mediaBoxHeight, mediaBoxBottomLeftX, mediaBoxBottomLeftY,
                            pdImage2);
                    System.out.println("scaledWidth: "+ scaledWidth);
                    // perfect portrait centered
                    contentStream.drawImage(pdImage2, adjustedX, mediaBoxBottomLeftY, scaledWidth, scaledHeight);

                    contentStream.close();

                }


            } else if (type.equals("application/pdf")) {

                System.out.println("File is a " + type + " type\n");


        try {

         //PDF processing

            // Load user file
            PDDocument userDocument = Loader.loadPDF(new File(inputUserFile));
            //convert user pdf to image
            PDFRenderer pdfRenderer = new PDFRenderer(userDocument);


            int i;
            for (i = 0; i < userDocument.getNumberOfPages(); ++i) {
                final_cs.importPage(temp_page);

            }

            System.out.println("final_Cs has " + i + " pages" + "\n");


            for (i = 0; i < userDocument.getNumberOfPages(); ++i) {
                //check for orientation
                PDRectangle pageSize = userDocument.getPage(i).getMediaBox();
                int degree = userDocument.getPage(i).getRotation();
                boolean isLandscape;
                isLandscape = (pageSize.getWidth() > pageSize.getHeight()) || (degree == 90) || (degree == 270);


                try (PDPageContentStream contentStream = new PDPageContentStream(userDocument, final_cs.getPage(i),
                        PDPageContentStream.AppendMode.APPEND, false)) {
                    BufferedImage image = pdfRenderer.renderImageWithDPI(i, 300.0F);
                    PDImageXObject pdImage = LosslessFactory.createFromImage(userDocument, image);

                    ////////////////////
//                    ImageScalar imageScalar = new ImageScalar(mediaBoxWidth, mediaBoxHeight, mediaBoxBottomLeftX,
//                            mediaBoxBottomLeftY, pdImage);
//                    float scaledWidth = imageScalar.getScaledWidth();
//                    float scaledHeight = imageScalar.getScaledHeight();
//                    float adjustedX = imageScalar.getAdjustedX();
//                    float adjustedY = imageScalar.getAdjustedY();


                    ///////////////////

                    /////// new calc
                    //image dimensions
//                    float imageWidth = pdImage.getWidth();
//                    System.out.println("image width: " + imageWidth+ "\n");
//                    float imageHeight = pdImage.getHeight();
//                    System.out.println("image height: " + imageHeight+ "\n");
//
//                    //scale fit image to media box
//                    float scale = Math.min(mediaBoxWidth / imageWidth, mediaBoxHeight/ imageHeight);
//                    System.out.println("Scale : " + scale + "\n");
//                    float scaledWidth = imageWidth * scale;
//                    System.out.println("scaledWidth "+scaledWidth+ "\n");
//                    float scaledHeight = imageHeight * scale;
//                    System.out.println("scaledHeight "+scaledHeight+ "\n");
//
//                    // dynamically adjust x,y to input
//                    float adjustedX = (mediaBoxWidth - scaledWidth)/2 + mediaBoxBottomLeftX;
//                    System.out.println("x= "+ adjustedX + "\n");
//                    float adjustedY = (mediaBoxHeight - scaledHeight)/ 2 + mediaBoxBottomLeftY;
//                    System.out.println("y= "+ adjustedY + "\n");
                    //////////////////////

                    if (isLandscape) {

                        System.out.println("Landscape" + "\n");

                        contentStream.drawImage(pdImage, mediaBoxBottomLeftX, adjustedY, scaledWidth, scaledHeight);

                    } else {

                        System.out.println("Protrait" + "\n");

                        contentStream.drawImage(pdImage, adjustedX, mediaBoxBottomLeftY, scaledWidth, scaledHeight);

                    }

                }

            }

        } catch (IOException e) {
            System.err.println("Error processing PDFs: " + e.getMessage());
        }


            }

        }catch (IOException e) {
            System.err.println("Not a supported file type: " + e.getMessage());
        }

        return final_cs;

    }
    float scaledWidth;
    float scaledHeight;
    public void imageProcessor(float mediaBoxWidth, float mediaBoxHeight, float mediaBoxBottomLeftX, float mediaBoxBottomLeftY,
                                      PDImageXObject pdImage2){

        float imageWidth = pdImage2.getWidth();
        System.out.println("image width: " + imageWidth+ "\n");
        float imageHeight = pdImage2.getHeight();
        System.out.println("image height: " + imageHeight+ "\n");

        //scale fit image to media box
        float scale = Math.min(mediaBoxWidth / imageWidth, mediaBoxHeight/ imageHeight);
        System.out.println("Scale : " + scale + "\n");
        scaledWidth = imageWidth * scale;
        System.out.println("scaledWidth "+scaledWidth+ "\n");
        scaledHeight = imageHeight * scale;
        System.out.println("scaledHeight "+scaledHeight+ "\n");

        // dynamically adjust x,y to input
        float adjustedX = (mediaBoxWidth - scaledWidth)/2 + mediaBoxBottomLeftX;
        System.out.println("x= "+ adjustedX + "\n");
        float adjustedY = (mediaBoxHeight - scaledHeight)/ 2 + mediaBoxBottomLeftY;
        System.out.println("y= "+ adjustedY + "\n");




    }


}
