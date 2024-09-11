package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

/* Notes: code needs to be cleaned
* needs preferences / save last save location
* needs to handle images from user
* needs to handle multiple pdfs batch-mode
* needs to handle multiple images for one pdf
* needs to handle mixed orientation (maybe)
* Nests a Cut Sheet PDF or image of a Cut Sheet PDF onto a project summary form.
* .exe instructions: https://www.youtube.com/watch?v=jKlyHG-zbjk, https://www.youtube.com/watch?v=N4MfDEGOEy4
 */




public class Main {

    public static void main(String[] args) throws IOException {


        String template = FormSelector.displayForm();

//        String inputUserFile = InputFileChooser.userFile();

        File[] userFile = InputFileChooser.multipleFileUserInput();

//        for (int i = userFile.length -1; i >= 0; i--) {
//            System.out.println(userFile[i]);
//                    }
//        System.exit(0);

        /* Code is funtional but lacks complexity needed
         * https://stackoverflow.com/questions/23326562/convert-pdf-files-to-images-with-pdfbox
         * */

        PDDocument final_cs = PdfImageCreator.pdfGenerator(template, inputUserFile, userFile);
//
        String selectedSaveFilePath = SaveFinalPDF.saveFinalPDF(final_cs);
//
        final_cs.close();
//
        OpenFinalPDF.openPDF(selectedSaveFilePath);


    }

}


