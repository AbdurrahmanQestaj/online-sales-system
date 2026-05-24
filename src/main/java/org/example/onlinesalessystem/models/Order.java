package org.example.onlinesalessystem.models;

public class Order {
    private int id;
    private int buyerId;
    private double totalPrice;
    private String status;

    public Order(int id, int buyerId, double totalPrice, String status) {
        this.id = id;
        this.buyerId = buyerId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order(int buyerId, double totalPrice, String status) {
        this.buyerId = buyerId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }
}