package im.educ.groovy.model

import groovy.transform.ToString

@ToString
class BankAccount {

    // Params
    private String accountNumber
    private Integer balance
    private String customerName
    private String pin

    // Constructors
    BankAccount() {
        this("00000000", "Empty Name", 0.0, "0000")
    }

    BankAccount(String accountNumber, String customerName, double balance, String pin) {
        this.accountNumber = accountNumber
        this.balance = balance
        this.customerName = customerName
        this.pin = pin
    }


    BankAccount(String accountNumber, String customerName) {
        this(accountNumber, customerName, 0.0, "0000")
        this.customerName = customerName
        this.accountNumber = accountNumber
    }

    // Getters
    String getAccountNumber() {
        return accountNumber
    }

    double getBalance() {
        return balance
    }

    String getCustomerName() {
        return customerName
    }

    String getPin() {
        return pin
    }


    // Setters
    void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber
    }

    void setBalance(double balance) {
        this.balance = balance
    }

    void setCustomerName(String customerName) {
        this.customerName = customerName
    }

    void setPin(String pin) {
        this.pin = pin
    }


    // Methods
    void depositFunds(Integer deposit) {
        if (deposit < 0) {
            System.out.println("Deposit must be greater than 0!")
        } else {
            this.balance += deposit
            System.out.println("New Balance is " + this.balance)
        }
    }

    void withdrawFunds(Integer withdraw) {
        if (withdraw > this.balance) {
            System.out.println("Insufficient Funds, " + this.balance + " available but " + withdraw + " was requested!")
        } else {
            this.balance -= withdraw
            System.out.println("New Balance is " + this.balance)
        }
    }


}