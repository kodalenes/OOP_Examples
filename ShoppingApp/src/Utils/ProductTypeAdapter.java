package Utils;

import Products.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class ProductTypeAdapter implements JsonDeserializer<Product>, JsonSerializer<Product> {

    private final Gson gson;

    public ProductTypeAdapter() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    @Override
    public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // productType alanını oku
        String productTypeStr = jsonObject.get("productType").getAsString();
        ProductType productType = ProductType.valueOf(productTypeStr);

        // ProductType'a göre doğru sınıfa deserialize et
        return switch (productType) {
            case BOOK -> gson.fromJson(json, Book.class);
            case ELECTRONICS -> gson.fromJson(json, Electronics.class);
            case FOOD -> gson.fromJson(json, Food.class);
            case CLOTHES -> gson.fromJson(json, Clothes.class);
        };
    }

    @Override
    public JsonElement serialize(Product src, Type typeOfSrc, JsonSerializationContext context) {
        // Alt sınıfın tüm alanlarını serialize et
        return gson.toJsonTree(src, src.getClass());
    }
}

