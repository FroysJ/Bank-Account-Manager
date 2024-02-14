package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountListTest {

    AccountList al;
    Account a1;
    Account a2;
    Account a3;
    Account a4;

    @BeforeEach
    void runBefore() {
        al = new AccountList();
        a1 = new Account("acc1");
        a2 = new Account("acc2");
        a3 = new Account("acc3");
        a4 = new Account("acc4");
    }

    @Test
    void testAddRemoveAccount() {
        assertTrue(al.isEmpty());
        assertEquals(0, al.getAccountList().size());
        al.addAccount(a1);
        assertEquals(1, al.getAccountList().size());
        assertFalse(al.isEmpty());
        al.addAccount(a2);
        assertEquals(2, al.getAccountList().size());
        al.addAccount(a3);
        assertEquals(3, al.getAccountList().size());
        assertEquals(a1, al.getAccountList().get(0));
        assertEquals(a2, al.getAccountList().get(1));
        assertEquals(a3, al.getAccountList().get(2));
        al.removeAccount(a1);
        assertEquals(2, al.getAccountList().size());
        assertEquals(a2, al.getAccountList().get(0));
        assertEquals(a3, al.getAccountList().get(1));
        al.removeAccount(a3);
        assertEquals(1, al.getAccountList().size());
        al.removeAccount(a2);
        assertTrue(al.isEmpty());
        assertEquals(0, al.getAccountList().size());
    }

    @Test
    void testContainsGetAccount() {
        assertTrue(al.isEmpty());
        assertFalse(al.contains("acc1"));
        assertNull(al.getAccount("acc5"));
        al.addAccount(a1);
        assertTrue(al.contains("acc1"));
        al.addAccount(a2);
        assertEquals(a2, al.getAccount("acc2"));
        al.addAccount(a3);
        assertFalse(al.contains("acc5"));
        al.addAccount(a4);
        assertEquals(a4, al.getAccount("acc4"));
        al.removeAccount(a4);
        al.removeAccount(a2);
        assertEquals(a1, al.getAccount("acc1"));
        assertFalse(al.contains("acc4"));
        assertNull(al.getAccount("acc2"));
        assertTrue(al.contains("acc3"));
        al.removeAccount(a3);
        al.removeAccount(a1);
        assertTrue(al.isEmpty());
    }
}