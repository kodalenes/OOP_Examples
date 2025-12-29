package Products;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    @Test
    void testDiscountCalculation()
    {
        Book book = new Book("Book" ,10.0 , 100 ,150);

        double actualDiscount = book.getDiscountAmount();
        double expectedDiscount = 2.0;

        assertEquals(expectedDiscount , actualDiscount , "Discount amount is incorrect for Book items!");
    }
}
