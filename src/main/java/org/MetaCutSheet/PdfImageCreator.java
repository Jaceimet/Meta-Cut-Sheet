package org.MetaCutSheet;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class PdfImageCreator {
    public enum Scaling {
        /** Print the image at 100% scale. */
        ACTUAL_SIZE,

        /** Shrink the image to fit the page, if needed. */
        SHRINK_TO_FIT,

        /** Stretch the image to fill the page, if needed. */
        STRETCH_TO_FIT,

        /** Stretch or shrink the image to fill the page, as needed. */
        SCALE_TO_FIT
    }


    public static PDDocument pdfGenerator(String template, String inputUserFile) {

        PDDocument final_cs = new PDDocument();

        String type;


        //image handling, needs exception handling -- semi-functional but image is warped
        try {
            type = Files.probeContentType(Paths.get(inputUserFile));

            if (type.equals("image/png") || type.equals("image/jpeg")) {

                System.out.println("File is an "+ type +" type\n");

                PDDocument existingDocument = Loader.loadPDF(new File(template));

                PDPage temp_page = existingDocument.getPage(0);

                final_cs.addPage(temp_page);


                PDImageXObject pdImage2 = PDImageXObject.createFromFile(inputUserFile, final_cs);


//                float pageWidth = temp_page.getArtBox().getWidth();
//                float pageHeight = temp_page.getArtBox().getHeight();
//                float pageWidth = 790.1f;
//                float pageHeight = 638.0F;
                float pageWidth = 638.0F;
                float pageHeight = 790.1f;
                float imageWidth = pdImage2.getWidth();
                float imageHeight = pdImage2.getHeight();
                float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);
                System.out.println("Scale 1: " + scale);

                float scaledWidth = imageWidth * scale;
                System.out.println(scaledWidth);
                float scaledHeight = imageHeight * scale;
                System.out.println(scaledHeight);

                float x = (pageWidth - scaledWidth)/2;
                float y = (pageHeight - scaledHeight)/ 2;

                //portrait? long side divided by the short side
                float aspectRatio = (float) pdImage2.getHeight() / pdImage2.getWidth();
                float targetWidth = 592f;
                float targetHeight = aspectRatio * targetWidth;


                PDPageContentStream contentStream = new PDPageContentStream(final_cs, temp_page,
                        PDPageContentStream.AppendMode.APPEND, false);

                boolean isLandscape = pdImage2.getHeight() < pdImage2.getWidth();


                if (isLandscape) {
                    System.out.println("image is in landscape orientation");
                    //possible landscape
//                    float aspectRatio2 = (float) pdImage2.getWidth() / pdImage2.getHeight();
//                    float targetHeight2 = aspectRatio2 * targetWidth;

                    contentStream.drawImage(pdImage2, 9.95f, 275f, targetWidth, targetHeight);
                    contentStream.close();
                }else {
                    System.out.println("image is in portrait orientation");
//                    float imageX = (float) (0.5 * (pdImage2.getWidth() - 648.0 / (double) pdImage2.getHeight() * (double) pdImage2.getWidth()));
//                    float imageY = 144.0F;
//                    float imageWidth2 = (float) (648.0 / (double) pdImage2.getHeight() * (double) pdImage2.getWidth());
//                    float imageHeight2 = 638.0F;
//                    contentStream.drawImage(pdImage2, imageX, imageY, imageWidth2, imageHeight2);
//                    float aspectRatio2 = (float) pdImage2.getHeight() /pdImage2.getWidth();
//                    float targetHeight2 = aspectRatio2 * targetWidth;

                    contentStream.drawImage(pdImage2, 9.95f, 121f, targetWidth, targetHeight);
//                    contentStream.drawImage(pdImage2, 10f, 125f, targetWidth, 660f);
//                    contentStream.drawImage(pdImage2, x, y, targetWidth, 660f);

                    contentStream.close();
                    //856.3f
                }


            } else if (type.equals("application/pdf")){

                System.out.println("File is a " + type + " type\n");

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
//            // Width constraint in inches (8.23 inches)790.1
//            // height constraint in inches (8.9in)857.3
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
