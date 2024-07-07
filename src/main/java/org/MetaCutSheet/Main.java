package org.MetaCutSheet;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

/* Notes: code needs to be cleaned
* needs preferences / save last save location
* needs to handle images from user
* needs to handle multiple pdfs batch-mode
* needs to handle multiple images for one pdf
* needs to handle mixed orientation (maybe)
* Nests a Cut Sheet PDF or image of a Cut Sheet PDF onto a project summary form.
 */


public class Main {

    public static void main(String[] args) throws IOException {

//        PdfImageCreator.PDFRescale();

        String template = FormSelector.displayForm();

        System.out.println("Template chosen: " + template);

        String inputUserFile = InputFileChooser.userFile();


        /* Code is funtional but lacks complexity needed
         * https://stackoverflow.com/questions/23326562/convert-pdf-files-to-images-with-pdfbox
         * */

        PDDocument final_cs = PdfImageCreator.pdfGenerator(template, inputUserFile);

        String selectedSaveFilePath = SaveFinalPDF.saveFinalPDF(final_cs);

        final_cs.close();

        OpenFinalPDF.openPDF(selectedSaveFilePath);


    }

}


