package controller.back.Blog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Blog.Commentaire;
import service.Blog.CommentaireService;

public class StatisticsController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label totalCommentsLabel;
    @FXML
    private Label activeCommentsLabel;
    @FXML
    private Label inactiveCommentsLabel;
    @FXML
    private PieChart commentsPieChart;
    @FXML
    private Button backButton;
    private AnchorPane pane;

    CommentaireService cs = new CommentaireService();

    @FXML
    void initialize() throws SQLException {
        // Fetch statistics data and update labels
        int totalComments = cs.getTotalComments();
        int activeComments = cs.getActiveCommentsCount();
        int inactiveComments = totalComments - activeComments;

        totalCommentsLabel.setText("Total Comments: " + totalComments);
        activeCommentsLabel.setText("Active Comments: " + activeComments);
        inactiveCommentsLabel.setText("Inactive Comments: " + inactiveComments);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Active Comments", activeComments),
                new PieChart.Data("Inactive Comments", inactiveComments));

        commentsPieChart.setData(pieChartData);
    }

    @FXML
    public void AfficherCommentaire (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherCommentaire.fxml"));
        Parent fxml = loader.load();
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }

    public void setPane(AnchorPane pane) {
        this.pane=pane;
    }
}