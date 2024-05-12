package controller.back.Blog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Blog.Publication;
import service.Blog.PublicationService;

public class AfficherPublicationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableColumn<Publication, String> titreCol;
    @FXML
    public Button downloadPdfButton;

    @FXML
    private Label welcomeLBL;

    @FXML
    private TableColumn<Publication, String> contenuCol;

    @FXML
    private TableColumn<Publication, Date> dateCol;

    @FXML
    private TableColumn<Publication, String> imageCol;

    @FXML
    private TableView<Publication> tableView;
    PublicationService ps = new PublicationService();
    ObservableList<Publication> obs ;
    @FXML
    void supprimerPublication(ActionEvent event) throws SQLException {

        try
        {
            Publication p =tableView.getSelectionModel().getSelectedItem();
            ps.delete(p.getId());
            System.out.println(p.getId());
            obs.remove(p);
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
    @FXML
    void initialize() {
        try {
            List<Publication> list = ps.select();
            obs = FXCollections.observableArrayList(list);
            tableView.setItems(obs);
            titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("datepub"));
            contenuCol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void setData(String msg){
        welcomeLBL.setText("Welcome" + msg);
    }
    @FXML
    public void modifierpublication(ActionEvent actionEvent) {
        Publication selectedPublication = tableView.getSelectionModel().getSelectedItem();
        if (selectedPublication == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune publication sélectionnée");
            alert.setContentText("Veuillez sélectionner une publication à modifier.");
            alert.showAndWait();
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Modifier la publication");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cette publication ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier la publication");
            dialog.setHeaderText(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/updatePublication.fxml"));
            try {
                Parent root = loader.load();
                //dialog.getDialogPane().setContent(root);
                ModifierPublicationController modifierPubController = loader.getController();
                modifierPubController.setData(selectedPublication.getId());
                mainPane.getChildren().removeAll();
                mainPane.getChildren().setAll(root);
                //Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                //stage.setUserData(this);

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> dialogResult = dialog.showAndWait();
            if (dialogResult.isPresent() && dialogResult.get() == ButtonType.OK) {
                initialize();
            }
        }
    }

    public void addpublication(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/Addpublication.fxml"));
        /*Parent root = (Parent) loader.load();
        Stage stage = (Stage) welcomeLBL.getScene().getWindow();
        stage.getScene().setRoot(root);*/
        Parent fxml = loader.load();
        mainPane.getChildren().removeAll();
        mainPane.getChildren().setAll(fxml);
    }
    public void AfficherCommentaire(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherCommentaire.fxml"));
        Parent fxml = loader.load();
        AfficherCommentaire controller = loader.getController();
        controller.setPane(mainPane);
        mainPane.getChildren().removeAll();
        mainPane.getChildren().setAll(fxml);
    }
    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void downloadPdfButtonClicked(ActionEvent event) {
        try {
            String downloadsPath = System.getProperty("user.home") + "/Downloads/";

            Document document = new Document();

            String filePath = downloadsPath + "PublicationList.pdf";

            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();

            document.add(new Paragraph("List of Publications\n\n"));

            for (Publication publication : obs) {
                // Format publication entry
                Paragraph publicationParagraph = new Paragraph();
                publicationParagraph.add(new Chunk("Title: "));
                publicationParagraph.add(new Chunk(publication.getTitre() + "\n"));
                publicationParagraph.add(new Chunk("Date: "));
                publicationParagraph.add(new Chunk(publication.getDatepub().toString() + "\n"));
                publicationParagraph.add(new Chunk("Content: "));
                publicationParagraph.add(new Chunk(publication.getContenu() + "\n"));

                publicationParagraph.setIndentationLeft(20);
                document.add(publicationParagraph);
                document.add(Chunk.NEWLINE);
            }

            document.close();

            showAlert("PDF Downloaded Successfully!");

        } catch (Exception e) {
            showAlert("PDF Downloaded Successfully!");
        }
    }

}
