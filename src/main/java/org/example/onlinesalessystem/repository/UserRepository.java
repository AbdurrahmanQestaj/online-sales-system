package org.example.onlinesalessystem.repository;

import org.example.onlinesalessystem.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends BaseRepository implements IRepository<User, Integer> {

    @Override
    public User create(User user) throws SQLException {
        String sql = """
                INSERT INTO users (full_name, email, username, password_hash, salt, role)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getFullName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getPasswordHash());
        statement.setString(5, user.getSalt());
        statement.setString(6, user.getRole());

        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();

        if (generatedKeys.next()) {
            user.setId(generatedKeys.getInt(1));
        }

        return user;

    }

    @Override
    public User update(User user) throws SQLException {
        String sql = """
                UPDATE users
                set full_name = ?, email = ?, password_hash = ?, salt = ?, role = ?
                WHERE id = ?
                """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFullName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getPasswordHash());
        statement.setString(5, user.getSalt());
        statement.setString(6, user.getRole());
        statement.setInt(7, user.getId());

        statement.executeUpdate();

        return user;
    }

    @Override
    public boolean delete(Integer id) throws SQLException{
        String sql = "DELETE FROM users WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        int affectedRows = statement.executeUpdate();

        return affectedRows > 0;
    }

    @Override
    public User getById(Integer id) throws SQLException{
        String sql = "SELECT * FROM users WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return fromResultSet(resultSet);
        }

        return null;
    }

    @Override
    public List<User> getAll() throws SQLException{
        String sql = "SELECT * FROM users";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            users.add(fromResultSet(resultSet));
        }

        return users;
     }

     public User getByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return fromResultSet(resultSet);
        }

        return null;
     }

     public User getByEmail(String email) throws SQLException{
        String sql = "SELECT * FROM users WHERE email = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return fromResultSet(resultSet);
        }

        return null;
     }

     private User fromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("full_name"),
                resultSet.getString("email"),
                resultSet.getString("username"),
                resultSet.getString("password_hash"),
                resultSet.getString("salt"),
                resultSet.getString("role")
        );
     }
}
