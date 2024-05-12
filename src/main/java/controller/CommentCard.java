package controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import model.Comment;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class CommentCard {

    @FXML
    private Text comment;

    @FXML
    private Text date;

    @FXML
    private Text name;

    @FXML
    private Text commentIcon;

    public void setData(Comment commentPassed){
        name.setText(commentPassed.getName());
        date.setText(String.valueOf(calculateTimeAgoWithPrettyTime(commentPassed.getCreatedAt())));
        comment.setText(commentPassed.getText());
        String[] nameParts = commentPassed.getName().split(" ");
        StringBuilder initialsBuilder = new StringBuilder();

        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initialsBuilder.append(Character.toUpperCase(part.charAt(0)));
            }
        }
        String initials = initialsBuilder.toString();
        commentIcon.setText(initials);
    }

    String calculateTimeAgoWithPrettyTime(Date pastTime) {
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(pastTime);
    }
}
