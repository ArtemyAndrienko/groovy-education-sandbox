package im.educ.groovy.model

import groovy.transform.Canonical
import groovy.transform.ToString


@Canonical
@ToString
class BankStore implements Store {
    List<BankAccount> accounts = []


    BankAccount searchByAccName(String name) {
        for (BankAccount ba in this.accounts) {
            if (name == ba.getAccountNumber()) {
                return ba
            }
        }
    }


    BankStore store(Object account) {
        this.accounts.add(account as BankAccount)
        return this
    }


    BankStore remove(Object account) {
        this.accounts.remove(account as BankAccount)
        return this
    }


}
