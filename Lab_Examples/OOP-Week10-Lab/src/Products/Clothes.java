package Products;

public class Clothes extends Product{

    private final int size;

    public Clothes(String name ,double price ,int size ,int stockAmount) {
        super(name, price ,stockAmount);
        this.size = size;
    }

    @Override
    public String toString() {
        return super.toString() + "Size " + size + " ";
    }

    @Override
    public double getDiscountAmount()
    {
       return getPrice() * 0.3;
    }
}
