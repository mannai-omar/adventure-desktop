package service;

import model.Activity;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityService implements IService<Activity> {
    Connection connection;
    public  ActivityService(){
        connection= MyDataBase.getInstance().getConnection();
    }

    public int add(Activity activity)throws SQLException {
        String sql = "INSERT INTO activity (name, location, price, type, description, max_guests, min_age, duration) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, activity.getName());
        preparedStatement.setString(2, activity.getLocation());
        preparedStatement.setFloat(3, activity.getPrice());
        preparedStatement.setString(4, activity.getType());
        preparedStatement.setString(5, activity.getDescription());
        preparedStatement.setInt(6, activity.getMax_guests());
        preparedStatement.setInt(7, activity.getMin_age());
        preparedStatement.setInt(8, activity.getDuration());

        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creating activity failed, no ID obtained.");
        }
    }

    @Override
    public void create(Activity activity) throws SQLException {

    }

    @Override
    public Activity authenticate(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public void update(Activity activity)throws SQLException {
        String sql ="update activity set name=?,location=?,price=?,type=?,description=?,max_guests=?,min_age=?,duration=? where id=? ";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1, activity.getName());
        preparedStatement.setString(2, activity.getLocation());
        preparedStatement.setFloat(3, activity.getPrice());
        preparedStatement.setString(4, activity.getType());
        preparedStatement.setString(5, activity.getDescription());
        preparedStatement.setInt(6,activity.getMax_guests());
        preparedStatement.setInt(7,activity.getMin_age());
        preparedStatement.setInt(8,activity.getDuration());
        preparedStatement.setInt(9,activity.getId());
        preparedStatement.executeUpdate();



    }

    @Override
    public void delete(int id)throws SQLException {
        String sql ="delete from activity where id=? ";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Activity> read() throws SQLException {
        return null;
    }


    public List<Activity> select() throws SQLException{
        List<Activity>activities=new ArrayList<>();
        String sql="select * from activity";
        Statement statement=connection.createStatement();
        ResultSet resultSet= statement.executeQuery(sql);
        while (resultSet.next()){
            Activity activity =new Activity();
            activity.setId(resultSet.getInt("id"));
            activity.setName(resultSet.getString("name"));
            activity.setLocation(resultSet.getString("location"));
            activity.setPrice(resultSet.getFloat("price"));
            activity.setDescription(resultSet.getString("description"));
            activity.setDuration(resultSet.getInt("duration"));
            activity.setMin_age(resultSet.getInt("min_age"));
            activity.setMax_guests(resultSet.getInt("max_guests"));
            activity.setType(resultSet.getString("type"));
            activities.add(activity);
        }
        return activities;
    }

    public Activity selectById(int id) throws SQLException {
        String sql = "SELECT * FROM activity WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Activity activity = new Activity();
            activity.setId(resultSet.getInt("id"));
            activity.setName(resultSet.getString("name"));
            activity.setLocation(resultSet.getString("location"));
            activity.setPrice(resultSet.getFloat("price"));
            activity.setType(resultSet.getString("type"));
            activity.setDescription(resultSet.getString("description"));
            activity.setMax_guests(resultSet.getInt("max_guests"));
            activity.setMin_age(resultSet.getInt("min_age"));
            activity.setDuration(resultSet.getInt("duration"));
            return activity;
        }
        return null;
    }


    public List<Activity> selectFavActivities() throws SQLException{
        List<Activity>activities=new ArrayList<>();
        String sql="SELECT * FROM activity JOIN fav_activities ON activity.id = fav_activities.activity_id;";
        Statement statement=connection.createStatement();
        ResultSet resultSet= statement.executeQuery(sql);
        while (resultSet.next()){
            Activity activity =new Activity();
            activity.setId(resultSet.getInt("id"));
            activity.setName(resultSet.getString("name"));
            activity.setLocation(resultSet.getString("location"));
            activity.setPrice(resultSet.getFloat("price"));
            activity.setDescription(resultSet.getString("description"));
            activity.setDuration(resultSet.getInt("duration"));
            activity.setMin_age(resultSet.getInt("min_age"));
            activity.setMax_guests(resultSet.getInt("max_guests"));
            activity.setType(resultSet.getString("type"));
            activities.add(activity);
        }
        return activities;
    }

}
