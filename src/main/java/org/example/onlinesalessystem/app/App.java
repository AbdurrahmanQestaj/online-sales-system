package org.example.onlinesalessystem.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL loginUrl = App.class.getResource("/views/login-view.fxml");

        if (loginUrl == null) {
            System.out.println("Nuk u gjet login-view.fxml");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(loginUrl);

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle("Online Sales System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}