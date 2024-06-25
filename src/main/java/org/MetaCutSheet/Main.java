package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

/* Notes: code needs to be cleaned
* needs preferences / save last save location
* needs to handle images from user
* needs to handle multiple pdfs batch-mode
* needs to handle multiple images for one pdf
* needs to handle mixed orientation
*  */


public class Main {

    public static void main(String[] args) throws IOException {

//        PdfImageCreator.PDFRescale();

        String template = FormSelector.displayForm();
        String inputUserFile = InputFileChooser.userFile();
        String selectedSaveFilePath;
//        PDDocument final_cs;
        //image handling, needs exception handling
/*        try {

            String type = Files.probeContentType(Paths.get(inputUserFile));
            System.out.println(type);

            if (type.equals("image/png") || type.equals("image/jpeg")) {
                PDDocument final_cs = null;
                PDImageXObject pdImage2 = PDImageXObject.createFromFile(inputUserFile, final_cs);

                System.out.println("success!");
            } else {
                System.out.println("fail");
            }
        }catch (IOException e) {
            System.err.println("Error processing images: " + e.getMessage());
        }*/

        // page measurements
        // Width constraint in inches (8.23 inches)
        // height constraint in inches (8.9in)
        // float maxHeight = 854.4f;


        /* Code is funtional but lacks complexity needed
         * https://stackoverflow.com/questions/23326562/convert-pdf-files-to-images-with-pdfbox
         * */
        // load pdfs

//        if (inputUserPDF.contains(".pdf")){
//
//        }else (load image (.jpeg or .png)maybe multiple)

        PDDocument final_cs = PdfImageCreator.PDFRescale(template, inputUserFile);


//        try (PDDocument existingDocument = Loader.loadPDF(new File(template));
//                      PDDocument userDocument = Loader.loadPDF(new File(inputUserFile))) {
//
//                //convert user pdf to image
//                PDFRenderer pdfRenderer = new PDFRenderer(userDocument);
//                BufferedImage pdfImage = pdfRenderer.renderImage(0, 1);
////            PDImageXObject.createFromFile(inputUserPDF, doc);
////            BufferedImage image = pdfRenderer.renderImageWithDPI(0, 300.0F);
//
//
//                // Calculate target height for resized image to maintain aspect ratio
//                float targetWidth = 592f;//612
//                float aspectRatio = (float) pdfImage.getHeight() / pdfImage.getWidth();
//                float targetHeight = aspectRatio * targetWidth;
//
//
//
//                // Functional but needs refining**********************************************************************
//
//                PDPage temp_page = existingDocument.getPage(0);
////                PDDocument final_cs = new PDDocument();
//                PDPage page = null;
//
//                int i;
//                for (i = 0; i < userDocument.getNumberOfPages(); ++i) {
//                    final_cs.importPage(temp_page);
//                    page = userDocument.getPage(i);
//
//                }
//
//                System.out.println("final_Cs has " + i + " pages" + "\n");
//
//
//                for (i = 0; i < userDocument.getNumberOfPages(); ++i) {
//                    //check for orientation
//                    PDRectangle pageSize = userDocument.getPage(i).getMediaBox();
//
//                    int degree = userDocument.getPage(i).getRotation();
//
//                    boolean isLandscape;
//
//                    if ((pageSize.getWidth() > pageSize.getHeight()) || (degree == 90) || (degree == 270)) {
//                        isLandscape = true;
//                    } else {
//                        isLandscape = false;
//                    }
//
//
//                    // Get page dimensions
//                    assert page != null;
//                    float pageWidth = page.getMediaBox().getWidth();
//                    //System.out.println("Page width: " + pageWidth + "\n");
////                    float pageHeight = page.getMediaBox().getHeight();
////                    System.out.println("Page Height: " + pageHeight + "\n");
//
//
//                    try (PDPageContentStream contentStream = new PDPageContentStream(userDocument, final_cs.getPage(i),
//                            PDPageContentStream.AppendMode.APPEND, false)) {
//                        BufferedImage image = pdfRenderer.renderImageWithDPI(i, 300.0F);
//                        PDImageXObject pdImage = LosslessFactory.createFromImage(userDocument, image);
////                    PDImageXObject pdImage2 = PDImageXObject.createFromFile(inputUserFile, final_cs);
//
//                        float imageX;
//                        float imageY;
//                        float imageWidth;
//                        float imageHeight;
//
//
//                        if (isLandscape) {
//
//                            System.out.println("Landscape" + "\n");
//
//                            contentStream.drawImage(pdImage, 9.95f, 275f, targetWidth, targetHeight);
//
//                        } else {
//                            // For portrait mode, keep the existing coordinates and dimensions
//                            System.out.println("Protrait" + "\n");
//
//                            imageX = (float) (0.5 * (pageWidth - 648.0 / (double) pdImage.getHeight() * (double) pdImage.getWidth()));
//                            imageY = 144.0F;
//                            imageWidth = (float) (648.0 / (double) pdImage.getHeight() * (double) pdImage.getWidth());
//                            imageHeight = 638.0F;
//                            contentStream.drawImage(pdImage, imageX, imageY, imageWidth, imageHeight);
//
//                        }
//
//                    }
//
//                }
//
////        selectedSaveFilePath = SaveFinalPDF.saveFinalPDF(final_cs);
//
//
//        } catch (IOException e) {
//            System.err.println("Error processing PDFs: " + e.getMessage());
//        }

        // issues with saving final_cs from main, these lines are not functional************

//        selectedSaveFilePath = SaveFinalPDF.saveFinalPDF(final_cs);
//
//        System.out.println("Successfully save to : " + selectedSaveFilePath + "\n");
//
//        OpenFinalPDF.openPDF(selectedSaveFilePath);

    }

}


