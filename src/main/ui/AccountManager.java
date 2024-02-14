package ui;

import model.Account;
import model.AccountList;

import java.util.Scanner;

public class AccountManager {

    private AccountList accountList = new AccountList();
    private Scanner userInput;

    public AccountManager() {
        startApp();
    }

    private void startApp() {
        boolean continueProcess = true;
        String userAction = null;
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
        System.out.println("Welcome to The Trustworthy Bank. What would you like to do?");

        while (continueProcess) {
            homeScreen();
            userAction = userInput.next();
            userAction = userAction.toLowerCase();
            if (userAction.equals("e")) {
                continueProcess = false;
            } else {
                doHomeScreenAction(userAction);
            }
        }

        System.out.println("Thank you for using The Trustworthy Bank. Have a nice day!");
    }

    private void homeScreen() {
        System.out.println("\nType 'c' to create a new account:");
        if (!accountList.isEmpty()) {
            System.out.println("Type 'b' to view your total balance across all accounts:");
            System.out.println("Type 's' to select an existing account:");
            System.out.println("Type 'n' to view the list of your accounts' names:");
            System.out.println("Type 'v' to view all your accounts' details:");
        }
        System.out.println("Type 'e' to exit:");
    }

    private void doHomeScreenAction(String userAction) {
        if (userAction.equals("c")) {
            createAccount();
        } else if (userAction.equals("b")) {
            viewTotalBal();
        } else if (userAction.equals("s")) {
            selectAccount();
        } else if (userAction.equals("n")) {
            printAccountDetails(true);
        } else if (userAction.equals("v")) {
            printAccountDetails(false);
        } else {
            System.out.println("Invalid action.");
        }
    }

    private void createAccount() {
        boolean invalidInput = true;
        System.out.println("You have chosen to create an account.");
        System.out.println("Please enter an account name. Name must be unique and between 3 to 12 characters:");
        while (invalidInput) {
            String name = userInput.next();
            if (name.length() >= 3 && name.length() <= 12 && !accountList.contains(name)) {
                Account account = new Account(name);
                accountList.addAccount(account);
                System.out.println("You have successfully created an account named \"" + name + ".\"");
                invalidInput = false;
            } else {
                System.out.println("Invalid name entered. Please enter a unique name with 3 to 12 characters:");
            }
        }
    }

    private void viewTotalBal() {
        if (!accountList.isEmpty()) {
            System.out.print("Your total balance across all accounts is: $");
            accountList.printTotalBal();
        } else {
            System.out.println("You do not have any existing accounts.");
        }
    }

    private void selectAccount() {
        boolean invalidInput = true;
        if (!accountList.isEmpty()) {
            System.out.println("You have chosen to select an account.");
            System.out.println("Please enter the name of the account you wish to select:");
            while (invalidInput) {
                String selection = userInput.next();
                if (accountList.contains(selection)) {
                    invalidInput = false;
                    System.out.println("You have successfully selected your account named \"" + selection + ".\"");
                    accountOptions(accountList.getAccount(selection));
                } else {
                    System.out.println("Invalid account. Please enter the name of the account you wish to select:");
                }
            }
        } else {
            System.out.println("You do not have any existing accounts.");
        }
    }

    private void printAccountDetails(boolean onlyPrintNames) {
        if (accountList.isEmpty()) {
            System.out.println("You do not have any existing accounts.");
        } else {
            if (onlyPrintNames) {
                accountList.printAccountNameList();
            } else {
                accountList.printAccountDetailsList();
            }
        }
    }

    private void accountOptions(Account account) {
        boolean continueProcess = true;
        String userAction = null;
        while (continueProcess) {
            accountOptionsScreen(account);
            userAction = userInput.next();
            userAction = userAction.toLowerCase();
            if (userAction.equals("e")) {
                continueProcess = false;
            } else {
                doAccountAction(account, userAction);
                if (!accountList.contains(account.getName())) {
                    continueProcess = false;
                }
            }
        }
    }

    private void accountOptionsScreen(Account account) {
        String name = account.getName();
        if (!account.isExpired()) {
            System.out.println("\nType 'd' to make a deposit to " + name + ":");
            System.out.println("Type 'w' to make a withdrawal from " + name + ":");
            System.out.println("Type 't' to make a transaction with " + name + ":");
        }
        System.out.println("Type 'r' to renew " + name + ":");
        System.out.println("Type 'v' to view your account details:");
        System.out.println("Type 'x' to delete " + name + ":");
        System.out.println("Type 'e' to exit to Home Screen:");
    }

    private void doAccountAction(Account account, String userAction) {
        if (userAction.equals("r")) {
            renewAccount(account);
        } else if (userAction.equals("v")) {
            viewDetails(account);
        } else if (userAction.equals("x")) {
            deleteAccount(account);
        } else if (!account.isExpired()) {
            if (userAction.equals("d")) {
                System.out.println("You have chosen to make a deposit to " + account.getName() + ".");
                System.out.println("Please enter an amount to deposit. Amount must be between $5.00 and $500,000.00:");
                makeDeposit(account, false);
            } else if (userAction.equals("w")) {
                System.out.println("You have chosen to make a withdrawal from " + account.getName() + ".");
                System.out.println("Please enter an amount to withdraw. Amount must be between $5.00 and $500,000.00:");
                makeWithdrawal(account, false);
            } else if (userAction.equals("t")) {
                System.out.println("You have chosen to make a transaction with " + account.getName() + ".");
                System.out.println("Please indicate whether this account will be receiving (r) or giving (g) funds:");
                makeTransaction(account);
            }
        } else {
            System.out.println("Invalid action. If your account has expired, please renew it to unlock more actions.");
        }
    }

    private void makeDeposit(Account account, boolean transaction) {
        boolean invalidInput = true;
        while (invalidInput) {
            double amount = userInput.nextDouble();
            if (amount >= 5 && amount <= 500000) {
                if (transaction) {
                    transactionDetails("r", amount);
                } else {
                    System.out.println("You have successfully deposited $"
                            + amount + " to " + account.getName() + ".");
                }
                account.deposit(amount);
                System.out.println("This account's balance is now $" + account.getBal() + ".");
                invalidInput = false;
            } else {
                System.out.println("Invalid amount entered. Amount must be between $5.00 and $500,000.00:");
            }
        }
    }

    private void makeWithdrawal(Account account, boolean transaction) {
        if (account.getBal() < 5) {
            System.out.println("Accounts must have $5.00 or more to participate in withdrawals.");
        } else {
            boolean invalidInput = true;
            while (invalidInput) {
                double amount = userInput.nextDouble();
                if (amount > account.getBal()) {
                    System.out.println("Insufficient funds. Please enter a valid amount:");
                } else if (amount >= 5 && amount <= 500000) {
                    if (transaction) {
                        transactionDetails("g", amount);
                    } else {
                        System.out.println("You have successfully withdrawn $"
                                + amount + " from " + account.getName() + ".");
                    }
                    account.withdraw(amount);
                    System.out.println("This account's balance is now $" + account.getBal() + ".");
                    invalidInput = false;
                } else {
                    System.out.println("Invalid amount entered. Amount must be between $5.00 and $500,000.00:");
                }
            }
        }
    }

    private void makeTransaction(Account account) {
        boolean invalidInput = true;
        while (invalidInput) {
            String transactionType = userInput.next();
            transactionType = transactionType.toLowerCase();
            if (transactionType.equals("r")) {
                System.out.println("Please indicate how much this account will be receiving.");
                System.out.println("Amount must be between $5.00 and $500,000.00:");
                makeDeposit(account, true);
                invalidInput = false;
            } else if (transactionType.equals("g")) {
                System.out.println("Please indicate how much this account will be giving.");
                System.out.println("Amount must be between $5.00 and $500,000.00:");
                makeWithdrawal(account, true);
                invalidInput = false;
            } else {
                System.out.println("Invalid transaction type.");
                System.out.println("Please indicate whether this account will be receiving (r) or giving (g) funds:");
            }
        }
    }

    private void transactionDetails(String type, double amount) {
        String otherParty;
        if (type.equals("r")) {
            System.out.println("Please type the name of the payor:");
            otherParty = userInput.next();
            System.out.println("You have successfully received $" + amount + " from " + otherParty + ".");
        } else if (type.equals("g")) {
            System.out.println("Please type the name of the payee:");
            otherParty = userInput.next();
            System.out.println("You have successfully paid $" + amount + " to " + otherParty + ".");
        }
    }

    private void renewAccount(Account account) {
        account.renewAccount();
        System.out.println("You have successfully renewed your account.");
        System.out.println("Your account will expire 5 years from now on " + account.getDateOfExpiry() + ".");
    }

    private void viewDetails(Account account) {
        account.viewAccountDetails();
    }

    private void deleteAccount(Account account) {
        System.out.println("Are you sure you want to delete your account?");
        System.out.println("Type 'yes' to confirm, enter anything else to cancel:");
        String confirmation = userInput.next();
        confirmation = confirmation.toLowerCase();
        if (confirmation.equals("yes")) {
            System.out.println("You have successfully deleted your account named " + account.getName() + ".");
            accountList.removeAccount(account);
        } else {
            System.out.println("Deletion cancelled. Your account remains accessible.");
        }
    }
}