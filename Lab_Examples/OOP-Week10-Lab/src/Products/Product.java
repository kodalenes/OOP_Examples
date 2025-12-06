package Products;

import StoreManager.DiscountBehavior;

public class Product implements DiscountBehavior {

    private String name;
    private double price;
    private int stockAmount;

    public Product(String name , double price , int stockAmount)
    {
        this.name = name ;
        this.price = price;
        this.stockAmount = stockAmount;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f TL)", name, price);
    }

    @Override
    public double getDiscountAmount() {
        return 0.0;
    }
}
