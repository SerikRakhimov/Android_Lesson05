package com.example.products;

public class Basket {
    private int totalSum;

    public Basket() {
        this.totalSum = 0;
    }

    public void addToBasket(int sum) {
        totalSum = totalSum + sum;
    }

    public int getTotalSum() {
        return totalSum;
    }

}
