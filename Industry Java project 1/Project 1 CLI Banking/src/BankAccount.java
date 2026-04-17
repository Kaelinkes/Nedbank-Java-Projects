// ==========================
// Ticket 1
// ==========================

/*
ISSUES & THINGS TO FIX:

1. Fields should be PRIVATE, not PROTECTED
   - Current: protected accountNumber, ownerName, balance
   - Fix: Change to private
   - Lines: ~44–46

2. Transaction list should NOT be List<String>
   - Should use List<Transaction>
   - You are storing Strings instead of proper objects
   - Lines: ~52, ~91, ~132, ~166

3. Getter name and type mismatch
   - Current: getTransactionHistory() returns List<String>
   - Should be: getTransactions() returning List<Transaction>
   - Lines: ~180–182

4. deposit() should CREATE a Transaction object
   - Currently adding String messages instead
   - Should instantiate Transaction class
   - Lines: ~84–92

5. deductFromBalance() should NOT print errors
   - This method should only deduct balance
   - Validation belongs in withdraw()
   - Lines: ~120–130

6. Missing package declaration
   - Should be: package banking.model;
   - Lines: Top of file (before imports)

7. Extra classes included (not required for this ticket)
   - SavingsAccount and Main should NOT be part of Ticket 1
   - Lines: ~188 onwards

8. Transaction class is missing entirely
   - Required based on spec
   - Affects: Entire transaction handling system (multiple locations)

NOTE:
Line numbers are approximate and may differ slightly depending on formatting/IDE.
No fixes are applied below — only comments showing where issues are.

COMMENTS & FEEDBACK: 
This implementation builds a strong foundation for the entire system by correctly using an abstract class to model real-world banking behaviour.
It shows a clear understanding of how to structure shared functionality while allowing flexibility for future account types.
The use of validation, helper methods, and transaction tracking reflects thoughtful design.
Overall, it sets a solid base that other parts of the project can confidently build on.
Good first attempt, well done!
*/


// ❌ ISSUE 6: Missing package declaration here
// Should be: package banking.model;

import java.util.ArrayList;//stores and saves transactions
import java.util.List;//this is the interface we will be using

// ── Exception ─────────────────────────────────────────────────────────────────
// ⚠️ ISSUE 7: This should likely be in its own file, not inside this one
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// ── Abstract class Here
public abstract class BankAccount {

    /*
     * ❌ ISSUE 1: These MUST be private, not protected
     * Location: Fields section (~44–46)
     */
    protected String accountNumber;
    protected String ownerName;
    protected double balance;

    /*
     * ❌ ISSUE 2: Should NOT be List<String>
     * Should be List<Transaction>
     * Location: (~52)
     */
    private List<String> transactionHistory;

    // ── Constructor ───────────────────────────────────────────────────────────
    public BankAccount(String accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;

        // ✅ Correct initialization
        this.transactionHistory = new ArrayList<>();
    }

    // ── Abstract method ───────────────────────────────────────────────────────
    public abstract void withdraw(double amount) throws InsufficientFundsException;

    // ── Deposit ───────────────────────────────────────────────────────────────
    public void deposit(double amount) {

        if (amount > 0) {

            this.balance += amount;

            /*
             * ❌ ISSUE 4:
             * You are adding a String instead of creating a Transaction object
             * Should create new Transaction(...)
             * Location: (~84–92)
             */
            addTransaction("Deposited: R" + amount + " | New Balance: R" + this.balance);

            System.out.println("Deposit successful: R" + amount);
        } else {
            System.out.println("Error: Deposit amount must be greater than zero.");
        }
    }

    // ── Print last 5 transactions ────────────────────
    public void printStatement() {

        System.out.println("--- Statement for: " + ownerName + " (" + accountNumber + ") ---");

        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {

            int startIndex = Math.max(0, transactionHistory.size() - 5);

            /*
             * ❌ ISSUE 2 (again): Using List<String> instead of Transaction
             * Location: (~91)
             */
            List<String> recent = transactionHistory.subList(startIndex, transactionHistory.size());

            for (String transaction : recent) {
                System.out.println(transaction);
            }
        }

        System.out.println("--- Current Balance: R" + balance + " ---");
    }

    // ── Protected helpers ─────────────────────────────────────────────────────

    protected void deductFromBalance(double amount) {

        /*
         * ❌ ISSUE 5:
         * This method should NOT handle validation or print errors
         * It should ONLY deduct balance
         * Location: (~120–130)
         */

        if (amount > 0 && this.balance - amount >= 0) {
            this.balance -= amount;
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    protected void addTransaction(String message) {

        /*
         * ❌ ISSUE 2 (again):
         * Should be adding Transaction objects, not Strings
         * Location: (~132)
         */
        transactionHistory.add(message);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    /*
     * ❌ ISSUE 3:
     * Wrong name and wrong type
     * Should be getTransactions() returning List<Transaction>
     * Location: (~180–182)
     */
    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

} // ← BankAccount ends here


// ── Subclass ──────────────────────────────────────────────────────────────────

/*
 * ❌ ISSUE 7:
 * This class should NOT be in this ticket/file
 * Ticket 1 only requires BankAccount
 * Location: (~188 onwards)
 */
class SavingsAccount extends BankAccount {

    private final double initialBalance;

    public SavingsAccount(String accountNumber, String ownerName, double initialBalance) {
        super(accountNumber, ownerName, initialBalance);
        this.initialBalance = initialBalance;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {

        if (amount <= 0) {
            throw new InsufficientFundsException("Withdrawal amount must be positive.");
        }

        if (amount > this.balance) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Available balance: R" + this.balance);
        }

        deductFromBalance(amount);

        addTransaction("Withdrawn: R" + amount + " | New Balance: R" + this.balance);

        System.out.println("Withdrawal successful: R" + amount);
    }

    public double getInitialBalance() {
        return initialBalance;
    }

}


// ── Entry Point ───────────────────────────────────────────────────────────────

/*
 * ❌ ISSUE 7:
 * Main class should NOT be part of this ticket
 */
class Main {

    public static void main(String[] args) {

        SavingsAccount account = new SavingsAccount("ACC001", "Emmanuel/Zack", 1000.00);

        account.deposit(500.00);

        try {
            account.withdraw(200.00);
            account.withdraw(5000.00);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

        account.printStatement();
    }
}
