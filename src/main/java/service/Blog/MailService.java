package service.Blog;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MailService {

    private final String username;
    private final String password;
    private final Properties properties;

    public MailService() {
        this.username = "hamoudachkir@yahoo.fr";
        this.password = "3nGNZCVsgtvk5FjX";

        // Configure email properties
        this.properties = new Properties();
        this.properties.put("mail.smtp.auth", "true");
        this.properties.put("mail.smtp.starttls.enable", "true");
        this.properties.put("mail.smtp.host", "smtp-relay.brevo.com");
        this.properties.put("mail.smtp.port", "587");
    }

    public void sendEmail(String to) throws MessagingException, IOException {
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Create a multipart message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("adventure@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Thank You for Your Comment");

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        String htmlContent = "<html><body>";
        htmlContent += "<div style='text-align:center;'>";
        htmlContent += "<h1>Thank You for Your Comment!</h1>";
        htmlContent += "<p>We appreciate your feedback and your contribution to our community.</p>";
        htmlContent += "<p>Best regards,<br>Adventure Team</p>";
        htmlContent += "</div></body></html>";
        messageBodyPart.setContent(htmlContent, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        Transport.send(message);
        System.out.println("Thank you email sent successfully to: " + to);
    }


}