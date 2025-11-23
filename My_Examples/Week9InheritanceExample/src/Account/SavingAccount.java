package Account;

import Utils.Logger;

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
        double interestAmount = getBalance() * interestRate;
        setBalance(getBalance() + interestAmount);
        System.out.println("Interest added to the account");
        System.out.printf("%s Balance is %.2f TL %n" , getAccHolder() , getBalance());
        Logger.log(String.format("INTEREST: %.2f TL interest added to Account %d. New Balance: %.2f TL",
                interestAmount, getAccNumber(), getBalance()));
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
            Logger.log(String.format("LIMIT: Account %d attempted withdrawal but exceeded monthly limit (3)", getAccNumber()));
        }
    }

    @Override
    public void transfer( BankAccount to, double amount) {
        super.transfer(to, amount);
    }

    @Override
    public void displayAccountInfo() {
        System.out.printf(
                "Account Number: %s ,Account Holder: %s , Balance: %.2f TL , Type: %s, Interest Rate: %.2f%n" ,
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
