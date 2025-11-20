package Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private TransactionType transactionType;
    private String transactionDate;
    private double balanceAfterTransaction;
    private double amount;
    private String note;

    public Transaction(TransactionType transactionType,double amount ,double balanceAfterTransaction,String note)
    {
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.note = note;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.transactionDate = now.format(formatter);
    }

    @Override
    public String toString() {
        return String.format("Type:%s ,Date:%s ,Transaction.Transaction Amount: %.2f ,Balance After:%.2f ,Note:%s",
                transactionType , transactionDate,amount,balanceAfterTransaction ,note);
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }
}
