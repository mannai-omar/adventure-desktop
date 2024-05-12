package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Category;
import model.Product;
import service.Listener;
import service.ServiceCategory;
import test.MainFX;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
public class ItemController {

    @FXML
    private Label Catname;

    @FXML
    private Label ProductName;

    @FXML
    private Label ProductPrice;
    @FXML
    private ImageView productImage;

    @FXML
    private AnchorPane root;

    final String CURRENCY = "TND";


    private Product product;
    private Listener listener;
    private Category cat;
    @FXML
    public void click (MouseEvent  event) throws SQLException {
        listener.onClick(product);
    }

    @FXML
    void initialize() {
       root.setOnMouseClicked(mouseEvent -> {
           try {
               listener.onClick(product);
           } catch (SQLException e) {
               System.err.println(e.getMessage());
           }
       });

    }
    public void setData(Product product, Listener listener) throws SQLException {
        this.listener = listener;
        this.product = product;

        ProductName.setText(product.getName());
        ProductPrice.setText(CURRENCY + product.getPrice());
        Catname.setText(getCategoryName(product.getCat_id()));
        Image image = new Image(new File(product.getImage()).toURI().toString());
        productImage.setImage(image);
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
}
