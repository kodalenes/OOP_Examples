package StoreManager;

import Exceptions.ProductCantFoundException;
import Products.*;
import Utils.InputUtils;
import Utils.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Store {

    private static final String PRODUCT_FILE = "product_file";
    List<Product> products = new ArrayList<>();
    List<ProductType> productTypeList = List.of(ProductType.values());

    public void addProductToStore()
    {
        listProductTypes();
        ProductType productType = ProductType.valueOf(InputUtils.readString("Enter product type?").toUpperCase());

        String name = InputUtils.readString("Enter product name?");
        double price = InputUtils.readDouble("Enter product price?");
        int stockAmount = InputUtils.readInt("Enter stock amount");

        Product product = null;
        switch (productType){
            case ELECTRONICS ->{
                int warrantyPeriod = InputUtils.readInt("Enter warranty period (months)?");
                product = new Electronics(name ,price ,warrantyPeriod , stockAmount);
            }
            case CLOTHES ->{
                int size = InputUtils.readInt("Enter size (0,1,2,3)?");
                product = new Clothes(name ,price ,size , stockAmount);
            }
            case FOOD ->{
                int consPeriod = InputUtils.readInt("Enter recommended consumption period (days)?");
                product = new Food(name,price ,stockAmount, consPeriod);
            }
            case BOOK ->{
                int page = InputUtils.readInt("Enter book's page");
                product = new Book(name ,price ,stockAmount , page);
            }
            default -> {
                System.out.println("Invalid type!");
                return;
            }
        }
        products.add(product);
        System.out.println(product.getName() + " added to store successfully.");
    }

    public void removeProductFromStore()
    {
        if (products.isEmpty())
        {
            System.out.println("Cart is empty!");
            return;
        }
        listAllProducts();

        String targetName = InputUtils.readString("Enter product name to remove from store?");

        String confirm = InputUtils.readString(targetName + " will delete.Are you sure (Y/N)");
        if (!confirm.equalsIgnoreCase("Y"))
            return;

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
                System.out.printf("%s :%s  Stock:%d %n",p.getProductType(), p, p.getStockAmount());
    }

    public void listProductTypes()
    {
        System.out.println(productTypeList);
    }

    public void saveToJSON()
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
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

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class , new LocalDateAdapter())
                .create();

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
