package service;

import model.Category;
import model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IService<T> {
    void create(T t) throws SQLException;
    T authenticate(String email, String password) throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> read() throws SQLException;


}
