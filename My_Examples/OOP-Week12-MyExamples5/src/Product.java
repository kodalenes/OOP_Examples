import java.util.Objects;

public class Product  implements Comparable<Product>{

    String name;
    double price;
    int stockAmount;

    Product(String name ,double price , int stockAmount)
    {
        this.name = name;
        this.price = price;
        this.stockAmount = stockAmount;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f %d" , name , price ,stockAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        Product other = (Product) obj;
        return this.getName().equals(((Product) obj).getName());
    }

    @Override
    public int compareTo(Product o) {
        return this.stockAmount - o.getStockAmount();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
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


}
