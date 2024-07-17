package org.MetaCutSheet;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
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

                //find template measurements
                PDRectangle pageSize = existingDocument.getPage(0).getMediaBox();
                float exPageWidth = pageSize.getWidth();
                System.out.println("exPageWidth "+exPageWidth);
                float exPageHeight = pageSize.getHeight();
                System.out.println("exPageHeight "+exPageHeight);

                //view box dimensions
                float mediaBoxWidth = 593F; //638.0F
                float mediaBoxHeight = 642f; //644f, 641.48f, 638.6f

                //image dimensions
                float imageWidth = pdImage2.getWidth();
                System.out.println("image width: " + imageWidth);
                float imageHeight = pdImage2.getHeight();
                System.out.println("image height: " + imageHeight);

                //scaled image dimensions
                float scaledWidth = imageWidth / mediaBoxWidth ;
                System.out.println("scaledWidth "+scaledWidth);
                float scaledHeight = imageHeight / mediaBoxHeight;
                System.out.println("scaledHeight "+scaledHeight);

                //scale image to view box
                float scale = Math.min(mediaBoxWidth / imageWidth, mediaBoxHeight/ imageHeight);
                System.out.println("Scale : " + scale);

                // functional scaling
                float scaledWidth3 = imageWidth * scale;
                System.out.println("scaledWidth3 "+scaledWidth3);
                float scaledHeight3 = imageHeight * scale;
                System.out.println("scaledHeight3 "+scaledHeight3);

                //  portrait stretch to view box (optional)
                float scaledWidth2 = imageWidth / scaledWidth;
                System.out.println("scaledWidth2 "+scaledWidth2);
                float scaledHeight2 = imageHeight / scaledHeight;
                System.out.println("scaledHeight2 "+scaledHeight2);

                // dynamically adjust x,y to input
                float x = (mediaBoxWidth - scaledWidth3)/2 + 9.95f;
                System.out.println("x= "+x);
                float y = (mediaBoxHeight - scaledHeight3)/ 2 + 139f;
                System.out.println("y= "+y);

                //(W.593, H.644 inside the box (Aspect ratio = 1.086003))
                // box area = 381,892
                // aspect = 0.7071 (portrait? long side divided by the short side)


            // functional but off center
//                float aspectRatio = (float) pdImage2.getHeight() / pdImage2.getWidth();
//                System.out.println("aspect ratio: " + aspectRatio);
//                float targetWidth = 465f;//540f
//                float targetHeight = aspectRatio * targetWidth;
//                //(W.593, H.644 inside the box (Aspect ratio = 1.086003))

                //portrait? long side divided by the short side

            // functional stretch to view box
                float aspectRatio = (float) pdImage2.getHeight() / pdImage2.getWidth();
                System.out.println("aspect ratio: " + aspectRatio);
                float targetWidth = 455f;//540f, 465f
                float targetHeight = aspectRatio * targetWidth;

//              (W.593, H.644 inside the box (Aspect ratio = 1.086003, area )
//               image aspect ratio: 1.4141475
//               view box area = 381,892

                float aspectRatioImageHeight = mediaBoxHeight /mediaBoxWidth * imageWidth;
                System.out.println("aspectRatioImageHeight: " + aspectRatioImageHeight);


                PDPageContentStream contentStream = new PDPageContentStream(final_cs, temp_page,
                        PDPageContentStream.AppendMode.APPEND, false);

                boolean isLandscape = pdImage2.getHeight() < pdImage2.getWidth();


                if (isLandscape) {
                    System.out.println("image is in landscape orientation");

                    contentStream.drawImage(pdImage2, 9.95f, y, scaledWidth3, scaledHeight3);

                    contentStream.close();
                }else {
                    System.out.println("image is in portrait orientation");

                    // functional but stretch to view box
//                    contentStream.drawImage(pdImage2, 9.95f, 139f, scaledWidth2, scaledHeight2);

                    // perfect portrait centered
                    contentStream.drawImage(pdImage2, x, 139f, scaledWidth3, scaledHeight3);

                    contentStream.close();

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
