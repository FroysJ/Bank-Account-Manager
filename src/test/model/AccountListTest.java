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
        assertTrue(al.getAccountList().isEmpty());
        assertEquals(0, al.getAccountList().size());
        al.addAccount(a1);
        assertEquals(1, al.getAccountList().size());
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
        assertEquals(0, al.getAccountList().size());
    }
}