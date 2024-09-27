package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

/* Notes: code needs to be cleaned
* () prioritize auto filling the form fields
* () needs preferences / save last save location
* () needs to handle multiple pdfs batch-mode/loop
* () file search window to be closer to the windows explorer window style
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
            System.out.println(userImageFile);
            final_cs = PdfImageCreator.pdfGenerator(template, userImageFile);
        }

//        // needs a switch to choose pdf or image/images
//        String inputUserFile = InputFileChooser.userFile();
//
//        File[] userFile = InputFileChooser.multipleFileUserInput();

//        for (int i = userFile.length -1; i >= 0; i--) {
//            System.out.println(userFile[i]);
//                    }
//        System.exit(0);

        /* Code is funtional but lacks complexity needed
         * https://stackoverflow.com/questions/23326562/convert-pdf-files-to-images-with-pdfbox
         * */

        // needs switch? to choose parameter sent?
//        PDDocument final_cs = PdfImageCreator.pdfGenerator(template, inputUserFile, userFile);

        String selectedSaveFilePath = SaveFinalPDF.saveFinalPDF(final_cs);

        final_cs.close();

        OpenFinalPDF.openPDF(selectedSaveFilePath);

        System.exit(0);

    }

}


