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

    public void printAccountNameList() {
        System.out.println("My Accounts:\n");
        for (Account a : accountList) {
            System.out.println(a.getName());
        }
    }

    public void printAccountDetailsList() {
        System.out.println("My Accounts:");
        for (Account a : accountList) {
            System.out.print("\n");
            a.viewAccountDetails();
        }
    }

    public void printTotalBal() {
        double totalBal = 0;
        for (Account a : accountList) {
            totalBal += a.getBal();
        }
        System.out.println(totalBal);
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
