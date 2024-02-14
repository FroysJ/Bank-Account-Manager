package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// A bank account with a name, balance, date opened, expiry date, and status
public class Account {

    private String name;
    private double bal;
    private final String dateOpened;
    private String dateOfExpiry;
    private boolean expired;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

    //https://www.w3schools.com/java/java_date.asp

    //EFFECTS: constructs a new account with given name, current date as date opened,
    //         expiry date as 5 years from now, expired status as false, and 0 balance
    public Account(String name) {
        this.name = name;
        this.dateOpened = LocalDate.now().format(format);
        this.dateOfExpiry = LocalDate.now().plusYears(5).format(format);
        this.expired = false;
        this.bal = 0;
    }

    //MODIFIES: dateOfExpiry, expired
    //EFFECTS: changes expiry date to 5 years after today, changes expired status to false
    public void renewAccount() {
        setExpiryDate(LocalDate.now().plusYears(5));
    }

    //REQUIRES: 5 <= amount <= 500000
    //MODIFIES: bal
    //EFFECTS: adds amount to balance
    public void deposit(double amount) {
        bal += amount;
    }

    //REQUIRES: 5 <= amount <= 500000 && amount <= bal
    //MODIFIES: bal
    //EFFECTS: subtracts amount from balance
    public void withdraw(double amount) {
        bal -= amount;
    }

    //MODIFIES: dateOfExpiry, expired
    //EFFECTS: changes expiry date to given date, changes expired status to reflect new expiry date
    public void setExpiryDate(LocalDate expDate) {
        dateOfExpiry = expDate.format(format);
        if (expDate.isBefore(LocalDate.now())) {
            expired = true;
        } else {
            expired = false;
        }
    }

    //EFFECTS: returns name
    public String getName() {
        return name;
    }

    //EFFECTS: returns bal
    public double getBal() {
        return bal;
    }

    //EFFECTS: returns dateOpened;
    public String getDateOpened() {
        return dateOpened;
    }

    //EFFECTS: returns dateOfExpiry
    public String getDateOfExpiry() {
        return dateOfExpiry;
    }

    //EFFECTS: returns expired status;
    public boolean isExpired() {
        return expired;
    }

}
