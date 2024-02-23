package persistence;

import model.Account;
import model.AccountList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

// code and comments taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads session (account list) from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AccountList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccountList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses account list from JSON object and returns it
    private AccountList parseAccountList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        AccountList al = new AccountList(name);
        addAccounts(al, jsonObject);
        return al;
    }

    // MODIFIES: al
    // EFFECTS: parses accounts from JSON object and adds them to account list
    private void addAccounts(AccountList al, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(al, nextAccount);
        }
    }

    // MODIFIES: al
    // EFFECTS: parses account from JSON object and adds it to account list
    private void addAccount(AccountList al, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double bal = jsonObject.getDouble("balance");
        String dateOpened = jsonObject.getString("date opened");
        String dateOfExpiry = jsonObject.getString("expiry date");
        boolean expired = jsonObject.getBoolean("expired status");
        Account account = new Account(name);
        account.deposit(bal);
        account.setDateOpened(LocalDate.parse(dateOpened, format));
        account.setExpiryDate(LocalDate.parse(dateOfExpiry, format));
        al.addAccount(account);
    }
}
