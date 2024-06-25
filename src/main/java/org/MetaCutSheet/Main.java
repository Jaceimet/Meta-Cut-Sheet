package org.MetaCutSheet;

import java.io.IOException;

/* Notes: code needs to be cleaned
* needs preferences / save last save location
* needs to handle images from user
* needs to handle multiple pdfs batch-mode
* needs to handle multiple images for one pdf
* needs to handle mixed orientation
* Nests a Cut Sheet PDF or image of a Cut Sheet PDF onto a project summary form.
 */


public class Main {

    public static void main(String[] args) throws IOException {

//        PdfImageCreator.PDFRescale();

        String template = FormSelector.displayForm();
        String inputUserFile = InputFileChooser.userFile();


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

        PdfImageCreator.PDFRescale(template, inputUserFile);



    }

}


