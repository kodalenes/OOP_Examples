import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Scanner;

public class Time
{
    //Attributes
    private final Scanner scanner;
    private int hour;
    private int minute;
    private int second;
    private LocalTime time;

    //Constructor
    public Time(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public String ToString(){ return String.format("%d:%d:%d\n" ,hour,minute,second);}

    public String toUniversalString()
    {
        return String.format("%02d:%02d:%02d %s" ,
                            hour > 12 ? hour%12 : hour,
                            minute,
                            second,
                            hour > 12 ? "PM" : "AM");
    }

    public void setTime()
    {
        System.out.println("Saati giriniz? (HH:mm:ss formatinda) ");
        try {
            this.time = LocalTime.parse(scanner.nextLine());
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
        }
        this.hour = time.getHour();
        this.minute = time.getMinute();
        this.second = time.getSecond();
    }

    //Getter
    public int getHour()
    {
        return hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public int getSecond()
    {
        return second;
    }

    public LocalTime getTime()
    {
        return time;
    }
}
