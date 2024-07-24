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
        PDDocument existingDocument = null;

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

                PDImageXObject pdImage2 = PDImageXObject.createFromFile(inputUserFile, final_cs);

                // needs to be a separate class?

                ///////////////////////////////////
                //image dimensions
                float imageWidth = pdImage2.getWidth();
                System.out.println("image width: " + imageWidth+ "\n");
                float imageHeight = pdImage2.getHeight();
                System.out.println("image height: " + imageHeight+ "\n");

                //scale fit image to media box
                float scale = Math.min(mediaBoxWidth / imageWidth, mediaBoxHeight/ imageHeight);
                System.out.println("Scale : " + scale + "\n");
                float scaledWidth = imageWidth * scale;
                System.out.println("scaledWidth "+scaledWidth+ "\n");
                float scaledHeight = imageHeight * scale;
                System.out.println("scaledHeight "+scaledHeight+ "\n");

                // dynamically adjust x,y to input
                float x = (mediaBoxWidth - scaledWidth)/2 + mediaBoxBottomLeftX;
                System.out.println("x= "+ x + "\n");
                float y = (mediaBoxHeight - scaledHeight)/ 2 + mediaBoxBottomLeftY;
                System.out.println("y= "+ y + "\n");
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

                    contentStream.drawImage(pdImage2, mediaBoxBottomLeftX, y, scaledWidth, scaledHeight);

                    contentStream.close();
                }else {
                    System.out.println("image is in portrait orientation" + "\n");

                    // non-functional stretch to media box (optional implementation)
//                    contentStream.drawImage(pdImage2, 9.95f, 139f, scaledWidth2, scaledHeight2);

                    // perfect portrait centered
                    contentStream.drawImage(pdImage2, x, mediaBoxBottomLeftY, scaledWidth, scaledHeight);

                    contentStream.close();

                }


            } else if (type.equals("application/pdf")) {

                System.out.println("File is a " + type + " type\n");


        try {

//          PDF processing  https://www.youtube.com/watch?v=0Enx1YagHqw
            PDPage page = null;

            PDDocument userDocument = Loader.loadPDF(new File(inputUserFile));

            //convert user pdf to image
            PDFRenderer pdfRenderer = new PDFRenderer(userDocument);
//            BufferedImage pdfImage = pdfRenderer.renderImage(0, 1);


            int i;
            for (i = 0; i < userDocument.getNumberOfPages(); ++i) {
                final_cs.importPage(temp_page);
                page = userDocument.getPage(i);

            }

            System.out.println("final_Cs has " + i + " pages" + "\n");


            for (i = 0; i < userDocument.getNumberOfPages(); ++i) {
                //check for orientation
                PDRectangle pageSize = userDocument.getPage(i).getMediaBox();
                int degree = userDocument.getPage(i).getRotation();

                boolean isLandscape;
                isLandscape = (pageSize.getWidth() > pageSize.getHeight()) || (degree == 90) || (degree == 270);


                // Get page dimensions


//                assert page != null;
//                float pageWidth = page.getMediaBox().getWidth();


                try (PDPageContentStream contentStream = new PDPageContentStream(userDocument, final_cs.getPage(i),
                        PDPageContentStream.AppendMode.APPEND, false)) {
                    BufferedImage image = pdfRenderer.renderImageWithDPI(i, 300.0F);
                    PDImageXObject pdImage = LosslessFactory.createFromImage(userDocument, image);


//                    float imageX;
//                    float imageY;
//                    float imageWidth;
//                    float imageHeight;
//
//                    // Calculate target height for resized image to maintain aspect ratio
//                    float targetWidth = 592f;//612
//                    float aspectRatio = (float) pdfImage.getHeight() / pdfImage.getWidth();
//                    float targetHeight = aspectRatio * targetWidth;

                    /////// new calc
                    //image dimensions
                    float imageWidth = pdImage.getWidth();
                    System.out.println("image width: " + imageWidth+ "\n");
                    float imageHeight = pdImage.getHeight();
                    System.out.println("image height: " + imageHeight+ "\n");

                    //scale fit image to media box
                    float scale = Math.min(mediaBoxWidth / imageWidth, mediaBoxHeight/ imageHeight);
                    System.out.println("Scale : " + scale + "\n");
                    float scaledWidth = imageWidth * scale;
                    System.out.println("scaledWidth "+scaledWidth+ "\n");
                    float scaledHeight = imageHeight * scale;
                    System.out.println("scaledHeight "+scaledHeight+ "\n");

                    // dynamically adjust x,y to input
                    float x = (mediaBoxWidth - scaledWidth)/2 + mediaBoxBottomLeftX;
                    System.out.println("x= "+ x + "\n");
                    float y = (mediaBoxHeight - scaledHeight)/ 2 + mediaBoxBottomLeftY;
                    System.out.println("y= "+ y + "\n");
                    //////////////////////
                    if (isLandscape) {

                        System.out.println("Landscape" + "\n");
                        //old calc
//                        contentStream.drawImage(pdImage, 9.95f, 275f, targetWidth, targetHeight);
                        //new calc
                        contentStream.drawImage(pdImage, mediaBoxBottomLeftX, y, scaledWidth, scaledHeight);

                    } else {
                        // For portrait mode, keep the existing coordinates and dimensions
                        System.out.println("Protrait" + "\n");

                        //old calc
//                        imageX = (float) (0.5 * (pageWidth - 648.0 / (double) pdImage.getHeight() * (double) pdImage.getWidth()));
//                        imageY = 139.0F;//144.0F
//                        imageWidth = (float) (648.0 / (double) pdImage.getHeight() * (double) pdImage.getWidth());
//                        imageHeight = 638.0F;
//                        contentStream.drawImage(pdImage, imageX, imageY, imageWidth, imageHeight);

                        //new calc
                        contentStream.drawImage(pdImage, x, mediaBoxBottomLeftY, scaledWidth, scaledHeight);

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


        //PDF processing  https://www.youtube.com/watch?v=0Enx1YagHqw
//        try {
//
//            PDDocument existingDocument = Loader.loadPDF(new File(template));
//            PDDocument userDocument = null;
//
//
//            //convert user pdf to image
//            PDFRenderer pdfRenderer = new PDFRenderer(userDocument);
//            BufferedImage pdfImage = pdfRenderer.renderImage(0, 1);
//
//            // Calculate target height for resized image to maintain aspect ratio
//            float targetWidth = 592f;//612
//            float aspectRatio = (float) pdfImage.getHeight() / pdfImage.getWidth();
//            float targetHeight = aspectRatio * targetWidth;
//
//            // page measurements
//            // Width constraint in inches (8.23 inches)790.1 (743 inside the box)
//            // height constraint in inches (8.9in)857.3 (639 inside the box)
//            // float maxHeight = 854.4f;
//
//            PDPage temp_page = existingDocument.getPage(0);
//            PDPage page = null;
//
//            int i;
//            for (i = 0; i < userDocument.getNumberOfPages(); ++i) {
//                final_cs.importPage(temp_page);
//                page = userDocument.getPage(i);
//
//            }
//
//            System.out.println("final_Cs has " + i + " pages" + "\n");
//
//
//            for (i = 0; i < userDocument.getNumberOfPages(); ++i) {
//                //check for orientation
//                PDRectangle pageSize = userDocument.getPage(i).getMediaBox();
//                int degree = userDocument.getPage(i).getRotation();
//                boolean isLandscape;
//
//                if ((pageSize.getWidth() > pageSize.getHeight()) || (degree == 90) || (degree == 270)) {
//                    isLandscape = true;
//                } else {
//                    isLandscape = false;
//                }
//
//
//                // Get page dimensions
//                assert page != null;
//                float pageWidth = page.getMediaBox().getWidth();
//
//
//                try (PDPageContentStream contentStream = new PDPageContentStream(userDocument, final_cs.getPage(i),
//                        PDPageContentStream.AppendMode.APPEND, false)) {
//                    BufferedImage image = pdfRenderer.renderImageWithDPI(i, 300.0F);
//                    PDImageXObject pdImage = LosslessFactory.createFromImage(userDocument, image);
//
//
//                    float imageX;
//                    float imageY;
//                    float imageWidth;
//                    float imageHeight;
//
//
//                    if (isLandscape) {
//
//                        System.out.println("Landscape" + "\n");
//
//                        contentStream.drawImage(pdImage, 9.95f, 275f, targetWidth, targetHeight);
//
//                    } else {
//                        // For portrait mode, keep the existing coordinates and dimensions
//                        System.out.println("Protrait" + "\n");
//
//                        imageX = (float) (0.5 * (pageWidth - 648.0 / (double) pdImage.getHeight() * (double) pdImage.getWidth()));
//                        imageY = 144.0F;
//                        imageWidth = (float) (648.0 / (double) pdImage.getHeight() * (double) pdImage.getWidth());
//                        imageHeight = 638.0F;
//                        contentStream.drawImage(pdImage, imageX, imageY, imageWidth, imageHeight);
//
//                    }
//
//                }
//
//            }
//
//        } catch (IOException e) {
//            System.err.println("Error processing PDFs: " + e.getMessage());
//        }

        return final_cs;

    }



}
