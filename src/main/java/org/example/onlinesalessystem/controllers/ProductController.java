package org.example.onlinesalessystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.onlinesalessystem.models.Product;
import org.example.onlinesalessystem.models.dto.CartItemDto;
import org.example.onlinesalessystem.services.CartService;
import org.example.onlinesalessystem.services.ProductService;
import org.example.onlinesalessystem.utils.SessionManager;

import java.util.List;

public class ProductController {
    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> productsListView;

    @FXML
    private TextField quantityField;

    @FXML
    private Label messageLabel;

    private final ProductService productService;
    private final CartService cartService;

    private List<Product> currentProducts;

    public ProductController() {
        this.productService = new ProductService();
        this.cartService = new CartService();
    }

    @FXML
    private void initialize() {
        loadProducts();
    }

    @FXML
    private void loadProducts() {
        try {
            productsListView.getItems().clear();

            currentProducts = productService.getAllProducts();

            for (Product product : currentProducts) {
                productsListView.getItems().add(formatProduct(product));
            }

        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleSearch() {
        try {
            productsListView.getItems().clear();

            String keyword = searchField.getText();

            currentProducts = productService.searchProducts(keyword);

            for (Product product : currentProducts) {
                productsListView.getItems().add(formatProduct(product));
            }

        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleAddToCart() {
        try {
            int selectedIndex = productsListView.getSelectionModel().getSelectedIndex();

            if (selectedIndex < 0) {
                messageLabel.setText("Zgjedh nje produkt.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            int quantity = Integer.parseInt(quantityField.getText());

            Product selectedProduct = currentProducts.get(selectedIndex);

            CartItemDto cartItemDto = new CartItemDto(
                    SessionManager.getCurrentUserId(),
                    selectedProduct.getId(),
                    quantity
            );

            cartService.addToCart(cartItemDto);

            messageLabel.setText("Produkti u shtua ne shporte!");
            messageLabel.setStyle("-fx-text-fill: green;");

            quantityField.clear();

        } catch (NumberFormatException e) {
            messageLabel.setText("Sasia duhet te jete numer.");
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

            Stage stage = (Stage) productsListView.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);

            stage.setScene(scene);
        } catch (Exception e) {
            messageLabel.setText("Gabim gjate kthimit ne Home.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private String formatProduct(Product product) {
        return product.getId() + " | " +
                product.getName() + " | " +
                product.getPrice() + " EUR | " +
                "Sasia: " + product.getQuantity() + " | " +
                "Kategoria: " + product.getCategory();
    }
}