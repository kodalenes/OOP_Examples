package Products;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void testDiscountCalculation() {

        Clothes shirt = new Clothes("Shirt",10 , 0 , 10);

        double expectedDiscount = 3.0;
        double actualDiscount = shirt.getDiscountAmount();

        assertEquals(expectedDiscount , actualDiscount ,"Discount amount is incorrect for Clothes items!");
    }
}