package service;

import model.Category;
import model.Product;
import utils.MyDataBase;
import service.IService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategory implements IService<Category> {
    private final Connection cnx;
    public ServiceCategory (){
        cnx = MyDataBase.getInstance().getConnection();
    }

    public void add(Category category) throws SQLException {
        String qry = "INSERT INTO `product_cat`(`name`) VALUES (?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, category.getName());

            stm.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @java.lang.Override
    public void create(Category category) throws SQLException {

    }

    @java.lang.Override
    public Category authenticate(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public void update(Category category) throws SQLException {
        String qry = "UPDATE `product_cat` SET name= ? WHERE id = ?";

        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(2,category.getId());
            stm.setString(1,category.getName());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(int i) {

    }

    public void delete(Category category) throws SQLException {
        String qry = "DELETE FROM `product_cat` WHERE id = ?";

        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, category.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> read() throws SQLException {
        String qry = "SELECT * FROM `product_cat`";
        ArrayList<Category> cats = new ArrayList<>();
        try{
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Category cat =new Category();
                cat.setId(rs.getInt(1));
                cat.setName(rs.getString(2));

                cats.add(cat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cats;
    }
}
