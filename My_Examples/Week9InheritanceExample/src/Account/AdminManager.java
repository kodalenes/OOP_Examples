package Account;

import Bank.Bank;
import Utils.InputUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class AdminManager {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public static boolean adminLogin()
    {
        System.out.println("----ADMIN LOGIN----");
        String username = InputUtils.readName("Enter username?");
        String password = InputUtils.readString("Enter password?");

        if (!password.equals(ADMIN_PASSWORD) && !username.equals(ADMIN_USERNAME))
        {
            System.out.println("Invalid username or password !");
            return false;
        }

        System.out.println("Admin login successful");
        return true;
    }

    public static void showAdminMenu()
    {
        if (!adminLogin()) return;

        boolean isOver = false;
        do {
            System.out.println("------ADMIN MENU------");
            System.out.println("1-Create Account");
            System.out.println("2-Delete Account (Admin Authority)");
            System.out.println("3-List All Accounts");
            System.out.println("4-Reset Password");
            System.out.println("5-Bank Report");
            System.out.println("0-Logout");
            int choice = InputUtils.readInt("Your choice: ");

            switch (choice){
                case 1 -> AccountManager.createAccount();
                case 2 -> AccountManager.deleteAccount();
                case 3 -> Bank.getInstance().displayAll();
                case 4 -> AccountManager.resetPassword();
                case 5 -> showBankReport();
                case 0 -> {
                            isOver = true;
                            Bank.getInstance().saveToJson();
                            Bank.getInstance().saveAccNumber();
                }
                default -> System.out.println("Invalid choice.Try again!");
            }
        }while (!isOver);
    }

    public static void showBankReport()
    {
        ArrayList<BankAccount> accounts = Bank.getInstance().getAccounts();
        double totalBalance = accounts.stream()
                .mapToDouble(BankAccount::getBalance)
                .sum();

        int totalAccounts = accounts.size();

        Optional<BankAccount> maxBalancedAcc = accounts.stream()
                .max(Comparator.comparingDouble(BankAccount::getBalance));

        if (totalAccounts == 0)
        {
            System.out.println("No accounts");
            return;
        }
        BankAccount account = maxBalancedAcc.get();
        //Report
        System.out.println("---BANK REPORT----");
        System.out.println("Total accounts: " + totalAccounts);
        System.out.println("Total balance: " + totalBalance);
        System.out.println("Max balanced account: " + account);
    }
}
