import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Bank {

    private final ArrayList<BankAccount> accounts = new ArrayList<>();

    public void addAccount(BankAccount account)
    {
        accounts.add(account);
    }

    public BankAccount getAccByNumber(int accNumber)
    {
        for (BankAccount b:accounts)
        {
            if (b.accNumber == accNumber)
            {
                return b;
            }
        }
        System.out.println("Account not found!");
        return null;
    }

    public void removeByNumber(int accNumber)
    {
        Iterator<BankAccount> it = accounts.iterator();

        while (it.hasNext())
        {
            if (it.next().getAccNumber() == accNumber)
            {
                it.remove();
                System.out.println("Account deleted:" + accNumber);
                return;
            }
        }
        System.out.println("Account not found!");
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

    public void saveToJson()
    {
        try {
            Gson gson = new GsonBuilder().
                    setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class , new LocalDateAdapter())
                    .create();

            FileWriter writer = new FileWriter("bank.json");

            gson.toJson(accounts,writer);
            writer.close();
            System.out.println("Accounts saved to JSON!");

        } catch (Exception e) {
            System.out.println("Error saving JSON: " + e.getMessage());;
        }
    }

    public void loadFromJson()
    {
        try{
            File file = new File("bank.json");
            if (!file.exists())
            {
                System.out.println("No JSON file found - starting with empty bank.");
                return;
            }

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class , new LocalDateAdapter())
                    .create();

            Reader reader = new FileReader(file);

            Type listType = new TypeToken<ArrayList<BankAccount>>(){}.getType();

            ArrayList<BankAccount> loadedAccounts = gson.fromJson(reader , listType);
            reader.close();

            if (loadedAccounts != null)
            {
                accounts.clear();
                for (BankAccount acc : loadedAccounts)
                {
                    if (acc.getHistory() == null){ acc.getHistory().clear();}
                }
                accounts.addAll(loadedAccounts);
                System.out.println("Accounts loaded from JSON!");
            }else {
                System.out.println("JSON file empty , starting fresh.");
            }
        } catch (Exception e) {
            System.out.println("Error loading JSON: " + e.getMessage());
        }
    }


    public ArrayList<BankAccount> getAccounts()
    {
        return new ArrayList<>(accounts);
    }


}

