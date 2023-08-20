import im.educ.groovy.model.ATMImplementation
import im.educ.groovy.model.BankAccount
import im.educ.groovy.model.BankStore
import im.educ.groovy.model.DispencingMachine
import im.educ.groovy.model.Hardware
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


import static org.junit.jupiter.api.Assertions.assertEquals

class ATMImplementationTest {

    @Test
    @DisplayName("Simple constructor check that works")
    void testATMDefault() {

        BankAccount alex = new BankAccount("12345678", "Alex")
        BankAccount mark = new BankAccount("87654321", "Mark")
        alex.setPin("1234")

        BankStore bs = new BankStore()
        bs.store(alex)
        bs.remove(mark)

        alex.depositFunds(40000)
        alex.withdrawFunds(500)

        Hardware hw = new Hardware()
        ATMImplementation atm1
        atm1 = new ATMImplementation(bs, hw, DispencingMachine.mod5.getMap(), [0, 0, 0, 0, 0])

        println atm1
        atm1.deposit("12345678", "1234", [5, 4, 3, 2, 10])
        println atm1.dispensingAvailableNotes
        atm1.withdraw("12345678", "1234", 3850)
        println atm1.dispensingAvailableNotes
        atm1.withdraw("12345678", "1234", 12500)
        println atm1.dispensingAvailableNotes
        atm1.withdraw("12345678", "1234", 9500)
        println atm1.dispensingAvailableNotes
        atm1.withdraw("12345678", "1234", 6900)
        println atm1.dispensingAvailableNotes
        def test = new BankAccount("12345678", "Alex")
        def expected = new BankAccount("12345678", "Alex", 0, "0000")
        assertEquals(test.getPin(), expected.getPin())

    }

}
