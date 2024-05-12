package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import model.Category;
import model.Product;
import service.ServiceCategory;
import service.ServiceProduct;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminProductController {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Product, String> CatTC;

    @FXML
    private TableColumn<Product, String> DescTC;

    @FXML
    private TableColumn<Product, String> ImgTC;

    @FXML
    private TableColumn<Product, String> NameTC;

    @FXML
    private TableColumn<Product, Integer> PriceTC;

    @FXML
    private TableView<Product> tableView;
    @FXML
    private AnchorPane mainPane;

    ServiceProduct sp = new ServiceProduct();
    ObservableList<Product> obs;

    @FXML
    void DeleteProduct(ActionEvent event) {
        Product p = tableView.getSelectionModel().getSelectedItem();
        sp.delete(p);
        obs.remove(p);
    }

    @FXML
    void initialize() {
        try {
            List<Product> list = sp.select();
            obs = FXCollections.observableArrayList(list);

            NameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
            PriceTC.setCellValueFactory(new PropertyValueFactory<>("price"));
            DescTC.setCellValueFactory(new PropertyValueFactory<>("description"));
            ImgTC.setCellValueFactory(new PropertyValueFactory<>("image"));

            CatTC.setCellValueFactory(cellData -> {
                Product product = cellData.getValue();
                try {
                    String categoryName = getCategoryName(product.getCat_id());
                    return new SimpleStringProperty(categoryName);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new SimpleStringProperty("");
                }
            });

            tableView.setItems(obs);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private String getCategoryName(int categoryId) throws SQLException {
        ServiceCategory serviceCategory = new ServiceCategory();
        List<Category> categories = serviceCategory.read();
        for (Category category : categories) {
            if (category.getId() == categoryId) {
                return category.getName();
            }
        }
        return "";
    }

    @FXML
    void EditProduct(ActionEvent actionEvent) {
        // Retrieve the selected product
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Open an edit dialog for the selected product
            openEditDialog(selectedProduct);
        } else {
            // Show an error message or prompt the user to select a product
            showAlert("Error", "No Product Selected", "Please select a product to edit.");
        }
    }

    private void openEditDialog(Product product) {
        // Create a dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Product");
        dialog.setHeaderText("Edit Product Details");

        // Set the button types (OK and Cancel)
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create the grid pane and add text fields for editing
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField(product.getName());
        TextField priceField = new TextField(String.valueOf(product.getPrice()));
        TextField descriptionField = new TextField(product.getDescription());
        TextField imageField = new TextField(product.getImage());

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Price:"), 0, 1);
        grid.add(priceField, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descriptionField, 1, 2);
        grid.add(new Label("Image:"), 0, 3);
        grid.add(imageField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the name field by default
        Platform.runLater(() -> nameField.requestFocus());

        // Convert the result to a pair when the OK button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(nameField.getText(), priceField.getText());
            }
            return null;
        });

        // Show the dialog and handle the result
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            // Update the product with the edited details
            product.setName(pair.getKey());
            product.setPrice(Integer.parseInt(pair.getValue()));
            product.setDescription(descriptionField.getText());
            product.setImage(imageField.getText());

            // Update the product in the database
            try {
                sp.update(product);
                // Optionally, refresh the table view to reflect the changes
                initialize();
            } catch (Exception e) {
                e.printStackTrace();
                // Handle the exception
            }
        });
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void showChart(ActionEvent event) {
        try {
            if (obs != null) { // Check if obs is initialized
                // Retrieve category data
                List<Category> categories = new ServiceCategory().read();

                // Create a PieChart.Data list to hold category names and product counts
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

                // Calculate the total number of products
                int totalProducts = obs.size();

                // Add each category and its product count to the pie chart data
                for (Category category : categories) {
                    int productCount = 0;
                    for (Product product : obs) {
                        if (product.getCat_id() == category.getId()) {
                            productCount++;
                        }
                    }
                    double percentage = (double) productCount / totalProducts * 100;
                    pieChartData.add(new PieChart.Data(category.getName() + " (" + String.format("%.2f", percentage) + "%)", productCount));
                }

                // Create and configure the pie chart
                PieChart chart = new PieChart(pieChartData);
                chart.setTitle("Products by Category");

                // Create a dialog to display the pie chart
                // Create a dialog to display the pie chart
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Category Pie Chart");
                alert.setHeaderText(null);

// Clear existing button types to avoid duplicates
                alert.getButtonTypes().clear();

// Add the OK button type
                alert.getButtonTypes().add(ButtonType.OK);

// Set the content of the dialog to the pie chart
                alert.getDialogPane().setContent(chart);

// Show the alert dialog and wait for it to be closed
                alert.showAndWait();
            } else {
                showAlert("Error", "Data Error", "Product data is not initialized.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database Error", "Failed to retrieve category data from the database.");
        }
    }

    @FXML
    void addProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddProduct.fxml"));
        Parent root = loader.load();

        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(root);
    }

}
