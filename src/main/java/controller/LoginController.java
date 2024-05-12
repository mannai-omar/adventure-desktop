package controller;

/*import com.dlsc.formsfx.model.structure.PasswordField;*/

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Statuser;
import model.User;
import service.UserService;
import service.statuserService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.prefs.Preferences;

public class LoginController {


    @FXML
    private Button alreadyBtn;

    @FXML
    private Button si_loginForm;

    @FXML
    private TextField si_email;

    @FXML
    private PasswordField si_password;

    @FXML
    private AnchorPane sideForm;

    @FXML
    private TextField re_email;

    @FXML
    private PasswordField re_password;

    @FXML
    private PasswordField re_password1;

    @FXML
    private Button si_loginBtn;

    @FXML
    private Button registerBtn;



    @FXML
    private ChoiceBox<String> choiceBoxRoles;

    private UserService userService;
    private statuserService statuserService;


    private Connection connection;

    private static User currentUser;


    @FXML
    private CheckBox rememberMeCheckbox;

    private static final String REMEMBER_ME_EMAIL_KEY = "rememberMeEmail";
    private static final String REMEMBER_ME_PASSWORD_KEY = "rememberMePassword";






    @FXML
    private void initialize() throws SQLException {

        rememberMeCheckbox.setSelected(true);
        registerBtn.setOnAction(this::handleRegisterButtonAction);
        si_loginBtn.setOnAction(this::handleLoginButtonAction);
        userService = new UserService();
        statuserService = new statuserService();

        ObservableList<String> roles = FXCollections.observableArrayList("admin", "client");
        choiceBoxRoles.setItems(roles);

        // Load saved login info if remember me is checked
        if (rememberMeCheckbox.isSelected()) {
            String savedEmail = getSavedEmail();
            String savedPassword = getSavedPassword();
            if (savedEmail != null && savedPassword != null) {
                si_email.setText(savedEmail);
                si_password.setText(savedPassword);
            }
        }





    }




    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == si_loginForm) {
            slider.setNode(sideForm);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(0.5));
            slider.setOnFinished((ActionEvent e) -> {
                alreadyBtn.setVisible(true);
                si_loginForm.setVisible(false);
            });
            slider.play();
        } else if (event.getSource() == alreadyBtn) {
            slider.setNode(sideForm);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(0.5));
            slider.setOnFinished((ActionEvent e) -> {
                alreadyBtn.setVisible(false);
                si_loginForm.setVisible(true);
            });
            slider.play();
        }

    }


    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        String email = re_email.getText();
        String password = re_password.getText();
        String password1 = re_password1.getText();
        String selectedRole = choiceBoxRoles.getValue();

        if (!password.equals(password1)) {
            showAlert("Invalid Password", "Passwords do not match. Please retype your password.");
            return;
        }

        // Check if email is valid
        if (!isValidEmail(email)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        // Check if password is valid
        if (!isValidPassword(password)) {
            showAlert("Invalid Password", "Password must be at least 6 characters long and contains at least one number.");
            return;
        }

        String roles;
        switch (selectedRole) {
            case "admin":
                roles = "{\"role\": \"admin\"}";
                break;
            case "client":
                roles = "{\"role\": \"client\"}";
                break;
            default:
                showAlert("Invalid Role", "Please select a valid role.");
                return;
        }

        // If email and password are valid, proceed with registration

        User user = new User(email, roles, password, 0, 0);


        try {
            userService.create(user);
            showAlert("Registration Successful", "User registered successfully!");

            TranslateTransition slider = new TranslateTransition();
            slider.setNode(sideForm);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(0.5));
            slider.setOnFinished((ActionEvent e) -> {
                alreadyBtn.setVisible(false);
                si_loginForm.setVisible(true);
            });
            slider.play();

        } catch (SQLException e) {
            showAlert("Registration Failed", "Failed to register user. Please try again later.");
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        // Implement your email validation logic here
        // You can use regular expressions or other methods to validate email format
        return email != null && !email.isEmpty() && email.contains("@");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }





    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String email = si_email.getText();
        String password = si_password.getText();
        boolean rememberMe = rememberMeCheckbox.isSelected();
        if (rememberMe) {
            saveLoginInfo(email, password);
        } else {
            clearLoginInfo();
        }




        try {
            User user = userService.authenticate(email, password);
            if (user != null) {
                String r = user.getRoles();
                System.out.println(user);
                setCurrentUser(user);



                // Check user role
                if (user.getRoles().contains("admin")) {
                    System.out.println("Redirecting to admin dashboard...");
                    closeLoginInterface();
                    loadBack();

                } else if (user.getRoles().contains("client")) {
                    // Insert into statuser table
                    LocalDate currentDate = LocalDate.now();
                    //Statuser statuser = new Statuser(user.getId(), currentDate);
                    //statuserService.create(statuser); // Assuming you have a method to insert a Statuser object into the database
                    System.out.println("Redirecting to client site...");
                    closeLoginInterface();
                    loadFront(email);

                } else {
                    showAlert("Login Failed", "User role not recognized.");
                }
            } else {
                showAlert("Login Failed", "Invalid email or password.");
            }
        } catch (SQLException e) {
            showAlert("Login Failed", "Failed to authenticate user. Please try again later.");
            e.printStackTrace();
        }
    }

    @FXML
    void loadFront(String email) {
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
            Parent root = loader.load();
            /*FrontController controller = loader.getController();
            controller.displayEmail(email);*/
            Stage stage = new Stage();
            stage.setScene(new Scene(root,900,550));
            stage.setTitle("Adventure");
            Image icon = new Image(getClass().getResourceAsStream("/Front/Blog/2.png"));
            stage.getIcons().add(icon);
            HelloController helloController = loader.getController();
            helloController.setLoginStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
            Parent root = loader.load();
            //BackController controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root,900,550));
            stage.setTitle("Adventure");
            Image icon = new Image(getClass().getResourceAsStream("/Front/Blog/2.png"));
            stage.getIcons().add(icon);
            HelloController helloController = loader.getController();
            helloController.setLoginStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void closeLoginInterface() {
        // Get the stage of any node in the scene hierarchy
        Stage stage = (Stage) si_loginBtn.getScene().getWindow(); // Assuming si_loginBtn is a node in the scene hierarchy
        // Close the stage
        stage.close();
    }


    // Method to set the current user
    private static void setCurrentUser(User user) {
        currentUser = user;
    }

    // Method to get the current user
    public static User getCurrentUser() {
        return currentUser;
    }




    public void forgotPassword(ActionEvent actionEvent) {

        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/forgotPassword.fxml"));

            // Create a new stage
            Stage stage = new Stage();

            // Set the scene with the loaded FXML file
            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void saveLoginInfo(String email, String password) {
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        prefs.put(REMEMBER_ME_EMAIL_KEY, email);
        prefs.put(REMEMBER_ME_PASSWORD_KEY, password);
    }

    private void clearLoginInfo() {
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        prefs.remove(REMEMBER_ME_EMAIL_KEY);
        prefs.remove(REMEMBER_ME_PASSWORD_KEY);
    }

    private String getSavedEmail() {
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        return prefs.get(REMEMBER_ME_EMAIL_KEY, null);
    }

    private String getSavedPassword() {
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        return prefs.get(REMEMBER_ME_PASSWORD_KEY, null);
    }



}

