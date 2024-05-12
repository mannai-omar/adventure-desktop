package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Activity;
import model.ActivityImages;
import service.ActivityImagesService;
import service.ActivityService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

public class ActivityTableItem {
    @FXML
    private Label duration;

    @FXML
    private Label locationn;

    @FXML
    private Label name;

    @FXML
    private Label type;

    @FXML
    private ImageView image;

    Activity activity;
    private ScrollPane pane;
    private ActivityDeletedListener activityDeletedListener;

    public void setPane(ScrollPane pane) {
        this.pane=pane;
    }
    public void setData(Activity activity,ActivityDeletedListener listener){
        Rectangle clip = new Rectangle(
                image.getFitWidth(), image.getFitHeight()
        );
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        image.setClip(clip);

        // Snapshot the rounded image
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage roundedImage = image.snapshot(parameters, null);

        image.setImage(roundedImage);



        ActivityImagesService activityImagesService = new ActivityImagesService();
        List<ActivityImages> images;
        try {
            images = activityImagesService.selectByActivityId(activity.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(!images.isEmpty()) {
            ActivityImages firstImage = images.get(0);
            //String imagePath = "file:///C:/Users/manna/IdeaProjects/pi/src/main/resources/assets/activityImages/" + firstImage.getUrl();
            Image image1 = new Image("C:/Users/manna/adventure-web/public/assets/uploads/activities/"+firstImage.getUrl());
            //System.out.println(imagePath);
            image.setImage(image1);
        }
        name.setText(activity.getName());
        locationn.setText(activity.getLocation());
        type.setText(activity.getType());
        duration.setText(String.valueOf(activity.getDuration()));
        this.activity=activity;
        this.activityDeletedListener = listener;
    }

    @FXML
    private void deleteActivity(javafx.event.ActionEvent actionEvent) {
        ActivityService activityService = new ActivityService();
        deleteRelatedUploads(activity.getId());
        try {
            activityService.delete(activity.getId());
            System.out.println("activity deleted!");
            if (activityDeletedListener != null) {
                activityDeletedListener.onActivityDeleted(activity);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void showActivity(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/showActivity.fxml"));
        Parent fxml = loader.load();
        ShowActivity controller = loader.getController();
        controller.setData(activity);
        controller.setPane(pane);
        pane.setContent(fxml);
    }

    public interface ActivityDeletedListener {
        void onActivityDeleted(Activity deletedActivity) throws SQLException;
    }

    public void deleteRelatedUploads(int deletedActivity){
        try {
            ActivityImagesService activityImagesService = new ActivityImagesService();
            List<ActivityImages> images = activityImagesService.selectByActivityId(deletedActivity);
            System.out.println(images);
            String destinationDirectory = "C:/Users/manna/IdeaProjects/pi/src/main/resources/assets/activityImages/";
            for (ActivityImages image : images) {
                Path imagePath = Paths.get(destinationDirectory + image.getUrl());
                Files.deleteIfExists(imagePath);
                System.out.println("Deleted image: " + imagePath);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
