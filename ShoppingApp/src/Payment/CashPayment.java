package Payment;

import Main.MainClass;
import Utils.InputUtils;
import Main.Menu;
import com.sun.tools.javac.Main;

public class CashPayment implements PaymentBehavior{

    @Override
    public boolean processPayment(double totalAmount)
    {
        double cashGiven = InputUtils.readDouble("Enter the amount of money the customer pays? (0 for menu)");
        if (cashGiven == 0)
            return false;

        if (cashGiven < totalAmount)
        {
            System.out.println("Insufficient funds. Process failed!");
            return false;
        }else
        {
            double change = cashGiven - totalAmount;
            System.out.println("Calculating change...");
            Menu.waitForSeconds(1);
            System.out.printf("Process successful. Change: %.2f TL%n" , change);
            return true;
        }
    }
}
