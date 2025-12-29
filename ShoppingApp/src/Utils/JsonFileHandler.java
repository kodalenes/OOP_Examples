package Utils;

import Products.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonFileHandler {

    private static final String PRODUCT_FILE = "product_file";

    public static void saveToJSON(List<Product> products)
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()//Tek satir degil alt alta yazmak
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())//Tarih bilgisini tanitma
                .registerTypeAdapter(Product.class, new ProductTypeAdapter())//Polimorfik destek
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

    public static List<Product> loadFromJSON()
    {
        File file = new File(PRODUCT_FILE);

        if (!file.exists())
        {
            System.out.println("No JSON file found - starting with empty store!");
            return new ArrayList<>();
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class , new LocalDateAdapter())
                .registerTypeAdapter(Product.class, new ProductTypeAdapter())//Polimorfik destek
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
                System.out.println("Products loaded successfully");
                return loadedList;
            }

        } catch (Exception e) {
            System.out.println("Error loading JSON!" + e.getMessage());
        }

        return new ArrayList<>();
    }
}

