package model;
import java.sql.Timestamp;
public class Comment {
    private int id;
    private String email;
    private String name;
    private String text;
    private int rating;
    private int activity;
    private Timestamp createdAt;

    public Comment() {
    }

    public Comment(String email, String name, String text, int rating, int activity) {
        this.email = email;
        this.name = name;
        this.text = text;
        this.rating = rating;
        this.activity = activity;
    }

    public Comment(int id, String email, String name, String text, int rating, int activity) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.text = text;
        this.rating = rating;
        this.activity = activity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                ", activity=" + activity +
                ", createdAt=" + createdAt +
                '}';
    }
}
