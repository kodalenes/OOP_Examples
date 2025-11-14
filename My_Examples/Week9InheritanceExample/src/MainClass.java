import java.util.Scanner;

public class MainClass {

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        Bank bank = new Bank();

        boolean isOver = false;
        do {
            System.out.println("1- Create Account");
            System.out.println("2- Delete Account");
            System.out.println("3- Deposit");
            System.out.println("4- Withdraw");
            System.out.println("5- Transfer");
            System.out.println("6- List Accounts");
            System.out.println("0- Quit");
            int choice = input.nextInt();

            switch (choice){
                case 1:
                    CreateAccount(bank, input);
                    break;
                case 2:
                    DeleteAccount(bank,input);
                    break;
                case 3:
                    makeDeposit(bank,input);
                    break;
                case 4:
                    makeWithdraw(bank,input);
                    break;
                case 5:
                    makeTransfer(bank,input);
                    break;
                case 6:
                    bank.displayAll();
                    break;
                case 0:
                    isOver = true;
                    break;
            }
        }while(!isOver);
    }

    public static void CreateAccount(Bank bank, Scanner input)
    {
        while (true) {
            System.out.println("Enter what type of account do you want to create.");
            System.out.println("1- Checking Account");
            System.out.println("2- Saving Account");
            System.out.println("0- Back");
            System.out.print("Your choice: ");
            int choice = input.nextInt();
            input.nextLine();//Buffer temizligi

            switch (choice)
            {
                case 1:
                    createCheckingAcc(bank, input);
                    break;
                case 2:
                    createSavingAcc(bank, input);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Geçersiz seçim, lütfen tekrar deneyin.");
                    break;
            }
        }
    }

    private static void createSavingAcc(Bank bank, Scanner input)
    {
        System.out.println("Enter Acc Number");
        String accNumber = input.nextLine();
        System.out.println("Enter Acc Holder Name");
        String accHolder = input.nextLine();
        SavingAccount s1 = new SavingAccount(accNumber,accHolder,0,0.05);
        bank.addAccount(s1);
        System.out.println("Account created.");
    }

    private static void createCheckingAcc(Bank bank, Scanner input)
    {
        System.out.println("Enter Acc Number");
        String accNumber = input.nextLine();
        System.out.println("Enter Acc Holder Name");
        String accHolder = input.nextLine();
        double overdraftLimit = 200;
        CheckingAccount c1 = new CheckingAccount(accNumber,accHolder,0,overdraftLimit);
        bank.addAccount(c1);
        System.out.println("Account created.");
    }

    private static void DeleteAccount(Bank bank, Scanner input)
    {
        input.nextLine();//Buffer temizligi
        System.out.println("Enter the account number that you want to delete");
        String accNum = input.nextLine();

        bank.removeByNumber(accNum);
    }

    private static void makeDeposit(Bank bank ,Scanner input)
    {
        input.nextLine();//Buffer temizligi
        System.out.println("Enter your account number");
        String accNumber = input.nextLine();

        BankAccount account = bank.getAccByNumber(accNumber);

        if (account != null) {
            System.out.println("Enter the amount to deposit?");
            double amount = input.nextDouble();
            account.deposit(amount);
        }
    }

    private static void makeWithdraw(Bank bank, Scanner input)
    {
        input.nextLine();//Buffer temizligi
        System.out.println("Enter your account number");
        String accNumber = input.nextLine();

        BankAccount account = bank.getAccByNumber(accNumber);

        if (account != null)
        {
            System.out.println("Enter the amount to withdraw?");
            double amount = input.nextDouble();
            account.withdraw(amount);
        }
    }

    private static void makeTransfer(Bank bank, Scanner input)
    {
        BankAccount transferAcc = null;
        BankAccount account = null;

        input.nextLine();//Buffer
        System.out.println("Enter your account number?");
        String accNumber = input.nextLine();

        account = bank.getAccByNumber(accNumber);

        if (account != null)
        {
            System.out.println("Enter the account number that you  want to transfer?");
            String transferAccNum = input.nextLine();
            transferAcc = bank.getAccByNumber(transferAccNum);
        }

        if (account != null && transferAcc != null)
        {
            System.out.println("Enter the amount to transfer?");
            double amount = input.nextDouble();
            account.transfer(transferAcc, amount);
        }
    }
}
