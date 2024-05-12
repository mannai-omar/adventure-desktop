package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Mail;
import service.UserService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;

public class ForgotPasswordController {

    @FXML
    private TextField code;

    @FXML
    private TextField email;

    @FXML
    private Button send;

    @FXML
    private Button verify;

    private String generatedCode;

    private UserService userService;

    private int userId;

    @FXML
    private void initialize() {
        email.setText("");
        userService = new UserService();

    }

    @FXML
    void sendCode(ActionEvent event) {
        // Check if the email exists in the database
        try {
            String userEmail = email.getText();
            UserService userService = new UserService();
             userId = userService.getUserIdByEmail(userEmail);
            if (userId == -1) {
                // If the email doesn't exist, show an alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Email not found in the database. Please enter a valid email.");
                alert.showAndWait();
                return; // Exit the method
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Inform the user if an error occurred while checking the email
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while checking the email. Please try again.");
            alert.showAndWait();
            return; // Exit the method
        }

        // Generate a random code
        generatedCode = generateRandomCode(6);

        // Send the random code via email
        try {
            Mail.send(email.getText(), generatedCode);
            // Inform the user that the code has been sent
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Code Sent");
            alert.setHeaderText(null);
            alert.setContentText("A verification code has been sent to your email.");
            alert.showAndWait();
        } catch (MessagingException e) {
            e.printStackTrace();
            // Inform the user if an error occurred while sending the email
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while sending the email. Please try again.");
            alert.showAndWait();
        }
    }


    @FXML
    void verifyCode(ActionEvent event) {
        String enteredCode = code.getText();

        // Check if the entered code matches the generated code
        if (enteredCode.equals(generatedCode)) {
            // If the code is correct, proceed to password change scene
            try {
                // Load the FXML file for the reset password scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resetPassword.fxml"));
                Parent root = loader.load();
                // Get the controller for the reset password scene
                ResetPasswordController resetPasswordController = loader.getController();

                // Pass the userId to the controller
                resetPasswordController.setUserId(userId);

                // Show the reset password scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

                System.out.println("Code verified. Proceed to password change scene.");
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the case where the reset password scene cannot be loaded
                // Inform the user or log an error message
            }
        } else {
            // If the code is incorrect, inform the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Code");
            alert.setHeaderText(null);
            alert.setContentText("The entered code is incorrect. Please try again.");
            alert.showAndWait();
        }
    }

    // Method to generate a random code
    private String generateRandomCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }
}
