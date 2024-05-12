package service;

import model.Product;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduct implements IService<Product> {

    private final Connection cnx;

    public ServiceProduct() {
        cnx = MyDataBase.getInstance().getConnection();
    }

    public void add(Product product) {
        String qry = "INSERT INTO `product`(`category_id`, `name`, `description`, `price`, `image`,`quantity`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, product.getCat_id());
            stm.setString(2, product.getName());
            stm.setString(3, product.getDescription());
            stm.setInt(4, product.getPrice());
            stm.setString(5, product.getImage());
            stm.setInt(6, 5);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void create(Product product) throws SQLException {

    }

    @Override
    public Product authenticate(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public void update(Product product) {
        String qry = "UPDATE `product` SET category_id=?,name=?,description=?,price=?,image=? WHERE id=?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, product.getCat_id());
            stm.setString(2, product.getName());
            stm.setString(3, product.getDescription());
            stm.setInt(4, product.getPrice());
            stm.setString(5, product.getImage());
            stm.setInt(6, product.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Product> read() throws SQLException {
        return List.of();
    }

    public void delete(Product product) {
        String qry = "DELETE FROM `product` WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, product.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> select() {
        ArrayList<Product> products = new ArrayList<>();
        String qry = "SELECT * FROM `product`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setImage(rs.getString("image"));
                p.setCat_id(rs.getInt(2));
                p.setPrice(rs.getInt(5));
                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public ArrayList<Product> selectByCategoryId(int categoryId) {
        ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE category_id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, categoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setImage(resultSet.getString("image"));
                    product.setCat_id(resultSet.getInt("category_id"));
                    product.setPrice(resultSet.getInt("price"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
