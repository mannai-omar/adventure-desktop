package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Category;
import model.Product;
import service.ServiceCategory;
import service.ServiceProduct;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class AddProduct {

    @FXML
    private MenuButton CatMB;

    @FXML
    private TextArea DescTA;

    @FXML
    private Button ImageTF;

    @FXML
    private TextField NameTF;

    @FXML
    private TextField PriceTF;

    private final ServiceCategory sc = new ServiceCategory();
    private final ServiceProduct sp = new ServiceProduct();
    private int selectedCategoryId = -1;

    @FXML
    void initialize() {
        populateCategoryMenu();
    }

    private void populateCategoryMenu() {
        try {
            List<Category> categories = sc.read();
            for (Category category : categories) {
                MenuItem menuItem = new MenuItem(category.getName());
                menuItem.setOnAction(event -> {
                    CatMB.setText(category.getName());
                    selectedCategoryId = category.getId(); // Capture the selected category ID
                });
                CatMB.getItems().add(menuItem);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        }
    }

    @FXML
    void AddProducts(ActionEvent event) {
        String name = NameTF.getText().trim();
        String desc = DescTA.getText().trim();
        String image = ImageTF.getText().trim();
        String priceStr = PriceTF.getText().trim();

        if (name.isEmpty() || desc.isEmpty() || image.isEmpty() || priceStr.isEmpty()) {
            showAlert("Error", "Empty Fields", "Please fill in all fields.");
            return;
        }

        // Check if the price input is a valid number
        int price;
        try {
            price = Integer.parseInt(priceStr);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Price", "Please enter a valid number for the price.");
            return;
        }

        // Check if a category is selected
        if (selectedCategoryId == -1) {
            showAlert("Error", "No Category Selected", "Please select a category.");
            return;
        }

        // Add the product to the database
        Product p = new Product(selectedCategoryId, price, name, image, desc);
        try {
            sp.add(p);
            showAlert("Success", "Product Added", "Product successfully added.");
        } catch (Exception e) {
            showAlert("Error", "Database Error", "An error occurred while adding the product to the database.");
        }
    }

    @FXML
    void SelectProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminProduct.fxml"));
            Parent root = loader.load();

            // Show the scene
            Stage stage = new Stage(); // Create a new stage for the ShowProduct view
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        // Set the initial directory for file chooser
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        // Add filters to restrict the file types to images only
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            // Set the selected file path to the Image Button
            ImageTF.setText(selectedFile.getAbsolutePath());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
