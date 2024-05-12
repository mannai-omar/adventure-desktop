package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.Category;
import model.Product;
import service.Listener;
import service.ServiceCategory;
import service.ServiceProduct;
import test.MainFX;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowProduct {

    @FXML
    private Label ProductCat;

    @FXML
    private MenuButton CATMB;

    @FXML
    private Text ProductDesc;

    @FXML
    private Label ProductName;

    @FXML
    private Label ProductPrice;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView productImage;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Pagination pagination;

    final String CURRENCY="TND";

    private ServiceProduct sp = new ServiceProduct();
    private List<Product> allProducts;
    private Listener listener;

    private final int ITEMS_PER_PAGE = 3;

    private String getCategoryName(int categoryId) {
        try {
            ServiceCategory serviceCategory = new ServiceCategory();
            List<Category> categories = serviceCategory.read();
            for (Category category : categories) {
                if (category.getId() == categoryId) {
                    return category.getName();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void setChosenProduct(Product product) {
        ProductName.setText(product.getName());
        ProductPrice.setText(CURRENCY + product.getPrice());
        ProductCat.setText(getCategoryName(product.getCat_id()));
        ProductDesc.setText(product.getDescription());

        // Convert the byte array to an Image and set it to the ImageView
        Image image = new Image(new File(product.getImage()).toURI().toString());
        productImage.setImage(image);
        productImage.setFitWidth(150); // Set width to fit properly
        productImage.setFitHeight(150); // Set height to fit properly
    }

    @FXML
    public void initialize() {
        try {
            // Initialize the listener
            listener = this::setChosenProduct;

            // Add "All" item to the CATMB MenuButton
            MenuItem allItem = new MenuItem("All");
            allItem.setOnAction(event -> {
                try {
                    loadAllProducts();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            CATMB.getItems().add(allItem);

            // Make the "All" item selected by default


            // Fetch all products and load them by default
            loadAllProducts();

            // Fetch the categories from the database and add them to the CATMB MenuButton
            try {
                ServiceCategory serviceCategory = new ServiceCategory();
                List<Category> categories = serviceCategory.read();
                for (Category category : categories) {
                    MenuItem menuItem = new MenuItem(category.getName());
                    menuItem.setOnAction(event -> {
                        try {
                            // Handle the action when a category is selected
                            loadProductsForCategory(category.getId());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                    CATMB.getItems().add(menuItem);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pagination.setPageFactory(this::createPage);
    }

    private void loadAllProducts() throws SQLException {
        // Fetch all products
        allProducts = sp.select();

        // Calculate the number of pages
        int pageCount = (int) Math.ceil((double) allProducts.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0);
    }

    private void loadProductsForCategory(int categoryId) throws SQLException {
        // Fetch products for the selected category
        allProducts = sp.selectByCategoryId(categoryId);

        // Calculate the number of pages
        int pageCount = (int) Math.ceil((double) allProducts.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0);
    }

    private void updatePage(int pageIndex) {
        grid.getChildren().clear(); // Clear the existing grid

        int startingIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startingIndex + ITEMS_PER_PAGE, allProducts.size());

        int column = 0;
        int row = 0;

        for (int i = startingIndex; i < endIndex; i++) {
            Product product = allProducts.get(i);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Item.fxml"));
            try {
                AnchorPane anchorPane = loader.load();
                ItemController itemController = loader.getController();
                itemController.setData(product, listener);

                grid.add(anchorPane, column, row);

                column++;
                if (column >= 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private AnchorPane createPage(int pageIndex) {
       AnchorPane anchorPane = new AnchorPane();
        updatePage(pageIndex);
        return anchorPane;
    }
}