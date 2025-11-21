package Account;
import javax.naming.InvalidNameException;
import Utils.PasswordCheck;
import Utils.InputUtils;
import Bank.Bank;

public class AccountManager {

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
                case 1 -> createCheckingAcc();
                case 2 -> createSavingAcc();
                case 0 -> isOver = true;
                default ->
                    System.out.println("Invalid choice! Try again!");
            }
        } while (!isOver);
    }

    public static void createSavingAcc()
    {
        BaseAccountInfo info = getBaseAccountinfo();

        SavingAccount s1 = new SavingAccount(info.accHolder(), info.password(),0.05);
        Bank.getInstance().addAccount(s1);
        System.out.println("Account created.");
    }

    public static void createCheckingAcc()
    {
        BaseAccountInfo info = getBaseAccountinfo();

        double overdraftLimit = 200;
        CheckingAccount c1 = new CheckingAccount(info.accHolder(),overdraftLimit ,info.password());
        Bank.getInstance().addAccount(c1);
        System.out.println("Account created.");
    }

    public static void DeleteAccount()
    {
        int accNum = InputUtils.readInt("Enter the account number that you want to delete");
        Bank.getInstance().removeByNumber(accNum);
    }

    public static void makeDeposit()
    {
        int accNumber = InputUtils.readInt("Enter account number?");
        BankAccount account = Bank.getInstance().getAccByNumber(accNumber);
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

    public static void makeWithdraw()
    {
        int accNumber = InputUtils.readInt("Enter account number?");
        BankAccount account = Bank.getInstance().getAccByNumber(accNumber);
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

    public static void makeTransfer()
    {
        int accNumber = InputUtils.readInt("Enter account number?");
        BankAccount account = Bank.getInstance().getAccByNumber(accNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        boolean login = AccountManager.login(account);
        if (!login) return;

        int transferAccNum = InputUtils.readInt("Enter the account number that you want to transfer");
        BankAccount transferAcc = Bank.getInstance().getAccByNumber(transferAccNum);

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

    public static void updateAccount()
    {
        int accNumber = InputUtils.readInt("Enter account number that you want to update");
        BankAccount account = Bank.getInstance().getAccByNumber(accNumber);
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

    public static void displayTransactionHistoryByNumber()
    {
        int accNumber = InputUtils.readInt("Enter account number that you want to display transaction history");
        BankAccount account = Bank.getInstance().getAccByNumber(accNumber);

        if (account != null)
        {
            account.displayTransactionHistory();
        }
    }

    private static BaseAccountInfo getBaseAccountinfo()
    {
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

    public static boolean login(BankAccount account)
    {
        if (account == null) return false;

        String enteredPass;
        // keep asking until correct password is entered or account is suspended
        while (true) {
            long now = System.currentTimeMillis();

            // if there is a suspension value set, decide whether it's still active or expired
            if (account.getSuspendedUntilMillis() != 0) {
                if (account.getSuspendedUntilMillis() > now) {
                    long timeLeft = (account.getSuspendedUntilMillis() - now) / 1000;
                    System.out.printf("Account suspended. Try again in %s min %s second later. %n" , timeLeft / 60 , timeLeft % 60);
                    return false;
                } else {
                    // suspension expired -> clear
                    account.setSuspendedUntilMillis(0);
                    account.setSuspended(false);
                    account.setPassTrialCounter(0);
                }
            }

            enteredPass = InputUtils.readString("Enter password?[6 digit number]");

            // check if password format is valid
            if (PasswordCheck.isInvalidPassword(enteredPass)) {
                continue; // ask again
            }

            // correct password -> success (use enteredPass.equals to avoid NPE if stored password is null)
            boolean isPassMatched = enteredPass.equals(account.getPassword());
            if (isPassMatched)
            {
                account.setSuspendedUntilMillis(0);
                account.setSuspended(false);
                account.setPassTrialCounter(0);
                return true;
            }

            // wrong password
            System.out.println("Wrong password!");
            account.setPassTrialCounter(account.getPassTrialCounter() + 1);

            if (account.getPassTrialCounter() >= 3) {
                account.setSuspended(true);
                account.setSuspendedUntilMillis(now + (5 * 60 * 1000)); // 5 minutes
                System.out.println("Account suspended for 5 minutes due to repeated failed attempts.");
                return false;
            }
        }
    }

}
