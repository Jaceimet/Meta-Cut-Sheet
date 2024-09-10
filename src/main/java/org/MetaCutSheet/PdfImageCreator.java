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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class PdfImageCreator {

    public static PDDocument pdfGenerator(String template, String inputUserFile) {

        PDDocument final_cs = new PDDocument();
        String type = null;
        PDPage temp_page = null;
        PDDocument existingDocument;
        PDImageXObject pdImage2;


        //media box dimensions
        float mediaBoxWidth = 593F; //638.0F
        float mediaBoxHeight = 642f; //644f, 641.48f, 638.6f
        float mediaBoxBottomLeftX = 9.95f;
        float mediaBoxBottomLeftY = 139f;

//////////////////////Diagnostic print out
//        InputStream templateFile = Resources.class.getResourceAsStream(template);
//        InputStream input = PdfImageCreator.getClass().getResourceAsStream(template);
//        System.out.println("input stream: " + templateFile);
        InputStream url = FormSelector.class.getResourceAsStream(template);
        System.out.println("URL: " + url);
//        File file = Paths.get(".", "resources", template).normalize().toFile();
//        System.out.println("Paths: " + file );
        File templateFile3 = new File(template);
        System.out.println(templateFile3.exists());
        System.out.println(templateFile3.isDirectory());
        System.out.println(templateFile3.canRead());
        System.out.println("Absolute path: " + new File(template).getAbsolutePath());
        System.out.println("The path is '" + templateFile3 + "'");
        System.out.println(template);
        System.out.println("current working dir. "+new File(".").getAbsolutePath());
////////////////////////////////////

        // Load template
        try {
            //Functional only in IDE
//            existingDocument = Loader.loadPDF(new File(template));

            //Functional only in JAR
//            existingDocument = Loader.loadPDF(PdfImageCreator.class.getResourceAsStream(template).readAllBytes());

            /////test/in-progress///works in local, need test jar
            existingDocument = Loader.loadPDF(PdfImageCreator.class.getResourceAsStream(template).readAllBytes());

            temp_page = existingDocument.getPage(0);

            //find template measurements
            PDRectangle pageSize = existingDocument.getPage(0).getMediaBox();
            float existingPageWidth = pageSize.getWidth();
            System.out.println("existingPageWidth " + existingPageWidth + "\n");
            float existingPageHeight = pageSize.getHeight();
            System.out.println("existingPageHeight " + existingPageHeight + "\n");

            //(W.593, H.644 inside the box (Aspect ratio = 1.086003))
            // box area = 381,892
            // aspect = 0.7071 (portrait? long side divided by the short side)
            // portrait aspect ratio = long side divided by the short side
        } catch (IOException e) {
            System.err.println("Error processing template: " + e.getMessage());
            ErrorMessages.templateCrash();
            System.exit(1);
        }

        try {
            type = Files.probeContentType(Paths.get(inputUserFile));
        } catch (IOException e) {
            System.err.println("Error processing inputUserFile type: " + e.getMessage());

        }

        try {

            //image handling, needs refining

            if (type != null && type.equals("image/png") || type != null && type.equals("image/jpeg")) {

                System.out.println("File is an " + type + " type\n");

                final_cs.addPage(temp_page);

                pdImage2 = PDImageXObject.createFromFile(inputUserFile, final_cs);


                // non-functional scale stretch to media box (optional calculation)
//                float scaledWidth2 = imageWidth / scaledWidth;
//                System.out.println("scaledWidth2 "+scaledWidth2);
//                float scaledHeight2 = imageHeight / scaledHeight;
//                System.out.println("scaledHeight2 "+scaledHeight2);


                assert temp_page != null;
                PDPageContentStream contentStream = new PDPageContentStream(final_cs, temp_page,
                        PDPageContentStream.AppendMode.APPEND, false);


                boolean isLandscape = pdImage2.getHeight() < pdImage2.getWidth();


                if (isLandscape) {
                    System.out.println("image is in landscape orientation" + "\n");

                    ImageScalar imageScalar = new ImageScalar(mediaBoxWidth, mediaBoxHeight, mediaBoxBottomLeftX,
                            mediaBoxBottomLeftY, pdImage2);

                    contentStream.drawImage(pdImage2, mediaBoxBottomLeftX, imageScalar.getAdjustedY(),
                            imageScalar.getScaledWidth(), imageScalar.getScaledHeight());

                    contentStream.close();

                } else {
                    System.out.println("image is in portrait orientation" + "\n");

                    // non-functional stretch to media box (optional implementation)
//                    contentStream.drawImage(pdImage2, 9.95f, 139f, scaledWidth2, scaledHeight2);

                    ImageScalar imageScalar = new ImageScalar(mediaBoxWidth, mediaBoxHeight, mediaBoxBottomLeftX,
                            mediaBoxBottomLeftY, pdImage2);

                    contentStream.drawImage(pdImage2, imageScalar.getAdjustedX(), mediaBoxBottomLeftY,
                            imageScalar.getScaledWidth(), imageScalar.getScaledHeight());

                    contentStream.close();

                }


            } else {
                assert type != null;
                if (type.equals("application/pdf")) {

                    System.out.println("File is a " + type + " type\n");


                    try {

                        //PDF processing
                        ProcessingMessage.processingMessage();
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


                                if (isLandscape) {

                                    System.out.println("Landscape" + "\n");

                                    ImageScalar imageScalar = new ImageScalar(mediaBoxWidth, mediaBoxHeight, mediaBoxBottomLeftX,
                                            mediaBoxBottomLeftY, pdImage);

                                    contentStream.drawImage(pdImage, mediaBoxBottomLeftX, imageScalar.getAdjustedY(),
                                            imageScalar.getScaledWidth(), imageScalar.getScaledHeight());

                                } else {

                                    System.out.println("Portrait" + "\n");

                                    ImageScalar imageScalar = new ImageScalar(mediaBoxWidth, mediaBoxHeight, mediaBoxBottomLeftX,
                                            mediaBoxBottomLeftY, pdImage);

                                    contentStream.drawImage(pdImage, imageScalar.getAdjustedX(), mediaBoxBottomLeftY,
                                            imageScalar.getScaledWidth(), imageScalar.getScaledHeight());

                                }

                            }

                        }

                    } catch (IOException e) {
                        System.err.println("Error processing PDFs: " + e.getMessage());
                    }
                }
            }

//            } else
//                System.out.println("Not a supported file type");
//                JFrame frame = new JFrame("Error");
//                JOptionPane.showMessageDialog(frame, "Not a supported file type, App shutting down...");
//                frame.dispose();
//                System.exit(1);

        } catch (Exception e) {
            System.err.println("Not a supported file type: " + e.getMessage());
        }

        return final_cs;

    }
}
