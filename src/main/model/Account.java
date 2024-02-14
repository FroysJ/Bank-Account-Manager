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
        setExpiryDate(LocalDate.now().plusYears(5));
    }

    public void deposit(double amount) {
        bal += amount;
    }

    public void withdraw(double amount) {
        bal -= amount;
    }

    public void setExpiryDate(LocalDate expDate) {
        dateOfExpiry = expDate.format(format);
        if (expDate.isBefore(LocalDate.now())) {
            expired = true;
        } else {
            expired = false;
        }
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
