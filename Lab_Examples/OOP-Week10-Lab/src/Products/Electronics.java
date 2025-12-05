package Products;

public class Electronics extends Product{

    private final int warrantyPeriod;

    public Electronics(String name, double price, int warrantyPeriod)
    {
        super(name, price);
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return super.toString() + "Warranty " + warrantyPeriod + " months ";
    }

    @Override
    public double getDiscountAmount() {
        return getPrice() * 0.1;
    }
}
