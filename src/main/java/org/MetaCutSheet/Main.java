package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

/* Notes: code needs to be cleaned
* () prioritize auto filling the form fields
* () needs preferences / save last save location
* () needs to handle multiple pdfs batch-mode/loop
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


        String template = FormSelector.displayForm();

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

        String selectedSaveFilePath = SaveFinalPDF.saveFinalPDF(final_cs);

        //Need loop for batch files

        final_cs.close();

        OpenFinalPDF.openPDF(selectedSaveFilePath);

        System.exit(0);

    }

}


