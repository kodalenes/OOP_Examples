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
            System.out.println("7- Display Transaction");
            System.out.println("8- Update Account Info");
            System.out.println("0- Quit");
            int choice = InputUtils.readInt("Your choice");

            switch (choice){
                case 1-> AccountManager.CreateAccount(passwordCheck);
                case 2-> AccountManager.DeleteAccount();
                case 3-> AccountManager.makeDeposit();
                case 4-> AccountManager.makeWithdraw();
                case 5-> AccountManager.makeTransfer();
                case 6-> Bank.getInstance().displayAll();
                case 7-> AccountManager.displayTransactionHistoryByNumber();
                case 8-> AccountManager.updateAccount();
                case 0->
                    {
                        isOver = true;
                        Bank.getInstance().saveToJson();
                        Bank.getInstance().saveAccNumber();
                    }
            }
        }while(!isOver);
    }
}
