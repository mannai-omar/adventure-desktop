package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Category;
import service.ServiceCategory;

public class AddCatController {

    @FXML
    private TextField CatTC;

    ServiceCategory sc = new ServiceCategory();

    @FXML
    void AddCat(ActionEvent event) {
        String name = CatTC.getText();
        Category cat = new Category(name);
        try{
            sc.add(cat);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Category added");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


}
