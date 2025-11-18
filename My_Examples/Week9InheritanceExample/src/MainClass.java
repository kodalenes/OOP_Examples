import javax.naming.InvalidNameException;
import java.util.Scanner;

public class MainClass {

    public static final Scanner input = new Scanner(System.in);
    public static void main(String[] args)
    {
        PasswordCheck passwordCheck = new PasswordCheck();
        Bank.loadAccNumber();
        Bank.loadFromJson();
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
                    CreateAccount(passwordCheck);
                    break;
                case 2:
                    DeleteAccount();
                    break;
                case 3:
                    makeDeposit();
                    break;
                case 4:
                    makeWithdraw();
                    break;
                case 5:
                    makeTransfer();
                    break;
                case 6:
                    Bank.displayAll();
                    break;
                case 7:
                    displayTransactionHistoryByNumber();
                    break;
                case 8:
                    updateAccount();
                    break;
                case 0:
                    isOver = true;
                    Bank.saveToJson();
                    Bank.saveAccNumber();
                    break;
            }
        }while(!isOver);
    }

    public static void CreateAccount(PasswordCheck passwordCheck)
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
                    createCheckingAcc(passwordCheck);
                    break;
                case 2:
                    createSavingAcc(passwordCheck);
                    break;
                case 0:
                    isOver = true;
                    break;
                default:
                    System.out.println("Invalid choice! Try again!");
                    break;
                }
            } while (!isOver);

    }

    private static void createSavingAcc(PasswordCheck passwordCheck)
    {
        BaseAccountInfo info = getBaseAccountinfo(passwordCheck);

        SavingAccount s1 = new SavingAccount(info.accHolder,0, info.password,0.05);
        Bank.addAccount(s1);
        System.out.println("Account created.");
    }

    private static void createCheckingAcc(PasswordCheck passwordCheck)
    {
        BaseAccountInfo info = getBaseAccountinfo(passwordCheck);

        double overdraftLimit = 200;
        CheckingAccount c1 = new CheckingAccount(info.accHolder(),0,overdraftLimit ,info.password(),overdraftLimit);
        Bank.addAccount(c1);
        System.out.println("Account created.");
    }

    private static void DeleteAccount()
    {
        int accNum = InputUtils.readInt("Enter the account number that you want to delete");
        Bank.removeByNumber(accNum);
    }

    private static void makeDeposit()
    {
        int accNumber = InputUtils.readInt("Enter account number?");
        BankAccount account = Bank.getAccByNumber(accNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        boolean login = AccountManager.login(account);

        if (login)
        {
            double amount = InputUtils.readDouble("Enter the amount to deposit");
            try {
                account.deposit(amount);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void makeWithdraw()
    {
        int accNumber = InputUtils.readInt("Enter account number?");
        BankAccount account = Bank.getAccByNumber(accNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        boolean login = AccountManager.login(account);

        if (login)
        {
            try {
                double amount = InputUtils.readDouble("Enter the amount to withdraw");
                account.withdraw(amount);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void makeTransfer()
    {
        BankAccount transferAcc = null;
        int accNumber = InputUtils.readInt("Enter account number?");
        BankAccount account = Bank.getAccByNumber(accNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        boolean login = AccountManager.login(account);
        if (!login) return;

        int transferAccNum = InputUtils.readInt("Enter the account number that you want to transfer");
        transferAcc = Bank.getAccByNumber(transferAccNum);

        if (transferAcc != null)
        {
            double amount = InputUtils.readDouble("Enter the amount to transfer");
            try {
                account.transfer(transferAcc, amount);

            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void updateAccount()
    {
        int accNumber = InputUtils.readInt("Enter account number that you want to update");
        BankAccount account = Bank.getAccByNumber(accNumber);
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

    private static void displayTransactionHistoryByNumber()
    {
        int accNumber = InputUtils.readInt("Enter account number that you want to display transaction history");
        BankAccount account = Bank.getAccByNumber(accNumber);

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
        } while (PasswordCheck.isInvalidPassword(password));
        return new BaseAccountInfo(accHolder, password);
    }

    private record BaseAccountInfo(String accHolder, String password)
    {
    }
}
