package StoreManager;

import Exceptions.EmptyCartException;
import Exceptions.InsufficientStockException;
import Exceptions.ProductCantFoundException;
import Payment.PaymentBehavior;
import Products.Electronics;
import Products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private ShoppingCart shoppingCart;
    private Product laptop;

    @BeforeEach
    void setUp()
    {
        shoppingCart = new ShoppingCart();
        laptop = new Electronics("Laptop" , 5000.0 ,24 , 10);
    }

    @Test
    void testAddToCart_Success() {
        int addAmount = 2;
        int initialStock = laptop.getStockAmount();

        shoppingCart.addToCart(laptop , addAmount);

        assertEquals(initialStock - addAmount , laptop.getStockAmount() , "Stock amount did not update correctly!");

        assertTrue(shoppingCart.cart.containsKey(laptop),"Product was not added to the cart map!");

        assertEquals(addAmount , shoppingCart.cart.get(laptop) , "Quantity in cart is incorrect!");

    }

    @Test
    void testAddToCart_InsufficientStock_ThrowsException()
    {
        int overLimitAmount = 11;

        assertThrows(InsufficientStockException.class, () ->{
            shoppingCart.addToCart(laptop , overLimitAmount);
        } ,"Should throw InsufficientStockException when stock is not enough!");

        assertEquals(10 , laptop.getStockAmount(),"Stock should not change if exception occurs!");
    }

    @Test
    void testRemoveFromCart_Success()
    {
        shoppingCart.addToCart(laptop, 5);
        int initialStockAfterAdd = laptop.getStockAmount();

        int currentInCart = shoppingCart.cart.get(laptop);
        int removeAmount = 2;

        shoppingCart.cart.put(laptop , currentInCart - removeAmount);
        laptop.setStockAmount(laptop.getStockAmount() + removeAmount);

        assertEquals(3 , shoppingCart.cart.get(laptop) , " Amount must be 3 at cart!");
        assertEquals(initialStockAfterAdd + removeAmount , laptop.getStockAmount() , "Stock must be return 7!");

    }

    @Test
    void testFindProductByName_Found() {
        shoppingCart.addToCart(laptop ,1);

        assertDoesNotThrow(() -> {
            Product found = shoppingCart.findProductByName("Laptop");
            assertEquals(laptop , found , "Correct product cannot found!");
        });
    }

    @Test
    void testFindProductByName_NotFound()
    {
        shoppingCart.addToCart(laptop , 1);

        assertThrows(ProductCantFoundException.class,() ->{
            shoppingCart.findProductByName("Macbook Pro");
        } ,"Should throws ProductCannotException when product cannot found!");
    }

    @Test
    void testPayment_Success_ClearsCart()
    {
        shoppingCart.addToCart(laptop ,1);

        PaymentBehavior fakePayment = totalAmount -> {
            assertEquals(4500 , totalAmount , "Total calculated incorrectly!");
            return true;
        };

        assertDoesNotThrow(() -> shoppingCart.payment(fakePayment));

        assertTrue(shoppingCart.cart.isEmpty() , "The cart wasn't cleared after payment!");
    }

    @Test
    void testPayment_EmptyCart()
    {
        PaymentBehavior fakePayment = totalAmount -> true;

        assertThrows(EmptyCartException.class, () -> {
            shoppingCart.payment(fakePayment);
        }, " Shouldn't pay with empty cart!");
    }

    @Test
    void testRestoreStock_WhenQuit() {
        shoppingCart.addToCart(laptop , 5);

        shoppingCart.restoreStock();

        assertEquals(10 , laptop.getStockAmount() , "Stock must be restore while user exit from app!");
    }

    @Test
    void testCalculateTotal()
    {
        //Arrange
        shoppingCart.addToCart(laptop ,1);

        double grossTotal = 5000;
        double expectedDiscount = laptop.getDiscountAmount();
        double expectedTotalAmount = grossTotal - expectedDiscount;
        double actualDiscountedAmount = shoppingCart.cart.entrySet().stream()
                .mapToDouble(entry -> {
                    double price = entry.getKey().getPrice();
                    double discountPerUnit = entry.getKey().getDiscountAmount();
                    return (price - discountPerUnit) * entry.getValue();
                })
                .sum();

        assertEquals(expectedTotalAmount, actualDiscountedAmount ,0.01 ,"The discounted amount should be 4500!");
    }
}