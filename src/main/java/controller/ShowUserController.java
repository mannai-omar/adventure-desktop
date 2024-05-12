package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import service.UserService;
import service.statuserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ShowUserController {

    @FXML
    private AnchorPane mainPane;

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



    @FXML
    private TableColumn<User, String> emailColumn;


    @FXML
    private TableColumn<User, Integer> ivColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> rolesColumn;

    @FXML
    private TableView<User> tableViewUser;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private Button createuserbtn;

    @FXML
    private Button deleteuserbtn;

    @FXML
    private Button updateuserbtn;

    @FXML
    private TextField searchBar;


    private final UserService userService = new UserService();
    private service.statuserService statuserService;
    private FilteredList<User> filteredUsers;

    public void initialize() {
        // Initialize columns
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        rolesColumn.setCellValueFactory(new PropertyValueFactory<>("roles"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        ivColumn.setCellValueFactory(new PropertyValueFactory<>("is_verified"));
        this.statuserService = new statuserService();

        // Load data and set up live search functionality
        try {
            List<User> users = userService.getAllUsers();
            filteredUsers = new FilteredList<>(FXCollections.observableArrayList(users));
            tableViewUser.setItems(filteredUsers);
        } catch (SQLException e) {
            showAlert("Error fetching users from the database: " + e.getMessage());
            tableViewUser.setItems(FXCollections.emptyObservableList());
            filteredUsers = new FilteredList<>(FXCollections.emptyObservableList());
        }

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(createFilterPredicate(newValue));
        });
    }


    public void loadData() {
        try {
            List<User> users = userService.getAllUsers();
            ObservableList<User> userList = FXCollections.observableArrayList(users);
            tableViewUser.setItems(userList);
        } catch (SQLException e) {
            showAlert("Error fetching courses from the database: " + e.getMessage());
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    void addUser(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addUser.fxml"));
            Parent root = loader.load();

            // Get the controller of the addCours.fxml
            AddUserController controller = loader.getController();
            controller.setPane(mainPane);
            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(root);
            // Show the add course window
            /*Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add User");
            stage.showAndWait();

            // After the add course window is closed, refresh the TableView
            loadData(); // Reload the data from the database*/
        } catch (IOException e) {
            e.printStackTrace(); // Handle any potential errors while loading the FXML file
        }
    }


    public void deleteUser(ActionEvent actionEvent) {

        // Get the selected course from the table
        User selectedUser = tableViewUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {

                // Delete the course from the database using the instance method
                userService.delete(selectedUser.getId());

                // Remove the course from the TableView
                tableViewUser.getItems().remove(selectedUser);

                showAlert("User deleted successfully!");
            } catch (SQLException e) {
                showAlert("Failed to delete user: " + e.getMessage());
            }
        } else {
            showAlert("Please select a user to delete.");
        }

    }

    public void updateUser(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateUser.fxml"));
            Parent root = loader.load();
            UpdateUserController controller = loader.getController();

            // Set the showCoursController reference in the UpdateCoursController
            controller.setShowController(this);
            controller.setPane(mainPane);

            // Get the selected course from the table
            User selectedUser = tableViewUser.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                controller.setselectedUser(selectedUser);
                /*Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update Course");
                stage.show();*/
                mainPane.getChildren().clear();
                mainPane.getChildren().setAll(root);
            } else {
                showAlert("Please select a course to update.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    private void ViewLoginChart(ActionEvent actionEvent) {
        try {
            Map<String, Integer> loginData = statuserService.countLoginsPerDay();

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Login Count per Day");
            xAxis.setLabel("Date");
            yAxis.setLabel("Login Count");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (Map.Entry<String, Integer> entry : loginData.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            Scene scene = new Scene(barChart, 800, 600);
            barChart.getData().add(series);

            Stage stage = new Stage();
            stage.setTitle("Login Chart");
            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Predicate<User> createFilterPredicate(String searchText) {
        return user -> {
            return user.getEmail().toLowerCase().contains(searchText.toLowerCase());
        };
    }


}
