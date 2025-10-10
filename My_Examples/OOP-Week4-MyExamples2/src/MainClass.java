import java.util.Scanner;

public class MainClass
{
    public static void main()
    {

        Scanner scanner = new Scanner(System.in);

        BankAccount bankAccount = new BankAccount(scanner,"Enes" ,100.0);
        BankAccount bankAccount1 = new BankAccount(scanner,"Omer" , 100.0);

        bankAccount1.deposit();
        System.out.println(bankAccount.getBalance());
        bankAccount.withdraw();
        System.out.println("Class sayisi: " + BankAccount.getClassCount());
    }
}
