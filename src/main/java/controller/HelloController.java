package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController implements Initializable {

    @FXML
    private Button frontUserBtn;

    @FXML
    private Button activities;
    @FXML
    private Button logoutB;

    @FXML
    private Button blogs;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button home;
    @FXML
    private Button usersButton;

    @FXML
    private Button shop;
    User currentUser;
    Boolean userIsAdmin ;
    private Stage stage;

    public void setLoginStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginController.getCurrentUser();
        String userRolesJson = currentUser.getRoles();
        String expectedRoleJson = "{\"role\": \"client\"}";
        if (userRolesJson.equals(expectedRoleJson))
            userIsAdmin=false;
        else
            userIsAdmin=true;

        if(userIsAdmin)
            usersButton.setVisible(true);

        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/home.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        home.setStyle("-fx-background-color: #2A332D;-fx-text-fill:  #ffffff;");

    }

    public void home(javafx.event.ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/home.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        home.setStyle("-fx-background-color: #2A332D;-fx-text-fill:  #ffffff;");
        activities.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        blogs.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        shop.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        usersButton.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        frontUserBtn.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
    }

    public void activities(javafx.event.ActionEvent actionEvent) throws IOException {

        if (!userIsAdmin) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activities.fxml"));
            Parent fxml = loader.load();
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/activitiesDashboard.fxml"));
            Parent fxml = loader.load();
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }
        activities.setStyle("-fx-background-color: #2A332D;-fx-text-fill:  #ffffff;");
        home.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        blogs.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        shop.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        usersButton.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        frontUserBtn.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
    }

    public void blogs(javafx.event.ActionEvent actionEvent) throws IOException {
        if (userIsAdmin) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherPublication.fxml"));
            Parent fxml = loader.load();
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Blog/AfficherPublicationF.fxml"));
            Parent fxml = loader.load();
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }
        blogs.setStyle("-fx-background-color: #2A332D;-fx-text-fill:  #ffffff;");
        home.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        activities.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        shop.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        usersButton.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        frontUserBtn.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
    }

    public void shop(javafx.event.ActionEvent actionEvent) throws IOException {
        if (userIsAdmin) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminProduct.fxml"));
            Parent fxml = loader.load();
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowProduct.fxml"));
            Parent fxml = loader.load();
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }
        shop.setStyle("-fx-background-color: #2A332D;-fx-text-fill:  #ffffff;");
        home.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        blogs.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        activities.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        usersButton.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        frontUserBtn.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
    }

    @FXML
    private void user(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/userFront.fxml"));
        Parent fxml = loader.load();
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

        frontUserBtn.setStyle("-fx-background-color: #2A332D;-fx-text-fill:  #ffffff;");
        home.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        blogs.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        activities.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        shop.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        usersButton.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
    }

    private void loadScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle("Adventure");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutB.getScene().getWindow();
        stage.close();
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 639, 400);
            stage.setScene(scene);
            stage.setTitle("Adventure");
            Image icon = new Image(getClass().getResourceAsStream("/Front/Blog/2.png"));
            stage.getIcons().add(icon);
            stage.setResizable(false);

            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void users(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/showUser.fxml"));
        Parent fxml = loader.load();
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        usersButton.setStyle("-fx-background-color: #2A332D;-fx-text-fill:  #ffffff;");
        home.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        blogs.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        shop.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        activities.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
        frontUserBtn.setStyle("-fx-background-color: #1D231F;-fx-text-fill:  #ffffff;");
    }

}