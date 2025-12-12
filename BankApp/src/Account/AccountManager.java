package Account;
import javax.naming.InvalidNameException;

import Currency.Currency;
import Currency.CurrencyService;
import Exceptions.AccountNotFoundException;
import Notifications.SmsWebhookService;
import Utils.PasswordCheck;
import Utils.InputUtils;
import Bank.Bank;
import Utils.Logger;

public class AccountManager {

    private static final SmsWebhookService smsService = new SmsWebhookService();

    public static void createAccount()
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
        Logger.log(String.format("NEW ACCOUNT: Created new Saving account with ID: %d", s1.getAccNumber()));
    }

    public static void createCheckingAcc()
    {
        BaseAccountInfo info = getBaseAccountinfo();

        double overdraftLimit = 200;
        CheckingAccount c1 = new CheckingAccount(info.accHolder(),overdraftLimit ,info.password());
        Bank.getInstance().addAccount(c1);
        System.out.println("Account created.");
        Logger.log(String.format("NEW ACCOUNT: Created new Checking account with ID: %d", c1.getAccNumber()));
    }

    public static void deleteAccount()
    {
        int accNum = 0;
        try {
            accNum = InputUtils.readInt("Enter the account number that you want to delete");
            Bank.getInstance().removeByNumber(accNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Logger.log(String.format("DELETE ACCOUNT: Account %d has been removed from the system.", accNum));
    }

    public static void makeDeposit()
    {
        int accNumber = InputUtils.readInt("Enter account number?");
        BankAccount account = null;
        try {
            account = Bank.getInstance().getAccByNumber(accNumber);

            boolean login = AccountManager.login(account);

            if (login)
            {
                double amount = InputUtils.readDouble("Enter the amount to deposit");
                account.deposit(amount);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void makeWithdraw()
    {
        int accNumber = InputUtils.readInt("Enter account number?");
        BankAccount account = null;
        try {
            account = Bank.getInstance().getAccByNumber(accNumber);

            boolean login = AccountManager.login(account);

            if (login)
            {
                double amount = InputUtils.readDouble("Enter the amount to withdraw");
                account.withdraw(amount);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void makeTransfer()
    {
        try {
            int accNumber = InputUtils.readInt("Enter account number?");
            BankAccount account = null;

            account = Bank.getInstance().getAccByNumber(accNumber);
            boolean login = AccountManager.login(account);
            if (!login) return;

            int transferAccNum = InputUtils.readInt("Enter the account number that you want to transfer");
            BankAccount transferAcc = null;

            transferAcc = Bank.getInstance().getAccByNumber(transferAccNum);

            double amount = InputUtils.readDouble("Enter the amount to transfer");
            account.transfer(transferAcc, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateAccount()
    {
        try {
            int accNumber = InputUtils.readInt("Enter account number that you want to update");
            BankAccount account = null;

            account = Bank.getInstance().getAccByNumber(accNumber);

            account.displayTransactionHistory();
            String newAccHolder;
            do {
                newAccHolder = InputUtils.readName("Enter new account holder");
                try {
                    String oldName = account.getAccHolder();
                    account.setAccHolder(newAccHolder);
                    Logger.log(String.format("UPDATE: Account %d holder name changed from '%s' to '%s'", accNumber, oldName, newAccHolder));
                    break;
                } catch (InvalidNameException e) {
                    System.out.println(e.getMessage());
                }
            } while (true);

        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void displayTransactionHistoryByNumber()
    {
        try {
            int accNumber = InputUtils.readInt("Enter account number that you want to display transaction history");
            BankAccount account = null;

            account = Bank.getInstance().getAccByNumber(accNumber);
            account.displayTransactionHistory();
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
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

    public static void showBalanceInForeignCurrency()
    {
        int accNumber = InputUtils.readInt("Enter account number?");

        try {
            BankAccount account = Bank.getInstance().getAccByNumber(accNumber);

            if (!AccountManager.login(account)) return;

            String inputCode = InputUtils.readString("Enter currency code? [EUR , USD etc.]");

            Currency targetCurrency = Currency.fromString(inputCode);

            if (targetCurrency == null)
            {
                System.out.println("Invalid currency code!");
                return;
            }

            System.out.println("Fetching live exchange rates...");
            double rate = CurrencyService.getExchangeRate(targetCurrency);

            if (rate == 0.0)
                System.out.println("Failed to fetch rates or service unavailable.");

            double convertedBalance = rate * account.getBalance();
            System.out.println("--- CURRENCY BALANCE ---");
            System.out.printf("Current Balance (TRY): %.2f TL%n", account.getBalance());
            System.out.printf("Exchange Rate (TRY -> %s): %.4f%n", targetCurrency.getCode(), rate);
            System.out.printf("Equivalent Balance: %.2f %s%n", convertedBalance, targetCurrency.getCode());
            Logger.log(String.format("INFO: Account %d checked balance in %s", accNumber, targetCurrency.getCode()));

        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
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
                Logger.log(String.format("LOGIN: Successful login for Account %d (%s)", account.getAccNumber(), account.getAccHolder()));
                return true;
            }

            // wrong password
            System.out.println("Wrong password!");
            account.setPassTrialCounter(account.getPassTrialCounter() + 1);

            if (account.getPassTrialCounter() >= 3) {
                account.setSuspended(true);
                account.setSuspendedUntilMillis(now + (5 * 60 * 1000)); // 5 minutes
                System.out.println("Account suspended for 5 minutes due to repeated failed attempts.");
                Logger.log(String.format("SECURITY: Account %d suspended for 5 minutes due to 3 failed login attempts", account.getAccNumber()));
                String smsMessage = String.format("WARNING: Your account (%d) has been suspended for 5 minutes due to 3 failed login attempts.", account.getAccNumber());
                smsService.sendNotificationWithStatus(smsMessage, "+905551234567", "URGENT");
                return false;
            }
        }
    }

    public static void resetPassword() {
        try {
            int accNum = InputUtils.readInt("Enter account number?");
            BankAccount account = Bank.getInstance().getAccByNumber(accNum);
            boolean isOver = false;
            do {
                String newPass = InputUtils.readString("Enter new password");
                if (!PasswordCheck.isInvalidPassword(newPass))
                {
                    account.setPassword(newPass);
                    Logger.log(String.format("PASSWORD RESET: Password changed for Account %d", accNum));
                    isOver = true;
                }
            }while(!isOver);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
