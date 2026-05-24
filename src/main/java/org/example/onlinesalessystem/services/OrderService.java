package org.example.onlinesalessystem.services;

import org.example.onlinesalessystem.exceptions.InvalidQuantityException;
import org.example.onlinesalessystem.exceptions.ProductNotFoundException;
import org.example.onlinesalessystem.models.CartItem;
import org.example.onlinesalessystem.models.Order;
import org.example.onlinesalessystem.models.OrderItem;
import org.example.onlinesalessystem.models.Product;
import org.example.onlinesalessystem.repository.CartRepository;
import org.example.onlinesalessystem.repository.OrderRepository;
import org.example.onlinesalessystem.repository.ProductRepository;

import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderService() {
        this.orderRepository = new OrderRepository();
        this.cartRepository = new CartRepository();
        this.productRepository = new ProductRepository();
    }

    public Order checkout(int userId) throws Exception {
        try {
            DatabaseService.beginTransaction();

            List<CartItem> cartItems = cartRepository.getByUserId(userId);

            if (cartItems.isEmpty()) {
                throw new Exception("Shporta eshte bosh.");
            }

            double totalPrice = 0;

            for (CartItem item : cartItems) {
                Product product = productRepository.getById(item.getProductId());

                if (product == null) {
                    throw new ProductNotFoundException("Produkti nuk ekziston.");
                }

                if (item.getQuantity() > product.getQuantity()) {
                    throw new InvalidQuantityException(
                            "Nuk ka sasi te mjaftueshme per produktin: " + product.getName()
                    );
                }

                totalPrice += product.getPrice() * item.getQuantity();
            }

            Order order = new Order(userId, totalPrice, "COMPLETED");
            Order createdOrder = orderRepository.create(order);

            for (CartItem item : cartItems) {
                Product product = productRepository.getById(item.getProductId());

                OrderItem orderItem = new OrderItem(
                        createdOrder.getId(),
                        product.getId(),
                        item.getQuantity(),
                        product.getPrice()
                );

                orderRepository.createOrderItem(orderItem);

                int newQuantity = product.getQuantity() - item.getQuantity();
                productRepository.updateQuantity(product.getId(), newQuantity);
            }

            cartRepository.clearCartByUserId(userId);

            DatabaseService.commitTransaction();

            return createdOrder;

        } catch (Exception e) {
            DatabaseService.rollbackTransaction();
            throw e;
        }
    }

    public List<Order> getOrdersByUserId(int userId) throws Exception {
        return orderRepository.getByBuyerId(userId);
    }

    public List<OrderItem> getOrderItems(int orderId) throws Exception {
        return orderRepository.getOrderItemsByOrderId(orderId);
    }
}