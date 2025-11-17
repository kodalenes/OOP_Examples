import javax.naming.InvalidNameException;
import java.util.Scanner;

public class MainClass {

    public static final Scanner input = new Scanner(System.in);
    public static void main(String[] args)
    {

        Bank bank = new Bank();
        PasswordCheck passwordCheck = new PasswordCheck();
        bank.loadAccNumber();
        bank.loadFromJson();
        boolean isOver = false;
        do {
            System.out.println("1- Create Account");
            System.out.println("2- Delete Account");
            System.out.println("3- Deposit");
            System.out.println("4- Withdraw");
            System.out.println("5- Transfer");
            System.out.println("6- List Accounts");
            System.out.println("7- Display Transaction History By Number");
            System.out.println("8- Update Account Info");
            System.out.println("0- Quit");
            int choice = InputUtils.readInt("Your choice");

            switch (choice){
                case 1:
                    CreateAccount(bank, passwordCheck);
                    break;
                case 2:
                    DeleteAccount(bank);
                    break;
                case 3:
                    makeDeposit(bank);
                    break;
                case 4:
                    makeWithdraw(bank);
                    break;
                case 5:
                    makeTransfer(bank);
                    break;
                case 6:
                    bank.displayAll();
                    break;
                case 7:
                    displayTransactionHistoryByNumber(bank);
                    break;
                case 8:
                    updateAccount(bank);
                    break;
                case 0:
                    isOver = true;
                    bank.saveToJson();
                    bank.saveAccNumber();
                    break;
            }
        }while(!isOver);
    }

    public static void CreateAccount(Bank bank, PasswordCheck passwordCheck)
    {
        boolean isOver = false;
        do {
            System.out.println("Enter what type of account do you want to create.");
            System.out.println("1- Checking Account");
            System.out.println("2- Saving Account");
            System.out.println("0- Back");
            int choice = InputUtils.readInt("Your choice");
            switch (choice) {
                case 1:
                    createCheckingAcc(bank, passwordCheck);
                    break;
                case 2:
                    createSavingAcc(bank, passwordCheck);
                    break;
                case 0:
                    isOver = true;
                    break;
                default:
                    System.out.println("Geçersiz seçim, lütfen tekrar deneyin.");
                    break;
                }
            } while (!isOver);

    }

    private static void createSavingAcc(Bank bank,PasswordCheck passwordCheck)
    {
        BaseAccountInfo info = getBaseAccountinfo(passwordCheck);

        SavingAccount s1 = new SavingAccount(info.accHolder,0, info.password,0.05);
        bank.addAccount(s1);
        System.out.println("Account created.");
    }

    private static void createCheckingAcc(Bank bank, PasswordCheck passwordCheck)
    {
        BaseAccountInfo info = getBaseAccountinfo(passwordCheck);

        double overdraftLimit = 200;
        CheckingAccount c1 = new CheckingAccount(info.accHolder(),0,overdraftLimit ,info.password(),overdraftLimit);
        bank.addAccount(c1);
        System.out.println("Account created.");
    }

    private static void DeleteAccount(Bank bank)
    {
        int accNum = InputUtils.readInt("Enter the account number that you want to delete");
        bank.removeByNumber(accNum);
    }

    private static void makeDeposit(Bank bank)
    {
        BasePasswordCheck info = getBasePasswordCheck(bank);

        if (info.isLogin())
        {
            double amount = InputUtils.readDouble("Enter the amount to deposit");
            try {
                info.account().deposit(amount);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void makeWithdraw(Bank bank)
    {
        BasePasswordCheck info = getBasePasswordCheck(bank);

        if (info.isLogin())
        {
            try {
                double amount = InputUtils.readDouble("Enter the amount to withdraw");
                info.account().withdraw(amount);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void makeTransfer(Bank bank)
    {
        BankAccount transferAcc = null;

        BasePasswordCheck info = getBasePasswordCheck(bank);

        if (info.account != null)
        {
            int transferAccNum = InputUtils.readInt("Enter the account number that you want to transfer");
            transferAcc = bank.getAccByNumber(transferAccNum);
        }

        if (info.account != null && transferAcc != null )
        {
            double amount = InputUtils.readDouble("Enter the amount to transfer");
            try {
                info.account().transfer(transferAcc, amount);

            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void updateAccount(Bank bank)
    {
        int accNumber = InputUtils.readInt("Enter account number that you want to update");
        BankAccount account = bank.getAccByNumber(accNumber);
        if (account != null) {
            account.displayTransactionHistory();
            String newAccHolder;
            do {
                newAccHolder = InputUtils.readName("Enter new account holder");
                try {
                    account.setAccHolder(newAccHolder);
                    break;
                } catch (InvalidNameException e) {
                    System.out.println(e.getMessage());
                }
            } while (true);
        }

    }

    private static void displayTransactionHistoryByNumber(Bank bank)
    {
        int accNumber = InputUtils.readInt("Enter account number that you want to display transaction history");
        BankAccount account = bank.getAccByNumber(accNumber);

        if (account != null)
        {
            account.displayTransactionHistory();
        }
    }

    private static BaseAccountInfo getBaseAccountinfo(PasswordCheck passwordCheck) {
        String accHolder = InputUtils.readName("Enter Acc Holder Name");

        String password;
        do {
            password = InputUtils.readString("Enter password[6 digit-only numbers]");
        } while (passwordCheck.isInvalidPassword(password));
        return new BaseAccountInfo(accHolder, password);
    }

    private record BaseAccountInfo(String accHolder, String password)
    {
    }

    private static BasePasswordCheck getBasePasswordCheck(Bank bank)
    {
        int trialCounter = 0;
        boolean isLogin = false;

        int accNumber = InputUtils.readInt("Enter your account number");

        BankAccount account = bank.getAccByNumber(accNumber);

        if (account != null) {
            while (trialCounter < 3)
            {
                String enteredPass = InputUtils.readString("Enter your 6 digit password");
                if (enteredPass.equals(account.password))
                {
                    System.out.println("Login successful");
                    isLogin = true;
                    break;
                }
                else {
                    System.out.println("Wrong password");
                }
                trialCounter++;
                if (trialCounter == 3)
                {
                    System.out.println("Your account is suspended try again later!");
                }
            }
        }
        return new BasePasswordCheck(isLogin, account);
    }

    private record BasePasswordCheck(boolean isLogin, BankAccount account)
    {
    }

}
