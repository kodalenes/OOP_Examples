package Products;

public class Book extends Product{

    int page;

    public Book(String name, double price, int stockAmount, int page) {
        super(name, price, stockAmount , ProductType.BOOK);
        this.page = page;
    }

    @Override
    public String toString() {
        return super.toString() + "Page:" + page;
    }

    @Override
    public double getDiscountAmount() {
        return getPrice() * 0.2;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
