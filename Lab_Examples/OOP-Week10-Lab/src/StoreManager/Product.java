package StoreManager;

public class Product implements DiscountBehavior{

    private String name;
    private double price;

    Product(String name , double price)
    {
        this.name = name ;
        this.price = price;
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

    @Override
    public String toString() {
        return String.format("%s (%.2f TL)", name, price);
    }

    @Override
    public double getDiscountAmount() {
        return 0.0;
    }
}
