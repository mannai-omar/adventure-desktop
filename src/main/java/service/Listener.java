package service;

import model.Product;

import java.sql.SQLException;

public interface Listener {
    public void onClick(Product product) throws SQLException;
}
