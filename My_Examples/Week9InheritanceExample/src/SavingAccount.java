public class SavingAccount extends BankAccount{
    private double interestRate = 0.05; //%5
    private int withdrawalCount = 0;

    SavingAccount(String accNumber , String accHolder , double balance)
    {
        super(accNumber,accHolder,balance);
    }

    public void addInterest()
    {
        balance += (balance * interestRate);
        System.out.println("Interest added to the account");
        System.out.printf("%s Balance is %f %n" , accHolder , getBalance());
    }

    @Override
    public void withdraw(double amount)
    {
        if (withdrawalCount < 3)
        {
            super.withdraw(amount);
            withdrawalCount++;
        } else {
            System.out.println("Monthly withdrawal limit exceeded!");
        }
    }

    public void resetMonthlyWithdrawals()
    {
        withdrawalCount = 0;
    }
}
