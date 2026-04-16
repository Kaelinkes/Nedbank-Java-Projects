//Ticket 1

//The package tells java where this class belongs in the project

import java.util.ArrayList;//stores and saves transactions
import java.util.List;//this is the interface we will be using

// ── Exception ─────────────────────────────────────────────────────────────────
// Custom exception to handle cases where an account
// does not have enough balance for a transaction
class InsufficientFundsException extends Exception {
    // Constructor that accepts a custom error message
    // and passes it to the parent Exception class
    public InsufficientFundsException(String message) {
        super(message);// This sends a message to the parent class
    }
}

// ── Abstract class Here
// ───────────────────────────────────────────────────────
public abstract class BankAccount {
    /*
     * public makes it assecible for other packages
     * abstract - makes the class name exclusive(cannot be used again)
     */
    protected String accountNumber;// stores values
    protected String ownerName;
    protected double balance;
    private List<String> transactionHistory;

    // ── Constructor ───────────────────────────────────────────────────────────
    public BankAccount(String accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;// assigns the value
        this.ownerName = ownerName;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();// initializes the list
    }/*
      * this constructor runs when the subclass calls it
      */

    // ── Abstract method ───────────────────────────────────────────────────────
    public abstract void withdraw(double amount) throws InsufficientFundsException;
    /*
     * doesnt require a body to function
     * throws an exception error if the user doesent have enough funds available
     * forces the subclass to use it
     */

    // ── Deposit ───────────────────────────────────────────────────────────────
    // Deposits a specified amount into the account
    // Parameter: amount - the money to be added to the balance
    public void deposit(double amount) {

        // Check that the deposit amount is positive
        if (amount > 0) {

            // Add the amount to the current balance
            this.balance += amount;

            // Record the transaction with details of the deposit and updated balance
            addTransaction("Deposited: R" + amount + " | New Balance: R" + this.balance);

            // Confirm successful deposit to the user
            System.out.println("Deposit successful: R" + amount);
        } else {

            // Handle invalid input where amount is zero or negative
            System.out.println("Error: Deposit amount must be greater than zero.");
        }
    }

    // ── Print last 5 transactions (or all if fewer than 5) ────────────────────
    // Prints a mini account statement showing recent transactions
    public void printStatement() {

        // Display account holder's name and account number
        System.out.println("--- Statement for: " + ownerName + " (" + accountNumber + ") ---");

        // Check if there are no transactions recorded
        if (transactionHistory.isEmpty()) {

            // Inform the user that no transactions exist
            System.out.println("No transactions found.");

        } else {

            // Determine the starting index to show only the last 5 transactions
            int startIndex = Math.max(0, transactionHistory.size() - 5);

            // Get a sublist of the most recent transactions
            List<String> recent = transactionHistory.subList(startIndex, transactionHistory.size());

            // Loop through and print each recent transaction
            for (String transaction : recent) {
                System.out.println(transaction);
            }
        }

        // Display the current account balance
        System.out.println("--- Current Balance: R" + balance + " ---");
    }

    // ── Protected helpers ─────────────────────────────────────────────────────

    // Deducts a specified amount from the account balance
    // Used internally by subclasses or other methods (e.g., withdrawal)
    protected void deductFromBalance(double amount) {

        // Check that the amount is valid and sufficient funds are available
        if (amount > 0 && this.balance - amount >= 0) {

            // Subtract the amount from the balance
            this.balance -= amount;

        } else {

            // Handle invalid amount or insufficient balance
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    // Adds a transaction record to the transaction history
    // Used to log account activity such as deposits and withdrawals
    protected void addTransaction(String message) {

        // Append the transaction message to the list
        transactionHistory.add(message);
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getAccountNumber() {
        return accountNumber;
    }// provides safer

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

} // ← BankAccount closes here -> subclass follows right underneath it
  // (SavingsAccount)

// ── Subclass
// ──────────────────────────────────────────────────────────────────

// SavingsAccount inherits from BankAccount and provides
// a specific implementation for withdrawals
class SavingsAccount extends BankAccount {

    // Stores the initial balance when the account was created
    private final double initialBalance;

    // Constructor to initialize account details and initial balance
    public SavingsAccount(String accountNumber, String ownerName, double initialBalance) {

        // Call the parent class constructor
        super(accountNumber, ownerName, initialBalance);

        // Store the initial balance separately
        this.initialBalance = initialBalance;
    }

    // Override the abstract withdraw method from BankAccount
    @Override // withdraw is declared abstract in BankAccount, so it must be implemented here
    public void withdraw(double amount) throws InsufficientFundsException {

        // Validate that the withdrawal amount is positive
        if (amount <= 0) {
            throw new InsufficientFundsException("Withdrawal amount must be positive.");
        }

        // Check if there are enough funds in the account
        if (amount > this.balance) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Available balance: R" + this.balance);
        }

        // Deduct the amount from the balance using a helper method
        deductFromBalance(amount);

        // Record the transaction with updated balance
        addTransaction("Withdrawn: R" + amount + " | New Balance: R" + this.balance);

        // Confirm successful withdrawal to the user
        System.out.println("Withdrawal successful: R" + amount);
    }

    // Getter method to retrieve the initial balance
    public double getInitialBalance() {
        return initialBalance;
    }

} // ← SavingsAccount class ends here

// ── Entry Point
// ───────────────────────────────────────────────────────────────
class Main {

    // Main method: program execution starts here
    public static void main(String[] args) {

        // Create a new SavingsAccount with account number, owner name, and initial
        // balance
        SavingsAccount account = new SavingsAccount("ACC001", "Emmanuel/Zack", 1000.00);

        // Deposit money into the account
        account.deposit(500.00);

        try {

            // Attempt a valid withdrawal
            account.withdraw(200.00);

            // Attempt a withdrawal that exceeds the balance
            // This is expected to throw an InsufficientFundsException
            account.withdraw(5000.00);

        } catch (InsufficientFundsException e) {

            // Handle the exception and display an error message
            System.out.println("Error: " + e.getMessage());
        }

        // Print the account statement, including recent transactions and balance
        account.printStatement();
    }
}