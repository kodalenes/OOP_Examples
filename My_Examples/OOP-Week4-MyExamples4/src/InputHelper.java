import com.sun.net.httpserver.Authenticator;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Consumer;

public class InputHelper
{
    /**
     * Hem InputMismatchException hem de IllegalArgumentException'ı içeride yakalar.
     * @param scanner       Scannner nesnesi
     * @param min valid min value
     * @param max valid max value
     * @param promptMessage kullaniciya gosterilecek soru mesaji
     * @return gecerli tamsayi degeri
     */

    public static int getValidIntInput(Scanner scanner ,int min ,int max, String promptMessage)
    {
        int input = -1;
        boolean isValid = false;

        while (!isValid)
        {
            System.out.println(promptMessage);

            try {
                input = scanner.nextInt();

                if(input < min || input > max)
                {
                    throw new IllegalArgumentException("The value must be between " + min + " " + max);
                }

                isValid = true;

            } catch (InputMismatchException e) {
                System.out.println("Error: invalid format.Please enter a whole number.");
                scanner.next();
            }catch (IllegalArgumentException e){
                System.out.println("Validation Error:" + e.getMessage());
            }
        }
        return input;
    }

    /**
     * Kullanıcıdan String alır ve bu String'i set metotlarına atamayı dener.
     * Hata (IllegalArgumentException) fırlatıldığı sürece tekrar sorar.
     * @param setter kabul edici
     * @param promptMessage Kullaniciya gosterilecek soru mesaji
     */
    public static void getValidStringInput(Scanner scanner, String promptMessage , Consumer<String> setter)
    {
        boolean isValid = false;

        System.out.println("\n---" + promptMessage);

        while (!isValid)
        {
            System.out.println(promptMessage + ": ");
            String input = scanner.nextLine().trim();//Bosluklu metinleri aliyoruz

            if (input.isEmpty()){
                System.out.println("Validation error: Input cannot be empty. Try again.");
                continue;
            }
            try {
                setter.accept(input);

                isValid = true;
                System.out.println("Succes");
            }catch (IllegalArgumentException e)
            {
                System.out.println("Validation Error: " + e.getMessage());
            }
        }
    }
}
