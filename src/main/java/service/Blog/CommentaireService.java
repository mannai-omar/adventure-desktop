package service.Blog;

import model.Blog.Commentaire;
import model.Blog.Publication;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService {
    Connection connection;
    public CommentaireService() {
        connection = MyDataBase.getInstance().getConnection();
    }
    public void add (Commentaire commentaire) throws SQLException {
        System.out.println(commentaire);
        String sql = "INSERT INTO commentaire (publication_id, description, datecomnt,active) VALUES (?, ?, CURRENT_TIMESTAMP,?)";    PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,commentaire.getPublication().getId());
        statement.setString(2,commentaire.getDescription());
        statement.setBoolean(3,true);
        statement.executeUpdate();
        System.out.println("added");
    }
    public void update(Commentaire commentaire) throws SQLException {
        String sql = "UPDATE commentaire SET publication_id=?, user_id=?, description=?, datecomnt=CURRENT_TIMESTAMP WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, commentaire.getPublication().getId());
        statement.setInt(2, commentaire.getUser_id());
        statement.setString(3, commentaire.getDescription());
        statement.setInt(4, commentaire.getId());
        statement.executeUpdate();
    }
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM commentaire WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
    public List<Commentaire> select() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String sql = "SELECT * FROM commentaire";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Commentaire commentaire = new Commentaire();
            commentaire.setId(resultSet.getInt("id"));
            commentaire.setPublication(fetchPublicationById(resultSet.getInt("publication_id")));
            commentaire.setDescription(resultSet.getString("description"));
            commentaire.setDatecomnt(resultSet.getDate("datecomnt"));
            commentaires.add(commentaire);
        }
        return commentaires;
    }

    public List<Commentaire> fetchCommentaireByPublicationID(int id) throws SQLException {
        String sql="SELECT * FROM Commentaire WHERE publication_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Commentaire> listecommentaire = new ArrayList<>();
        while (resultSet.next()) {
            Commentaire commentaire= new Commentaire();
            commentaire.setPublication(fetchPublicationById(resultSet.getInt("publication_id")));
            commentaire.setUser_id(resultSet.getInt("user_id"));
            commentaire.setDescription(resultSet.getString("description"));
            commentaire.setDatecomnt(resultSet.getDate("datecomnt"));
            listecommentaire.add(commentaire);
        }
        System.out.println(listecommentaire.size());
        return listecommentaire;
    }
    public Publication fetchPublicationById(int id) throws SQLException {
        String sql = "SELECT * FROM Publication WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Publication publication = new Publication();
            publication.setId(resultSet.getInt("id"));
            publication.setTitre(resultSet.getString("titre"));
            publication.setContenu(resultSet.getString("contenu"));
            publication.setImage(resultSet.getString("image"));
            return publication;
        }
        return null;
    }
    public int getTotalComments() throws SQLException {
        String sql = "SELECT COUNT(*) FROM commentaire";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0; // Return 0 if there are no comments or an error occurs
    }

    public int getActiveCommentsCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM commentaire WHERE active = true";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0; // Return 0 if there are no active comments or an error occurs
    }
}
