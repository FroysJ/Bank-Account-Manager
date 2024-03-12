package ui;

import model.Account;
import model.AccountList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

// GUI framework code and comments taken from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

// Account manager GUI: provides the same functionality as account manager but in graphical form
public class AccountManagerGui extends JFrame {

    private static final String JSON_STORE = "./data/Accounts.json";
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
    private AccountList al;
    private JComboBox<String> printCombo;
    private JDesktopPane desktop;
    private JInternalFrame homeScreen;
    private JInternalFrame generalOptions;
    private JInternalFrame accOptions;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DefaultComboBoxModel<String> nameList;
    private boolean firstToggle;

    //EFFECTS: constructs the account manager application
    public AccountManagerGui() {
        al = new AccountList("My Accounts");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        desktop = new JDesktopPane();
        homeScreen = new JInternalFrame("Home", false, false, false, false);
        generalOptions = new JInternalFrame("General Options", false, false, false, false);
        accOptions = new JInternalFrame("Account Options", false, false, false, false);
        nameList = new DefaultComboBoxModel<>();

        setContentPane(desktop);
        setTitle("Account Manager");
        setSize(WIDTH, HEIGHT);

        addHomePanel();
        addGeneralPanel();
        addAccPanel();
        addMenu();

        homeScreen.pack();
        homeScreen.setVisible(true);
        desktop.add(homeScreen);

        setInvisibleFrame(generalOptions, homeScreen);
        setInvisibleFrame(accOptions, generalOptions);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    //MODIFIES: desktop
    //EFFECTS: adds new invisible frame to desktop
    private void setInvisibleFrame(JInternalFrame frame, JInternalFrame prevFrame) {
        frame.pack();
        frame.setVisible(false);
        frame.setLocation(prevFrame.getLocation().x + prevFrame.getWidth(),
                prevFrame.getLocation().y);
        desktop.add(frame);
    }

    /**
     * Helper to add control buttons for home panel.
     */
    //MODIFIES: homeScreen
    //EFFECTS: adds buttons to homeScreen
    private void addHomePanel() {
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(4,1));
        homePanel.add(new JButton(new LoadAction()));
        homePanel.add(new JButton(new SaveAction()));
        homePanel.add(new JButton(new CreateAccountAction()));
        homePanel.add(new JButton(new ExitAppAction()));
        homeScreen.add(homePanel, BorderLayout.WEST);
    }

    //MODIFIES: generalOptions
    //EFFECTS: adds buttons to generalOptions
    private void addGeneralPanel() {
        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new GridLayout(4,1));
        generalPanel.add(new JButton(new ViewAllDetailsAction()));
        generalPanel.add(new JButton(new ViewAllNamesAction()));
        generalPanel.add(new JButton(new ViewTotalBalAction()));
        generalPanel.add(new JLabel("Date: " + LocalDate.now().format(format), SwingConstants.CENTER));
        generalOptions.add(generalPanel, BorderLayout.WEST);
    }

    //MODIFIES: accOptions
    //EFFECTS: adds buttons to accOptions
    private void addAccPanel() {
        JPanel accPanel = new JPanel();
        accPanel.setLayout(new GridLayout(4,2));
        accPanel.add(new JLabel("Select Account →", SwingConstants.CENTER));
        accPanel.add(new JComboBox(nameList));
        accPanel.add(new JButton(new ViewDetailsAction()));
        accPanel.add(new JButton(new DepositAction()));
        accPanel.add(new JButton(new WithdrawAction()));
        accPanel.add(new JButton(new TransactionAction()));
        accPanel.add(new JButton(new RenewAction()));
        accPanel.add(new JButton(new DeleteAccountAction()));
        accOptions.add(accPanel, BorderLayout.WEST);
    }

    //MODIFIES: generalOptions, accOptions
    //EFFECTS: toggles visibility of frames based on whether al is empty or not
    private void toggleHomeScreenExtension() {
        if (al.isEmpty()) {
            generalOptions.setVisible(false);
            accOptions.setVisible(false);
        } else {
            generalOptions.setVisible(true);
            accOptions.setVisible(true);
        }
    }

    /**
     * Helper to create print options combo box
     * @return  the combo box
     */
    //EFFECTS: creates and returns combo box for account selection options
    private JComboBox<String> createPrintCombo() {
        printCombo = new JComboBox<String>();
        for (Account a : al.getAccountList()) {
            printCombo.addItem(a.getName());
        }
        return printCombo;
    }

    /**
     * Adds menu bar.
     */
    //EFFECTS: adds menu bar
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        setFileMenu(menuBar);
        setHomeMenu(menuBar);
        setAccountMenu(menuBar);

        setJMenuBar(menuBar);
    }

    //EFFECTS: adds file menu
    private void setFileMenu(JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        addMenuItem(fileMenu, new LoadAction(),
                KeyStroke.getKeyStroke("control L"));
        addMenuItem(fileMenu, new SaveAction(),
                KeyStroke.getKeyStroke("control S"));

        menuBar.add(fileMenu);
    }

    //EFFECTS: adds home menu
    private void setHomeMenu(JMenuBar menuBar) {
        JMenu homeMenu = new JMenu("Home");
        homeMenu.setMnemonic('H');
        addMenuItem(homeMenu, new CreateAccountAction(), null);
        addMenuItem(homeMenu, new ViewAllDetailsAction(), null);
        addMenuItem(homeMenu, new ViewAllNamesAction(), null);
        addMenuItem(homeMenu, new ViewTotalBalAction(), null);
        addMenuItem(homeMenu, new ExitAppAction(),
                KeyStroke.getKeyStroke("control W"));
        menuBar.add(homeMenu);
    }

    //EFFECTS: adds account menu
    private void setAccountMenu(JMenuBar menuBar) {
        JMenu accountMenu = new JMenu("Account");
        accountMenu.setMnemonic('A');
        addMenuItem(accountMenu, new ViewDetailsAction(), null);
        addMenuItem(accountMenu, new DepositAction(), null);
        addMenuItem(accountMenu, new WithdrawAction(), null);
        addMenuItem(accountMenu, new TransactionAction(), null);
        addMenuItem(accountMenu, new RenewAction(), null);
        addMenuItem(accountMenu, new DeleteAccountAction(), null);
        menuBar.add(accountMenu);
    }

    /**
     * Adds an item with given handler to the given menu
     * @param theMenu  menu to which new item is added
     * @param action   handler for new menu item
     * @param accelerator    keystroke accelerator for this menu item
     */
    //MODIFIES: theMenu
    //EFFECTS: adds item to menu
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    /**
     * Helper to centre main application window on desktop
     */
    //EFFECTS: centres main window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    /**
     * Represents action to be taken when user wants to load from file.
     */
    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load From File");
        }

        //EFFECTS: loads app state from file
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                al = jsonReader.read();
                for (Account a : al.getAccountList()) {
                    nameList.addElement(a.getName());
                }
                JOptionPane.showMessageDialog(null, "Loaded from file");
                toggleHomeScreenExtension();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file",
                        "Load failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save To File");
        }

        //EFFECTS: saves app state to file
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(al);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, "Saved to file");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to write to file",
                        "Save failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Represents action to be taken when user wants to create a new account.
     */
    private class CreateAccountAction extends AbstractAction {

        CreateAccountAction() {
            super("Create New Account");
        }

        //MODIFIES: account list (al)
        //EFFECTS: creates an account and adds it to the account list
        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = JOptionPane.showInputDialog(null,
                    "Please enter account name (3-12 characters):",
                    "Account Name",
                    JOptionPane.PLAIN_MESSAGE);
            if (name == null) {
                //do nothing
            } else if (name.length() >= 3 && name.length() <= 12 && !al.contains(name)) {
                Account account = new Account(name);
                al.addAccount(account);
                nameList.addElement(account.getName());
                JOptionPane.showMessageDialog(null,
                        "You have successfully created an account named \"" + name + "\".",
                        "Account Name",
                        JOptionPane.PLAIN_MESSAGE);
                toggleHomeScreenExtension();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid name entered. Name must be unique with 3-12 characters.",
                        "Invalid Name",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    //EFFECTS: helper for actions requiring an account to be selected:
    // returns account if an account is selected, null otherwise
    private Account selectAccountAction() {
        if (al.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "You do not have any existing accounts.",
                    "No Existing Accounts",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            String selected = (String) nameList.getSelectedItem();
            return al.getAccount(selected);
        }
    }

    /**
     * Represents action to be taken when user wants to delete an account.
     */
    private class DeleteAccountAction extends AbstractAction {

        DeleteAccountAction() {
            super("Delete Account");
        }

        //MODIFIES: account list (al)
        //EFFECTS: deletes an account and removes it from the account list
        @Override
        public void actionPerformed(ActionEvent evt) {
            Account a = selectAccountAction();
            if (a == null) {
                // do nothing
            } else {
                int confirmation = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete your account named \"" + a.getName() + "\"?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    al.removeAccount(a);
                    nameList.removeElement(a.getName());
                    JOptionPane.showMessageDialog(null, "Account Deleted");
                    toggleHomeScreenExtension();
                } else {
                    JOptionPane.showMessageDialog(null, "Deletion Cancelled");
                }
            }
        }
    }

    /**
     * Represents action to be taken when user wants to view the total balance across accounts.
     */
    private class ViewTotalBalAction extends AbstractAction {

        ViewTotalBalAction() {
            super("View Total Balance");
        }

        //EFFECTS: displays total account balance
        @Override
        public void actionPerformed(ActionEvent evt) {
            Account a = selectAccountAction();
            if (a == null) {
                // do nothing
            } else {
                double totalBal = 0;
                for (Account acc : al.getAccountList()) {
                    totalBal += acc.getBal();
                }
                JOptionPane.showMessageDialog(null,
                        "Your total balance across all accounts is: $" + totalBal);
            }
        }
    }

    /**
     * Represents action to be taken when user wants to view all account names.
     */
    private class ViewAllNamesAction extends AbstractAction {

        ViewAllNamesAction() {
            super("View Account Names");
        }

        //EFFECTS: displays all account names
        @Override
        public void actionPerformed(ActionEvent evt) {
            Account a = selectAccountAction();
            if (a == null) {
                // do nothing
            } else {
                TextPrinter tp = new TextPrinter(AccountManagerGui.this, "All Account Names:");
                desktop.add(tp);
                tp.print(al.getAccountList(), "names");
            }
        }
    }

    //MODIFIES: desktop
    //EFFECTS: helper for displaying details; creates a text frame and adds it to desktop
    private class TextPrinter extends JInternalFrame {

        private static final int WIDTH = 200;
        private static final int HEIGHT = 500;
        private JTextArea textArea;

        //EFFECTS: constructs a textPrinter
        public TextPrinter(Component parent, String title) {
            super(title, false, true, false, false);
            textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            add(scrollPane);
            setSize(WIDTH, HEIGHT);
            setPosition(parent);
            setVisible(true);
        }

        /**
         * Sets the position of window in which log will be printed relative to
         * parent
         * @param parent  the parent component
         */
        //MODIFIES: textArea
        //EFFECTS: sets position of textArea
        private void setPosition(Component parent) {
            setLocation(parent.getWidth() - getWidth() - 20,
                    parent.getHeight() - getHeight() - 20);
        }

        //EFFECTS: returns details to be printed
        public String viewDetails(Account a) {
            String status;
            if (a.isExpired()) {
                status = "Expired";
            } else {
                status = "Active";
            }
            return "Account name: " + a.getName() + "\n"
                + "Account balance: $" + a.getBal() + "\n"
                + "Date opened: " + a.getDateOpenedString() + "\n"
                + "Expiry Date: " + a.getDateOfExpiryString() + "\n"
                + "Status: " + status + "\n";
        }

        //EFFECTS: prints details as specified (names, all details, details)
        public void print(Object o, String code) {
            if (code.equals("names")) {
                for (Account a : (ArrayList<Account>) o) {
                    textArea.setText(textArea.getText() + a.getName() + "\n");
                }
                repaint();
            } else if (code.equals("all details")) {
                for (Account a : (ArrayList<Account>) o) {
                    textArea.setText(textArea.getText() + viewDetails(a) + "\n");
                }
                repaint();
            } else if (code.equals("details")) {
                Account a = (Account) o;
                textArea.setText(textArea.getText() + viewDetails(a));
            }
        }
    }

    /**
     * Represents action to be taken when user wants to view all account details.
     */
    private class ViewAllDetailsAction extends AbstractAction {

        ViewAllDetailsAction() {
            super("View All Account Details");
        }

        //EFFECTS: displays all account details
        @Override
        public void actionPerformed(ActionEvent evt) {
            Account a = selectAccountAction();
            if (a == null) {
                // do nothing
            } else {
                TextPrinter tp = new TextPrinter(AccountManagerGui.this, "All Account Details:");
                desktop.add(tp);
                tp.print(al.getAccountList(), "all details");
            }
        }
    }

    /**
     * Represents action to be taken when user wants to exit the app.
     */
    private class ExitAppAction extends AbstractAction {

        ExitAppAction() {
            super("Exit Account Manager");
        }

        //EFFECTS: exits app after confirmation
        @Override
        public void actionPerformed(ActionEvent evt) {
            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit account manager?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
    }

    //EFFECTS: determines if given amount is valid based on transaction type and account balance
    private boolean validAmount(String input, String code, double bal) {
        double amount = -1;
        try {
            amount = parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid amount entered. Amount must be between $5 and $500000.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (amount < 5 || amount > 500000 || (code.equals("withdrawal") && amount > bal)) {
            JOptionPane.showMessageDialog(null,
                    "Invalid amount entered. Amount must be between $5 and $500000.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Represents action to be taken when user wants to deposit to an account.
     */
    private class DepositAction extends AbstractAction {

        DepositAction() {
            super("Deposit");
        }

        //MODIFIES: account (a)
        //EFFECTS: makes a deposit to an account (a)
        @Override
        public void actionPerformed(ActionEvent evt) {
            Account a = selectAccountAction();
            if (a == null) {
                // do nothing
            } else {
                String input = JOptionPane.showInputDialog(null,
                        "Please enter amount between $5 and $500000 to deposit:",
                        "Deposit",
                        JOptionPane.PLAIN_MESSAGE);
                if (input == null || !validAmount(input, "deposit", a.getBal())) {
                    //do nothing
                } else {
                    Double amount = parseDouble(input);
                    a.deposit(amount);
                    JOptionPane.showMessageDialog(null,
                            "You have successfully deposited $"
                                    + amount + " to " + a.getName() + ".",
                            "Deposit Successful",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

    /**
     * Represents action to be taken when user wants to withdraw from an account.
     */
    private class WithdrawAction extends AbstractAction {

        WithdrawAction() {
            super("Withdraw");
        }

        //MODIFIES: account (a)
        //EFFECTS: makes a withdrawal from account (a)
        @Override
        public void actionPerformed(ActionEvent evt) {
            Account a = selectAccountAction();
            if (a == null) {
                // do nothing
            } else {
                String input = JOptionPane.showInputDialog(null,
                        "Please enter amount between $5 and $500000 to withdraw:",
                        "Withdrawal",
                        JOptionPane.PLAIN_MESSAGE);
                if (input == null || !validAmount(input, "withdrawal", a.getBal())) {
                    //do nothing
                } else {
                    Double amount = parseDouble(input);
                    a.withdraw(amount);
                    JOptionPane.showMessageDialog(null,
                            "You have successfully withdrawn $"
                                    + amount + " from " + a.getName() + ".",
                            "Withdrawal Successful",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

    /**
     * Represents action to be taken when user wants to make a transaction.
     */
    private class TransactionAction extends AbstractAction {

        TransactionAction() {
            super("Make Transaction");
        }

        //MODIFIES: account (a)
        //EFFECTS: makes a transaction with an account (a)
        @Override
        public void actionPerformed(ActionEvent evt) {
            Account a = selectAccountAction();
            if (a == null) {
                // do nothing
            } else {
                Object[] transactionTypes = {"Giving", "Receiving"};
                int type = JOptionPane.showOptionDialog(null,
                        "Please indicate whether " + a.getName() + " will be giving or receiving funds:",
                        "Transaction Type",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, transactionTypes, null);
                if (type == JOptionPane.YES_OPTION) {
                    WithdrawAction withdrawAction = new WithdrawAction();
                    withdrawAction.actionPerformed(null);
                } else if (type == JOptionPane.NO_OPTION) {
                    DepositAction depositAction = new DepositAction();
                    depositAction.actionPerformed(null);
                }
            }
        }
    }

    /**
     * Represents action to be taken when user wants to renew an account.
     */
    private class RenewAction extends AbstractAction {

        RenewAction() {
            super("Renew Account");
        }

        //MODIFIES: account (a)
        //EFFECTS: renews account (a)
        @Override
        public void actionPerformed(ActionEvent evt) {
            Account a = selectAccountAction();
            if (a == null) {
                // do nothing
            } else {
                a.renewAccount();
                JOptionPane.showMessageDialog(null,
                        "Account Renewed: will expire in 5 years.");
            }
        }
    }

    /**
     * Represents action to be taken when user wants to view account details.
     */
    private class ViewDetailsAction extends AbstractAction {

        ViewDetailsAction() {
            super("View Account Details");
        }

        //EFFECTS: displays account details
        @Override
        public void actionPerformed(ActionEvent evt) {
            Account a = selectAccountAction();
            if (a == null) {
                // do nothing
            } else {
                TextPrinter tp = new TextPrinter(AccountManagerGui.this,
                        "Account Details: " + a.getName());
                desktop.add(tp);
                tp.print(a, "details");
            }
        }
    }

    //EFFECTS: starts the application
    public static void main(String[] args) {
        new LoadingScreen();
        new AccountManagerGui();
    }
}
