package Main;

import StoreManager.ShoppingCart;
import StoreManager.Store;
import com.sun.tools.javac.Main;

public class MainClass {

    static void main(String[] args) {
        Store store = new Store();
        ShoppingCart cart = new ShoppingCart();
        Menu.userMenu(store , cart);
    }
}
