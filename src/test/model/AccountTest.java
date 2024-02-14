package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @Test
    void testRenewOpenedExpired() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
        assertEquals(LocalDate.now(), a5.getDateOpened());
        assertEquals(LocalDate.now().format(format), a5.getDateOpenedString());
        assertEquals(LocalDate.now().plusYears(5), a5.getDateOfExpiry());
        assertEquals(LocalDate.now().plusYears(5).format(format), a5.getDateOfExpiryString());
        assertFalse(a5.isExpired());
        a5.setExpiryDate(LocalDate.parse("Tue, Oct 11 2005", format));
        assertEquals(LocalDate.parse("Tue, Oct 11 2005", format), a5.getDateOfExpiry());
        assertEquals("Tue, Oct 11 2005", a5.getDateOfExpiryString());
        assertTrue(a5.isExpired());
        a5.renewAccount();
        assertEquals(LocalDate.now().plusYears(5), a5.getDateOfExpiry());
        assertEquals(LocalDate.now().plusYears(5).format(format), a5.getDateOfExpiryString());
        assertFalse(a5.isExpired());
    }

    @Test
    void testAccountDetails() {
        assertEquals("acc5", a5.getName());
        assertEquals("acc6", a6.getName());
        assertEquals(0, a5.getBal());
        assertEquals(0, a6.getBal());
        assertFalse(a5.isExpired());
        assertFalse(a6.isExpired());
    }
}