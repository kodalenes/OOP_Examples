package Currency;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyService {

    public static double getExchangeRate(Currency targetCurrency)
    {
        if (targetCurrency == Currency.TRY)
        {
            return 1.0;
        }

        String url = "https://api.frankfurter.app/latest?from=TRY&to=" + targetCurrency.getCode();

        try (HttpClient client =  HttpClient.newHttpClient()){

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body() , JsonObject.class);

            return jsonObject.getAsJsonObject("rates").get(targetCurrency.getCode()).getAsDouble();
        }catch (Exception e)
        {
            System.out.println("Cant get currency info: " + e.getMessage());
            return 0.0;
        }
    }
}
