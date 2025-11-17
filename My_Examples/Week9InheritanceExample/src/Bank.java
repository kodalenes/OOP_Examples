import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Bank {

    private static final String ACCOUNT_COUNTER_FILE = "account_counter.json";
    private static final String BANK_ACCOUNT_FILE = "bank.json";
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
        BankAccount toRemove = null;

        //Find the toremove accnum
        for (BankAccount account : accounts)
        {
            if (account.getAccNumber() == accNumber)
            {
                toRemove = account;
                break;
            }
        }

        if (toRemove != null)
        {
            accounts.remove(toRemove);
            System.out.println("Account deleted successfully");

            if (accounts.isEmpty())
            {
                BankAccount.setAccNumberMaker(1000);
            }else
            {
                int maxAccNumber = accounts.stream()
                        .mapToInt(BankAccount::getAccNumber)
                        .max()
                        .orElse(999);
                BankAccount.setAccNumberMaker(maxAccNumber + 1);
            }
            saveAccNumber();
        }else
        {
            System.out.println("Account not found");
        }
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

            FileWriter writer = new FileWriter(BANK_ACCOUNT_FILE);

            gson.toJson(accounts,writer);
            writer.close();
            System.out.println("Accounts saved to JSON!");

        } catch (Exception e) {
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }

    public void loadFromJson()
    {
        try{
            File file = new File(BANK_ACCOUNT_FILE);
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

    public void saveAccNumber()
    {
        try
        {
            FileWriter writer = new FileWriter(ACCOUNT_COUNTER_FILE);
            Gson gson = new Gson();
            gson.toJson(BankAccount.getAccNumberMaker() ,writer);
            writer.flush();
        }catch (IOException e)
        {
            System.out.println("Error saving account number: " + e.getMessage());
        }
    }

    public void loadAccNumber()
    {
        try{
            FileReader fileReader = new FileReader(ACCOUNT_COUNTER_FILE);
            Gson gson = new Gson();
            Integer counter = gson.fromJson(fileReader , Integer.class);
            if (counter != null)
            {
                BankAccount.setAccNumberMaker(counter);
            }
        }catch (IOException e)
        {
            System.out.println("No previous account counter found, starting from 1000");
        }
    }

    public ArrayList<BankAccount> getAccounts()
    {
        return new ArrayList<>(accounts);
    }


}

