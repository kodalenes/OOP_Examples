package Utils;

import java.util.Scanner;

public class InputUtils {
    public static Scanner scanner = new Scanner(System.in);

    public static String readString(String message)
    {
        while(true)
        {
            System.out.print(message + ":");
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
            System.out.print(message + ":");
            try{
                int value = Integer.parseInt(scanner.nextLine());
                if (value < 0)
                {
                    System.out.println("Value cannot be negative!");
                    continue;
                }
                return value;
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
                System.out.print(message + ":");
                double value = Double.parseDouble(scanner.nextLine());
                if (value < 0)
                {
                    System.out.println("Value cannot be negative!");
                    continue;
                }
                return value;
            }catch (NumberFormatException e)
            {
                System.out.println("Enter valid number!");
            }
        }
    }
}
