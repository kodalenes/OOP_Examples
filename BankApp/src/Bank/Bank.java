package Bank;
import Exceptions.AccountNotFoundException;
import Utils.BankAccountAdapter;
import Utils.LocalDateAdapter;
import Utils.Logger;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

import Account.BankAccount;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Bank {

    private static final Bank instance = new Bank();

    private static final String ACCOUNT_COUNTER_FILE = "account_counter.json";
    private static final String BANK_ACCOUNT_FILE = "bank.json";
    private static final ArrayList<BankAccount> accounts = new ArrayList<>();

    private Bank(){}

    public  void addAccount(BankAccount account)
    {
        accounts.add(account);
    }

    public  BankAccount getAccByNumber(int accNumber) throws AccountNotFoundException
    {
        return accounts.stream()
                .filter(bankAccount -> bankAccount.getAccNumber() == accNumber)
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account not found!"));
    }

    public  void removeByNumber(int accNumber) throws AccountNotFoundException
    {
        BankAccount toRemove = null;

        toRemove = accounts.stream()
                 .filter(acc ->acc.getAccNumber() == accNumber)
                 .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account not found!"));


        accounts.remove(toRemove);
        System.out.println("Account deleted successfully");

        saveAccNumber();

    }

    public  void displayAll()
    {
        if (accounts.isEmpty())
        {
            System.out.println("No accounts");
            return;
        }
        accounts.forEach(BankAccount::displayAccountInfo);
    }

    public  void saveToJson()
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class , new LocalDateAdapter())
                .create();

        try (FileWriter writer = new FileWriter(BANK_ACCOUNT_FILE)) {
            gson.toJson(accounts, writer);
            System.out.println("Accounts saved to JSON!");
            Logger.log(String.format("SYSTEM: %d accounts saved to JSON file", accounts.size()));
        } catch (Exception e) {
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }

    public  void loadFromJson()
    {
        File file = new File(BANK_ACCOUNT_FILE);
        if (!file.exists())
        {
            System.out.println("No JSON file found - starting with empty bank.");
            return;
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BankAccount.class , new BankAccountAdapter())
                .registerTypeAdapter(LocalDate.class , new LocalDateAdapter())
                .create();

        Type listType = new TypeToken<ArrayList<BankAccount>>(){}.getType();

        try (Reader reader = new FileReader(file)) {
            ArrayList<BankAccount> loadedAccounts = gson.fromJson(reader , listType);

            if (loadedAccounts != null)
            {
                accounts.clear();
                // ensure each account has a non-null history
                for (BankAccount acc : loadedAccounts)
                {
                    acc.getHistory();
                }
                accounts.addAll(loadedAccounts);
                System.out.println("Accounts loaded from JSON!");
                Logger.log(String.format("SYSTEM: %d accounts loaded from JSON file", loadedAccounts.size()));

                int maxAccNumber = accounts.stream()
                        .mapToInt(BankAccount::getAccNumber)
                        .max()
                        .orElse(999);
                int currentCounter = BankAccount.getAccNumberMaker();

                if (maxAccNumber >= currentCounter)
                {
                    BankAccount.setAccNumberMaker(maxAccNumber + 1);
                    saveAccNumber();
                }
            } else {
                System.out.println("JSON file empty , starting fresh.");
            }
        } catch (Exception e) {
            System.out.println("Error loading JSON: " + e.getMessage());
        }
    }

    public  void saveAccNumber()
    {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(ACCOUNT_COUNTER_FILE)) {
            gson.toJson(BankAccount.getAccNumberMaker() ,writer);
        } catch (IOException e) {
            System.out.println("Error saving account number: " + e.getMessage());
        }
    }

    public  void loadAccNumber()
    {
        File file = new File(ACCOUNT_COUNTER_FILE);
        if (!file.exists()) {
            System.out.println("No previous account counter found, starting from 1000");
            return;
        }

        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader(file)) {
            Integer counter = gson.fromJson(fileReader , Integer.class);
            if (counter != null)
            {
                BankAccount.setAccNumberMaker(counter);
            }
        } catch (IOException e) {
            System.out.println("Error loading account counter: " + e.getMessage());
        }
    }

    public ArrayList<BankAccount> getAccounts()
    {
        return new ArrayList<>(accounts);
    }

    public static Bank getInstance() {
        return instance;
    }
}
