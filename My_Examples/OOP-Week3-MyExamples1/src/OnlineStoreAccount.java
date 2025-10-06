import java.util.Scanner;

public class OnlineStoreAccount
{
    public String username;
    private Double balance;
    private int failedLogInCount;
    private Double cartTotal = 0.0;
    private String password;
    private Boolean isSuspended = false;
    private Boolean isLogInSuccessful = false;

    OnlineStoreAccount(String name ,Double balance)
    {
        this.username = name;
        this.balance = balance;
        this.failedLogInCount = 0;
    }

    Scanner scanner = new Scanner(System.in);

    public void login()
    {
        while(failedLogInCount < 3)
        {
            System.out.println("Please enter your password to login!");
            String enteredPassword = scanner.next();
            if (enteredPassword.equals(password))
            {
                isLogInSuccessful = true;
                isSuspended = false;
                System.out.println("Log in successful.");
                return;
            }
            else
            {
                failedLogInCount++;
                System.out.println("Incorrect password.Attempts left: " + (3 - failedLogInCount));
            }
        }

        if (failedLogInCount == 3)
        {
            isSuspended = true;
            System.out.println("Account suspended!");
        }
    }

    public void deposit(Double amount)
    {
        if(isOperationBlocked()) return;
        if (amount <= 0) {
            System.out.println("Yatırılan miktar pozitif olmalı!");
            return;
        }
        this.balance += amount;
        System.out.printf("Balance is %.2f\n",this.getBalance());

    }

    public void addToCart()
    {
        if (isOperationBlocked()) return;

        System.out.println("Please enter the price of material");
        double price = scanner.nextDouble();
        if (price <= 0) {
            System.out.println("Fiyat pozitif olmalı!");
            return;
        }
        this.cartTotal += price;
        System.out.printf("The cart total is %.2f\n",getCartTotal());

    }

    public void checkout()
    {
        if (isOperationBlocked()) return;

        if (this.balance >= cartTotal)
        {
            this.balance -= cartTotal;
            System.out.println("Thank you for your purchase. ");
        }
        else
        {
            System.out.println("Insufficient balance for checkout.\n");
        }
        System.out.printf("Balance is %.2f\n",this.getBalance());
    }


    public void setPassword(String password)
    {
        this.password = password;
    }

    public Double getBalance()
    {
        return this.balance;
    }

    public Double getCartTotal()
    {
        return this.cartTotal;
    }

    public boolean isOperationBlocked()
    {
        return (!isLogInSuccessful || isSuspended);
    }

}
