package model;

import java.sql.Timestamp;

public class Reservation {
    private int id;
    private Timestamp date;
    private int nbrTickets;
    private String userEmail;
    private int activityId;
    private String status;

    public Reservation() {
    }

    public Reservation(Timestamp date, int nbrTickets, String userEmail, int activityId, String status) {
        this.date = date;
        this.nbrTickets = nbrTickets;
        this.userEmail = userEmail;
        this.activityId = activityId;
        this.status = status;
    }

    public Reservation(int id, Timestamp date, int nbrTickets, String userEmail, int activityId, String status) {
        this.id = id;
        this.date = date;
        this.nbrTickets = nbrTickets;
        this.userEmail = userEmail;
        this.activityId = activityId;
        this.status = status;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getNbrTickets() {
        return nbrTickets;
    }

    public void setNbrTickets(int nbrTickets) {
        this.nbrTickets = nbrTickets;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date=" + date +
                ", nbrTickets=" + nbrTickets +
                ", userEmail='" + userEmail + '\'' +
                ", activityId=" + activityId +
                ", status='" + status + '\'' +
                '}';
    }
}
