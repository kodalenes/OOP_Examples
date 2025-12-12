package Products;

import java.util.Objects;

public class Product implements DiscountBehavior {

    private String name;
    private double price;
    private int stockAmount;
    private ProductType productType;

    public Product(String name , double price , int stockAmount , ProductType productType)
    {
        this.name = name ;
        this.price = price;
        this.stockAmount = stockAmount;
        this.productType = productType;
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

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ProductType getProductType() {
        return productType;
    }

    /**
     * @return Calculates (product's price * applied discount rate) and returns.
     */
    @Override
    public double getDiscountAmount() {
        return 0.0;
    }


    @Override
    public String toString() {
        return String.format("%s (%.2f TL)", name, price);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;

        Product otherProduct = (Product) other;
        return Objects.equals(this.name , otherProduct.getName())
                && Objects.equals(this.productType , otherProduct.getProductType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name , productType);
    }
}
