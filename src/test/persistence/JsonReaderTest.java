package persistence;

import model.Account;
import model.AccountList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// code taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AccountList al = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccountList.json");
        try {
            AccountList al = reader.read();
            assertEquals("My Accounts", al.getName());
            assertEquals(0, al.getAccountList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccountList.json");
        try {
            AccountList al = reader.read();
            assertEquals("My Accounts", al.getName());
            List<Account> accountList = al.getAccountList();
            assertEquals(3, accountList.size());
            checkAccount("acc1", 100, LocalDate.parse("Thu, Feb 15 2024", format),
                    LocalDate.parse("Thu, Feb 15 2029", format), false, accountList.get(0));
            checkAccount("acc2", 200, LocalDate.parse("Thu, Jan 11 2024", format),
                    LocalDate.parse("Thu, Jan 11 2029", format), false, accountList.get(1));
            checkAccount("acc3", 300, LocalDate.parse("Fri, Feb 01 2019", format),
                    LocalDate.parse("Thu, Feb 01 2024", format), true, accountList.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}