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

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            AccountList al = new AccountList("My Accounts");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccountList() {
        try {
            AccountList al = new AccountList("My Accounts");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccountList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccountList.json");
            al = reader.read();
            assertEquals("My Accounts", al.getName());
            assertEquals(0, al.getAccountList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccountList() {
        try {
            AccountList al = new AccountList("My Accounts");
            Account account4 = new Account("acc4");
            account4.deposit(400);
            account4.setDateOpened(LocalDate.parse("Thu, Feb 08 2024", format));
            account4.setExpiryDate(LocalDate.parse("Thu, Feb 08 2029", format));
            al.addAccount(account4);
            Account account5 = new Account("acc5");
            account5.deposit(500);
            account5.setDateOpened(LocalDate.parse("Fri, Feb 08 2019", format));
            account5.setExpiryDate(LocalDate.parse("Thu, Feb 08 2024", format));
            al.addAccount(account5);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccountList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccountList.json");
            al = reader.read();
            assertEquals("My Accounts", al.getName());
            List<Account> accountList = al.getAccountList();
            assertEquals(2, accountList.size());
            checkAccount("acc4", 400, LocalDate.parse("Thu, Feb 08 2024", format),
                    LocalDate.parse("Thu, Feb 08 2029", format), false, accountList.get(0));
            checkAccount("acc5", 500, LocalDate.parse("Fri, Feb 08 2019", format),
                    LocalDate.parse("Thu, Feb 08 2024", format), true, accountList.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}