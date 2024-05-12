package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Reservation;
import service.ReservationService;

import java.sql.SQLException;

public class ReservationTableItem {

    @FXML
    private Button cancelButton;

    @FXML
    private Label date;

    @FXML
    private Label email;

    @FXML
    private Label status;

    @FXML
    private Label tickets;

    Reservation reservation;
    public void setData(Reservation reservation){

        email.setText(reservation.getUserEmail());
        tickets.setText(String.valueOf(reservation.getNbrTickets()));
        status.setText(reservation.getStatus());
        date.setText(String.valueOf(reservation.getDate()));
        this.reservation=reservation;
        if ("Canceled".equals(reservation.getStatus()))
            cancelButton.setDisable(true);
    }

    @FXML
    private void cancelReservation(javafx.event.ActionEvent actionEvent) {
        reservation.setStatus("Canceled");
        ReservationService reservationService = new ReservationService();
        try {
            reservationService.update(reservation);
            status.setText("Canceled");
            cancelButton.setDisable(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
