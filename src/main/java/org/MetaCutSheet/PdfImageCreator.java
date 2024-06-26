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


        //image handling, needs exception handling
        try {
            type = Files.probeContentType(Paths.get(inputUserFile));

            if (type.equals("image/png") || type.equals("image/jpeg")) {

                System.out.println("File is an "+ type +" type\n");

                PDImageXObject pdImage2 = PDImageXObject.createFromFile(inputUserFile, final_cs);


            } else if (type.equals("application/pdf")){

                System.out.println("File is a " + type + " type\n");

            }
        }catch (IOException e) {
            System.err.println("Error processing file type: " + e.getMessage());
        }

        //image handler method

//        if(type.equals())
//        try(PDDocument existingDocument = Loader.loadPDF(new File(template))){
//
//            PDImageXObject pdImage2 = PDImageXObject.createFromFile(inputUserFile, final_cs);
//
//
//
//
//        } catch (IOException e) {
//            System.err.println("Error processing PDFs: " + e.getMessage());
//        }



        try {

            PDDocument existingDocument = Loader.loadPDF(new File(template));
            PDDocument userDocument = null;

            if(type.equals("image/png") || type.equals("image/jpeg")){

                PDImageXObject pdImage = PDImageXObject.createFromFile(inputUserFile, final_cs);

                float targetWidth = 592f;//612
                float aspectRatio = (float) pdImage.getHeight() / pdImage.getWidth();
                float targetHeight = aspectRatio * targetWidth;

                PDPage temp_page = existingDocument.getPage(0);
                PDPage page = null;

                final_cs.importPage(temp_page);





            } else if (type.equals("application/pdf")){

                userDocument = Loader.loadPDF(new File(inputUserFile));


            }

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

//            System.out.println("PDF Created" + "\n");
//
//
//            String selectedSaveFilePath = SaveFinalPDF.saveFinalPDF(final_cs);
//
//
//            OpenFinalPDF.openPDF(selectedSaveFilePath);



        } catch (IOException e) {
            System.err.println("Error processing PDFs: " + e.getMessage());
        }

        return final_cs;

    }

}
