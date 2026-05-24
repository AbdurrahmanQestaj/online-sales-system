package org.example.onlinesalessystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.onlinesalessystem.models.Order;
import org.example.onlinesalessystem.models.OrderItem;
import org.example.onlinesalessystem.models.Product;
import org.example.onlinesalessystem.repository.ProductRepository;
import org.example.onlinesalessystem.services.OrderService;
import org.example.onlinesalessystem.utils.SessionManager;

import java.util.List;

public class OrderController {
    @FXML
    private ListView<String> ordersListView;

    @FXML
    private Label messageLabel;

    private final OrderService orderService;
    private final ProductRepository productRepository;

    public OrderController() {
        this.orderService = new OrderService();
        this.productRepository = new ProductRepository();
    }

    @FXML
    private void initialize() {
        loadOrders();
    }

    @FXML
    private void loadOrders() {
        try {
            ordersListView.getItems().clear();

            int userId = SessionManager.getCurrentUserId();

            List<Order> orders = orderService.getOrdersByUserId(userId);

            if (orders.isEmpty()) {
                messageLabel.setText("Nuk keni ende porosi.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            for (Order order : orders) {
                ordersListView.getItems().add(
                        "Order ID: " + order.getId() +
                                " | Totali: " + order.getTotalPrice() + " EUR" +
                                " | Statusi: " + order.getStatus()
                );

                List<OrderItem> items = orderService.getOrderItems(order.getId());

                for (OrderItem item : items) {
                    Product product = productRepository.getById(item.getProductId());

                    String productName = "Produkt i panjohur";

                    if (product != null) {
                        productName = product.getName();
                    }

                    ordersListView.getItems().add(
                            "   - " + productName +
                                    " | Sasia: " + item.getQuantity() +
                                    " | Cmimi: " + item.getUnitPrice() + " EUR"
                    );
                }
            }

            messageLabel.setText("");

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

            Stage stage = (Stage) ordersListView.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);

            stage.setScene(scene);

        } catch (Exception e) {
            messageLabel.setText("Gabim gjate kthimit ne Home.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}