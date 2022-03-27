package manager;

import dao.UserDAO;
import entity.User;
import utils.StringUtils;

import java.util.List;
import java.util.Set;

public class UserManager {

    private final UserDAO userDAO;

    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createUser(String name, String email) {
        if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(email)) {
            User user = new User(name, email);
            userDAO.createUser(user);
        }
    }

    public void removeUser(String name) {
        userDAO.removeUserById(name);
    }

    public List<User> getUsersByIds(List<String> userIds) {
        return userDAO.getUsersById(userIds);
    }

    public User getUserById(String userId) {
        return userDAO.getUserById(userId);
    }

    public Set<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
