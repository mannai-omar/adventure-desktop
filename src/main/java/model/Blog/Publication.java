package model.Blog;
import java.util.Objects;
import java.util.Date;
public class Publication {
    private int id ;
    private String contenu ;
    private String image ;
    private Date datepub ;
    private String titre ;
    private int totalReactions;
    private int likes;

    public Publication() {
    }

    public Publication( String contenu, String image, String titre,int likes) {
        this.contenu = contenu;
        this.image = image;
        this.datepub = new Date();
        this.titre = titre;
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public java.util.Date getDatepub() {
        return (java.util.Date) datepub;
    }
    public int getTotalReactions() {
        return totalReactions;
    }

    public void setTotalReactions(int totalReactions) {
        this.totalReactions = totalReactions;
    }
    public java.util.Date setDatepub(java.util.Date datepub) {
        this.datepub = new Date();;
        return null;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", image='" + image + '\'' +
                ", datepub=" + datepub +
                ", titre='" + titre + '\'' +
                ", likes='" + likes + '\''+
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publication that)) return false;
        return getId() == that.getId();
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContenu(), getImage(), getTitre());
    }
}
