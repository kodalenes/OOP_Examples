import java.util.Scanner;

public class Account
{
    private Double balance;
    private String ownerName;
    private int failedAttempts;
    private double transferredAmount;
    private int password;

    Account(String name ,Double balance)
    {
        this.ownerName = name;
        this.balance = balance;
        this.failedAttempts = 0;
    }

    public void setPassword(int password)
    {
        this.password = password;
    }

    public void add(Double amount)
    {
        this.balance += amount;
    }

    public void withdraw(Double amount)
    {

        while (this.failedAttempts < 3)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter 4-digit password");
            int passwordFromKeyboard = scanner.nextInt();

            //Sifre dogruysa
            if (this.password == passwordFromKeyboard)
            {
                this.balance -= amount;
                break;
            }
            else//Sifre dogru degilse
            {
                System.out.println("Try again!");
                failedAttempts++;
            }
        }

        if (this.failedAttempts == 3)
        {
            System.out.println("Your account has been blocked,please try again later. ");
        }
    }

    public Double getBalance()
    {
        return balance;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public void transferTo(Account to , Double amount)
    {
        if (this.balance >= amount)
        {
            this.balance -= amount;
            this.transferredAmount += amount;
            to.add(amount);
        }
        else System.out.println("Insufficient funds");

    }

    public Double getTransferredAmount()
    {
        return transferredAmount;
    }

}
