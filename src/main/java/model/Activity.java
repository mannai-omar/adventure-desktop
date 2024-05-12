package model;

public class Activity {

    private int id;
    private String name;
    private String location;
    private float price;
    private String type;
    private String description;
    private int max_guests;
    private int min_age;
    private int duration;

    public Activity() {
    }

    public Activity(String name, String location, float price, String type, String description, int max_guests, int min_age, int duration) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.type = type;
        this.description = description;
        this.max_guests = max_guests;
        this.min_age = min_age;
        this.duration = duration;
    }

    public Activity(int id, String name, String location, float price, String type, String description, int max_guests, int min_age, int duration) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.type = type;
        this.description = description;
        this.max_guests = max_guests;
        this.min_age = min_age;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public float getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getMax_guests() {
        return max_guests;
    }

    public int getMin_age() {
        return min_age;
    }

    public int getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMax_guests(int max_guests) {
        this.max_guests = max_guests;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", max_guests=" + max_guests +
                ", min_age=" + min_age +
                ", duration=" + duration +
                '}';
    }
}
