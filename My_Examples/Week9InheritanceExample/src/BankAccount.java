public class BankAccount {
    protected String accNumber;
    protected String accHolder;
    protected double balance;

    BankAccount(String accNumber , String accHolder , double balance)
    {
        this.accHolder = accHolder;
        this.accNumber = accNumber;
        this.balance = balance;
    }
    //Para yatirma
    public void deposit(double amount)
    {
        balance += amount;
        System.out.println("Successfully deposit " + amount);
        System.out.printf("%s Balance is %f %n" , accHolder , getBalance());
    }

    //Para cekme
    public void withdraw(double amount)
    {
        if (amount > getBalance())
        {
            System.out.println("Withdraw failed!");
        }
        else {
            balance -= amount;
            System.out.println("Successfully withdraw " + amount);
            System.out.printf("%s Balance is %f %n" , accHolder , getBalance());
        }
    }

    public void displayAccountInfo()
    {
        System.out.printf("Account Number: %s ,Account Holder: %s , Balance: %.2f%n" ,accNumber , accHolder , getBalance());
    }

    public double getBalance() {
        return balance;
    }
}
