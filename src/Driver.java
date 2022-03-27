import dao.ExpenseDAO;
import dao.GroupDao;
import dao.UserDAO;
import manager.ExpenseManager;
import manager.GroupManager;
import manager.UserManager;

public class Driver {

    public static void main(String[] args) {

        ExpenseDAO expenseDAO = new ExpenseDAO();
        UserDAO userDAO = new UserDAO();
        GroupDao groupDao = new GroupDao();

//        ISplitAmount equalSplitAmount = new EqualSplitAmountImpl();

        UserManager userManager = new UserManager(userDAO);
        GroupManager groupManager = new GroupManager(userManager, groupDao);
        ExpenseManager expenseManager = new ExpenseManager(expenseDAO, userManager, groupManager);




//        userManager.addUser(u1);
//        userManager.addUser(u2);

        System.out.println(userManager.getAllUsers());

//        userManager.addP2PExpense(n1, n2, 100d);



    }

}
