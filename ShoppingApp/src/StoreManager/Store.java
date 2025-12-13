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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Store {

    public static Store instance;

    private static final String PRODUCT_FILE = "product_file";
    List<Product> products ;
    List<ProductType> productTypeList;

    private Store()
    {
        products = new ArrayList<>();
        productTypeList = List.of(ProductType.values());
    }
    public static Store getInstance()
    {
        if (instance == null)
        {
            instance = new Store();
        }
        return instance;
    }

    public void addProductToStore()
    {
        listProductTypes();
        ProductType productType = null;
        while(productType == null)
        {
            try {
                String input = InputUtils.readString("Enter product type? (0 for exit)").toUpperCase();
                if (input.equalsIgnoreCase("0")) return;
                productType = ProductType.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid product type!");
            }
        }

        //First get common infos
        String name = InputUtils.readString("Enter product name?");
        double price = InputUtils.readDouble("Enter product price?");
        int stockAmount = InputUtils.readInt("Enter stock amount");

        //Then gets specific infos
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

    /**
     * Deletes product from store by entered product name
     */
    public void removeProductFromStore()
    {
        //if list is empty return
        if (products.isEmpty())
        {
            System.out.println("Cart is empty!");
            return;
        }
        listAllProducts();

        String targetName = InputUtils.readString("Enter product name to remove from store? (0 for exit)");
        if (targetName.equalsIgnoreCase("0")) return;
        Product product = findProductByName(targetName);

        String confirm = InputUtils.readString(targetName + " will delete.Are you sure (Y/N)");
        if (!confirm.equalsIgnoreCase("Y"))
            return;

        products.remove(product);

        System.out.println(targetName + " is removed from store.");
    }

    public Product findProductByName(String targetName) throws ProductCantFoundException
    {
        return products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(targetName))
                .findFirst()
                .orElseThrow(() -> new ProductCantFoundException("Product cannot found!"));
    }

    public void listAllProducts()
    {
        if (products == null || products.isEmpty())
        {
            System.out.println("There is no product!");
            return;
        }

        System.out.println("--------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-10s%n", "CATEGORY", "NAME", "PRICE", "STOCK");
        System.out.println("--------------------------------------------------------------------");

        for (Product p : products) {
            if (p != null) {
                // %-15s: 15 karakterlik alan ayır ve sola yasla
                // %-20s: 20 karakterlik alan ayır ve sola yasla
                System.out.printf("%-15s %-20s %-15s %-10d%n",
                        p.getProductType(),
                        p.getName(),
                        String.format("%.2f TL", p.getPrice()),
                        p.getStockAmount()
                );
            }
        }
        System.out.println("--------------------------------------------------------------------");
    }

    public void listProductsByPriceGrow()
    {
        System.out.println("----Price (growing)");
        products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .forEach(p -> System.out.printf("%s :%.2f TL Stock: %d %n"
                        ,p.getName() , p.getPrice() , p.getStockAmount()));
    }

    public void listProductsByPriceDecreasing()
    {
        System.out.println("----Price (decreasing)");
        products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .forEach(p -> System.out.printf("%s :%.2f TL Stock: %d %n"
                        ,p.getName() , p.getPrice() , p.getStockAmount()));
    }

    /**
     * Gets min and max value to list product in range
     * @param min min range price
     * @param max max range price
     */
    public void listProductsInPriceRange(double min , double max)
    {
        System.out.println("----Product in " + min + "-" + max + " range----" );
        boolean found = products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .peek(p -> System.out.printf("%s :%.2f TL Stock: %d %n"
                        ,p.getName() , p.getPrice() , p.getStockAmount()))
                .count() > 0;

        if (!found)
            System.out.println("There is no product in range!");
    }

    public void listProductsByCategory()
    {
        System.out.println(productTypeList);
        ProductType choice = ProductType.valueOf(InputUtils.readString("Enter product category?").toUpperCase());

        products.stream()
                .filter(p -> p.getProductType().equals(choice))
                .forEach(p -> System.out.printf("%s :%.2f TL Stock: %d %n"
                        ,p.getName() , p.getPrice() , p.getStockAmount()));
    }

    public void listProductTypes()
    {
        System.out.println(productTypeList);
    }

    public void saveToJSON()
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()//Tek satir degil alt alta yazmak
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())//Tarih bilgisini tanitma
                .create();

        //opening file (if there is an error writer will auto close)
        try(FileWriter writer = new FileWriter(PRODUCT_FILE)){
            gson.toJson(products , writer);//writes list to json
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

        //Create reader (if there is an error reader will auto close)
        try(FileReader reader = new FileReader(PRODUCT_FILE)){
            //This line says java you're going to read Product ArrayList
            //So this line introduce the files info to gson
            Type listType = new TypeToken<ArrayList<Product>>(){}.getType();

            //Read the file and convert it to Product List
            List<Product> loadedList = gson.fromJson(reader , listType);

            if(loadedList != null)
            {
                //If read list doesn't empty
                //Our product list now equal read list
                this.products = loadedList;
                System.out.println("Products loaded successfully");
            }

        } catch (Exception e) {
            System.out.println("Error loading JSON!" + e.getMessage());
        }
    }

}
