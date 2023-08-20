package im.educ.groovy.model

import groovy.transform.Canonical
import groovy.transform.ToString


@ToString
@Canonical
class ATMImplementation {
    BankStore bs
    Hardware hw
    Map<Integer, Integer> dispencingMachine
    ArrayList dispensingAvailableNotes

    ATMImplementation(BankStore bs, Hardware hw, Map<Integer, Integer> dm, ArrayList dan) {
        this.bs = bs
        this.hw = hw
        this.dispencingMachine = dm
        this.dispensingAvailableNotes = dan
    }

    ATMImplementation withdraw(String acc, String pin, Integer sum) {
        def ba = this.bs.searchByAccName(acc)
        if (ba) {
            if (!validatePin(ba.pin, pin))
                return
        }
        Integer stay = sum
        println "Снятие наличных: ${sum}"
        ArrayList<Integer> expectedNotesMap = new ArrayList<>(Collections.nCopies(this.dispensingAvailableNotes.size(), 0))
        int slotIter = 0

        if (sum > availableSum(this.dispensingAvailableNotes)) {
            println("Указанная сумма больше остатка денежных средств в машине")
            return
        }

        for (i in this.dispencingMachine) {
            Integer currentNominal = i.key.toInteger()
            Integer expectedNumOfNotes = stay / currentNominal
            Integer actualNumOfNotes

            if (this.dispensingAvailableNotes[slotIter] < expectedNumOfNotes) {
                expectedNumOfNotes = this.dispensingAvailableNotes[slotIter] as Integer
                actualNumOfNotes = 0
            } else {
                actualNumOfNotes = this.dispensingAvailableNotes[slotIter] - expectedNumOfNotes
            }

            if (expectedNumOfNotes > 0 && (actualNumOfNotes >= 0)) {
                expectedNotesMap[slotIter] += expectedNumOfNotes
                stay -= expectedNumOfNotes * currentNominal
            } else {
                expectedNotesMap[slotIter] += 0
            }
            slotIter++
        }

        println "Будет списано: ${expectedNotesMap} следующих номиналов ${this.dispencingMachine.keySet()}"
        if (stay == 0) {
            println "Выполняем списание со счета: ${sum}"
            (this.dispensingAvailableNotes - expectedNotesMap).each {
                if (it <= 0) {
                    println("Недостаточно купюр !!! ||| Остаток в банкомате ${ availableSum(this.dispensingAvailableNotes)}")
                    return
                }
            }
            this.dispensingAvailableNotes = this.dispensingAvailableNotes - expectedNotesMap
            ba.withdrawFunds(sum)
        } else {
            println "Операция не может быть выполнена! Доступные номиналы: ${this.dispencingMachine.keySet()} ||| Остаток в банкомате ${ availableSum(this.dispensingAvailableNotes)}"
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


    ArrayList deposit(String acc, String pin, ArrayList sum) {
        def ba = this.bs.searchByAccName(acc)
        if (ba) {
            if (!validatePin(ba.pin, pin))
                return
        }
        println("Внесение наличных: ${availableSum(sum)}")
        ba.depositFunds(availableSum(sum))
        this.dispensingAvailableNotes += sum
        return this.dispensingAvailableNotes
    }

    static Boolean validatePin(String stored, String entered) {
        println("===Проверка ПИНа: ===")
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

}
