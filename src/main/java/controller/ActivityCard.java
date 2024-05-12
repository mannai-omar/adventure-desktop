package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Activity;
import model.ActivityImages;
import service.ActivityImagesService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ActivityCard {

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label locationn;

    @FXML
    private Pane pane;

    Activity activity;

    public void setData(Activity activity) {
        Rectangle clip = new Rectangle(
                image.getFitWidth(), image.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
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
        if(!images.isEmpty()){
            ActivityImages firstImage = images.get(0);
            //Image image1 = new Image(getClass().getResourceAsStream("/assets/activityImages/" + firstImage.getUrl()));
            Image image1= new Image("C:/Users/manna/adventure-web/public/assets/uploads/activities/"+firstImage.getUrl());
            image.setImage(image1);
        }

        name.setText(activity.getName());
        locationn.setText(activity.getLocation());
        price.setText(String.valueOf(activity.getPrice()));
        this.activity = activity;
    }



    public void setPane(Pane pane) {
        this.pane=pane;
    }

    @FXML
    private void showDetails(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activityDetails.fxml"));
            Parent fxml = loader.load();
            ActivityDetails controller = loader.getController();
            controller.setData(activity);
            controller.setPane(pane);
            pane.getChildren().clear();
            pane.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
