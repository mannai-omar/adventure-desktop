package service;

import model.Statuser;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class statuserService {

    private Connection connection;

    public statuserService() {
        connection = MyDataBase.getInstance().getConnection();
    }


    public void create(Statuser stat) throws SQLException {
        String sql = "INSERT INTO statuser (iduser, date) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, stat.getIduser());

        // Convert LocalDate to SQL Date
        LocalDate date = stat.getDate();
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        ps.setDate(2, sqlDate);

        ps.executeUpdate();
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM statuser";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        // Return 0 if no records or an error occurs
        return 0;
    }

    public Map<String, Integer> countLoginsPerDay() throws SQLException {
        Map<String, Integer> loginsPerDay = new HashMap<>();
        String sql = "SELECT DATE_FORMAT(date, '%Y-%m-%d') AS login_date, COUNT(*) AS login_count FROM statuser GROUP BY login_date";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String loginDate = rs.getString("login_date");
                    int loginCount = rs.getInt("login_count");
                    loginsPerDay.put(loginDate, loginCount);
                }
            }
        }

        return loginsPerDay;
    }



}
