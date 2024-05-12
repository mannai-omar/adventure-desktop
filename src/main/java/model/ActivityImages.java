package model;

public class ActivityImages {

    private int id;
    private String url;
    private int activity;

    public ActivityImages() {
    }

    public ActivityImages(String url, int activity) {
        this.url = url;
        this.activity = activity;
    }

    public ActivityImages(int id, String url, int activity) {
        this.id = id;
        this.url = url;
        this.activity = activity;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getActivity() {
        return activity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "ActivityImages{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", activity=" + activity +
                '}';
    }
}
