package Main;

import Exceptions.EmptyCartException;
import Exceptions.InsufficientStockException;
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

    private static final int adminPass = 1234;

    public static void main(String[] args) {
        Store store = new Store();
        ShoppingCart cart = new ShoppingCart();
        userMenu(store ,cart);
    }

    private static void userMenu(Store store , ShoppingCart cart) {

        boolean isOver = false;
        do {
            try {
                System.out.println("----WELCOME STORE----");
                System.out.println("1-Products");
                System.out.println("2-List cart");
                System.out.println("3-Remove from cart");
                System.out.println("4-Pay");
                System.out.println("9-Admin Panel");
                System.out.println("0-Exit");

                int choice = InputUtils.readInt("Your choice:");

                switch (choice){
                    case 1 -> listAndAdd(store ,cart);
                    case 2 -> cart.listCart();
                    case 3 -> cart.removeFromCart();
                    case 4 -> payment(cart);
                    case 9 -> adminMenu(store);
                    case 0 -> {
                        System.out.println("Exit...");
                        isOver = true;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }while(!isOver);
    }

    private static void adminMenu(Store store)
    {
        int pass = InputUtils.readInt("Enter admin password?");
        if (pass != adminPass)
        {
            System.out.println("Wrong password!");
            return;
        }
        boolean isOver = false;

        do {
            System.out.println("---------");
            System.out.println("1-Add Product To Store");
            System.out.println("2-Remove Product From Store");
            System.out.println("0-Back");
            System.out.println("---------");
            int choice = InputUtils.readInt("Your choice");

            switch (choice){
                case 1 -> store.addProductToStore();
                case 2 -> store.removeProductFromStore();
                case 0 -> isOver = true;
            }
        }while(!isOver);
    }

    private static void listAndAdd(Store store, ShoppingCart cart) {
        store.listAllProducts();

        String targetProductName = InputUtils.readString("Enter product name that you want to add to cart? (Enter 0 to exit)");
        if (targetProductName.equalsIgnoreCase("0"))
            return;

        Product product = null;
        try {
            product = store.findProductByName(targetProductName);
            int amount = InputUtils.readInt("How many " + targetProductName + " do you want to buy?");
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
