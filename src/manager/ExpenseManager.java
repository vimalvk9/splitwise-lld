package manager;

import dao.ExpenseDAO;
import entity.Expense;
import entity.Group;
import entity.User;
import enums.ExpenseType;
import factory.SplitAmountImplFactory;
import request.AddExpenseReq;
import response.PostSplitAmountUserInfo;
import strategy.ISplitAmount;
import utils.CollectionUtils;
import utils.StringUtils;

import java.util.*;

public class ExpenseManager {

    private final ExpenseDAO expenseDAO;
    private final UserManager userManager;
    private final GroupManager groupManager;

    public ExpenseManager(ExpenseDAO expenseDAO, UserManager userManager, GroupManager groupManager) {
        this.expenseDAO = expenseDAO;
        this.userManager = userManager;
        this.groupManager = groupManager;
    }

    public void addExpense(AddExpenseReq addExpenseReq) {

        if (addExpenseReq.getExpenseType() == ExpenseType.P2P) {
            addP2PExpense(addExpenseReq);
        }
        else if (addExpenseReq.getExpenseType() == ExpenseType.GROUP) {
            addGroupExpense(addExpenseReq);
        }
        else {
            throw new IllegalArgumentException("ExpenseType " + addExpenseReq.getExpenseType() + " not found");
        }
    }

    private void addP2PExpense(AddExpenseReq addExpenseReq) {

        User paidByUser = userManager.getUserById(addExpenseReq.getPaidBy());
        User paidToUser = userManager.getUserById(addExpenseReq.getSharedAmong().get(0));

        if (Objects.nonNull(paidByUser) && Objects.nonNull(paidToUser) && addExpenseReq.getAmount() > 0) {
            addTransactionToUserExpense(addExpenseReq.getAmount(), addExpenseReq.getPaidBy(), addExpenseReq.getSharedAmong().get(0));
            addTransactionToUserExpense(-1 * addExpenseReq.getAmount(), addExpenseReq.getSharedAmong().get(0), addExpenseReq.getPaidBy());
        }
    }

    private void addTransactionToUserExpense(Double amount, String paidByUser, String paidToUser) {

        if (Objects.nonNull(paidByUser) && Objects.nonNull(paidToUser) && amount > 0) {

            Map<String, Double> paidByUserLog = expenseDAO.getPeerToPeerExpenseLog(paidByUser);

            Double initialAmount = paidByUserLog.getOrDefault(paidToUser, 0d);
            paidByUserLog.put(paidToUser, initialAmount + amount);

            expenseDAO.updatePeerToPeerExpenseLog(paidByUser, paidByUserLog);
        }
    }

    private void addGroupExpense(AddExpenseReq addExpenseReq) {

        ISplitAmount splitAmountStrategy = SplitAmountImplFactory.getSplitAmountImplementation(addExpenseReq.getSplitType());

        if (StringUtils.isEmpty(addExpenseReq.getGroupName())) {
            throw new IllegalArgumentException("groupNAme is must");
        }

        Group group = groupManager.getGroupById(addExpenseReq.getGroupName());

        User paidByUser = userManager.getUserById(addExpenseReq.getPaidBy());
        List<String> sharedAmongUserIds = addExpenseReq.getSharedAmong();
        List<User> sharedAmongUsers = userManager.getUsersByIds(sharedAmongUserIds);

        if (Objects.nonNull(paidByUser) && Objects.nonNull(group) && CollectionUtils.isNotEmpty(sharedAmongUsers) &&
                sharedAmongUsers.size() == sharedAmongUserIds.size()) {

            // add expense to list of expenses
            addExpenseToUserExpensesListForGroup(group, addExpenseReq);

            // split amount
            PostSplitAmountUserInfo splitAmountUserInfo = splitAmountStrategy.split(addExpenseReq.getAmount(),
                    addExpenseReq.getPaidBy(),
                    addExpenseReq.getSharedAmong(),
                    addExpenseReq.getAmountDistributionRatio());

            // update netbalance for users
            updateGroupExpenseBalance(splitAmountUserInfo, addExpenseReq);
        }
    }

    private void addExpenseToUserExpensesListForGroup(Group group, AddExpenseReq addExpenseReq) {
        Expense expense = new Expense(addExpenseReq.getPaidBy(), addExpenseReq.getAmount(), addExpenseReq.getExpenseType(),
                addExpenseReq.getSharedAmong());

        Map<String, List<Expense>> userExpensesMap = expenseDAO.getGroupWiseUserExpenseTracker().get(group.getName());
        List<Expense> userExpenses = userExpensesMap.get(addExpenseReq.getPaidBy());
        userExpenses.add(expense);

        userExpensesMap.put(addExpenseReq.getPaidBy(), userExpenses);
        expenseDAO.updateGroupWiseUserExpenseTracker(group.getName(), userExpensesMap);
    }

    private void updateGroupExpenseBalance(PostSplitAmountUserInfo splitAmountUserInfo, AddExpenseReq addExpenseReq) {

        // 1 -- update data for user who paid
        Map<String, Map<String, Double>> groupExpenseLog = expenseDAO.getGroupExpenseLog(addExpenseReq.getGroupName());
        Map<String, Double> paidByUserExpenseLog = groupExpenseLog.getOrDefault(addExpenseReq.getPaidBy(), new HashMap<>());

        for(String sharedUser: splitAmountUserInfo.getPostSplitAmountShare().keySet()) {

            Double amountToAdd = splitAmountUserInfo.getPostSplitAmountShare().get(sharedUser);
            Double initAmt = paidByUserExpenseLog.getOrDefault(sharedUser, 0d);

            paidByUserExpenseLog.put(sharedUser, initAmt + amountToAdd);
        }

        expenseDAO.updateGroupUserExpenseLog(addExpenseReq.getGroupName(), addExpenseReq.getPaidBy(), paidByUserExpenseLog);


        // 2 -- update data for whom were paid on behalf
        for(String sharedUser: splitAmountUserInfo.getPostSplitAmountShare().keySet()) {

            Map<String, Double> sharedUserExpenseLog = groupExpenseLog.getOrDefault(sharedUser, new HashMap<>());

            Double amountToAdd = -1 * splitAmountUserInfo.getPostSplitAmountShare().get(sharedUser);
            Double initAmt = sharedUserExpenseLog.getOrDefault(addExpenseReq.getPaidBy(), 0d);

            sharedUserExpenseLog.put(sharedUser, initAmt + amountToAdd);
            expenseDAO.updateGroupUserExpenseLog(addExpenseReq.getGroupName(), sharedUser, sharedUserExpenseLog);
        }
    }
}
