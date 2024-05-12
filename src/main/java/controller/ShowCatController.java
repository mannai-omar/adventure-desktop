package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Category;
import service.ServiceCategory;

import java.sql.SQLException;
import java.util.List;

public class ShowCatController {

    ServiceCategory sc = new ServiceCategory();

    @FXML
    private TableColumn<Category, String> CatName;

    @FXML
    private TableView<Category> tableCat;
    ObservableList<Category> obs;



    @FXML
    void DeleteCat(ActionEvent event) {
        Category cat = tableCat.getSelectionModel().getSelectedItem();
        try {
            sc.delete(cat);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        obs.remove(cat);
    }

    @FXML
    void initialize() {
        try {
            List<Category> list = sc.read();
            obs = FXCollections.observableArrayList(list);

            CatName.setCellValueFactory(new PropertyValueFactory<>("name"));



            tableCat.setItems(obs);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
