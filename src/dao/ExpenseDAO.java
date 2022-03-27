package dao;

import entity.Expense;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseDAO {

    // k - userId, v.k1 - userId, v.v1 - netbalance
    private final Map<String, Map<String, Double>> userExpenseTracker; // used for P2P expense tracker
//
//    {
//        "vimal": {
//            "rakesh" : 150,
//            "arpit" :  150
//        },
//
//        "rakesh" : {
//            "vimal" :  -100
//        },
//
//        "arpit" :{
//            "vimal" : 50
//        }
//    }



    // k - groupId, v.k1 - userId, v.v1.k1 - userId, v.v1.k2 - netbalance
    private final Map<String, Map<String, Map<String, Double>>> groupExpenseTracker; // used for expense tracking within group

    // k - groupId, v.k1 - userId , v.v1 - list of expenses done by userId in group
    private final Map<String, Map<String, List<Expense>>> groupWiseUserExpenseTracker; // used to capture list of expenses done by the users in the group


    public ExpenseDAO() {
        this.userExpenseTracker = new HashMap<>();
        this.groupExpenseTracker = new HashMap<>();
        this.groupWiseUserExpenseTracker = new HashMap<>();
    }

    public Map<String, Map<String, Double>> getUserExpenseTracker() {
        return userExpenseTracker;
    }

    public Map<String, Map<String, Map<String, Double>>> getGroupExpenseTracker() {
        return groupExpenseTracker;
    }

    public Map<String, Map<String, List<Expense>>> getGroupWiseUserExpenseTracker() {
        return groupWiseUserExpenseTracker;
    }

    public void printUserExpense() {
        System.out.println("-----------------------------");
        userExpenseTracker.forEach( (k,v) -> {

            System.out.println(k + " -> " + v.toString());

        });
        System.out.println("-----------------------------");
    }

    public void printGroupExpenseTracker() {
        System.out.println("-----------------------------");
        groupExpenseTracker.forEach( (k,v) -> {

            System.out.println(k + " : ");
            v.forEach((k1,v1) -> {
                System.out.println(k1 + " : " + v1.toString());
            });

        });
        System.out.println("-----------------------------");
    }

    public void printGroupWiseUserExpenseTracker() {
        System.out.println("-----------------------------");
        groupWiseUserExpenseTracker.forEach( (k,v) -> {

            System.out.println(k + " : ");
            v.forEach((k1,v1) -> {
                System.out.println(k1 + " : " + v1.toString());
            });

        });
        System.out.println("-----------------------------");
    }

    public Map<String, Double> getPeerToPeerExpenseLog(String userId) {
        return this.userExpenseTracker.getOrDefault(userId, new HashMap<>());
    }

    public Map<String, Map<String, Double>> getGroupExpenseLog(String group) {
        return this.groupExpenseTracker.getOrDefault(group, new HashMap<>());
    }
    public void updatePeerToPeerExpenseLog(String userId, Map<String, Double> userLog) {
        this.userExpenseTracker.put(userId, userLog);
    }

    public void updateGroupWiseUserExpenseTracker(String name, Map<String, List<Expense>> userExpensesMap) {
        this.groupWiseUserExpenseTracker.put(name, userExpensesMap);
    }

    public void updateGroupUserExpenseLog(String groupName, String userId, Map<String, Double> paidByUserExpenseLog) {
        this.groupExpenseTracker.get(groupName).put(userId, paidByUserExpenseLog);
    }
}
