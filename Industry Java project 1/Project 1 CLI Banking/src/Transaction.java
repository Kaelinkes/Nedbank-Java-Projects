/*
        ===============================================
                        TICKET 2
        ===============================================

ISSUES & THINGS TO FIX:

1. Incorrect package name
   - Required: banking.model
   - Current: com.mycompany.transaction
   - Fix: Change package to match specification
   - Lines: 41

2. Test main method should NOT be included
   - Ticket only requires Transaction class
   - Lines: ~144-154

3. (Minor) Enum accessibility is correct but ensure it's public inside class
   - Already correct, just clarify and confirm
   - Lines: ~50-61

4. (Minor) Formatting must EXACTLY match spec
   - Spec: '[2024-03-15 14:32] DEPOSIT R 500.00 Balance: R 1500.00'
   - Your format matches ✔ but spacing/capitalisation must remain exact (Good programming practice)
   - Lines: ~121-141

NOTE:
Line numbers are approximate depending on IDE formatting.
No fixes are applied below — only comments highlighting issues.

COMMENTS & FEEDBACK:
This class demonstrates a clean and reliable approach to handling financial records through immutability and well-structured data. 
By ensuring that transactions cannot be modified after creation, it reflects real-world banking standards and audit requirements. 
The use of enums, timestamps, and formatted output shows attention to both functionality and detail. 
It’s a strong example of how good design choices lead to secure and maintainable code.
Well done, exellent first attempt!
*/


package com.mycompany.transaction;
//Declares the package (folder structure) where this class belongs

import java.time.LocalDateTime;
//Imports LocalDateTime to store the exact date and time of a transaction

import java.time.format.DateTimeFormatter;
//Imports DateTimeFormatter to control how the date/time is displayed


public class Transaction {
//This class represents a single bank transaction (like a receipt for deposit/withdrawal)

    public enum Type {
        //Enum defines a fixed set of transaction types

        DEPOSIT,
        //Represents money going INTO the account

        WITHDRAWAL
        //Represents money going OUT of the account
    }

    private final Type type;
    //Stores the type of transaction (DEPOSIT or WITHDRAWAL)
    //'final'means it cannot be changed after the object is created

    private final double amount;
    //Stores how much money is involved in the transaction

    private final double balanceAfter;
    //Stores the account balance AFTER the transaction happens

    private final LocalDateTime timestamp;
    //Stores the exact date and time when the transaction was created

    public Transaction(Type type, double amount, double balanceAfter) {
        //Constructor:used to create a new Transaction object

        this.type = type;
        //Sets the transaction type

        this.amount = amount;
        //Sets the transaction amount

        this.balanceAfter = balanceAfter;
        //Sets the balance after the transaction

        this.timestamp = LocalDateTime.now();
        //Automatically sets the timestamp to the current date and time
    }

    public Type getType() {
        //Getter method to access the transaction type

        return type;
        //Returns the type (DEPOSIT or WITHDRAWAL)
    }

    public double getAmount() {
        //Getter method to access the transaction amount

        return amount;
        //Returns the amount
    }

    public double getBalanceAfter() {
        //Getter method to access the balance after transaction

        return balanceAfter;
        //Returns the balance after transaction
    }

    public LocalDateTime getTimestamp() {
        //Getter method to access the timestamp

        return timestamp;
        //Returns the date and time of the transaction
    }

    @Override
    public String toString() {
        //Overrides default toString()to display a nicely formatted transaction

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //Defines how the date and time should look (removes the 'T' and seconds)

        return String.format("[%s] %s R %.2f Balance: R %.2f",
                //Creates a formatted string showing all transaction details

                timestamp.format(formatter),
                //Formats timestamp into readable form

                type,
                //Displays transaction type

                amount,
                //Displays amount with 2 decimal places

                balanceAfter);
                //Displays balance after transaction with 2 decimal places
    }

    //TEST MAIN METHOD(optional)
    public static void main(String[] args) {
        //Main method:starting point of the program (used here to test the class)

        Transaction t1 = new Transaction(Type.DEPOSIT, 500.00, 1500.00);
        //Creates a transaction:deposit of R500 resulting in balance R1500

        System.out.println(t1);
        //Prints the transaction (calls toString()automatically)
    }
}
