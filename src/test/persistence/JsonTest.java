package persistence;

import model.Account;
import model.AccountList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

// code taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonTest {

    protected final DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
    protected void checkAccount(String name, double bal, LocalDate dateOpened,
                                LocalDate dateOfExpiry, boolean expired, Account account) {
        assertEquals(name, account.getName());
        assertEquals(bal, account.getBal());
        assertEquals(dateOpened, account.getDateOpened());
        assertEquals(dateOfExpiry, account.getDateOfExpiry());
        assertEquals(expired, account.isExpired());
    }
}
