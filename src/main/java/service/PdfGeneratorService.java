package service;

import model.Reservation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class PdfGeneratorService {

    public void generatePdf(String filePath, Reservation reservation, String activity) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                File imageFile = new File("C:/Users/manna/IdeaProjects/Projet-PIDEV-JAVA2/Adventure-JAVA--main/src/main/resources/Front/Blog/2.png");
                PDImageXObject image = PDImageXObject.createFromFileByContent(imageFile, document);

                // Get the image dimensions
                float imageWidth = 50;
                float imageHeight = 50;

                contentStream.drawImage(image, 20, PDRectangle.LETTER.getHeight() - 20 - imageHeight, imageWidth, imageHeight);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Reservation Details");
                contentStream.endText();


                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 650);
                contentStream.setLeading(14);
                contentStream.showText(activity);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 630);
                contentStream.setLeading(14);
                contentStream.showText("Email : "+reservation.getUserEmail());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 610);
                contentStream.setLeading(14);
                contentStream.showText("Tickets : "+reservation.getNbrTickets());
                contentStream.endText();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = reservation.getDate().toLocalDateTime().format(formatter);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 590);
                contentStream.setLeading(14);
                contentStream.showText("Date : "+formattedDate);
                contentStream.endText();
            }



            document.save(filePath);
            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
