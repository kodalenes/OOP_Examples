package Main;

import Exceptions.EmptyCartException;
import Exceptions.ProductCantFoundException;
import Payment.CashPayment;
import Payment.CreditCardPayment;
import Payment.PaymentBehavior;
import Products.Product;
import StoreManager.ShoppingCart;
import StoreManager.Store;
import Utils.InputUtils;

import java.util.concurrent.TimeUnit;

public class Menu {

    private static final int adminPass = 1234;

    public static void userMenu(ShoppingCart cart)
    {
        Store.getInstance().loadFromJSON();
        boolean isOver = false;
        do {
            try {
                System.out.println("----WELCOME STORE----");
                System.out.println("1-Products");
                System.out.println("2-Filter list products");
                System.out.println("3-List cart");
                System.out.println("4-Remove from cart");
                System.out.println("5-Pay");
                System.out.println("9-Admin Panel");
                System.out.println("0-Exit");

                int choice = InputUtils.readInt("Your choice:");

                switch (choice){
                    case 1 -> listAndAdd(cart);
                    case 2 -> filterList(cart);
                    case 3 -> cart.listCart();
                    case 4 -> cart.removeFromCart();
                    case 5 -> payment(cart);
                    case 9 -> adminMenu();
                    case 0 -> {
                        cart.restoreStock();
                        Store.getInstance().saveToJSON();
                        System.out.println("Exit...");
                        isOver = true;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }while(!isOver);
    }

    private static void adminMenu()
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
            System.out.println("3-List products");
            System.out.println("0-Back");
            System.out.println("---------");
            int choice = InputUtils.readInt("Your choice");

            switch (choice){
                case 1 -> Store.getInstance().addProductToStore();
                case 2 -> Store.getInstance().removeProductFromStore();
                case 3 -> Store.getInstance().listAllProducts();
                case 0 -> {
                    Store.getInstance().saveToJSON();
                    isOver = true;
                }
            }
        }while(!isOver);
    }

    private static void listAndAdd(ShoppingCart cart)
    {
        Store.getInstance().listAllProducts();

        String targetProductName = InputUtils.readString("Enter product name that you want to add to cart? (Enter 0 to exit)");
        if (targetProductName.equalsIgnoreCase("0"))
            return;

        Product product = null;
        try {
            product = Store.getInstance().findProductByName(targetProductName);
            int amount = InputUtils.readInt("How many " + targetProductName + " do you want to buy?");
            cart.addToCart(product , amount);
        } catch (ProductCantFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void filterList(ShoppingCart cart)
    {
        System.out.println("1-List products by decreasing price.");
        System.out.println("2-List products by growing price.");
        System.out.println("3-List products in price range.");
        System.out.println("4-List products by category.");
        System.out.println("0-Back");
        int choice = InputUtils.readInt("Enter filter type?");

        switch (choice){
            case 1 -> Store.getInstance().listProductsByPriceDecreasing();
            case 2 -> Store.getInstance().listProductsByPriceGrow();
            case 3 -> {
                double min = InputUtils.readDouble("Enter min price?");
                double max = InputUtils.readDouble("Enter max price?");
                Store.getInstance().listProductsInPriceRange(min , max);
            }
            case 4 -> Store.getInstance().listProductsByCategory();
            case 0 -> {
                return;
            }
        }
    }

    private static void payment(ShoppingCart cart)
    {
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
