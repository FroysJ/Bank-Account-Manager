package model;

import java.util.ArrayList;

public class AccountList {

    private ArrayList<Account> accountList;

    public AccountList() {
        this.accountList = new ArrayList<Account>();
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }

    public void removeAccount(Account account) {
        accountList.remove(account);
    }

    public boolean isEmpty() {
        return accountList.isEmpty();
    }

    public boolean contains(String name) {
        for (Account a : accountList) {
            if (a.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Account getAccount(String name) {
        for (Account a : accountList) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }
}
