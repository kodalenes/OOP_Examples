package main;

import Account.AccountManager;
import Bank.Bank;
import Utils.InputUtils;
import Utils.Logger;
import Account.AdminManager;

import java.util.Scanner;

public class MainClass {

    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args)
    {
        MainMenu();
    }

    private static void MainMenu()
    {
        Logger.log("SYSTEM: Banking System started");
        Bank.getInstance().loadAccNumber();
        Bank.getInstance().loadFromJson();

        boolean isOver = false;
        do {
            System.out.println("---MENU---");
            System.out.println("1-Admin Menu");
            System.out.println("2-User Menu");
            System.out.println("0-Quit");
            int choice = InputUtils.readInt("Your choice");
            switch (choice){
                case 1 -> AdminManager.showAdminMenu();
                case 2 -> userMenu();
                case 0 -> {
                    isOver = true;
                    Bank.getInstance().saveToJson();
                    Bank.getInstance().saveAccNumber();
                    Logger.log("SYSTEM: Banking System shut down");
                }
            }
        }while(!isOver);
    }

    private static void userMenu()
    {
        boolean isOver = false;
        do {
            System.out.println("1- Deposit");
            System.out.println("2- Withdraw");
            System.out.println("3- Transfer");
            System.out.println("4- Display Transaction");
            System.out.println("5- Update Account Info");
            System.out.println("0- Quit");
            int choice = InputUtils.readInt("Your choice");

            switch (choice){
                case 1-> AccountManager.makeDeposit();
                case 2-> AccountManager.makeWithdraw();
                case 3-> AccountManager.makeTransfer();
                case 4-> AccountManager.displayTransactionHistoryByNumber();
                case 5-> AccountManager.updateAccount();
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
