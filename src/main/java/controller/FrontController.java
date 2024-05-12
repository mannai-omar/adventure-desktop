package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class FrontController {

    @FXML
    private Label emailLabel;

    @FXML
    private Button frontBoutiqueBtn;

    @FXML
    private Button frontGuideBtn;

    @FXML
    private Button frontHomeBtn;

    @FXML
    private Button frontPackBtn;

    @FXML
    private Button frontTransportBtn;

    @FXML
    private Button frontUserBtn;

    @FXML
    private Button frontVisiteBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private void initialize() {
        frontBoutiqueBtn.setOnAction(this::handleBtnBoutique);
        frontGuideBtn.setOnAction(this::handleBtnGuide);
        frontHomeBtn.setOnAction(this::handleBtnHome);
        frontUserBtn.setOnAction(this::handleBtnUtilisateur);
        frontVisiteBtn.setOnAction(this::handleBtnVisite);
        frontTransportBtn.setOnAction(this::handleBtnTransport);
        frontPackBtn.setOnAction(this::handleBtnPack);


    }



    private void handleBtnLogout(ActionEvent actionEvent) {
        System.out.println("Logout");
    }

    private void handleBtnPack(ActionEvent actionEvent) {
        System.out.println("Redirecting to gestion pack");
    }

    private void handleBtnTransport(ActionEvent actionEvent) {
        System.out.println("Redirecting to gestion transport");
    }

    private void handleBtnVisite(ActionEvent actionEvent) {
        System.out.println("Redirecting to gestion visite ");
    }

    private void handleBtnUtilisateur(ActionEvent actionEvent) {
        System.out.println("Redirecting to gestion utilisateur");
        loadScene("/userFront.fxml");
    }



    private void handleBtnHome(ActionEvent actionEvent) {
        System.out.println("Redirecting to home");
    }

    private void handleBtnGuide(ActionEvent actionEvent) {
        System.out.println("Redirecting to gestion guide");
    }

    private void handleBtnBoutique(ActionEvent actionEvent) {
        System.out.println("Redirecting to gestion ");
        //loadScene("/path/to/your/BoutiquePage.fxml");
    }

    // Load the desired FXML file and show it in a new stage
    private void loadScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle("Ziyara");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void displayEmail(String email) {
        emailLabel.setText("Welcome: " + email);
    }

    public void logOut(ActionEvent actionEvent) {

        Stage stage;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to log out?");
            if (alert.showAndWait().get()== ButtonType.OK){
                stage = (Stage) logOutBtn.getScene().getWindow();
                System.out.println("you logged out");
                stage.close();
            }
        }

}
