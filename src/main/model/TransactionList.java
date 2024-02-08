package model;

import java.util.ArrayList;

public class TransactionList {

    private ArrayList<Transaction> transactionList;

    public TransactionList() {
        this.transactionList = new ArrayList<Transaction>();
    }

    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transactionList.remove(transaction);
    }

}
