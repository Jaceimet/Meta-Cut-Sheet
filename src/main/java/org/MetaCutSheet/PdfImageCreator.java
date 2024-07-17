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

                //print box dimensions
                float pageWidth = 593F; //638.0F
                float pageHeight = 644f; //790.1

                //image dimensions
                float imageWidth = pdImage2.getWidth();
                System.out.println("image width: " + imageWidth);
                float imageHeight = pdImage2.getHeight();
                System.out.println("image height: " + imageHeight);

                float scaledWidth = imageWidth / pageWidth ;
                System.out.println("scaledWidth "+scaledWidth);
                float scaledHeight = imageHeight / pageHeight;
                System.out.println("scaledHeight "+scaledHeight);

                //scale?
//                float scale = Math.min(imageWidth / pageWidth, imageHeight / pageHeight); //w:2.7026 h:2.95328
//                System.out.println("Scale : " + scale);
                float scale = Math.min(pageWidth / imageWidth, pageHeight/ imageHeight); //w:2.7026 h:2.95328
                System.out.println("Scale : " + scale);

                float scaledWidth3 = imageWidth * scale;
                System.out.println("scaledWidth3 "+scaledWidth3);
                float scaledHeight3 = imageHeight * scale;
                System.out.println("scaledHeight3 "+scaledHeight3);

                // fit to view box
                float scaledWidth2 = imageWidth / scaledWidth;
                System.out.println("scaledWidth2 "+scaledWidth2);
                float scaledHeight2 = imageHeight / scaledHeight;
                System.out.println("scaledHeight2 "+scaledHeight2);

                float x = (pageWidth - scaledWidth3)/2 + 9.95f;
                System.out.println("x= "+x);
//                float y = (pageHeight - scaledHeight)/ 2;
//                System.out.println("y="+y);

                // box area = 381,892
                // aspect = 0.7071
                //w=519.6643
                //h=735.0553

                //w=1275/1650*381982 =543.2935
                //h=381982/w =703.0859




            // functional but off center
//                float aspectRatio = (float) pdImage2.getHeight() / pdImage2.getWidth();
//                System.out.println("aspect ratio: " + aspectRatio);
//                float targetWidth = 465f;//540f
//                float targetHeight = aspectRatio * targetWidth;
//                //(W.593, H.644 inside the box (Aspect ratio = 1.086003))

                //portrait? long side divided by the short side

            // functional fit to view box
                float aspectRatio = (float) pdImage2.getHeight() / pdImage2.getWidth();
                System.out.println("aspect ratio: " + aspectRatio);
                float targetWidth = 455f;//540f, 465f
                float targetHeight = aspectRatio * targetWidth;

//              (W.593, H.644 inside the box (Aspect ratio = 1.086003, area )
//               image aspect ratio: 1.4141475
//               view box area = 381,892

                float aspectRatioImageHeight = pageHeight /pageWidth * imageWidth;
                System.out.println("aspectRatioImageHeight: " + aspectRatioImageHeight);


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

                    //prototypes
//                    contentStream.drawImage(pdImage2, 9.95f, 139f, targetWidth, targetHeight);
//                    contentStream.drawImage(pdImage2, 10f, 125f, targetWidth, 660f);

                    // functional but fit to view box
//                    contentStream.drawImage(pdImage2, 9.95f, 139f, scaledWidth2, scaledHeight2);
                    // functional but not centered
//                    contentStream.drawImage(pdImage2, 9.95f, 139f, scaledWidth3, scaledHeight3);
                    //portrait centered
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
