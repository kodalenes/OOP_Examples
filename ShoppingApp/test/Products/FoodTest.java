package Products;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodTest {

    @Test
    void testExpirationDate()
    {
        //Arrange (Preparation)
        int shelfLifeDays = 10;
        Food milk = new Food("Milk" , 20.0,100,shelfLifeDays);

        //Act (Execution)
        LocalDate actualExpDate = milk.getExpirationDate();
        LocalDate expectedExpData = LocalDate.now().plusDays(shelfLifeDays);

        //Assert (Verification)
        assertEquals(expectedExpData , actualExpDate ,"Expiration Date is calculated incorrectly");
    }

    @Test
    void testDiscountCalculation()
    {
        Food apple = new Food("Apple" ,10.0 , 100 ,5);

        double actualDiscount = apple.getDiscountAmount();
        double expectedDiscount = 1.0;

        assertEquals(expectedDiscount , actualDiscount , "Discount amount is incorrect for Food items!");
    }
}
