package org.example.onlinesalessystem.models.dto;

public class ProductDto {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private int sellerId;

    public ProductDto(String name, String description, double price, int quantity, String category, int sellerId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.sellerId = sellerId;
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
}
