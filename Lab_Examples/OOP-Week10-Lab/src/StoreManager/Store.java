package StoreManager;

import Exceptions.ProductCantFoundException;

import java.util.ArrayList;
import java.util.List;

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

    public Product findProductByName(String targetName) throws ProductCantFoundException {
        for (Product p : products)
        {
            if (p.name().equalsIgnoreCase(targetName))
            {
                return p;
            }
        }

        throw new ProductCantFoundException("Cannot Found StoreManager.Product!");
    }

    public void listAllProducts()
    {
        for (Product p : products)
            if (p != null)
                System.out.printf("%s%n", p);
    }

}
