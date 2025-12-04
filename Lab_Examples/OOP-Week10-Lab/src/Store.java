import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Store {
    List<Product> products = new ArrayList<>();

    public void addProductToStore(String name , double price)
    {
        products.add(new Product(name ,price));
    }

    public void removeProductFromStore(String targetName)
    {
        boolean isRemoved = products.removeIf(product -> product.name().equalsIgnoreCase(targetName));

        if (isRemoved)
            System.out.println(targetName + " is removed from store.");
    }

    public void listAllProducts()
    {
        for (Product p : products)
            if (p != null)
                System.out.println(p);
    }

}
