package Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "activity_log.txt";

    public static void log(String message)
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedTime = now.format(formatter);
        String logMessage = String.format("[%s] %s%n",formattedTime , message);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE,true))){
            ;
            writer.write(logMessage);
        }catch (IOException e)
        {
            System.out.println("Error: Log cannot write");
        }
    }
}
