package Payment;

import Main.MainClass;
import Utils.InputUtils;

public class CashPayment implements PaymentBehavior{

    @Override
    public boolean processPayment(double totalAmount)
    {
        double cashGiven = InputUtils.readDouble("Enter the amount of money the customer pays?");

        if (cashGiven < totalAmount)
        {
            System.out.println("Insufficient funds. Process failed!");
            return false;
        }else
        {
            double change = cashGiven - totalAmount;
            System.out.println("Calculating change...");
            MainClass.waitForSeconds(1);
            System.out.printf("Process successful. Change: %.2f TL%n" , change);
            return true;
        }
    }
}
