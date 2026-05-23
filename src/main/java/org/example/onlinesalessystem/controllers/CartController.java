package org.example.onlinesalessystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.onlinesalessystem.models.CartItem;
import org.example.onlinesalessystem.models.Order;
import org.example.onlinesalessystem.models.Product;
import org.example.onlinesalessystem.repository.ProductRepository;
import org.example.onlinesalessystem.services.CartService;
import org.example.onlinesalessystem.services.OrderService;
import org.example.onlinesalessystem.utils.SessionManager;

import java.util.List;

public class CartController {
    @FXML
    private ListView<String> cartListView;

    @FXML
    private Label totalLabel;

    @FXML
    private Label messageLabel;

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductRepository productRepository;

    private List<CartItem> currentCartItems;

    public CartController() {
        this.cartService = new CartService();
        this.orderService = new OrderService();
        this.productRepository = new ProductRepository();
    }

    @FXML
    private void initialize() {
        loadCart();
    }

    private void loadCart() {
        try {
            cartListView.getItems().clear();

            int userId = SessionManager.getCurrentUserId();

            currentCartItems = cartService.getCartByUserId(userId);

            double total = 0;

            for (CartItem item : currentCartItems) {
                Product product = productRepository.getById(item.getProductId());

                if (product != null) {
                    double subtotal = product.getPrice() * item.getQuantity();
                    total += subtotal;

                    cartListView.getItems().add(
                            "Cart ID: " + item.getId() +
                                    " | " + product.getName() +
                                    " | Cmimi: " + product.getPrice() + " EUR" +
                                    " | Sasia: " + item.getQuantity() +
                                    " | Subtotal: " + subtotal + " EUR"
                    );
                }
            }

            totalLabel.setText("Totali: " + total + " EUR");

            if (currentCartItems.isEmpty()) {
                messageLabel.setText("Shporta eshte bosh.");
                messageLabel.setStyle("-fx-text-fill: red;");
            } else {
                messageLabel.setText("");
            }

        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleRemoveFromCart() {
        try {
            int selectedIndex = cartListView.getSelectionModel().getSelectedIndex();

            if (selectedIndex < 0) {
                messageLabel.setText("Zgjedh nje produkt per ta hequr.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            CartItem selectedItem = currentCartItems.get(selectedIndex);

            cartService.removeFromCart(selectedItem.getId());

            messageLabel.setText("Produkti u hoq nga shporta.");
            messageLabel.setStyle("-fx-text-fill: green;");

            loadCart();

        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleCheckout() {
        try {
            int userId = SessionManager.getCurrentUserId();

            Order order = orderService.checkout(userId);

            messageLabel.setText(
                    "Porosia u krye me sukses! ID: " +
                            order.getId() +
                            ", Totali: " +
                            order.getTotalPrice() +
                            " EUR"
            );
            messageLabel.setStyle("-fx-text-fill: green;");

            loadCart();

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

            Stage stage = (Stage) cartListView.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);

            stage.setScene(scene);

        } catch (Exception e) {
            messageLabel.setText("Gabim gjate kthimit ne Home.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}