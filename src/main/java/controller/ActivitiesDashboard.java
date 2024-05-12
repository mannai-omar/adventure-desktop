package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Activity;
import model.ActivityImages;
import model.Comment;
import model.Reservation;
import service.ActivityImagesService;
import service.ActivityService;
import service.CommentService;
import service.ReservationService;
import utils.MyDataBase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ActivitiesDashboard implements Initializable,ActivityTableItem.ActivityDeletedListener  {

    @FXML
    private ScrollPane mainPane;

    @FXML
    private VBox activitiesVbox;

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
    private TextField type;

    @FXML
    private Label activitiesCount;
    @FXML
    private Label rating;
    List<File> selectedFiles;

    @FXML
    private BarChart<?,?> activitiesChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Activity> activities=new ArrayList<>();

        // Get the resources directory dynamically using the class loader
        String destinationDirectory = getClass().getResource("/assets/activityImages/").getPath();


        System.out.println(destinationDirectory);

        Connection connection= MyDataBase.getInstance().getConnection();
        System.out.println(connection);
        ActivityService activityService =new ActivityService();
        try {
            activities=activityService.select();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            initChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        activitiesCount.setText(String.valueOf(activities.size()));

        List<Comment> comments=new ArrayList<>();

        CommentService commentService =new CommentService();
        try {
            comments=commentService.select();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        float rate = 0;

        for (int i = 0; i < comments.size(); i++) {
            rate += (float) comments.get(i).getRating() / comments.size();
        }
        rating.setText(String.format("%.2f", rate));


        try {
            for (int i = 0; i < activities.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/activityTableItem.fxml"));
                Parent fxml = fxmlLoader.load();
                ActivityTableItem controller = fxmlLoader.getController();
                controller.setData(activities.get(i),this);
                controller.setPane(mainPane);
                activitiesVbox.getChildren().add(fxml);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addActivity(javafx.event.ActionEvent actionEvent) {
        if(validateActivity()) {
            String name = nameField.getText();
            String location = locationn.getText();
            String activityType = type.getText();
            String activityDescription = desc.getText();
            int activityDuration = Integer.parseInt(duration.getText());
            int maxGuestsValue = Integer.parseInt(maxGuests.getText());
            int minAgeValue = Integer.parseInt(minAge.getText());
            float activityPrice = Float.parseFloat(price.getText());

            Activity newActivity = new Activity(name, location, activityPrice, activityType,
                activityDescription, maxGuestsValue, minAgeValue, activityDuration);


            ActivityService activityService = new ActivityService();
            try {
                int activityId = activityService.add(newActivity);
                System.out.println("activity added !");

                if (selectedFiles != null) {
                    try {
                        ActivityImagesService activityImagesService = new ActivityImagesService();
                        String destinationDirectory = "C:/Users/manna/adventure-web/public/assets/uploads/activities/";

                        File destDir = new File(destinationDirectory);
                        if (!destDir.exists()) {
                            destDir.mkdirs();
                        }

                        for (File selectedFile : selectedFiles) {
                            String fileName = selectedFile.getName();
                            Path destinationPath = Paths.get(destinationDirectory + fileName);
                            Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                            ActivityImages image = new ActivityImages(fileName, activityId);
                            activityImagesService.add(image);
                            System.out.println("File saved to: " + destinationPath);

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



                nameField.clear();
                locationn.clear();
                type.clear();
                desc.clear();
                duration.clear();
                maxGuests.clear();
                minAge.clear();
                price.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            refreshActivities();
        }
    }

    @FXML
    private void chooseImage(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:/Users/manna/OneDrive/Bureau/test"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        Stage stage = (Stage) mainPane.getScene().getWindow();
        selectedFiles = fileChooser.showOpenMultipleDialog(stage);


    }


    private void refreshActivities() {
        activitiesVbox.getChildren().clear();
        System.out.println("activity box clear!");
        List<Activity> activities = new ArrayList<>();
        Connection connection = MyDataBase.getInstance().getConnection();
        ActivityService activityService = new ActivityService();
        try {
            activities = activityService.select();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            for (Activity activity : activities) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/activityTableItem.fxml"));
                Parent fxml = fxmlLoader.load();
                ActivityTableItem controller = fxmlLoader.getController();
                controller.setData(activity,this);
                controller.setPane(mainPane);
                activitiesVbox.getChildren().add(fxml);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException
        }

        activitiesCount.setText(String.valueOf(activities.size()));

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
            nameField.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        } else {
            nameField.setStyle("");
        }

        if (location.isEmpty()) {
            locationn.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        } else {
            locationn.setStyle("");
        }

        if (activityType.isEmpty()) {
            type.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        } else {
            type.setStyle("");
        }

        if (activityDescription.isEmpty()) {
            desc.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        } else {
            desc.setStyle("");
        }

        try {
            int activityDuration = Integer.parseInt(durationText);
            if (activityDuration <= 0) {
                duration.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
                isValid = false;
            } else {
                duration.setStyle("");
            }
        } catch (NumberFormatException e) {
            duration.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        }

        try {
            int maxGuestsValue = Integer.parseInt(maxGuestsText);
            if (maxGuestsValue <= 0) {
                maxGuests.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
                isValid = false;
            } else {
                maxGuests.setStyle("");
            }
        } catch (NumberFormatException e) {
            maxGuests.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        }

        try {
            int minAgeValue = Integer.parseInt(minAgeText);
            if (minAgeValue <= 0) {
                minAge.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
                isValid = false;
            } else {
                minAge.setStyle("");
            }
        } catch (NumberFormatException e) {
            minAge.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        }

        try {
            float activityPrice = Float.parseFloat(priceText);
            if (activityPrice <= 0) {
                price.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
                isValid = false;
            } else {
                price.setStyle("");
            }
        } catch (NumberFormatException e) {
            price.setStyle("-fx-border-color: transparent transparent red transparent;-fx-border-width: 0 0 2px 0;");
            isValid = false;
        }

        return isValid;
    }

    public void initChart() throws SQLException {
        activitiesChart.getData().forEach(series -> {
            series.getData().clear();
        });
        activitiesChart.getData().clear();
        List<Activity> activities = new ArrayList<>();
        ReservationService reservationService=new ReservationService();
        ActivityService activityService = new ActivityService();
        try {
            activities = activityService.select();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        XYChart.Series series = new XYChart.Series();
        for (Activity activity : activities) {
            int reservationCount = 0;
            List<Reservation> reservations = reservationService.selectByActivityId(activity.getId());
            for(Reservation reservation : reservations)
                reservationCount ++;
            series.getData().add(new XYChart.Data(String.valueOf(activity.getId()),reservationCount));
        }
        series.setName("Reservations in activities");

        activitiesChart.getData().add(series);

    }




    public void onActivityDeleted(Activity deletedActivity) throws SQLException {
        refreshActivities();
        initChart();
    }

    public void setStackPane(StackPane contentArea) {}

}