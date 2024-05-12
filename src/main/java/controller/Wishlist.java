package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Activity;
import service.ActivityService;
import utils.MyDataBase;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Wishlist implements Initializable {
    @FXML
    private GridPane activitiesGrid;
    @FXML
    private Pane mainPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Label noActivities;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int columns = 0;
        int row = 1;
        List<Activity> activities=new ArrayList<>();

        Connection connection= MyDataBase.getInstance().getConnection();
        System.out.println(connection);
        ActivityService activityService =new ActivityService();
        try {
            activities=activityService.selectFavActivities();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (activities.isEmpty())
            noActivities.setVisible(true);
        else {
            try {
                for (int i = 0; i < activities.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/activityCard.fxml"));
                    Parent fxml = fxmlLoader.load();
                    ActivityCard controller = fxmlLoader.getController();
                    controller.setPane(mainPane);
                    controller.setData(activities.get(i));
                    if (columns == 3) {
                        columns = 0;
                        ++row;
                    }
                    activitiesGrid.add(fxml, columns++, row);
                    GridPane.setMargin(fxml, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void setPane(Pane pane){
        this.mainPane = pane;
    }
}
