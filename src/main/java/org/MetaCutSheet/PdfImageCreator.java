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


        //image handling, needs exception handling -- non-functional
        try {
            type = Files.probeContentType(Paths.get(inputUserFile));

            if (type.equals("image/png") || type.equals("image/jpeg")) {

                System.out.println("File is an "+ type +" type\n");

                PDDocument existingDocument = Loader.loadPDF(new File(template));

                PDPage temp_page = existingDocument.getPage(0);

                final_cs.addPage(temp_page);

                PDImageXObject pdImage2 = PDImageXObject.createFromFile(inputUserFile, final_cs);

                float targetWidth = 592f;
                float aspectRatio = (float) pdImage2.getHeight() / pdImage2.getWidth();
                float targetHeight = aspectRatio * targetWidth;

                PDPageContentStream contentStream = new PDPageContentStream(final_cs, temp_page,
                        PDPageContentStream.AppendMode.APPEND, false);

                boolean isLandscape = pdImage2.getHeight() < pdImage2.getWidth();


                if (isLandscape) {
                    System.out.println("image is in landscape orientation");
                    contentStream.drawImage(pdImage2, 9.95f, 275f, targetWidth, targetHeight);
                }else {
                    System.out.println("image is in portrait orientation");
                    float imageX = (float) (0.5 * (pdImage2.getWidth() - 648.0 / (double) pdImage2.getHeight() * (double) pdImage2.getWidth()));
                    float imageY = 144.0F;
                    float imageWidth = (float) (648.0 / (double) pdImage2.getHeight() * (double) pdImage2.getWidth());
                    float imageHeight = 638.0F;
                    contentStream.drawImage(pdImage2, imageX, imageY, imageWidth, imageHeight);
                }

            } else if (type.equals("application/pdf")){

                System.out.println("File is a " + type + " type\n");

            }
        }catch (IOException e) {
            System.err.println("Not a supported file type: " + e.getMessage());
        }

        //PDF processing  https://www.youtube.com/watch?v=0Enx1YagHqw
        try {

            PDDocument existingDocument = Loader.loadPDF(new File(template));
            PDDocument userDocument = null;


            //convert user pdf to image
            PDFRenderer pdfRenderer = new PDFRenderer(userDocument);
            BufferedImage pdfImage = pdfRenderer.renderImage(0, 1);

            // Calculate target height for resized image to maintain aspect ratio
            float targetWidth = 592f;//612
            float aspectRatio = (float) pdfImage.getHeight() / pdfImage.getWidth();
            float targetHeight = aspectRatio * targetWidth;

            // page measurements
            // Width constraint in inches (8.23 inches)
            // height constraint in inches (8.9in)
            // float maxHeight = 854.4f;

            PDPage temp_page = existingDocument.getPage(0);
            PDPage page = null;

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

                if ((pageSize.getWidth() > pageSize.getHeight()) || (degree == 90) || (degree == 270)) {
                    isLandscape = true;
                } else {
                    isLandscape = false;
                }


                // Get page dimensions
                assert page != null;
                float pageWidth = page.getMediaBox().getWidth();


                try (PDPageContentStream contentStream = new PDPageContentStream(userDocument, final_cs.getPage(i),
                        PDPageContentStream.AppendMode.APPEND, false)) {
                    BufferedImage image = pdfRenderer.renderImageWithDPI(i, 300.0F);
                    PDImageXObject pdImage = LosslessFactory.createFromImage(userDocument, image);


                    float imageX;
                    float imageY;
                    float imageWidth;
                    float imageHeight;


                    if (isLandscape) {

                        System.out.println("Landscape" + "\n");

                        contentStream.drawImage(pdImage, 9.95f, 275f, targetWidth, targetHeight);

                    } else {
                        // For portrait mode, keep the existing coordinates and dimensions
                        System.out.println("Protrait" + "\n");

                        imageX = (float) (0.5 * (pageWidth - 648.0 / (double) pdImage.getHeight() * (double) pdImage.getWidth()));
                        imageY = 144.0F;
                        imageWidth = (float) (648.0 / (double) pdImage.getHeight() * (double) pdImage.getWidth());
                        imageHeight = 638.0F;
                        contentStream.drawImage(pdImage, imageX, imageY, imageWidth, imageHeight);

                    }

                }

            }

        } catch (IOException e) {
            System.err.println("Error processing PDFs: " + e.getMessage());
        }

        return final_cs;

    }

}
