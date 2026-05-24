package org.example.onlinesalessystem.repository;

import org.example.onlinesalessystem.models.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends BaseRepository implements IRepository<Product, Integer> {
    
    
    @Override
    public Product create(Product product) throws SQLException {
        String sql = """
                INSERT INTO products (name, description, price, quantity, category, seller_id, is_active)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setDouble(3, product.getPrice());
        statement.setInt(4, product.getQuantity());
        statement.setString(5, product.getCategory());
        statement.setInt(6, product.getSellerId());
        statement.setBoolean(7, product.isActive());
        
        statement.executeUpdate();
        
        ResultSet generatedKeys = statement.getGeneratedKeys();

        if (generatedKeys.next()){
            product.setId(generatedKeys.getInt(1));
        }

        return product;
    }

    @Override
    public Product update(Product product) throws SQLException {
        String sql = """
                UPDATE products
                SET name = ?, description = ?, price = ?, quantity = ?, category = ?, seller_id = ?
                WHERE id = ?
                """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setDouble(3, product.getPrice());
        statement.setInt(4, product.getQuantity());
        statement.setString(5, product.getCategory());
        statement.setInt(6, product.getSellerId());
        statement.setInt(7, product.getId());

        statement.executeUpdate();

        return product;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return softDelete(id);
    }
    public boolean softDelete(int productId) throws SQLException {
        String sql = "UPDATE products SET is_active = FALSE WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, productId);

        int affectedRows = statement.executeUpdate();

        return affectedRows > 0;
    }

    @Override
    public Product getById(Integer id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return fromResultSet(resultSet);
        }

        return null;
    }

    @Override
    public List<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM products WHERE is_active = TRUE";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<Product> products = new ArrayList<>();

        while (resultSet.next()) {
            products.add(fromResultSet(resultSet));
        }

        return products;
    }

    public List<Product> searchByName(String keyword) throws SQLException {
        String sql = "SELECT * FROM products WHERE name LIKE ? AND is_active = TRUE";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + keyword + "%");

        ResultSet resultSet = statement.executeQuery();

        List<Product> products = new ArrayList<>();

        while (resultSet.next()){
            products.add(fromResultSet(resultSet));
        }

        return products;
    }
    public List<Product> getByCategory(String category) throws SQLException {
        String sql = "SELECT * FROM products WHERE category = ? AND is_active = TRUE";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, category);

        ResultSet resultSet = statement.executeQuery();

        List<Product> products = new ArrayList<>();

        while(resultSet.next()) {
            products.add(fromResultSet(resultSet));
        }

        return products;
    }

    private Product fromResultSet(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDouble("price"),
                resultSet.getInt("quantity"),
                resultSet.getString("category"),
                resultSet.getInt("seller_id"),
                resultSet.getBoolean("is_active")
        );
    }

    public void updateQuantity(int productId, int newQuantity) throws SQLException {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, newQuantity);
        statement.setInt(2, productId);

        statement.executeUpdate();
    }

    public List<Product> getBySellerId(int sellerId) throws SQLException {
        String sql = "SELECT * FROM products WHERE seller_id = ? AND is_active = TRUE";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, sellerId);

        ResultSet resultSet = statement.executeQuery();

        List<Product> products = new ArrayList<>();

        while (resultSet.next()) {
            products.add(fromResultSet(resultSet));
        }

        return products;
    }
}
