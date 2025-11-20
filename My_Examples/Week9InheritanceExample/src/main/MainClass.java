package main;

import Account.AccountManager;
import Bank.Bank;
import Utils.InputUtils;
import Utils.PasswordCheck;

import java.util.Scanner;

public class MainClass {

    public static final Scanner input = new Scanner(System.in);
    public static void main(String[] args)
    {
        MainMenu();
    }

    private static void MainMenu()
    {
        PasswordCheck passwordCheck = new PasswordCheck();
        Bank.getInstance().loadAccNumber();
        Bank.getInstance().loadFromJson();
        boolean isOver = false;
        do {
            System.out.println("1- Create Account");
            System.out.println("2- Delete Account");
            System.out.println("3- Deposit");
            System.out.println("4- Withdraw");
            System.out.println("5- Transfer");
            System.out.println("6- List Accounts");
            System.out.println("7- Display Transaction.Transaction History By Number");
            System.out.println("8- Update Account Info");
            System.out.println("0- Quit");
            int choice = InputUtils.readInt("Your choice");

            switch (choice){
                case 1:
                    AccountManager.CreateAccount(passwordCheck);
                    break;
                case 2:
                    AccountManager.DeleteAccount();
                    break;
                case 3:
                    AccountManager.makeDeposit();
                    break;
                case 4:
                    AccountManager.makeWithdraw();
                    break;
                case 5:
                    AccountManager.makeTransfer();
                    break;
                case 6:
                    Bank.getInstance().displayAll();
                    break;
                case 7:
                    AccountManager.displayTransactionHistoryByNumber();
                    break;
                case 8:
                    AccountManager.updateAccount();
                    break;
                case 0:
                    isOver = true;
                    Bank.getInstance().saveToJson();
                    Bank.getInstance().saveAccNumber();
                    break;
            }
        }while(!isOver);
    }
}
