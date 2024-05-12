package controller;

import com.google.zxing.WriterException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.*;
import service.*;
import utils.MyDataBase;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityDetails {
    @FXML
    private Button favButton;
    @FXML
    private TextField comment;
    @FXML
    private Text desc;
    @FXML
    private Text name;
    @FXML
    private Text locationn;
    @FXML
    private TextField nbrTickets;
    @FXML
    private TextField rating;
    @FXML
    private DatePicker date;
    @FXML
    private VBox commentsVbox;
    @FXML
    private Pane pane;
    @FXML
    private AnchorPane imageSlider;
    @FXML
    private Button locationIcon;
    Activity activity;
    User currentUser;

    public void setData(Activity activity) throws IOException {
        currentUser = LoginController.getCurrentUser();
        Image icon = new Image(getClass().getResourceAsStream("/assets/darkLocation.png"));
        ImageView iconView = new ImageView(icon);
        locationIcon.setGraphic(iconView);
        name.setText(activity.getName());
        locationn.setText(activity.getLocation());
        desc.setText(activity.getDescription());
        this.activity=activity;
        ActivityImagesService activityImagesService =new ActivityImagesService();
        List<ActivityImages> images=new ArrayList<>();

        List<Comment> comments=new ArrayList<>();

        Connection connection= MyDataBase.getInstance().getConnection();
        System.out.println(connection);

        try {
            images=activityImagesService.selectByActivityId(activity.getId());
            System.out.println(images);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        FXMLLoader fxmlLoaderr = new FXMLLoader(getClass().getResource("/imageSlider.fxml"));
        Parent fxml = fxmlLoaderr.load();
        ImageSlider controller = fxmlLoaderr.getController();
        controller.setImages(images);
        System.out.println(images);
        imageSlider.getChildren().clear();
        imageSlider.getChildren().add(fxml);

        setComments();

        System.out.println(comments);

        FavActivityService favActivityService=new FavActivityService();
        try{
        if(favActivityService.isActivityInWishlist(activity.getId())) {
            favButton.setText("Remove From Wishlist");
        }else {
            favButton.setText("Add To Wishlist");
        }


        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setPane(Pane pane) {
        this.pane=pane;
    }

    @FXML
    private void navigateBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activities.fxml"));
            Parent fxml = loader.load();
            pane.getChildren().clear();
            pane.getChildren().add(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addComment(ActionEvent actionEvent) {
        if(validateCommentFields()) {
            String commentText = comment.getText();
            int intRating = Integer.valueOf(rating.getText());
            String[] parts = currentUser.getEmail().split("@");
            String name = parts[0];
            Comment newComment = new Comment(currentUser.getEmail(), name, commentText, intRating, activity.getId());

            CommentService commentService = new CommentService();

            try {
                commentService.add(newComment);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            comment.clear();
            rating.clear();
            setComments();
        }
    }

    @FXML
    private void addReservation(ActionEvent actionEvent) throws IOException, WriterException {
        if(validateReservationFields()) {
            Timestamp selectedDate = Timestamp.valueOf(date.getValue().atStartOfDay());
            int intNbrTickets = Integer.valueOf(nbrTickets.getText());

            Reservation newReservation = new Reservation(selectedDate, intNbrTickets, currentUser.getEmail(), activity.getId(), "Pending");
            ReservationService reservationService = new ReservationService();

            try {
                reservationService.add(newReservation);
                date.setValue(null);
                nbrTickets.clear();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            QrcodeGeneratorService qrcodeGeneratorService=new QrcodeGeneratorService();
            PdfGeneratorService pdfGeneratorService=new PdfGeneratorService();
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String formattedDate = dateFormat.format(currentDate);
            String tempFilePath = "C:/xampp/htdocs/reservations/Reservation_Details_"+formattedDate+".pdf";
            File tempFile = new File(tempFilePath);
            System.out.println(tempFile);
            pdfGeneratorService.generatePdf(tempFilePath, newReservation, activity.getName());
            qrcodeGeneratorService.createQrCode("http://localhost/reservations/Reservation_Details_"+formattedDate+".pdf");
            EmailService emailService = new EmailService();
            try {
                emailService.sendEmail(currentUser.getEmail(), tempFilePath);
            } catch (MessagingException e) {
                System.out.println("Failed to send email: " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                File qrCodeFile = new File("C:/Users/manna/IdeaProjects/pi/src/main/resources/assets/qrCode.jpg");
                if (qrCodeFile.exists()) {
                    qrCodeFile.delete();
                    System.out.println("qrCode deleted");
                }
            }
        }
    }

    public void setComments() {
        commentsVbox.getChildren().clear();
        CommentService commentService = new CommentService();
        List<Comment> comments;
        try {
            comments = commentService.selectByActivityId(activity.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int maxCommentsToShow = 3;

        for (int i = comments.size() - 1; i >= Math.max(0, comments.size() - maxCommentsToShow); i--){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/commentCard.fxml"));
                Parent commentFXML = fxmlLoader.load();
                CommentCard commentController = fxmlLoader.getController();
                commentController.setData(comments.get(i));
                commentsVbox.getChildren().add(commentFXML);
            } catch (IOException e) {
                System.out.println("Error loading comment card: " + e.getMessage());
            }
        }

        if (comments.size() > maxCommentsToShow) {
            Button seeMoreButton = new Button("See More ..");
            seeMoreButton.setUnderline(true);
            seeMoreButton.setStyle("-fx-background-color: #ffffff;-fx-cursor: hand;-fx-text-fill:  #1D231F;");
            seeMoreButton.setOnMouseEntered(e -> seeMoreButton.setStyle("-fx-background-color: #ffffff;-fx-text-fill:  #2F5C3E; -fx-underline: true; -fx-cursor: hand;"));
            seeMoreButton.setOnMouseExited(e -> seeMoreButton.setStyle("-fx-background-color: #ffffff;-fx-text-fill:  #1D231F; -fx-underline: true; -fx-cursor: hand;"));
            seeMoreButton.setOnAction(event -> showAllComments(comments));
            commentsVbox.getChildren().add(seeMoreButton);
        }
    }

    private void showAllComments(List<Comment> comments) {
        commentsVbox.getChildren().clear();
        for (int i = comments.size()-1; i >= 0 ; i--) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/commentCard.fxml"));
                Parent commentFXML = fxmlLoader.load();
                CommentCard commentController = fxmlLoader.getController();
                commentController.setData(comments.get(i));
                commentsVbox.getChildren().add(commentFXML);
            } catch (IOException e) {
                System.out.println("Error loading comment card: " + e.getMessage());
            }
        }


        Button seeLessButton = new Button("See Less ..");
        seeLessButton.setUnderline(true);
        seeLessButton.setStyle("-fx-background-color: #ffffff;-fx-cursor: hand;-fx-text-fill:  #1D231F;");
        seeLessButton.setOnMouseEntered(e -> seeLessButton.setStyle("-fx-background-color: #ffffff;-fx-text-fill:  #2F5C3E; -fx-underline: true; -fx-cursor: hand;"));
        seeLessButton.setOnMouseExited(e -> seeLessButton.setStyle("-fx-background-color: #ffffff;-fx-text-fill:  #1D231F; -fx-underline: true; -fx-cursor: hand;"));
        seeLessButton.setOnAction(event -> setComments());
        commentsVbox.getChildren().add(seeLessButton);

    }


    @FXML
    private boolean validateReservationFields() {
        LocalDate selectedDate = date.getValue();
        String ticketsText = nbrTickets.getText();
        boolean isValid=true;

        if (selectedDate == null) {
            System.out.println("Please select a date.");
            date.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        } else
            date.setStyle("");

        LocalDate currentDate = LocalDate.now();
        if (selectedDate.isBefore(currentDate)) {
            System.out.println("Selected date must be greater than the current date.");
            date.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        } else
            date.setStyle("");

        if (ticketsText.isEmpty()) {
            System.out.println("Please enter the number of tickets.");
            nbrTickets.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        } else
            nbrTickets.setStyle("");

        try {
            int intTickets = Integer.parseInt(ticketsText);
            if (intTickets <= 0) {
                System.out.println("Number of tickets must be greater than 0.");
                nbrTickets.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
                isValid = false;
            } else
                nbrTickets.setStyle("");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid integer for the number of tickets.");
            isValid = false;
        }
        return isValid;
    }

    @FXML
    private boolean validateCommentFields() {
        String commentText = comment.getText();
        String ratingText = rating.getText();
        boolean isValid =true;

        if (commentText.isEmpty()) {
            System.out.println("Please enter a comment.");
            comment.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        } else
            comment.setStyle("");




        if (ratingText.isEmpty()) {
            System.out.println("Please enter a rating.");
            rating.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        } else
            rating.setStyle("");

        try {
            int intRating = Integer.parseInt(ratingText);
            if (intRating < 1 || intRating > 5) {
                System.out.println("Rating must be between 1 and 5.");
                rating.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
                isValid = false;
            } else
                rating.setStyle("");
        } catch (NumberFormatException e) {
            System.out.println("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            return false;
        }

        return isValid;
    }

    public void fav(ActionEvent actionEvent) throws SQLException {
        FavActivityService favActivityService=new FavActivityService();
        if(favActivityService.isActivityInWishlist(activity.getId())) {
            favActivityService.delete(activity.getId());
            System.out.println("activity removed from wishlist!");
            favButton.setText("Add To Wishlist");
        }else {
            favActivityService.add(new FavActivitiy(activity.getId(), "email@test.com"));
            System.out.println("activity added to wishlist!");
            favButton.setText("Remove From Wishlist");
        }
    }
}