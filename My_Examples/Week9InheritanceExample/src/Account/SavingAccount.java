package Account;

public class SavingAccount extends BankAccount{

    private double interestRate = 0.05; //%5
    private int withdrawalCount = 0;

    SavingAccount(String accHolder,String password, double interestRate)
    {
        super(accHolder, password);
        setAccType(AccountType.SAVING);
        this.interestRate = interestRate;
    }

    public void addInterest()
    {
        setBalance(getBalance() + (getBalance() * interestRate));
        System.out.println("Interest added to the account");
        System.out.printf("%s Balance is %.2f %n" , getAccHolder() , getBalance());
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
    public void transfer( BankAccount to, double amount) {
        super.transfer(to, amount);
    }

    @Override
    public void displayAccountInfo() {
        System.out.printf(
                "Account Number: %s ,Account Holder: %s , Balance: %.2f , Type: %s, Interest Rate: %.2f%n" ,
                getAccNumber() ,
                getAccHolder() ,
                getBalance() ,
                getAccType() ,
                interestRate);
    }

    public void resetMonthlyWithdrawals()
    {
        withdrawalCount = 0;
    }
}
