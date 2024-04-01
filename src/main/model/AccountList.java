package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// persistence code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// A list of bank accounts
public class AccountList implements Writable {

    private String name;
    private ArrayList<Account> accountList;

    //EFFECTS: constructs an empty account list with a name and logs app start event
    public AccountList(String name) {
        this.name = name;
        this.accountList = new ArrayList<Account>();
        EventLog.getInstance().logEvent(new Event("Account Manager application started"));
    }

    //REQUIRES: account is not already in the list
    //MODIFIES: accountList
    //EFFECTS: adds given account to the account list
    public void addAccount(Account account) {
        accountList.add(account);
    }

    //REQUIRES: account is in the list
    //MODIFIES: accountList
    //EFFECTS: removes the given account from the list and logs account removal
    //         (same as deletion in the context of the ui) event
    public void removeAccount(Account account) {
        accountList.remove(account);
        EventLog.getInstance().logEvent(new Event("Account deleted: " + account.getName()));
    }

    //EFFECTS: returns whether account list is empty
    public boolean isEmpty() {
        return accountList.isEmpty();
    }

    //EFFECTS: returns whether an account with the given name is in the list
    public boolean contains(String name) {
        for (Account a : accountList) {
            if (a.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns an account with the given name or null if fails
    public Account getAccount(String name) {
        for (Account a : accountList) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    //EFFECTS: returns this.accountList
    public ArrayList<Account> getAccountList() {
        return accountList;
    }

    //EFFECTS: returns name
    public String getName() {
        return name;
    }

    //EFFECTS: returns total bal and logs view total balance event
    public double getTotalBal() {
        double totalBal = 0;
        for (Account a : accountList) {
            totalBal += a.getBal();
        }
        EventLog.getInstance().logEvent(new Event("Total balance viewed: $" + totalBal));
        return totalBal;
    }

    //EFFECTS: differentiates between view details/view names actions in event log
    public void eventCode(String s) {
        if (s.equals("names")) {
            EventLog.getInstance().logEvent(new Event("Viewed all account names"));
        } else if (s.equals("all details")) {
            EventLog.getInstance().logEvent(new Event("Viewed all account details"));
        }
    }

    //EFFECTS: creates and returns a JSONObject for persistence
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("accounts", accountsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account a : accountList) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
