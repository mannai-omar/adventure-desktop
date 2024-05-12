package controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.ActivityImages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageSlider {

    @FXML
    private Button back;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private Button next;

    private List<Image> images = new ArrayList<>();

    private int currentIndex = 0;




    public void setImages(List<ActivityImages> images) {
        System.out.println(images);
        for (ActivityImages image : images) {
            addImageView(image.getUrl());
            System.out.println(image.getUrl());
        }
        updateImage();
    }
    public void addImageView(String path) {
        File file = new File("C:/Users/manna/adventure-web/public/assets/uploads/activities/" + path);
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            images.add(imageView.getImage());
        } else {
            System.out.println("Image file does not exist at path: " + file.getAbsolutePath());
        }
    }


    private void updateImage() {
        mainAnchor.getChildren().clear();
        ImageView imageView = new ImageView(images.get(currentIndex));
        imageView.setFitWidth(700);
        imageView.setFitHeight(170);
        Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);

        // Snapshot the rounded imageView
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage roundedImage = imageView.snapshot(parameters, null);

        imageView.setImage(roundedImage);
        mainAnchor.getChildren().add(imageView);
    }

    public void translateAnimation(double duration, Node node, double width) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        translateTransition.setByX(width);
        translateTransition.play();
    }

    @FXML
    void next(ActionEvent event) {
        if (currentIndex < images.size() - 1)
            currentIndex++;
        else
            currentIndex=0;

        updateImage();
    }

    @FXML
    void back(ActionEvent event) {
        if (currentIndex > 0)
            currentIndex--;
        else
            currentIndex=images.size()-1;

        updateImage();

    }

}
