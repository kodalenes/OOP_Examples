package StoreManager;

import Exceptions.ProductCantFoundException;
import Products.Product;
import Utils.InputUtils;

import java.util.ArrayList;
import java.util.List;

public class Store {
    List<Product> products = new ArrayList<>();

    public void addProductToStore()
    {
        String name = InputUtils.readString("Enter product name?");
        double price = InputUtils.readDouble("Enter product price?");
        int stockAmount = InputUtils.readInt("Enter stock amount");
        Product product = new Product(name ,price ,stockAmount);
        products.add(product);
    }

    public void removeProductFromStore()
    {
        String targetName = InputUtils.readString("Enter product name to remove from store?");
        boolean isRemoved = products.removeIf(product -> product.getName().equalsIgnoreCase(targetName));

        if (isRemoved)
            System.out.println(targetName + " is removed from store.");
    }

    public Product findProductByName(String targetName) throws ProductCantFoundException {
        for (Product p : products)
        {
            if (p.getName().equalsIgnoreCase(targetName))
            {
                return p;
            }
        }

        throw new ProductCantFoundException("Product cannot found!");
    }

    public void listAllProducts()
    {
        for (Product p : products)
            if (p != null)
                System.out.printf("%s Stock:%d %n", p, p.getStockAmount());
    }
}
