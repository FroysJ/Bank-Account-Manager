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

}
