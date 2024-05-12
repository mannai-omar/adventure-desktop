package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateUserController {


    @FXML
    private Button updateUserBtn;

    @FXML
    private TextField updateemailuser;

    @FXML
    private ChoiceBox<String> updateisuser;

    @FXML
    private TextField updatepassworduser;

    @FXML
    private ChoiceBox<String> updateroleuser;


    private User selectedUser;

    private ShowUserController showUserController;

    private final UserService userService = new UserService();
    private AnchorPane pane;


    public void initialize() {
        // Initialize the choice box with options
        ObservableList<String> options = FXCollections.observableArrayList("true", "false");
        updateisuser.setItems(options);
        ObservableList<String> roles = FXCollections.observableArrayList("admin", "client");
        updateroleuser.setItems(roles);
    }

    public void setShowController(ShowUserController showUserController) {

        this.showUserController = showUserController;
    }




    public void setselectedUser(User user) {

        this.selectedUser = user;
        updateemailuser.setText(String.valueOf(user.getEmail()));
        updatepassworduser.setText(user.getPassword());
        if (user.getRoles().contains("admin")) {
            updateroleuser.setValue("admin");
        } else if (user.getRoles().contains("client")) {
            updateroleuser.setValue("client");
        }

        if (user.getIs_verified()==0) {
            updateisuser.setValue("false");
        } else if (user.getIs_verified()==1) {
            updateisuser.setValue("true");
        }

    }

    public void updateUser(ActionEvent actionEvent) {

        try {
            // Input validation
            String email = updateemailuser.getText().trim();
            String pswd = updatepassworduser.getText().trim();
            String selectedRole = updateroleuser.getValue();
            String isVerified = updateisuser.getValue();

            // Check if email is valid
            if (!isValidEmail(email)) {
                showAlert("Invalid Email");
                return;
            }

            // Check if password is valid
        /*if (!isValidPassword(password)) {
            showAlert("Invalid Password", "Password must be at least 6 characters long.");
            return;
        }*/


            String roles;
            switch (selectedRole) {
                case "admin":
                    roles = "{\"role\": \"admin\"}";
                    break;
                case "client":
                    roles = "{\"role\": \"client\"}";
                    break;
                default:
                    showAlert("Invalid Role");
                    return;
            }

            int iv;
            switch (isVerified) {
                case "true":
                    iv = 1;
                    break;
                case "false":
                    iv = 0;
                    break;
                default:
                    showAlert("Invalid is_verified");
                    return;
            }

            // Use CoursService to update the cours in the database

            selectedUser.setEmail(email);
            selectedUser.setPassword(pswd);
            selectedUser.setIs_verified(iv);
            selectedUser.setRoles(roles);
            userService.update(selectedUser);
            System.out.println(selectedUser);

            // Show success message
            showAlert("User updated successfully!");

            /*// Close the window
            Stage stage = (Stage) updateemailuser.getScene().getWindow();
            stage.close();*/

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showUser.fxml"));
            Parent fxml = loader.load();
            pane.getChildren().clear();
            pane.getChildren().add(fxml);

            // Refresh the table view if showCoursController is not null
            if (showUserController != null) {
                showUserController.loadData();
            } else {
                System.out.println("showCoursController is null");
            }
        } catch (SQLException e) {
            showAlert("Failed to update User: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private boolean isValidEmail(String email) {
        // Implement your email validation logic here
        // You can use regular expressions or other methods to validate email format
        return email != null && !email.isEmpty() && email.contains("@");
    }

    private boolean isValidPassword(String password) {
        // Implement your password validation logic here
        return password != null && password.length() >= 6;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setPane(AnchorPane mainPane) {
        this.pane=mainPane;
    }
}
