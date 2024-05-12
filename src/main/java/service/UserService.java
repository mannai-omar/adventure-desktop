package service;

import model.User;
import service.IService;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {


    private Connection connection;

    public UserService() {
        connection = MyDataBase.getInstance().getConnection();
    }



    @Override
    public void create(User user) throws SQLException {
        String sql = "INSERT INTO user (email, roles, password, is_verified, google_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getRoles());
        ps.setString(3, user.getPassword());
        ps.setInt(4, user.getIs_verified());
        ps.setLong(5, user.getGoogle_id());
        ps.executeUpdate();
    }


    @Override
    public User authenticate(String email, String password) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user=new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setRoles(resultSet.getString("roles"));
                    user.setIs_verified(resultSet.getInt("is_verified"));
                    user.setGoogle_id(resultSet.getLong("google_id"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                } else {
                    return null; // Authentication failed
                }
            }
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE user SET email = ?, password = ?, roles = ?, is_verified = ? WHERE email = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRoles());
            ps.setInt(4, user.getIs_verified());
            ps.setString(5, user.getEmail()); // Use email to identify the user record

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                // Handle case where no rows were updated (user with given email not found)
                // Optionally, throw an exception or log a message
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            // Optionally, throw a custom exception or log a more detailed error message
            throw e;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    @Override
    public List<User> read() throws SQLException {
        String sql = "SELECT * FROM user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<User> userList = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setRoles(rs.getString("roles"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setId(rs.getInt("id"));
            user.setIs_verified(rs.getInt("is_verified"));
            user.setGoogle_id(rs.getLong("google_id"));
            userList.add(user);
        }
        return userList;
    }


    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setRoles(rs.getString("roles"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setId(rs.getInt("id"));
                    user.setIs_verified(rs.getInt("is_verified"));
                    user.setGoogle_id(0);
                    userList.add(user);
                }
            }
        }
        return userList;
    }


    public void updatePassword(User user) throws SQLException {
        String sql = "UPDATE user SET password = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getId()); // Use ID to identify the user record

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                // Handle case where no rows were updated
                // Optionally, throw an exception or log a message
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            // Optionally, throw a custom exception or log a more detailed error message
            throw e;
        }
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setRoles(rs.getString("roles"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setId(rs.getInt("id"));
                    user.setIs_verified(rs.getInt("is_verified"));
                    user.setGoogle_id(rs.getLong("google_id"));
                    return user;
                } else {
                    return null; // No user found with the given ID
                }
            }
        }
    }



    public int getUserIdByEmail(String email) throws SQLException {
        String sql = "SELECT id FROM user WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return -1; // Return -1 if no user found with the given email
                }
            }
        }
    }



    public void updatePasswordById(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE user SET password = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setInt(2, userId);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                // Handle case where no rows were updated
                // Optionally, throw an exception or log a message
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            // Optionally, throw a custom exception or log a more detailed error message
            throw e;
        }
    }
}
