/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

//Change Package to banking.model
package com.mycompany.transaction;

//This defines the package where the Transaction class is stored.
//It helps organise the project into structured folders.

import java.time.LocalDateTime;
//Imports LocalDateTime to capture the exact date and time a transaction occurs.

import java.time.format.DateTimeFormatter;
//Imports DateTimeFormatter to format how date/time will be displayed in output.


public class Transaction {

    //This class models a single bank transaction.
    //Each object represents one financial action (deposit or withdrawal)
    //and stores its details permanently (immutable record).

    public enum Type {
        // Defines the two possible types of transactions in the system.

        DEPOSIT,      // Money added into the account
        WITHDRAWAL    // Money taken out of the account
    }

    private final Type type;
    //Stores whether this transaction is a deposit or withdrawal.
    //final ensures it cannot be changed after creation (immutability).

    private final double amount;
    //Stores the amount of money involved in the transaction.

    private final double balanceAfter;
    //Stores the account balance after this transaction is completed.

    private final LocalDateTime timestamp;
    //Stores the exact time when the transaction object was created.

    public Transaction(Type type, double amount, double balanceAfter) {
        //Constructor used to create a new Transaction object.

        this.type = type;
        //Assigns the transaction type (DEPOSIT or WITHDRAWAL).

        this.amount = amount;
        //Assigns the transaction amount.

        this.balanceAfter = balanceAfter;
        //Assigns the resulting balance after the transaction.

        this.timestamp = LocalDateTime.now();
        //Automatically records the current system date and time.
    }

    public Type getType() {
        //Returns the type of transaction.

        return type;
    }

    public double getAmount() {
        //Returns the transaction amount.

        return amount;
    }

    public double getBalanceAfter() {
        //Returns the balance after this transaction was applied.

        return balanceAfter;
    }

    public LocalDateTime getTimestamp() {
        //Returns the exact timestamp when this transaction was created.

        return timestamp;
    }

    @Override
    public String toString() {
        //Converts the transaction object into a readable string format.

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //Defines the display format for the timestamp.

        return String.format("[%s] %s R %.2f Balance: R %.2f",
                timestamp.format(formatter), //formatted date and time
                type,                        //transaction type
                amount,                      //transaction amount
                balanceAfter);               //balance after transaction
    }

    // TEST MAIN METHOD (only for testing purposes)
    public static void main(String[] args) {

        Transaction t1 = new Transaction(Type.DEPOSIT, 500.00, 1500.00);
        // Creates a sample transaction: deposit of 500 leading to balance 1500.

        System.out.println(t1);
        // Prints the transaction using the toString() method.
    }
}