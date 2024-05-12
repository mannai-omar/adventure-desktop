package controller.front.Blog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
public class CustomerCard extends Pane {

    protected final ImageView photo;
    protected final Label name;
    protected final Label mobile;
    protected final Label label;
    protected final Label date;
    protected final Label visits;
    protected final Label label0;
    protected final HBox iconContainer;

    protected final Button deleteButton;
    protected final Button modifyButton;
    private int ID;

    public CustomerCard(int Id, String Name,String contenu, String Image) {
        setLayoutY(10.0);
        photo = new ImageView();
        name = new Label();
        mobile = new Label();
        label = new Label();
        date = new Label();
        visits = new Label();
        label0 = new Label();
        iconContainer = new HBox();
        deleteButton = new Button();
        modifyButton = new Button();

        setId(Id + "");
        setPrefHeight(233.0);
        setPrefWidth(245.0);
        setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(3);
        dropShadow.setWidth(3);
        dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
        setEffect(dropShadow);

        photo.setImage(new Image("https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg"));
        photo.setLayoutX(50);
        photo.setLayoutY(25);
        photo.setFitHeight(100);
        photo.setFitWidth(150);

        name.setAlignment(Pos.TOP_LEFT);
        name.setContentDisplay(ContentDisplay.CENTER);
        name.setLayoutX(14.0);
        name.setLayoutY(130.0);
        name.setPrefHeight(26.0);
        name.setPrefWidth(215.0);
        name.setText(Name);
        name.setFont(Font.font("System", FontWeight.BOLD, 17.0));
        mobile.setAlignment(Pos.TOP_LEFT);
        mobile.setContentDisplay(ContentDisplay.CENTER);
        mobile.setLayoutX(14.0);
        mobile.setLayoutY(150);
        mobile.setPrefHeight(26.0);
        mobile.setPrefWidth(215.0);
        mobile.setText(String.valueOf(contenu)+" dt");
        mobile.setFont(Font.font("System", FontWeight.BOLD, 17.0));



        iconContainer.setAlignment(Pos.CENTER_RIGHT);
        iconContainer.setPrefHeight(40.0);
        iconContainer.setPrefWidth(200.0);
        iconContainer.setLayoutX(24);
        iconContainer.setLayoutY(206);
        deleteButton.setText("ADD");
        deleteButton.setPrefHeight(32.0);
        deleteButton.setPrefWidth(80);
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Are you sure you want to delete this customer?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {

                    });
        });
        modifyButton.setText("Details");
        modifyButton.setPrefHeight(32.0);
        modifyButton.setPrefWidth(85);
        modifyButton.getStyleClass().add("modify-button");
        modifyButton.setOnAction(e -> {
            try {
                System.out.println("heeeloo");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        iconContainer.getChildren().addAll(deleteButton, modifyButton);

        getChildren().addAll(photo, name,mobile, label, label0, iconContainer);

    }
}
