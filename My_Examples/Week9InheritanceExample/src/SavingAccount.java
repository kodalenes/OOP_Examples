public class SavingAccount extends BankAccount{

    private double interestRate = 0.05; //%5
    private int withdrawalCount = 0;
    private final String accType = "Saving";

    SavingAccount(String accNumber , String accHolder , double balance , double interestRate)
    {
        super(accNumber,accHolder,balance,"Saving");
        this.interestRate = interestRate;
    }

    public void addInterest()
    {
        balance += (balance * interestRate);
        System.out.println("Interest added to the account");
        System.out.printf("%s Balance is %.2f %n" , accHolder , getBalance());
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

    @Override
    public void transfer(BankAccount to, double amount) {
        super.transfer(to, amount);
    }

    @Override
    public void displayAccountInfo() {
        System.out.printf(
                "Account Number: %s ,Account Holder: %s , Balance: %.2f , Type: %s, Interest Rate: %.2f%n" ,
                accNumber ,
                accHolder ,
                getBalance() ,
                accType ,
                interestRate);
    }

    public void resetMonthlyWithdrawals()
    {
        withdrawalCount = 0;
    }
}
