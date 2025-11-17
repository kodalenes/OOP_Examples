import java.util.InputMismatchException;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
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
            System.out.println("7- Update Account Info");
            System.out.println("0- Quit");
            int choice = input.nextInt();

            switch (choice){
                case 1:
                    CreateAccount(bank, passwordCheck, input);
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
                case 7:
                    updateAccount(bank, input);
                    break;
                case 0:
                    isOver = true;
                    bank.saveToJson();
                    bank.saveAccNumber();
                    break;
            }
        }while(!isOver);
    }

    public static void CreateAccount(Bank bank, PasswordCheck passwordCheck, Scanner input)
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
                    createCheckingAcc(bank,passwordCheck, input);
                    break;
                case 2:
                    createSavingAcc(bank,passwordCheck, input);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Geçersiz seçim, lütfen tekrar deneyin.");
                    break;
            }
        }
    }

    private static void createSavingAcc(Bank bank,PasswordCheck passwordCheck, Scanner input)
    {
        BaseAccountInfo info = getBaseAccountinfo(passwordCheck, input);

        SavingAccount s1 = new SavingAccount(info.accHolder,0, info.password,0.05);
        bank.addAccount(s1);
        System.out.println("Account created.");
    }

    private static void createCheckingAcc(Bank bank, PasswordCheck passwordCheck, Scanner input)
    {
        BaseAccountInfo info = getBaseAccountinfo(passwordCheck, input);

        double overdraftLimit = 200;
        CheckingAccount c1 = new CheckingAccount(info.accHolder(),0,overdraftLimit ,info.password(),overdraftLimit);
        bank.addAccount(c1);
        System.out.println("Account created.");
    }

    private static void DeleteAccount(Bank bank, Scanner input)
    {
        input.nextLine();//Buffer temizligi
        System.out.println("Enter the account number that you want to delete");
        int accNum = input.nextInt();

        bank.removeByNumber(accNum);
    }

    private static void makeDeposit(Bank bank ,Scanner input)
    {
        BasePasswordCheck info = getBasePasswordCheck(bank, input);

        if (info.isLogin())
        {
            System.out.println("Enter the amount to deposit?");
            double amount = input.nextDouble();
            try {
                info.account().deposit(amount);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void makeWithdraw(Bank bank, Scanner input)
    {
        BasePasswordCheck info = getBasePasswordCheck(bank, input);

        if (info.isLogin())
        {
            try {
                System.out.println("Enter the amount to withdraw?");
                double amount = input.nextDouble();
                info.account().withdraw(amount);
            } catch (InputMismatchException e) {
                System.out.println("Enter valid number");
                input.nextLine();
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void makeTransfer(Bank bank, Scanner input)
    {
        BankAccount transferAcc = null;

        BasePasswordCheck info = getBasePasswordCheck(bank,input);

        if (info.account != null)
        {
            System.out.println("Enter the account number that you  want to transfer?");
            int transferAccNum = input.nextInt();
            transferAcc = bank.getAccByNumber(transferAccNum);
        }

        if (info.account != null && transferAcc != null )
        {
            System.out.println("Enter the amount to transfer?");
            double amount = input.nextDouble();
            try {
                info.account().transfer(transferAcc, amount);

            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void updateAccount(Bank bank, Scanner input)
    {
        System.out.println("Enter account number that you want to update?");
        int accNumber = input.nextInt();
        input.nextLine();
        BankAccount account = bank.getAccByNumber(accNumber);
        if (account != null)
        {
            System.out.println("Enter new account holder?");
            String newAccHolder = input.nextLine();

            account.setAccHolder(newAccHolder);
        }
    }

    private static BaseAccountInfo getBaseAccountinfo(PasswordCheck passwordCheck, Scanner input)
    {
        System.out.println("Enter Acc Holder Name");
        String accHolder = input.nextLine();
        String password;
        do {
            System.out.println("Enter password[6 digit-only numbers]");
            password = input.nextLine();
        } while (!passwordCheck.isValid(password));
        return new BaseAccountInfo(accHolder, password);
    }

    private record BaseAccountInfo(String accHolder, String password)
    {
    }

    private static BasePasswordCheck getBasePasswordCheck(Bank bank, Scanner input)
    {
        int trialCounter = 0;
        boolean isLogin = false;

        input.nextLine();//Buffer temizligi
        System.out.println("Enter your account number");
        int accNumber = input.nextInt();

        BankAccount account = bank.getAccByNumber(accNumber);

        if (account != null) {
            while (trialCounter < 3)
            {
                input.nextLine();
                System.out.println("Enter your 6 digit password");
                String enteredPass = input.nextLine();
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
