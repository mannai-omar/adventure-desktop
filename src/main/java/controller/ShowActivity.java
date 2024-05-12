package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Activity;
import model.Comment;
import model.Reservation;
import service.ActivityService;
import service.CommentService;
import service.ReservationService;
import utils.MyDataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowActivity implements CommentTableItem.CommentDeletedListener{


    @FXML
    private ScrollPane pane;

    @FXML
    private Label activityId;

    @FXML
    private TextArea desc;

    @FXML
    private TextField duration;

    @FXML
    private TextField locationn;

    @FXML
    private TextField maxGuests;

    @FXML
    private TextField minAge;

    @FXML
    private TextField nameField;

    @FXML
    private TextField price;

    @FXML
    private Label rating;

    @FXML
    private Label reservationsCount;

    @FXML
    private Label commentsCounter;

    @FXML
    private TextField type;
    private Activity activity;

    @FXML
    private VBox commentsVbox;

    @FXML
    private VBox reservationsVbox;


    public void setPane(ScrollPane pane) {
        this.pane=pane;
    }
    public void setData(Activity activity) throws IOException {
        nameField.setText(activity.getName());
        activityId.setText(String.valueOf(activity.getId()));
        locationn.setText(activity.getLocation());
        type.setText(activity.getType());
        desc.setText(activity.getDescription());
        duration.setText(String.valueOf(activity.getDuration()));
        maxGuests.setText(String.valueOf(activity.getMax_guests()));
        minAge.setText(String.valueOf(activity.getMin_age()));
        price.setText(String.valueOf(activity.getPrice()));
        this.activity=activity;

        List<Reservation> reservations=new ArrayList<>();

        Connection connection= MyDataBase.getInstance().getConnection();
        System.out.println(connection);
        ReservationService reservationService =new ReservationService();
        try {
            reservations=reservationService.selectByActivityId(activity.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        reservationsCount.setText(String.valueOf(reservations.size()));

        List<Comment> comments=new ArrayList<>();

        CommentService commentService =new CommentService();
        try {
            comments=commentService.selectByActivityId(activity.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        setStatCards();
        setComments(comments);
        setReservations(reservations);
    }

    private void setStatCards() {
        List<Reservation> reservations=new ArrayList<>();
        ReservationService reservationService =new ReservationService();
        try {
            reservations=reservationService.selectByActivityId(activity.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        reservationsCount.setText(String.valueOf(reservations.size()));

        List<Comment> comments=new ArrayList<>();

        CommentService commentService =new CommentService();
        try {
            comments=commentService.selectByActivityId(activity.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        float rate = 0;

        for (int i = 0; i < comments.size(); i++) {
            rate += (float) comments.get(i).getRating() / comments.size();

        }
        rating.setText(String.format("%.2f", rate));
        commentsCounter.setText(String.valueOf(comments.size()));
    }

    @FXML
    private void updateActivity(javafx.event.ActionEvent actionEvent) {
        if(validateActivity()) {
            String name = nameField.getText();
            String location = locationn.getText();
            String activityType = type.getText();
            String activityDescription = desc.getText();
            int activityDuration = Integer.parseInt(duration.getText());
            int maxGuestsValue = Integer.parseInt(maxGuests.getText());
            int minAgeValue = Integer.parseInt(minAge.getText());
            float activityPrice = Float.parseFloat(price.getText());

            Activity updatedActivity = new Activity(activity.getId(), name, location, activityPrice, activityType,
                    activityDescription, maxGuestsValue, minAgeValue, activityDuration);

            ActivityService activityService = new ActivityService();
            try {
                activityService.update(updatedActivity);
                System.out.println("activity edited !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void navigateBack(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activitiesDashboard.fxml"));
            Parent fxml = loader.load();
            ActivitiesDashboard controller = loader.getController();
            pane.setContent(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setComments(List<Comment> comments){
        try {
            for (int i = 0; i < comments.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/commentTabItem.fxml"));
                Parent fxml = fxmlLoader.load();
                CommentTableItem controller = fxmlLoader.getController();
                controller.setData(comments.get(i),this);
                commentsVbox.getChildren().add(fxml);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setReservations(List<Reservation> reservations){
        try {
            for (int i = 0; i < reservations.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/reservationTabItem.fxml"));
                Parent fxml = fxmlLoader.load();
                ReservationTableItem controller = fxmlLoader.getController();
                controller.setData(reservations.get(i));
                reservationsVbox.getChildren().add(fxml);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCommentDeleted(Comment commentt) {
        commentsVbox.getChildren().clear();
        List<Comment> comments=new ArrayList<>();

        CommentService commentService =new CommentService();
        try {
            comments=commentService.selectByActivityId(activity.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        setComments(comments);
        setStatCards();
    }

    public boolean validateActivity() {
        String name = nameField.getText();
        String location = locationn.getText();
        String activityType = type.getText();
        String activityDescription = desc.getText();
        String durationText = duration.getText();
        String maxGuestsText = maxGuests.getText();
        String minAgeText = minAge.getText();
        String priceText = price.getText();

        boolean isValid = true;

        if (name.isEmpty()) {
            nameField.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            nameField.setStyle("");
        }

        if (location.isEmpty()) {
            locationn.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            locationn.setStyle("");
        }

        if (activityType.isEmpty()) {
            type.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            type.setStyle("");
        }

        if (activityDescription.isEmpty()) {
            desc.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            desc.setStyle("");
        }

        try {
            int activityDuration = Integer.parseInt(durationText);
            if (activityDuration <= 0) {
                duration.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                duration.setStyle("");
            }
        } catch (NumberFormatException e) {
            duration.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        try {
            int maxGuestsValue = Integer.parseInt(maxGuestsText);
            if (maxGuestsValue <= 0) {
                maxGuests.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                maxGuests.setStyle("");
            }
        } catch (NumberFormatException e) {
            maxGuests.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        try {
            int minAgeValue = Integer.parseInt(minAgeText);
            if (minAgeValue <= 0) {
                minAge.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                minAge.setStyle("");
            }
        } catch (NumberFormatException e) {
            minAge.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        try {
            float activityPrice = Float.parseFloat(priceText);
            if (activityPrice <= 0) {
                price.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                price.setStyle("");
            }
        } catch (NumberFormatException e) {
            price.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        return isValid;
    }
}
