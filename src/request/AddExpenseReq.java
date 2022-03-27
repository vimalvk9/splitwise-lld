package request;

import enums.ExpenseType;
import enums.SplitType;

import java.util.List;
import java.util.Map;

public class AddExpenseReq {

    private String groupName;
    private String paidBy;
    private Double amount;
    private List<String> sharedAmong;
    private ExpenseType expenseType;
    private SplitType splitType;
    private Map<String, Double> amountDistributionRatio;

    public AddExpenseReq(String groupName, String paidBy, Double amount, List<String> sharedAmong, ExpenseType expenseType, SplitType splitType) {
        this.setGroupName(groupName);
        this.setPaidBy(paidBy);
        this.setAmount(amount);
        this.setSharedAmong(sharedAmong);
        this.setExpenseType(expenseType);
        this.setSplitType(splitType);
    }



    public String getGroupName() {
        return groupName;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public Double getAmount() {
        return amount;
    }

    public List<String> getSharedAmong() {
        return sharedAmong;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public SplitType getSplitType() {
        return splitType;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setSharedAmong(List<String> sharedAmong) {
        this.sharedAmong = sharedAmong;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public void setSplitType(SplitType splitType) {
        this.splitType = splitType;
    }

    public Map<String, Double> getAmountDistributionRatio() {
        return amountDistributionRatio;
    }

    public void setAmountDistributionRatio(Map<String, Double> amountDistributionRatio) {
        this.amountDistributionRatio = amountDistributionRatio;
    }
}
