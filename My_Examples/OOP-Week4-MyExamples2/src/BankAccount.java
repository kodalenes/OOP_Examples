import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankAccount
{
    // Attributes
    private String username;
    private Double balance;
    private static int classCount;
    private Scanner scanner;

    public BankAccount(Scanner scanner,String username, Double balance)
    {
        this.username = username;
        this.balance = balance;
        this.scanner = scanner;
        classCount++;
    }

    public void deposit()
    {
        try {
            System.out.println("Enter the count that you want to deposit");
            double amount = scanner.nextDouble();
            if (amount <= 0)
                throw new IOException("Deposit amount must be bigger then 0");

            this.balance += amount;
            System.out.println("Balance: " + this.getBalance());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void withdraw()
    {
        try {
            System.out.println("Enter amount that u want to withdraw?");
            double amount = scanner.nextDouble();
            if (amount <= 0 )
                throw new IllegalArgumentException("Withdraw amount must be bigger then 0");
            if (amount > balance)
                throw new IllegalArgumentException("Amount is bigger then balance");

            this.balance -= amount;
            System.out.println("Balance: " + this.getBalance());
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number!");
            scanner.nextLine();
        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Double getBalance()
    {
        return balance;
    }

    public void setBalance(Double balance)
    {
        this.balance = balance;
    }

    public static int getClassCount()
    {
        return classCount;
    }
}
