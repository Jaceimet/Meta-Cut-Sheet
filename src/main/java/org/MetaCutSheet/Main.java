package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

/* Notes: code needs to be cleaned
* () prioritize auto filling the form fields
* () needs to utilize form
* (x) needs to handle multiple pdfs batch-mode/loop
* (x) needs preferences / save last save location
* (x) file search window to be closer to the windows explorer window style
* (x) needs to handle images
* (x) needs to handle multiple images for one pdf
* (x) needs to handle mixed orientation (maybe)
* Nests a Cut Sheet PDF or image of a Cut Sheet PDF onto a project summary form.
* .exe instructions: https://www.youtube.com/watch?v=jKlyHG-zbjk, https://www.youtube.com/watch?v=N4MfDEGOEy4
 */

public class Main {

    public static void main(String[] args) throws IOException {

        String userPDFFile;
        File[] userImageFile;
        PDDocument final_cs;

        do {

            // Select cutsheet
            String template = FormSelector.displayForm();

            // Select PDF or Image
            String userSelection = PDFOrImagesSelector.displayPDFOrImageForm();

            // PDF or images processing
            if (userSelection == "PDF") {

                userPDFFile = InputFileChooser.userPDFFileInput();
                final_cs = PdfImageCreator.pdfGenerator(template, userPDFFile);

            } else {

                userImageFile = InputFileChooser.multipleImageFileUserInput();
                System.out.println("user image file: " + userImageFile);
                final_cs = PdfImageCreator.pdfGenerator(template, userImageFile);
            }

            // Select save path
            String selectedSaveFilePath = SaveFinalPDF.saveFinalPDF(final_cs);

            //Need loop for batch files

            final_cs.close();

            OpenFinalPDF.openPDF(selectedSaveFilePath);

        }while (BatchModeLoop.displayForm() == 0);



        System.exit(0);

    }

}


