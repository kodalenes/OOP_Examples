package StoreManager;

import Exceptions.ProductCantFoundException;
import Products.Electronics;
import Products.Product;
import Utils.InputUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Store {

    private static final String PRODUCT_FILE = "product_file";
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

    public Product findProductByName(String targetName) throws ProductCantFoundException
    {
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
        if (products.isEmpty())
        {
            System.out.println("There is no product!");
            return;
        }
        for (Product p : products)
            if (p != null)
                System.out.printf("%s Stock:%d %n", p, p.getStockAmount());
    }

    public void saveToJSON()
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try(FileWriter writer = new FileWriter(PRODUCT_FILE)){
            gson.toJson(products , writer);
            System.out.println("Products saved to JSON!");

        }catch (Exception e)
        {
            System.out.println("Error saving JSON!" + e.getMessage());
        }
    }

    public void loadFromJSON()
    {
        File file = new File(PRODUCT_FILE);

        if (!file.exists())
        {
            System.out.println("No JSON file found - starting with empty store!");
            return;
        }

        Gson gson = new GsonBuilder().create();

        try(FileReader reader = new FileReader(PRODUCT_FILE)){

            Type listType = new TypeToken<ArrayList<Product>>(){}.getType();

            List<Product> loadedList = gson.fromJson(reader , listType);

            if(loadedList != null)
            {
                this.products = loadedList;
                System.out.println("Products loaded successfully");
            }

        } catch (Exception e) {
            System.out.println("Error loading JSON!" + e.getMessage());
        }
    }

}
