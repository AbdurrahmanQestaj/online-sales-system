package org.example.onlinesalessystem.repository;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T, ID> {
    T create(T model) throws SQLException;

    T update(T model) throws SQLException;

    boolean delete(ID id) throws SQLException;

    T getById(ID id) throws SQLException;

    List<T> getAll() throws SQLException;
}
