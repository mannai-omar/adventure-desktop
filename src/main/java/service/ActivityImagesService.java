package service;

import model.ActivityImages;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ActivityImagesService {
    Connection connection;
    public  ActivityImagesService(){
        connection= MyDataBase.getInstance().getConnection();
    }

    public List<ActivityImages> select() throws SQLException{
        List<ActivityImages>activityImages=new ArrayList<>();
        String sql="select * from activity_images";
        Statement statement=connection.createStatement();
        ResultSet resultSet= statement.executeQuery(sql);
        while (resultSet.next()){
            ActivityImages activityImage =new ActivityImages();
            activityImage.setId(resultSet.getInt("id"));
            activityImage.setUrl(resultSet.getString("url"));
            activityImage.setActivity(resultSet.getInt("activity_id"));

            activityImages.add(activityImage);
        }
        return activityImages;
    }

    public List<ActivityImages> selectByActivityId(int activityId) throws SQLException {
        List<ActivityImages> activityImages = new ArrayList<>();
        String sql = "SELECT * FROM activity_images where activity_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, activityId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ActivityImages activityImage =new ActivityImages();
            activityImage.setId(resultSet.getInt("id"));
            activityImage.setUrl(resultSet.getString("url"));
            activityImage.setActivity(resultSet.getInt("activity_id"));
            activityImages.add(activityImage);
        }
        return activityImages;
    }

    public void add(ActivityImages activityImage) throws SQLException {
        String sql = "INSERT INTO activity_images (url, activity_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, activityImage.getUrl());
        preparedStatement.setInt(2, activityImage.getActivity());
        preparedStatement.executeUpdate();
    }
}
