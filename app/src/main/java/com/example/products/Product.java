package com.example.products;

public class Product {
    private int foodResId;
    private String name, description;
    private int price;

    public Product(int foodResId, String name, String description, int price) {
        this.foodResId = foodResId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getFoodResId() {
        return foodResId;
    }

    public void setFoodResId(int flagResId) {
        this.foodResId = flagResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String capital) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
