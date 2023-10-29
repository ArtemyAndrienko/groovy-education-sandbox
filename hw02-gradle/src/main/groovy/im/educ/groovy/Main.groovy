package im.educ.groovy

import im.educ.groovy.model.ATM
import im.educ.groovy.model.BankAccount
import im.educ.groovy.model.BankStore
import im.educ.groovy.model.Currency
import im.educ.groovy.model.DispencingMachine
import im.educ.groovy.model.Hardware
import static im.educ.groovy.Utils.overrideMethods


static void main(String[] args) {
    overrideMethods()

    BankAccount alex = new BankAccount("12345678", "Alex")
    BankAccount mark = new BankAccount("87654321", "Mark")
    alex.setPin("1234")

    BankStore bs = new BankStore()
    bs.store(alex)
    bs.store(mark)
    println(bs)
    bs.remove(mark)
    println(bs)
    println bs.searchByAccName("12345678")

    alex.depositFunds(40000)
    alex.withdrawFunds(500)
    println alex

    Hardware hw = new Hardware()
    println hw
    ATM atm1
    atm1 = new ATM(bs, hw, DispencingMachine.mod5.getMap(), Currency.RUB, [0, 0, 0, 0, 0])

    println atm1
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

    println alex
    println atm1.inquiry(bs, alex.accountNumber)


}