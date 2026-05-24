package org.example.onlinesalessystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.onlinesalessystem.models.User;
import org.example.onlinesalessystem.utils.SessionManager;

public class HomeController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {
        User currentUser = SessionManager.getCurrentUser();

        if (currentUser != null) {
            welcomeLabel.setText("Mire se erdhe, " + currentUser.getFullName() + "!");
        }
    }

    @FXML
    private void goToProducts() {
        loadPage("/views/products-view.fxml", 800, 500);
    }

    @FXML
    private void goToAddProduct() {
        loadPage("/views/add-product-view.fxml", 700, 500);
    }

    @FXML
    private void goToCart() {
        loadPage("/views/cart-view.fxml", 800, 500);
    }

    @FXML
    private void handleLogout() {
        SessionManager.logout();
        loadPage("/views/login-view.fxml", 600, 400);
    }

    @FXML
    private void goToOrders() {
        loadPage("/views/orders-view.fxml", 800, 500);
    }

    @FXML
    private void goToMyProducts() {
        loadPage("/views/my-products-view.fxml", 800, 500);
    }

    private void loadPage(String path, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource(path)
            );

            if (fxmlLoader.getLocation() == null) {
                messageLabel.setText("FXML nuk u gjet: " + path);
                return;
            }

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), width, height);

            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Gabim: " + e.getMessage());
        }
    }
}