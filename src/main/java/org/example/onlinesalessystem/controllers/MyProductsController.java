package org.example.onlinesalessystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.onlinesalessystem.models.Product;
import org.example.onlinesalessystem.services.ProductService;
import org.example.onlinesalessystem.utils.SessionManager;

import java.util.List;

public class MyProductsController {
    @FXML
    private ListView<String> myProductsListView;

    @FXML
    private Label messageLabel;

    private final ProductService productService;

    private List<Product> currentProducts;

    public MyProductsController() {
        this.productService = new ProductService();
    }

    @FXML
    private void initialize() {
        loadMyProducts();
    }

    @FXML
    private void loadMyProducts() {
        try {
            myProductsListView.getItems().clear();

            int sellerId = SessionManager.getCurrentUserId();

            currentProducts = productService.getProductsBySellerId(sellerId);

            if (currentProducts.isEmpty()) {
                messageLabel.setText("Nuk keni shtuar ende produkte.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            for (Product product : currentProducts) {
                myProductsListView.getItems().add(
                        product.getId() + " | " +
                                product.getName() + " | " +
                                product.getPrice() + " EUR | " +
                                "Sasia: " + product.getQuantity() + " | " +
                                "Kategoria: " + product.getCategory()
                );
            }

            messageLabel.setText("");

        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleDeleteProduct() {
        try {
            int selectedIndex = myProductsListView.getSelectionModel().getSelectedIndex();

            if (selectedIndex < 0) {
                messageLabel.setText("Zgjedh nje produkt per ta fshire.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            Product selectedProduct = currentProducts.get(selectedIndex);

            productService.deleteMyProduct(
                    selectedProduct.getId(),
                    SessionManager.getCurrentUserId()
            );

            messageLabel.setText("Produkti u fshi me sukses.");
            messageLabel.setStyle("-fx-text-fill: green;");

            loadMyProducts();

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

            Stage stage = (Stage) myProductsListView.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);

            stage.setScene(scene);

        } catch (Exception e) {
            messageLabel.setText("Gabim gjate kthimit ne Home.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}