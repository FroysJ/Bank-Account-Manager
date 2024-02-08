package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String date;
    private String transactionType;
    private String payor;
    private String payee;
    private double amount;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

    public Transaction(Account account, String transactionType, String otherParty, double amount) {
        this.date = LocalDate.now().format(format);
        this.amount = amount;
        this.transactionType = transactionType;
        if (transactionType == "Deposit") {
            this.payor = otherParty;
            this.payee = account.getName();
            account.deposit(amount);
        } else {
            this.payor = account.getName();
            this.payee = otherParty;
            account.withdraw(amount);
        }
    }

}
