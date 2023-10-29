package im.educ.groovy.model

import groovy.transform.Canonical
import groovy.transform.ToString


@ToString
@Canonical
class ATM {
    BankStore bs
    Hardware hw
    Map<Integer, Integer> dispencingMachine
    Currency currency
    ArrayList usedSlots

    ATM(BankStore bs, Hardware hw, Map<Integer, Integer> dm, Currency cur, ArrayList dan) {
        this.bs = bs
        this.hw = hw
        this.currency = cur
        this.dispencingMachine = dm
        this.usedSlots = dan
    }

    ATM withdraw(String acc, String pin, Currency curr, Integer sum) {
        def ba = this.bs.searchByAccName(acc)
        if (ba) {
            if (!validatePin(ba.pin, pin))
                return
        }
        if (!checkCurrency(curr, this.currency)){
            println ("Данная валюта не поддерживается для примема: ${curr.toString()}")
        }

        def currencySign = getCurrency(curr)

        Integer stay = sum
        println "Снятие наличных: ${sum} ${currencySign}"
        ArrayList<Integer> expectedNotesMap = new ArrayList<>(Collections.nCopies(this.usedSlots.size(), 0))
        int slotIter = 0

        if (sum > availableSum(this.usedSlots)) {
            println("Указанная сумма больше остатка денежных средств в машине")
            return
        }

        for (i in this.dispencingMachine) {
            Integer currentNominal = i.key.toInteger()
            Integer expectedNumOfNotes = stay / currentNominal
            Integer actualNumOfNotes

            if (this.usedSlots[slotIter] < expectedNumOfNotes) {
                expectedNumOfNotes = this.usedSlots[slotIter] as Integer
                actualNumOfNotes = 0
            } else {
                actualNumOfNotes = this.usedSlots[slotIter] - expectedNumOfNotes
            }

            if (expectedNumOfNotes > 0 && (actualNumOfNotes >= 0)) {
                expectedNotesMap[slotIter] += expectedNumOfNotes
                stay -= expectedNumOfNotes * currentNominal
            } else {
                expectedNotesMap[slotIter] += 0
            }
            slotIter++
        }

        println "Будет списано: ${expectedNotesMap} ${currencySign} следующих номиналов ${this.dispencingMachine.keySet()}"
        if (stay == 0) {
            println "Выполняем списание со счета: ${sum}  ${currencySign}"
            (this.usedSlots - expectedNotesMap).each {
                if (it <= 0) {
                    println("Недостаточно купюр !!! ||| Остаток в банкомате ${ availableSum(this.usedSlots)}")
                    return
                }
            }
            this.usedSlots = this.usedSlots - expectedNotesMap
            ba.withdrawFunds(sum)
        } else {
            println "Операция не может быть выполнена! Доступные номиналы: ${this.dispencingMachine.keySet()} ||| Остаток в банкомате ${ availableSum(this.usedSlots)}"
        }
        return this
    }


    ATM withdraw(String acc, String pin, Currency curr, ArrayList expectedNotesMap) {
        def ba = this.bs.searchByAccName(acc)
        if (ba) {
            if (!validatePin(ba.pin, pin))
                return
        }
        if (!checkCurrency(curr, this.currency)){
            println ("Данная валюта не поддерживается для примема: ${curr.toString()}")
        }

        def currencySign = getCurrency(curr)
        Integer sum = availableSum(expectedNotesMap)

        println "Будет списано: ${sum} ${currencySign} следующих номиналов ${this.dispencingMachine.keySet()}"
        if (availableSum(this.usedSlots) > sum) {
            this.usedSlots = this.usedSlots - expectedNotesMap
            ba.withdrawFunds(sum)
        } else {
            println "Операция не может быть выполнена - недостаточно купюр! Доступные номиналы: ${this.dispencingMachine.keySet()} ||| Остаток в банкомате ${ availableSum(this.usedSlots)}"
        }
        return this
    }

    Integer availableSum(ArrayList sum) {
        int result = 0
        int ii = 0
        for (i in this.dispencingMachine) {
            result += sum[ii] * i.key
            ii++
        }
        return result
    }


    ArrayList deposit(String acc, String pin, Currency curr, ArrayList sum) {
        def currencySign = getCurrency(curr)
        def ba = this.bs.searchByAccName(acc)

        if (ba) {
            if (!validatePin(ba.pin, pin))
                return
        }
        if (!checkCurrency(curr, this.currency)){
            println ("Данная валюта не поддерживается для примема: ${curr.toString()}")
        }

        println("Внесение наличных: ${availableSum(sum)} ${currencySign}")
//        this.usedSlots = this.usedSlots + sum
        this.usedSlots << this.usedSlots
        ba.depositFunds(availableSum(sum))
        return this.usedSlots
    }

    static Boolean validatePin(String stored, String entered) {
        if (entered.equals(stored)) {
            println "===ПИН введен корректно==="
            return true
        } else {
            println "===Неверный ПИН!!! Операция отклонена==="
            return false
        }
    }

    static Double inquiry(BankStore bs, String customerId) {
        return bs.searchByAccName(customerId).getBalance()
    }


    static String getCurrency(Currency curr){
        switch (curr){
            case Currency.RUB:
                return "руб."
                break
            case Currency.EUR:
                return "€"
                break
            case Currency.USD:
                return "\$"
                break
            default:
                return "---"
        }
    }

    static checkCurrency(Currency expected, Currency entered){
        return (expected.toString() == entered.toString())
    }

}
