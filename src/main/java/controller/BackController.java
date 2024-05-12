package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;


public class BackController {

    @FXML
    private Button btnBoutique;

    @FXML
    private Button btnGuide;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPack;

    @FXML
    private Button btnTransport;

    @FXML
    private Button btnUtilisateur;

    @FXML
    private Button btnVisite;


    private Connection connection;


    @FXML
    private void initialize() {
        btnBoutique.setOnAction(this::handleBtnBoutique);
        btnGuide.setOnAction(this::handleBtnGuide);
        btnHome.setOnAction(this::handleBtnHome);
        btnUtilisateur.setOnAction(this::handleBtnUtilisateur);
        btnVisite.setOnAction(this::handleBtnVisite);
        btnTransport.setOnAction(this::handleBtnTransport);
        btnPack.setOnAction(this::handleBtnPack);
        btnLogout.setOnAction(this::handleBtnLogout);

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
        loadScene("/showUser.fxml");
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
            Stage stage = (Stage) btnBoutique.getScene().getWindow(); // Get the current stage
            stage.setTitle("Ziyara");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










}
