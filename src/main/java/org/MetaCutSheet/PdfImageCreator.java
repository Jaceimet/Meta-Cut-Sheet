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
import java.io.IOException;/*NOTE this is a test code written to try to center a image on a pdf but in a new way*/


public class PdfImageCreator {


    public static PDDocument PDFRescale(String template,String inputUserFile){

        PDDocument final_cs = new PDDocument();


        try (PDDocument existingDocument = Loader.loadPDF(new File(template));
             PDDocument userDocument = Loader.loadPDF(new File(inputUserFile))) {

            //convert user pdf to image
            PDFRenderer pdfRenderer = new PDFRenderer(userDocument);
            BufferedImage pdfImage = pdfRenderer.renderImage(0, 1);
//            PDImageXObject.createFromFile(inputUserPDF, doc);
//            BufferedImage image = pdfRenderer.renderImageWithDPI(0, 300.0F);


            // Calculate target height for resized image to maintain aspect ratio
            float targetWidth = 592f;//612
            float aspectRatio = (float) pdfImage.getHeight() / pdfImage.getWidth();
            float targetHeight = aspectRatio * targetWidth;



            // Functional but needs refining**********************************************************************

            PDPage temp_page = existingDocument.getPage(0);
//            PDDocument final_cs = new PDDocument();
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
                //System.out.println("Page width: " + pageWidth + "\n");
//                    float pageHeight = page.getMediaBox().getHeight();
//                    System.out.println("Page Height: " + pageHeight + "\n");


                try (PDPageContentStream contentStream = new PDPageContentStream(userDocument, final_cs.getPage(i),
                        PDPageContentStream.AppendMode.APPEND, false)) {
                    BufferedImage image = pdfRenderer.renderImageWithDPI(i, 300.0F);
                    PDImageXObject pdImage = LosslessFactory.createFromImage(userDocument, image);
//                    PDImageXObject pdImage2 = PDImageXObject.createFromFile(inputUserFile, final_cs);

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


            // issue with saving the final_cs from main, these lines are functional but don't belong here



            String selectedSaveFilePath = SaveFinalPDF.saveFinalPDF(final_cs);

            System.out.println("Successfully save to : " + selectedSaveFilePath + "\n");

            OpenFinalPDF.openPDF(selectedSaveFilePath);



        } catch (IOException e) {
            System.err.println("Error processing PDFs: " + e.getMessage());
        }

        return final_cs;


    }

}
