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
import javafx.stage.Window;
import model.User;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFrontController {

    @FXML
    private Button deleteUserFront;

    @FXML
    private Button editUserFront;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelMdp;

    User currentUser = LoginController.getCurrentUser();

    private final UserService userService = new UserService();


    public void initialize() {


        labelEmail.setText(currentUser.getEmail());
        labelMdp.setText(currentUser.getPassword());
    }


    public void deleteUser(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete User");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this user?");

        // Show the dialog and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    System.out.println("Deleting user " + currentUser.getId());
                    userService.delete(currentUser.getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("User deleted successfully.");
                logout();

            } else {
                // User clicked Cancel or closed the dialog, do nothing
                System.out.println("Deletion cancelled.");
            }
        });
    }


    private void logout() {
        // Close all open stages
        closeOpenStages();

        // Clear the current user session
        currentUser = null;

        // Additional logout actions if needed
    }

    private void closeOpenStages() {
        // Get all open stages
        List<Stage> openStages = new ArrayList<>();
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage) {
                openStages.add((Stage) window);
            }
        }

        // Close each open stage
        for (Stage stage : openStages) {
            stage.close();
        }
    }

    public void changerMDP(ActionEvent actionEvent) {
        try {
            // Load the updateuserfront.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateUserFront.fxml"));
            Parent root = loader.load();

            // Access the current stage
            Stage stage = (Stage) editUserFront.getScene().getWindow();

            // Set the scene of the current stage to the new scene
            stage.setScene(new Scene(root));
            stage.setTitle("Update User");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
