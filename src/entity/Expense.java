package entity;

import enums.ExpenseType;

import java.util.List;

public class Expense {
    private static Long counter = 1L;

    private final Long id;
    private final String paidBy;
    private final Double amount;
    private final ExpenseType expenseType;
    private final List<String> sharedBy;

    public Expense(String paidBy, Double amount, ExpenseType expenseType, List<String> sharedBy) {
        this.id = counter++;
        this.paidBy = paidBy;
        this.amount = amount;
        this.expenseType = expenseType;
        this.sharedBy = sharedBy;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", paidBy='" + paidBy + '\'' +
                ", amount=" + amount +
                ", expenseType=" + expenseType +
                ", sharedBy=" + sharedBy +
                '}';
    }
}
