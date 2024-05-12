package service;

import model.Reservation;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {
    Connection connection;

    public ReservationService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    public int add(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (date, nbr_tickets, user_email, activity_id, status) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setTimestamp(1, reservation.getDate());
        preparedStatement.setInt(2, reservation.getNbrTickets());
        preparedStatement.setString(3, reservation.getUserEmail());
        preparedStatement.setInt(4, reservation.getActivityId());
        preparedStatement.setString(5, reservation.getStatus());
        preparedStatement.executeUpdate();
        return 0;
    }

    @Override
    public void create(Reservation reservation) throws SQLException {

    }

    @Override
    public Reservation authenticate(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservation SET date=?, nbr_tickets=?, user_email=?, activity_id=?, status=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setTimestamp(1, reservation.getDate());
        preparedStatement.setInt(2, reservation.getNbrTickets());
        preparedStatement.setString(3, reservation.getUserEmail());
        preparedStatement.setInt(4, reservation.getActivityId());
        preparedStatement.setString(5, reservation.getStatus());
        preparedStatement.setInt(6, reservation.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Reservation> read() throws SQLException {
        return null;
    }

    public List<Reservation> select() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(resultSet.getInt("id"));
            reservation.setDate(resultSet.getTimestamp("date"));
            reservation.setNbrTickets(resultSet.getInt("nbr_tickets"));
            reservation.setUserEmail(resultSet.getString("user_email"));
            reservation.setActivityId(resultSet.getInt("activity_id"));
            reservation.setStatus(resultSet.getString("status"));
            reservations.add(reservation);
        }
        return reservations;
    }

    public Reservation selectById(int id) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(resultSet.getInt("id"));
            reservation.setDate(resultSet.getTimestamp("date"));
            reservation.setNbrTickets(resultSet.getInt("nbr_tickets"));
            reservation.setUserEmail(resultSet.getString("user_email"));
            reservation.setActivityId(resultSet.getInt("activity_id"));
            reservation.setStatus(resultSet.getString("status"));
            return reservation;
        }
        return null;
    }

    public List<Reservation> selectByActivityId(int activityId) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation where activity_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, activityId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(resultSet.getInt("id"));
            reservation.setDate(resultSet.getTimestamp("date"));
            reservation.setNbrTickets(resultSet.getInt("nbr_tickets"));
            reservation.setUserEmail(resultSet.getString("user_email"));
            reservation.setActivityId(resultSet.getInt("activity_id"));
            reservation.setStatus(resultSet.getString("status"));
            reservations.add(reservation);
        }
        return reservations;
    }
}
