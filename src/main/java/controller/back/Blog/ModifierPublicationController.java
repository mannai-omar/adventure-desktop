package controller.back.Blog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Blog.Publication;
import service.Blog.PublicationService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class ModifierPublicationController {

    public javafx.scene.control.Label cheminphoto;
    public javafx.scene.control.TextField TitreTF;
    public javafx.scene.control.TextField ContenuTF;
    private TableView<Publication> tableView;
    private Publication publication;
    private String photoPath;
    private int idpub;
    @FXML
    void EnregistrerPublication(ActionEvent event) throws SQLException {
        this.publication = new Publication();
        String contenu = ContenuTF.getText();
        String image = cheminphoto.getText();
        String titre = TitreTF.getText();
        Date datepub = new Date();
        this.publication.setTitre(titre);
        this.publication.setContenu(contenu);
        this.publication.setImage(image);
        this.publication.setDatepub(datepub);
        this.publication.setId(idpub);

        PublicationService publicationService = new PublicationService();
        try {
            publicationService.update(publication);
            afficherAlerte("Publication modifiée", "La publication a été modifiée avec succès.");

            Stage stage = (Stage) ContenuTF.getScene().getWindow();
            AfficherPublicationController afficherPubController = (AfficherPublicationController) stage.getUserData();
            afficherPubController.initialize();
            ContenuTF.getScene().getWindow().hide();
        } catch (Exception e) {
            afficherAlerte("Erreur lors de la modification", "Une erreur s'est produite lors de la modification de la commande : " + e.getMessage());
        }

    }
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private Publication getData() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    void AfficherPublication(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/Afficherpublication.fxml"));
        try {
            Parent root = (Parent) loader.load();
            Stage stage = (Stage) TitreTF.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue AfficherPublication.fxml : " + e.getMessage());
        }


    }
    public void setData(int id) {
        this.idpub=id;
        System.out.println(id);
    }

    public void ajouter_photo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            photoPath= selectedFile.getAbsolutePath();
            cheminphoto.setText(photoPath);
        }
    }
}
