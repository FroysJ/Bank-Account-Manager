package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// persistence code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// A bank account with a name, balance, date opened, expiry date, and status
public class Account implements Writable {

    private String name;
    private double bal;
    private LocalDate dateOpened;
    private LocalDate dateOfExpiry;
    private boolean expired;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

    //https://www.w3schools.com/java/java_date.asp

    //EFFECTS: constructs a new account with given name, current date as date opened,
    //         expiry date as 5 years from now, expired status as false, and 0 balance;
    //         logs account creation event
    public Account(String name) {
        this.name = name;
        this.dateOpened = LocalDate.now();
        this.dateOfExpiry = LocalDate.now().plusYears(5);
        this.expired = false;
        this.bal = 0;
        EventLog.getInstance().logEvent(new Event("Account created: " + this.name));
    }

    //MODIFIES: dateOfExpiry, expired
    //EFFECTS: changes expiry date to 5 years after today, changes expired status to false;
    //         logs account renewal event
    public void renewAccount() {
        setExpiryDate(LocalDate.now().plusYears(5));
        EventLog.getInstance().logEvent(new Event("Account renewed: " + this.name));
    }

    //REQUIRES: 5 <= amount <= 500000
    //MODIFIES: bal
    //EFFECTS: adds amount to balance;
    //         logs deposit event
    public void deposit(double amount) {
        bal += amount;
        EventLog.getInstance().logEvent(new Event("$" + amount + " deposited to: " + this.name));
    }

    //REQUIRES: 5 <= amount <= 500000 && amount <= bal
    //MODIFIES: bal
    //EFFECTS: subtracts amount from balance
    //         logs withdrawal event
    public void withdraw(double amount) {
        bal -= amount;
        EventLog.getInstance().logEvent(new Event("$" + amount + " withdrawn from: " + this.name));
    }

    //MODIFIES: dateOfExpiry, expired
    //EFFECTS: changes expiry date to given date, changes expired status to reflect new expiry date
    public void setExpiryDate(LocalDate expDate) {
        dateOfExpiry = expDate;
        if (expDate.isBefore(LocalDate.now())) {
            expired = true;
        } else {
            expired = false;
        }
    }

    //MODIFIES: dateOpened
    //EFFECTS: changes date opened to given date
    public void setDateOpened(LocalDate openDate) {
        dateOpened = openDate;
    }

    //EFFECTS: returns name
    public String getName() {
        return name;
    }

    //EFFECTS: returns bal
    public double getBal() {
        return bal;
    }

    //EFFECTS: returns dateOpened
    public LocalDate getDateOpened() {
        return dateOpened;
    }

    //EFFECTS: returns dateOfExpiry
    public LocalDate getDateOfExpiry() {
        return dateOfExpiry;
    }

    //EFFECTS: returns dateOpened as a string;
    public String getDateOpenedString() {
        return dateOpened.format(format);
    }

    //EFFECTS: returns dateOfExpiry as a string
    public String getDateOfExpiryString() {
        return dateOfExpiry.format(format);
    }

    //EFFECTS: returns expired status;
    public boolean isExpired() {
        return expired;
    }

    //EFFECTS: logs view details action
    public void eventCode(String s) {
        if (s.equals("details")) {
            EventLog.getInstance().logEvent(new Event("Viewed account details for: " + this.name));
        }
    }

    //EFFECTS: creates and returns new JSONObject for persistence
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("balance", bal);
        json.put("date opened", getDateOpenedString());
        json.put("expiry date", getDateOfExpiryString());
        json.put("expired status", expired);
        return json;
    }

}
