package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    Account a5;
    Account a6;

    @BeforeEach
    void runBefore() {
        a5 = new Account("acc5");
        a6 = new Account("acc6");
    }

    @Test
    void testDepositWithdraw() {
        assertEquals(0, a5.getBal());
        assertEquals(0, a6.getBal());
        a5.deposit(500);
        assertEquals(500, a5.getBal());
        a6.deposit(1000);
        assertEquals(1000, a6.getBal());
        a5.withdraw(200);
        assertEquals(300, a5.getBal());
        a6.withdraw(1000);
        assertEquals(0, a6.getBal());
    }
}