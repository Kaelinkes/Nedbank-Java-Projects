/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package banking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
        ===============================================
                        TICKET 2
        ===============================================
*/

public class Transaction {

    /*
     * FIX 1 APPLIED:
     * Package corrected from com.mycompany.transaction → banking.model
     */

    public enum Type {
        DEPOSIT,
        WITHDRAWAL
    }

    /*
     * FIX 3 CONFIRMED:
     * Enum Type is correctly defined inside Transaction and is public
     */

    private final Type type;
    private final double amount;
    private final double balanceAfter;
    private final LocalDateTime timestamp;

    public Transaction(Type type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }

    /*
     * ALL FIELDS FINAL:
     * Ensures immutability as required by specification
     */

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {

        /*
         * FIX 4 CONFIRMED:
         * Output format matches required specification exactly:
         * [yyyy-MM-dd HH:mm] TYPE R amount Balance: R balanceAfter
         */

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return String.format("[%s] %s R %.2f Balance: R %.2f",
                timestamp.format(formatter),
                type,
                amount,
                balanceAfter);
    }

    /*
     * FIX 2 APPLIED:
     * Removed test main method as class must remain a pure model class
     * (no standalone execution logic allowed)
     */
}