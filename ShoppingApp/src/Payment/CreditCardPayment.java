package Payment;
import Main.MainClass;
import Main.Menu;
import Utils.InputUtils;

public class CreditCardPayment implements PaymentBehavior{

    @Override
    public boolean processPayment(double totalAmount)
    {
        String creditCard = InputUtils.readString("Enter credit card number? (0 for menu)");
        if (creditCard.equalsIgnoreCase("0"))
            return false;
        String CVC = InputUtils.readString("Enter CVC code? (0 for menu)");
        if (CVC.equalsIgnoreCase("0"))
            return false;

        System.out.println("Connecting bank system...");
        Menu.waitForSeconds(2);

        System.out.println("Payment is confirmed...");
        Menu.waitForSeconds(1);

        System.out.println("Process successful " + totalAmount + " withdrawn from your account!");
        return true;
    }

}
