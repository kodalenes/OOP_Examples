public class BankAccount {
    protected String accNumber;
    protected String accHolder;
    protected double balance;
    protected String accType;

    //Constructor
    BankAccount(String accNumber , String accHolder , double balance ,String accType)
    {
        this.accHolder = accHolder;
        this.accNumber = accNumber;
        this.balance = balance;
        this.accType = accType;
    }

    //Methods

    //Para yatirma
    public void deposit(double amount)
    {
        balance += amount;
        System.out.println("Successfully deposit " + amount);
        System.out.printf("%s Balance is %.2f %n" , accHolder , getBalance());
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

    public void transfer(BankAccount to, double amount)
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

    public void setBalance(double balance) {
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
}
