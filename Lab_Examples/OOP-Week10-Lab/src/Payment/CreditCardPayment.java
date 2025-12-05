package Payment;
import Main.MainClass;
import Utils.InputUtils;

public class CreditCardPayment implements PaymentBehavior{

    @Override
    public boolean processPayment(double totalAmount)
    {
        InputUtils.readString("Enter credit card number?");
        InputUtils.readString("Enter CVC code?");

        System.out.println("Connecting bank system...");
        MainClass.waitForSeconds(2);

        System.out.println("Payment is confirmed...");
        MainClass.waitForSeconds(1);

        System.out.println("Process successful " + totalAmount + " withdrawn from your account!");
        return true;
    }

}
