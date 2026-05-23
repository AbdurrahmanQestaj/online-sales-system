package org.example.onlinesalessystem.repository;

import org.example.onlinesalessystem.models.Order;
import org.example.onlinesalessystem.models.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends BaseRepository implements IRepository<Order, Integer> {

    @Override
    public Order create(Order order) throws SQLException {
        String sql = """
                INSERT INTO orders (buyer_id, total_price, status)
                VALUES (?, ?, ?)
                """;

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, order.getBuyerId());
        statement.setDouble(2, order.getTotalPrice());
        statement.setString(3, order.getStatus());

        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();

        if (generatedKeys.next()) {
            order.setId(generatedKeys.getInt(1));
        }

        return order;
    }

    @Override
    public Order update(Order order) throws SQLException {
        String sql = """
                UPDATE orders
                SET total_price = ?, status = ?
                WHERE id = ?
                """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, order.getTotalPrice());
        statement.setString(2, order.getStatus());
        statement.setInt(3, order.getId());

        statement.executeUpdate();

        return order;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        int affectedRows = statement.executeUpdate();

        return affectedRows > 0;
    }

    @Override
    public Order getById(Integer id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return fromResultSet(resultSet);
        }

        return null;
    }

    @Override
    public List<Order> getAll() throws SQLException {
        String sql = "SELECT * FROM orders";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<Order> orders = new ArrayList<>();

        while (resultSet.next()) {
            orders.add(fromResultSet(resultSet));
        }

        return orders;
    }

    public List<Order> getByBuyerId(int buyerId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE buyer_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, buyerId);

        ResultSet resultSet = statement.executeQuery();

        List<Order> orders = new ArrayList<>();

        while (resultSet.next()) {
            orders.add(fromResultSet(resultSet));
        }

        return orders;
    }

    public OrderItem createOrderItem(OrderItem orderItem) throws SQLException {
        String sql = """
                INSERT INTO order_items (order_id, product_id, quantity, unit_price)
                VALUES (?, ?, ?, ?)
                """;

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, orderItem.getOrderId());
        statement.setInt(2, orderItem.getProductId());
        statement.setInt(3, orderItem.getQuantity());
        statement.setDouble(4, orderItem.getUnitPrice());

        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();

        if (generatedKeys.next()) {
            orderItem.setId(generatedKeys.getInt(1));
        }

        return orderItem;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderId);

        ResultSet resultSet = statement.executeQuery();

        List<OrderItem> items = new ArrayList<>();

        while (resultSet.next()) {
            items.add(new OrderItem(
                    resultSet.getInt("id"),
                    resultSet.getInt("order_id"),
                    resultSet.getInt("product_id"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("unit_price")
            ));
        }

        return items;
    }

    private Order fromResultSet(ResultSet resultSet) throws SQLException {
        return new Order(
                resultSet.getInt("id"),
                resultSet.getInt("buyer_id"),
                resultSet.getDouble("total_price"),
                resultSet.getString("status")
        );
    }
}