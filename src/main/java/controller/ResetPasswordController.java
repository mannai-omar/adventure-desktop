package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import service.UserService;

import java.sql.SQLException;

public class ResetPasswordController {

    @FXML
    private PasswordField mdpField1;

    @FXML
    private PasswordField mdpField2;

    private UserService userService;

    private int userId;

    @FXML
    public void initialize() {
        userService = new UserService();
    }

    // Setter method
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    public void changerMDP(ActionEvent actionEvent) {
        String newPassword = mdpField1.getText();
        String confirmPassword = mdpField2.getText();

        if (!newPassword.equals(confirmPassword)) {
            // Passwords don't match, show an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match. Please try again.");
            alert.showAndWait();
            return;
        }

        try {
            // Update the password in the database
            userService.updatePasswordById(userId, newPassword);

            // Password updated successfully, show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Password updated successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            // Inform the user if an error occurred while updating the password
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating the password. Please try again.");
            alert.showAndWait();
        }
    }
}
