package model;

public class User {

    int id;
    String email;
    String roles;
    String password;
    int is_verified=0;
    long google_id;

    public User(int id, String email, String roles, String password, int isVerified, long googleId) {
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(int is_verified) {
        this.is_verified = is_verified;
    }

    public long getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(long google_id) {
        this.google_id = google_id;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", password='" + password + '\'' +
                ", is_verified=" + is_verified +
                ", google_id=" + google_id +

                '}';
    }

    public User(int id, String email, String roles, String password, int is_verified) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.is_verified = is_verified;
    }

    public User(String email, String roles, String password, int is_verified, long google_id) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.is_verified = is_verified;
        this.google_id = google_id;


    }
}
