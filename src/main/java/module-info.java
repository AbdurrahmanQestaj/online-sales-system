module org.example.onlinesalessystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.onlinesalessystem.controllers to javafx.fxml;

    exports org.example.onlinesalessystem.app;
    exports org.example.onlinesalessystem.models;
    exports org.example.onlinesalessystem.models.dto;
    exports org.example.onlinesalessystem.services;
    exports org.example.onlinesalessystem.repository;
    exports org.example.onlinesalessystem.utils;
}