package Utils;

import java.util.Scanner;

public class InputUtils {
    public static Scanner scanner = new Scanner(System.in);

    public static String readString(String message)
    {
        while(true)
        {
            System.out.println(message + ":");
            String value = scanner.nextLine().trim();

            if (value.isEmpty())
            {
                System.out.println("Error: This field cannot be empty!");
            }else {
                return value;
            }
        }
    }

    public static int readInt(String message)
    {
        while(true)
        {
            System.out.println(message + ":");
            try{
                return Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e)
            {
                System.out.println("Enter valid number!");
            }
        }
    }

    public static double readDouble(String message)
    {
        while(true)
        {
            try{
                System.out.println(message + ":");
                return Double.parseDouble(scanner.nextLine());
            }catch (NumberFormatException e)
            {
                System.out.println("Enter valid number!");
            }
        }
    }
}
