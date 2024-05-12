package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Comment;
import service.CommentService;

import java.sql.SQLException;

public class CommentTableItem {
    @FXML
    private Label text;

    @FXML
    private Label createdAt;

    @FXML
    private Label email;

    @FXML
    private Label name;

    @FXML
    private Label rating;
    Comment commentt;
    private CommentDeletedListener commentDeletedListener;
    public void setData(Comment comment, CommentDeletedListener listener){
        name.setText(comment.getName());
        email.setText(comment.getEmail());
        rating.setText(String.valueOf(comment.getRating()));
        text.setText(comment.getText());
        createdAt.setText(String.valueOf(comment.getCreatedAt()));
        this.commentt=comment;
        this.commentDeletedListener = listener;
    }

    @FXML
    private void deleteComment(javafx.event.ActionEvent actionEvent) {
        CommentService commentService = new CommentService();

        try {
            commentService.delete(commentt.getId());
            System.out.println("comment deleted!");
            if (commentDeletedListener != null) {
                commentDeletedListener.onCommentDeleted(commentt);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public interface CommentDeletedListener {
        void onCommentDeleted(Comment commentt);
    }
}
