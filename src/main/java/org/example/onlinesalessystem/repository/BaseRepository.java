package org.example.onlinesalessystem.repository;

import org.example.onlinesalessystem.services.DatabaseService;

import java.sql.Connection;

public abstract class BaseRepository {
    protected Connection connection;

    public BaseRepository() {
        this.connection = DatabaseService.getConnection();
    }
}
