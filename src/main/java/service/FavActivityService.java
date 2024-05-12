package service;

import model.FavActivitiy;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavActivityService implements IService<FavActivitiy> {
    Connection connection;
    public FavActivityService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    public List<FavActivitiy> select() throws SQLException {
        List<FavActivitiy> favActivities = new ArrayList<>();
        String sql = "SELECT * FROM fav_activities";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            FavActivitiy favActivity = new FavActivitiy();
            favActivity.setId(resultSet.getInt("id"));
            favActivity.setEmail(resultSet.getString("user_email"));
            favActivity.setActivity(resultSet.getInt("activity_Id"));
            favActivities.add(favActivity);
        }
        return favActivities;
    }

    public int add(FavActivitiy favActivitiy) throws SQLException {
        String sql = "INSERT INTO fav_activities (user_email,activity_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, favActivitiy.getEmail());
        preparedStatement.setInt(2, favActivitiy.getActivity());
        preparedStatement.executeUpdate();
        return 0;
    }


    @Override
    public void create(FavActivitiy favActivitiy) throws SQLException {

    }

    @Override
    public FavActivitiy authenticate(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public void update(FavActivitiy favActivitiy) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM fav_activities WHERE activity_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<FavActivitiy> read() throws SQLException {
        return null;
    }

    public boolean isActivityInWishlist(int activityId) throws SQLException {
        List<FavActivitiy> favActivities = new ArrayList<>();
        String sql = "SELECT * FROM fav_activities WHERE activity_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, activityId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            FavActivitiy favActivity = new FavActivitiy();
            favActivity.setId(resultSet.getInt("id"));
            favActivity.setEmail(resultSet.getString("user_email"));
            favActivity.setActivity(resultSet.getInt("activity_Id"));
            favActivities.add(favActivity);
        }
        if(favActivities.isEmpty())
            return false;
        else
            return true;
    }


}
