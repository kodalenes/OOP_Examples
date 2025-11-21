package Account;

public class CheckingAccount extends BankAccount{

    private double overdraftLimit;
    CheckingAccount(String accHolder,double overdraftLimit,String password)
    {
        super(accHolder,password, "Checking");
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount)
    {
        if (getBalance() >= amount)
        {
            super.withdraw(amount);
        }
        else if (getBalance() < amount && getAvailableBalance() >= amount)
        {
            double usedOverdraftLimit = amount - getBalance();
            overdraftLimit -= usedOverdraftLimit;
            setBalance(0);
            System.out.println("Withdraw successful with overdraft limit.");
            System.out.printf("%s Balance is %.2f%n" , getAccHolder() , getBalance());
            System.out.printf("%s Overdraft limit is %.2f%n" , getAccHolder() , overdraftLimit);
        }else
        {
            System.out.println("Insufficient balance and overdraft limit!");
        }
    }

    @Override
    public void transfer(BankAccount to, double amount) {
        super.transfer(to, amount);
    }

    @Override
    public void displayAccountInfo() {
        System.out.printf(
                "Account Number: %s ,Account Holder: %s , Balance: %.2f , Type: %s, OverdraftLimit : %.2f , Available Balance: %.2f%n" ,
                                    getAccNumber(),
                                    getAccHolder(),
                                    getBalance(),
                                    getAccType(),
                                    overdraftLimit,
                                    getAvailableBalance());
    }

    public double getAvailableBalance()
    {
        return getBalance() + overdraftLimit;
    }
}
