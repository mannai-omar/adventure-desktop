package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.User;
import service.UserService;

import java.sql.SQLException;

public class updateUserFrontController {


    @FXML
    private PasswordField mdpField1;

    @FXML
    private PasswordField mdpField2;

    private final UserService userService = new UserService();

    User currentUser = LoginController.getCurrentUser();


    public void changerMDP(ActionEvent actionEvent) {
        String mdp1 = mdpField1.getText();
        String mdp2 = mdpField2.getText();

        if (mdp1.equals(mdp2)) {
            // Check if the password is valid
            if (isValidPassword(mdp1)) {
                try {
                    currentUser.setPassword(mdp1);
                    userService.updatePassword(currentUser);
                    // Password updated successfully
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Information");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Password updated successfully");
                    successAlert.showAndWait();

                    Stage stage = (Stage) mdpField1.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    // Password update failed
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Failed to update password.");
                    errorAlert.showAndWait();
                    e.printStackTrace(); // Print the exception stack trace for debugging
                }
            } else {
                // Password is not valid
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Password is not valid. It must be at least 6 characters long and contain at least one number.");
                alert.showAndWait();
            }
        } else {
            // Passwords do not match
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match.");
            alert.showAndWait();
        }
    }


    private boolean isValidPassword(String password) {
        // Check if password is not null, not empty, and has length at least 6
        if (password != null && !password.isEmpty() && password.length() >= 6) {
            // Check if password contains at least one number
            boolean containsNumber = false;
            for (char c : password.toCharArray()) {
                if (Character.isDigit(c)) {
                    containsNumber = true;
                    break;
                }
            }
            return containsNumber;
        }
        return false;
    }

}
