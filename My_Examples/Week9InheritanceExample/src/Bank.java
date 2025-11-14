import java.util.ArrayList;
import java.util.Iterator;

public class Bank {

    private final ArrayList<BankAccount> accounts = new ArrayList<>();

    public void addAccount(BankAccount account)
    {
        accounts.add(account);
    }

    public void removeByNumber(String accNumber)
    {
        Iterator<BankAccount> it = accounts.iterator();

        while (it.hasNext())
        {
            if (it.next().getAccNumber().equals(accNumber))
            {
                it.remove();
                System.out.println("Account deleted:" + accNumber);
                return;
            }
        }
        System.out.println("Account not found!");
    }

    public BankAccount findByNumber(String accNumber)
    {
        for (BankAccount a : accounts)
        {
            if (a.accNumber.equals(accNumber))
            {
                return a;
            }
        }
        System.out.println("Account not found");
        return null;
    }

    public ArrayList<BankAccount> getAccounts()
    {
        return new ArrayList<>(accounts);
    }

    public BankAccount getAccByNumber(String accNumber)
    {
        for (BankAccount b:accounts)
        {
            if (b.accNumber.equals(accNumber))
            {
                return b;
            }
        }
        System.out.println("Account not found!");
        return null;
    }

    public void displayAll()
    {
        if (accounts.isEmpty())
        {
            System.out.println("No accounts");
            return;
        }
        for (BankAccount a: accounts) a.displayAccountInfo();
    }
}

