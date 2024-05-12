package model;

public class FavActivitiy {

    private int id;
    private int activity;
    private String email;

    public FavActivitiy() {
    }
    public FavActivitiy(int id, int activity, String email) {
        this.id = id;
        this.activity = activity;
        this.email = email;
    }

    public FavActivitiy(int activity, String email) {
        this.activity = activity;
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "FavActivitiy{" +
                "id=" + id +
                ", activity=" + activity +
                ", email='" + email + '\'' +
                '}';
    }

}
