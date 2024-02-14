package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Account {

    private String name;
    private double bal;
    private final String dateOpened;
    private String dateOfExpiry;
    private boolean expired;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

    //https://www.w3schools.com/java/java_date.asp

    public Account(String name) {
        this.name = name;
        this.dateOpened = LocalDate.now().format(format);
        this.dateOfExpiry = LocalDate.now().plusYears(5).format(format);
        this.expired = false;
        this.bal = 0;
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

    public void viewAccountDetails() {
        System.out.println("Account name: " + name);
        System.out.println("Account balance: " + bal);
        System.out.println("Date opened: " + dateOpened);
        System.out.println("Date of expiry: " + dateOfExpiry);
        String status;
        if (isExpired()) {
            status = "Expired";
        } else {
            status = "Active";
        }
        System.out.println("Status: " + status);
    }

    public String getName() {
        return name;
    }

    public double getBal() {
        return bal;
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public String getDateOfExpiry() {
        return dateOfExpiry;
    }

    public boolean isExpired() {
        return expired;
    }

}
