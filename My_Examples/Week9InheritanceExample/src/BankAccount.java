import java.time.LocalDate;

public class BankAccount {
    //Account Info
    protected String accNumber;
    protected String accHolder;
    protected double balance;
    protected String accType;
    protected String password;
    protected PasswordCheck passwordCheck;
    protected double DAILY_WITHDRAWAL_LIMIT = 5000;
    protected double amountWithdrawnToday;
    //Date
    protected LocalDate today;
    protected LocalDate lastWithdrawalDate;

    //Constructor
    BankAccount(String accNumber , String accHolder , double balance , String password ,String accType)
    {
        this.accHolder = accHolder;
        this.accNumber = accNumber;
        this.balance = balance;
        this.password = password;
        this.accType = accType;
    }

    //Methods
    public void deposit(double amount)
    {
        if (!(amount > 0))
        {
            throw new IllegalArgumentException("Deposit amount must be above 0");
        }

        balance += amount;
        System.out.println("Successfully deposit " + amount);
        System.out.printf("%s Balance is %.2f %n" , accHolder , getBalance());


    }

    public void withdraw(double amount)
    {
        today = LocalDate.now();
        if (lastWithdrawalDate == null || lastWithdrawalDate.isBefore(today))
        {
            amountWithdrawnToday = 0;
        }

        if (!(amount > 0))
        {
            throw new IllegalArgumentException("Withdraw amount must be above 0");
        }
        else if (amountWithdrawnToday + amount > DAILY_WITHDRAWAL_LIMIT)
        {
            throw new DailyLimitExceededException("Daily withdraw limit exceeded. Remaining limit: "
                    +  (DAILY_WITHDRAWAL_LIMIT - amountWithdrawnToday));
        }
        else if (amount > getBalance())
        {
            System.out.println("Withdraw failed!");
        }
        else {
            balance -= amount;
            amountWithdrawnToday += amount;
            lastWithdrawalDate = today;
            double currentRemainingLimit = DAILY_WITHDRAWAL_LIMIT - amountWithdrawnToday;
            System.out.println("Successfully withdraw " + amount);
            System.out.printf("%s Balance is %.2f %n" , accHolder , getBalance());
            System.out.printf("%s remaining daily withdraw limit is: %.2f%n" , accHolder ,currentRemainingLimit);
        }
    }

    public void transfer(BankAccount to, double amount)
    {
        if (amount > 0)
        {
            if (balance >= amount)
            {
                to.balance += amount;
                balance -= amount;
                System.out.println("Transfer successful");
            }
            else {
                System.out.println("Insufficient balance.");
            }
        }else
        {
            throw new IllegalArgumentException("Transfer amount must be above 0");
        }
    }

    public void displayAccountInfo()
    {
        System.out.printf("Account Number: %s ,Account Holder: %s , Balance: %.2f , Account type: %s%n" ,
                accNumber ,
                accHolder ,
                getBalance() ,
                accType);
    }

    //Setter&Getter
    public void setAccHolder(String accHolder) {
        this.accHolder = accHolder;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getAccHolder() {
        return accHolder;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccType() {
        return accType;
    }

    public void setDAILY_WITHDRAWAL_LIMIT(double DAILY_WITHDRAWAL_LIMIT) {
        this.DAILY_WITHDRAWAL_LIMIT = DAILY_WITHDRAWAL_LIMIT;
    }

    public double getDAILY_WITHDRAWAL_LIMIT() {
        return DAILY_WITHDRAWAL_LIMIT;
    }
}
