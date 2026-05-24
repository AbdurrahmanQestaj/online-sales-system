package org.example.onlinesalessystem.repository;

import org.example.onlinesalessystem.models.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartRepository extends BaseRepository implements IRepository<CartItem, Integer> {

    @Override
    public CartItem create(CartItem cartItem) throws SQLException {
        String sql = """
                INSERT INTO cart_items (user_id, product_id, quantity)
                VALUES (?, ?, ?)
                """;

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, cartItem.getUserId());
        statement.setInt(2, cartItem.getProductId());
        statement.setInt(3, cartItem.getQuantity());

        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();

        if (generatedKeys.next()) {
            cartItem.setId(generatedKeys.getInt(1));
        }

        return cartItem;
    }

    @Override
    public CartItem update(CartItem cartItem) throws SQLException {
        String sql = """
                UPDATE cart_items
                SET quantity = ?
                WHERE id = ?
                """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cartItem.getQuantity());
        statement.setInt(2, cartItem.getId());

        statement.executeUpdate();

        return cartItem;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        int affectedRows = statement.executeUpdate();

        return affectedRows > 0;
    }

    @Override
    public CartItem getById(Integer id) throws SQLException {
        String sql = "SELECT * FROM cart_items WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return fromResultSet(resultSet);
        }

        return null;
    }

    @Override
    public List<CartItem> getAll() throws SQLException {
        String sql = "SELECT * FROM cart_items";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<CartItem> cartItems = new ArrayList<>();

        while (resultSet.next()) {
            cartItems.add(fromResultSet(resultSet));
        }

        return cartItems;
    }

    public List<CartItem> getByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM cart_items WHERE user_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);

        ResultSet resultSet = statement.executeQuery();

        List<CartItem> cartItems = new ArrayList<>();

        while (resultSet.next()) {
            cartItems.add(fromResultSet(resultSet));
        }

        return cartItems;
    }

    public CartItem getByUserAndProduct(int userId, int productId) throws SQLException {
        String sql = """
                SELECT * FROM cart_items
                WHERE user_id = ? AND product_id = ?
                """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.setInt(2, productId);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return fromResultSet(resultSet);
        }

        return null;
    }

    public void clearCartByUserId(int userId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE user_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.executeUpdate();
    }

    private CartItem fromResultSet(ResultSet resultSet) throws SQLException {
        return new CartItem(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("product_id"),
                resultSet.getInt("quantity")
        );
    }
}