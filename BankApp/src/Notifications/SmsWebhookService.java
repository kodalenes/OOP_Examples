package Notifications;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class SmsWebhookService implements INotification{

    private final String WEBHOOK_URL = "https://webhook.site/4fc9f368-62df-4a4f-b788-07164390e1a3";

    @Override
    public void sendNotification(String message, String phoneNumber)
    {
        sendNotificationWithStatus(message, phoneNumber, "INFO");
    }

    public void sendNotificationWithStatus(String message, String phoneNumber, String status)
    {
        try (HttpClient client = HttpClient.newHttpClient()){
        Map<String,String> data = new HashMap<>();
        data.put("to" ,phoneNumber);
        data.put("sms content" , message);
        data.put("status" , status); // URGENT veya INFO

        Gson gson = new Gson();
        String jsonBody = gson.toJson(data);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEBHOOK_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        System.out.println("Sending to SMS Webhook service. [Status: " + status + "]");
        client.sendAsync(request,HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::statusCode)
                    .thenAccept(statusCode -> System.out.println("SMS Service Response: " + statusCode))
                    .join();

        } catch (Exception e) {
            System.out.println("SMS sending error: " + e.getMessage());
        }
    }
}
