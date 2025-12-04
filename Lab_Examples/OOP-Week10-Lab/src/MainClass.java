public class MainClass {

    static void main() {
        Store store = new Store();
        ShoppingCart cart = new ShoppingCart();

        store.addProductToStore("Laptop" , 5000);
        store.addProductToStore("Mouse" , 600);
        store.addProductToStore("Monitor" , 1000);
        store.addProductToStore("Keyboard" , 750);

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
               case 1 -> store.listAllProducts();
               case 2 -> cart.listCart();
               case 3 -> cart.removeFromCart();
               case 4 -> payment(cart);
               case 0 -> {
                   System.out.println("Exit...");
                   isOver = true;
               }
           }
        }while(isOver);
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

        cart.payment(paymentMethod);
    }
}
