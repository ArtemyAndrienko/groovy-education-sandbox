import im.educ.groovy.model.BankAccount
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertEquals
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut


class BankAccountTest {
    @Test
    @DisplayName("Simple constructor check that works")
    void testBankAccountConstructor() {
        def test = new BankAccount("12345678", "Alex")
        def expected = new BankAccount("12345678", "Alex", 0, "0000")
        assertEquals(test.getPin(), expected.getPin())
        assertEquals(test.getAccountNumber(), expected.getAccountNumber())
        assertEquals(test.getCustomerName(), expected.getCustomerName())
        assertEquals(test.getBalance(), expected.getBalance())
    }

    @Test
    @DisplayName("Simple constructor check with custom setters that works")
    void testBankAccountSetters() {
        def test = new BankAccount()
        test.setAccountNumber("12345678")
        test.setBalance(0)
        test.setPin("0000")
        test.setCustomerName("Alex")
        def expected = new BankAccount("12345678", "Alex", 0, "0000")
        assertEquals(test.getPin(), expected.getPin())
        assertEquals(test.getAccountNumber(), expected.getAccountNumber())
        assertEquals(test.getCustomerName(), expected.getCustomerName())
        assertEquals(test.getBalance(), expected.getBalance())
    }

    @Test
    @DisplayName("Check deposit")
    void testDepositFunds() {
        def test = new BankAccount("12345678", "Alex")
        test.depositFunds(150)
        def expected = 150
        assertEquals(test.getBalance(), expected)
    }

    @Test
    @DisplayName("Check withdraw")
    void testWithdrawFunds() {
        def test = new BankAccount("12345678", "Alex")
        test.setBalance(300)
        test.withdrawFunds(150)
        def expected = 150
        assertEquals(test.getBalance(), expected)
    }

    @Test
    void WithdrawFundsBalanceIsBelowZero() {
        def test = new BankAccount("12345678", "Alex")
        def sum = 200
        String text = tapSystemOut(() -> {
            test.withdrawFunds(sum)
        })
        assertEquals("Insufficient Funds, 0 available but " + sum + " was requested!", text.trim());
    }

    @Test
    void WithdrawFundsBalanceIsOk() {
        def test = new BankAccount("12345678", "Alex", 200, "0000")
        def sum = 200
        String text = tapSystemOut(() -> {
            test.withdrawFunds(sum)
        })
        assertEquals("New Balance is " + 0, text.trim());
    }

    @Test
    void DepositFundsNewBalanceIsBelowZero() {
        def test = new BankAccount("12345678", "Alex")
        def sum = -100
        String text = tapSystemOut(() -> {
            test.depositFunds(sum)
        })
        assertEquals("Deposit must be greater than 0!", text.trim());
    }


    @Test
    void DepositFundsBalanceIsOk() {
        def test = new BankAccount("12345678", "Alex")
        def sum = 100
        String text = tapSystemOut(() -> {
            test.depositFunds(sum)
        })
        assertEquals("New Balance is " + sum, text.trim())
    }

}