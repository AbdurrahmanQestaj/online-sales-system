package org.example.onlinesalessystem.models;

import java.util.SplittableRandom;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private int sellerId;
    private boolean active;

    public Product(int id, String name, String description, double price, int quantity,String category, int sellerId, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category =  category;
        this.sellerId = sellerId;
        this.active = active;
    }

    public Product(String name, String description, double price, int quantity, String category, int sellerId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.sellerId = sellerId;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public  int getSellerId() {
        return sellerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
