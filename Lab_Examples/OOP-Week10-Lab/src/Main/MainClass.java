package Main;

import Exceptions.EmptyCartException;
import Exceptions.ProductCantFoundException;
import Payment.CashPayment;
import Payment.CreditCardPayment;
import Payment.PaymentBehavior;
import Products.Clothes;
import Products.Electronics;
import Products.Product;
import StoreManager.*;
import Utils.InputUtils;

import java.util.concurrent.TimeUnit;

public class MainClass {

    static void main() {
        Store store = new Store();
        ShoppingCart cart = new ShoppingCart();

        store.addProductToStore(new Electronics("Laptop" , 5000 , 12));
        store.addProductToStore(new Electronics("Mouse" , 600 , 12));
        store.addProductToStore(new Electronics("Monitor" , 1000 , 24));
        store.addProductToStore(new Electronics("Keyboard" , 750 , 24));
        store.addProductToStore(new Clothes("T-Shirt" ,250 , 2));
        store.addProductToStore(new Clothes("Tie" ,100 , 1));
        store.addProductToStore(new Clothes("Trousers" ,300 , 3));

        boolean isOver = false;
        do {
            System.out.println("----WELCOME STORE----");
            System.out.println("1-Products");
            System.out.println("2-List cart");
            System.out.println("3-Remove from cart");
            System.out.println("4-Pay");
            System.out.println("0-Exit");

           int choice = InputUtils.readInt("Your choice:");

           switch (choice){
               case 1 -> listAndAdd(store ,cart);
               case 2 -> cart.listCart();
               case 3 -> cart.removeFromCart();
               case 4 -> payment(cart);
               case 0 -> {
                   System.out.println("Exit...");
                   isOver = true;
               }
           }
        }while(!isOver);
    }

    private static void listAndAdd(Store store , ShoppingCart cart)
    {
        store.listAllProducts();

        String targetProductName = InputUtils.readString("Enter product name that you want to add to cart?");
        int amount = InputUtils.readInt("How many " + targetProductName + " do you want to buy?");

        Product product = null;
        try {
            product = store.findProductByName(targetProductName);
            cart.addToCart(product , amount);
        } catch (ProductCantFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void payment(ShoppingCart cart) {
        System.out.println("Choose payment method");
        System.out.println("1-Credit Card");
        System.out.println("2-Cash");
        int method = InputUtils.readInt("Your choice");

        PaymentBehavior paymentMethod;
        if (method == 1)
            paymentMethod = new CreditCardPayment();
        else
            paymentMethod = new CashPayment();

        try {
            cart.payment(paymentMethod);
        } catch (EmptyCartException e) {
            System.out.println("Empty cart.Add product to cart!");
        }
    }

    public static void waitForSeconds(int seconds)
    {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            System.out.println("Error: Process failed");
            Thread.currentThread().interrupt();
        }
    }
}
