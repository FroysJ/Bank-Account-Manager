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

    //EFFECTS: constructs an empty account list with a name
    public AccountList(String name) {
        this.name = name;
        this.accountList = new ArrayList<Account>();
    }

    //REQUIRES: account is not already in the list
    //MODIFIES: accountList
    //EFFECTS: adds given account to the account list
    public void addAccount(Account account) {
        accountList.add(account);
    }

    //REQUIRES: account is in the list
    //MODIFIES: accountList
    //EFFECTS: removes the given account from the list
    public void removeAccount(Account account) {
        accountList.remove(account);
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
