package model;

import java.time.LocalDate;

public class Statuser {
    private int iduser;
    private int id;
    private LocalDate date;

    public Statuser(int iduser, LocalDate date) {
        this.iduser = iduser;
        this.date = date;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Statuser{" +
                "iduser=" + iduser +
                ", id=" + id +
                ", date=" + date +
                '}';
    }
}
