package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Account {

    private String name;
    private double bal;
    private TransactionList transactionList;
    private final String dateOpened;
    private String dateOfExpiry;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

    //https://www.w3schools.com/java/java_date.asp

    public Account(String name) {
        this.name = name;
        this.dateOpened = LocalDate.now().format(format);
        this.dateOfExpiry = LocalDate.now().plusYears(5).format(format);
        this.bal = 0;
        this.transactionList = new TransactionList();
    }

    public void renewAccount() {
        dateOfExpiry = LocalDate.now().plusYears(5).format(format);
    }

    public void deposit(double amount) {
        bal += amount;
    }

    public void withdraw(double amount) {
        bal -= amount;
    }

    public String getName() {
        return name;
    }

    public double getBal() {
        return bal;
    }

}
