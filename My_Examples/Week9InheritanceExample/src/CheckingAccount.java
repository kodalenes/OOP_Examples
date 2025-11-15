public class CheckingAccount extends BankAccount{

    private double overdraftLimit;
    CheckingAccount(String accNumber , String accHolder , double balance ,double overdraftLimit,String password, double interestRate)
    {
        super(accNumber,accHolder,balance ,password, "Checking");
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount)
    {
        if (balance >= amount)
        {
            super.withdraw(amount);
        }
        else if (balance < amount && getAvailableBalance() >= amount)
        {
            double usedOverdraftLimit = amount - balance;
            overdraftLimit -= usedOverdraftLimit;
            balance = 0;
            System.out.println("Withdraw successful with overdraft limit.");
            System.out.printf("%s Balance is %.2f%n" , accHolder , getBalance());
            System.out.printf("%s Overdraft limit is %.2f%n" , accHolder , overdraftLimit);
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
                                    accNumber ,
                                    accHolder ,
                                    getBalance() ,
                                    accType ,
                                    overdraftLimit ,
                                    getAvailableBalance());
    }

    public double getAvailableBalance()
    {
        return getBalance() + overdraftLimit;
    }
}
