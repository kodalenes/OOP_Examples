package Products;

import java.time.LocalDate;

public class Food extends Product{

    private LocalDate productionDate;
    private LocalDate expirationDate;

    public Food(String name, double price, int stockAmount , int recConsumptionPeriod) {
        super(name, price, stockAmount, ProductType.FOOD);
        calculateExpDate(recConsumptionPeriod);
    }

    private void calculateExpDate(int recConsumptionPeriod)
    {
        LocalDate now = LocalDate.now();
        productionDate = LocalDate.now();
        expirationDate = now.plusDays(recConsumptionPeriod);
    }

    @Override
    public String toString() {
        return super.toString() + "PDD:" + productionDate + " EXP:" + expirationDate;
    }

    @Override
    public double getDiscountAmount() {
        return getPrice() * 0.1;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
