package org.example.onlinesalessystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.onlinesalessystem.models.dto.ProductDto;
import org.example.onlinesalessystem.services.ProductService;
import org.example.onlinesalessystem.utils.SessionManager;

public class AddProductController {
    @FXML
    private TextField nameField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField categoryField;

    @FXML
    private Label messageLabel;

    private final ProductService productService;

    public AddProductController() {
        this.productService = new ProductService();
    }

    @FXML
    private void handleAddProduct() {
        try {
            if (!SessionManager.isLoggedIn()) {
                messageLabel.setText("Duhet te jeni te kycur per te shtuar produkt.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            ProductDto productDto = new ProductDto(
                    nameField.getText(),
                    descriptionArea.getText(),
                    price,
                    quantity,
                    categoryField.getText(),
                    SessionManager.getCurrentUserId()
            );

            productService.addProduct(productDto);

            messageLabel.setText("Produkti u shtua me sukses!");
            messageLabel.setStyle("-fx-text-fill: green;");

            clearFields();

        } catch (NumberFormatException e) {
            messageLabel.setText("Cmimi dhe sasia duhet te jene numra valid.");
            messageLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToHome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/views/home-view.fxml")
            );

            Stage stage = (Stage) nameField.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);

            stage.setScene(scene);
        } catch (Exception e) {
            messageLabel.setText("Gabim gjate kthimit ne Home.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void clearFields() {
        nameField.clear();
        descriptionArea.clear();
        priceField.clear();
        quantityField.clear();
        categoryField.clear();
    }
}