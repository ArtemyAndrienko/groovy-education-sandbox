import im.educ.groovy.model.ATM
import im.educ.groovy.model.BankAccount
import im.educ.groovy.model.BankStore
import im.educ.groovy.model.Currency
import im.educ.groovy.model.DispencingMachine
import im.educ.groovy.model.Hardware
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import static im.educ.groovy.Utils.overrideMethods
import static org.junit.jupiter.api.Assertions.assertEquals

class ATMTest {


    @Test
    @DisplayName("test currencies")
    void testCurrency() {
        assertEquals(ATM.getCurrency(Currency.RUB), "руб.")
        assertEquals(ATM.getCurrency(Currency.EUR), "€")
        assertEquals(ATM.getCurrency(Currency.USD), "\$")
    }

    @Test
    @DisplayName("get/set methods")
    void testATMGettersSetters() {
        BankAccount alex = new BankAccount("12345678", "Alex")
        alex.setAccountNumber("0000000")
        alex.setCustomerName("Alex1")
        alex.setPin("1111")
        alex.setBalance(0)

        def expected = "0000000"
        assertEquals(expected, alex.getAccountNumber())
        expected = 0
        assertEquals(expected, alex.getBalance())
        expected = "1111"
        assertEquals(expected, alex.getPin())
        expected = "Alex1"
        assertEquals(expected, alex.getCustomerName())
    }

    @Test
    @DisplayName("Simple constructor check that works")
    void testATMDefault() {
        overrideMethods()
        BankAccount alex = new BankAccount("12345678", "Alex")
        BankAccount mark = new BankAccount("87654321", "Mark")
        alex.setPin("1234")
        assertEquals(alex.getPin(), "1234")

        BankStore bs = new BankStore()
        bs.store(alex)
        bs.remove(mark)
        assertEquals(alex.toString(), new BankAccount("12345678", "Alex", 0, "1234").toString())
        assertEquals(null, bs.searchByAccName(mark.accountNumber))

        alex.depositFunds(40000)
        alex.withdrawFunds(500)

        Hardware hw = new Hardware()
        ATM atm1
        atm1 = new ATM(bs, hw, DispencingMachine.mod5.getMap(), Currency.RUB, [0, 0, 0, 0, 0])
        println atm1

        String text
        text = tapSystemOut(() -> {
            ATM.validatePin(alex.getPin(), "1234")
        })
        assertEquals("===ПИН введен корректно===", text.trim())
        text = tapSystemOut(() -> {
            ATM.validatePin(alex.getPin(), "0000")
        })
        assertEquals("===Неверный ПИН!!! Операция отклонена===", text.trim())

        atm1.deposit("12345678", "1234", Currency.EUR, [5, 4, 3, 2, 10])
        println atm1.usedSlots
        atm1.withdraw("12345678", "1234", Currency.RUB, 3850)
        println atm1.usedSlots
        atm1.withdraw("12345678", "1234", Currency.RUB, 12500)
        println atm1.usedSlots
        atm1.withdraw("12345678", "1234", Currency.RUB, 9500)
        println atm1.usedSlots
        atm1.withdraw("12345678", "1234", Currency.RUB, 6900)
        println atm1.usedSlots
        atm1.withdraw("12345678", "1234", Currency.RUB, [0, 0, 0, 0, 10])
        println atm1.usedSlots
        def test = new BankAccount("12345678", "Alex")
        def expected = new BankAccount("12345678", "Alex", 0, "0000")
        assertEquals(test.getPin(), expected.getPin())
    }

}
