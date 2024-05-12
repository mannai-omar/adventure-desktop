package controller.back.Blog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Blog.Commentaire;
import model.Blog.Publication;
import service.Blog.CommentaireService;
public class AfficherCommentaire {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableView<Commentaire> table1view;
    @FXML
    private TableColumn<Commentaire, Date> dateCCol;
    @FXML
    private TableColumn<Commentaire, Boolean> ActionCol;
    @FXML
    private Label Welcome;
    @FXML
    private TableColumn<Commentaire, String> pubCol;
    @FXML
    private TableColumn<Commentaire, Integer> userCol;
    @FXML
    private TableColumn<Commentaire, String> descriptionCol;
    CommentaireService cs = new CommentaireService();
    ObservableList<Commentaire> obsC;
    private AnchorPane pane;
    @FXML
    void cd107b(ActionEvent event) {
    }
    @FXML
    void initialize() {
        try {
            List<Commentaire> list = cs.select();
            obsC= FXCollections.observableArrayList(list);
            table1view.setItems(obsC);
            dateCCol.setCellValueFactory(new PropertyValueFactory<>("datecomnt"));
            ActionCol.setCellValueFactory(new PropertyValueFactory<>("active"));
            userCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            pubCol.setCellValueFactory(cellData -> {
                Commentaire commentaire = cellData.getValue();
                Publication publication = commentaire.getPublication();
                if (publication != null) {
                    return new SimpleStringProperty(publication.getTitre());
                } else {
                    return new SimpleStringProperty("");
                }
            });
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void afficherPublication(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherPublication.fxml"));
        Parent fxml = loader.load();
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }
    public void setData(String msg){
        Welcome.setText("Welcome" + msg);
    }
    @FXML
    void afficherStatistiques(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/statisticsCommentaire.fxml"));
        Parent fxml = loader.load();
        StatisticsController controller = loader.getController();
        controller.setPane(pane);
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }

    public void setPane(AnchorPane pane) {
        this.pane=pane;
    }
}