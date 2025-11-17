public class InputUtils {
    public static int readInt(String message)
    {
        while (true)
        {
            System.out.println(message + ": ");
            try {
                return Integer.parseInt(MainClass.input.nextLine());
            }catch (NumberFormatException e)
            {
                System.out.println("Enter valid number!");
            }
        }
    }

    public static String readString(String message)
    {
        while(true)
        {
            System.out.println(message + ": ");
            String value = MainClass.input.nextLine().trim();

            if (value.isEmpty())
            {
                System.out.println("Error: This field cannot be empty!");
            }
            else
            {
                return value;
            }
        }
    }

    public static String readName(String message) {
        while (true) {
            System.out.print(message + ": ");
            String name = MainClass.input.nextLine().trim();

            if (!name.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+")) {
                System.out.println("Error: Name must be include just letters!");
            } else {
                return name;
            }
        }
    }

    public static double readDouble(String message)
    {
        while (true)
        {
            System.out.println(message + ": ");
            try {
                return Double.parseDouble(MainClass.input.nextLine());
            }catch (NumberFormatException e)
            {
                System.out.println("Enter valid number!");
            }
        }
    }
}
