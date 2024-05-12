package controller.front.Blog;

import com.dark.programs.speech.translator.GoogleTranslate;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.Blog.Commentaire;
import model.Blog.Publication;

import service.Blog.CommentaireService;
import service.Blog.MailService;
import service.Blog.PublicationService;
import javafx.scene.control.Button;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailsController implements Initializable {
    @FXML
    private Label contenuLabel;

    @FXML
    private Label titre;

    @FXML
    private Label date;

    @FXML
    private Label contenu;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<String> ListComment;
    @FXML
    private TableColumn<Commentaire, String> comment;

    @FXML
    private TextArea AreaComment;
    public static Publication pub;
    @FXML
    private Label reactionName;
    @FXML
    private HBox commentContainer;

    @FXML
    private Label nbReactions;


    @FXML
    private ImageView imgReaction;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private ImageView imgLike;
    private long startTime = 0;
    private Publication publication;
    private int likesCount = pub.getLikes();
    public void setContenu(String contenu) {
        contenuLabel.setText(contenu);
    }
    public void initialize() {
        afficherPublication();
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    private void afficherPublication() {
        if (pub != null) {
            titre.setText(pub.getTitre());
            date.setText(pub.getDatepub().toString());
            contenu.setText(pub.getContenu());
            likesLabel.setText(String.valueOf(pub.getLikes()));
            String imageUrl = pub.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Image image = new Image(imageUrl);
                imageView.setImage(image);
            } else {
            }

            remplirListeCommentaires();
        }
    }

    private void remplirListeCommentaires() {
        ArrayList<Commentaire> listCommentaire = new ArrayList<>();
        try {
            for(var comm: new CommentaireService().select()){
                if(comm.getPublication().getId() == pub.getId())
                    listCommentaire.add(comm);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> ListSrting = new ArrayList<>();
        for(var s: listCommentaire){
            ListSrting.add(s.getDescription());
        }

        ListComment.setItems(FXCollections.observableList(ListSrting));

    }

    @FXML
    void AddCommentaire() {
        CommentaireService comServ = new CommentaireService();
        MailService mailService = new MailService();
        String filteredComment = filterBadWords(AreaComment.getText());
        try {
            comServ.add(new Commentaire(pub, 1, filteredComment));
            System.out.println("comment added");
            System.out.println(comServ.select());
            mailService.sendEmail("hamoudachkir@yahoo.fr");
        } catch (SQLException | MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String filterBadWords(String comment) {
        String[] badWords = {"badword1", "badword2", "badword3"};

        String regexPattern = "\\b(" + String.join("|", badWords) + ")\\b";
        Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(comment);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, "*".repeat(matcher.group().length()));
        }
        matcher.appendTail(stringBuffer);

        return stringBuffer.toString();
    }


    @FXML
    void userCommentaire() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherPublication();
    }
    @FXML
    private Button likeButton;
    @FXML
    private Label likesLabel;
    @FXML
    void likeButtonClicked() throws SQLException {
        // Increment the likes count
        PublicationService pubServ = new PublicationService();
        pubServ.incrementLikes(pub.getId());

        // Update the likesLabel text
        pub.setLikes(pub.getLikes() + 1);
        likesLabel.setText(String.valueOf(pub.getLikes()));
    }
    @FXML
    void Translate(ActionEvent event) throws IOException {
        String Newfeedtext = GoogleTranslate.translate("fr", contenu.getText());
        contenu.setText(Newfeedtext);
    }




}
