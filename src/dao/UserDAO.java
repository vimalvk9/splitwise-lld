package dao;

import entity.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserDAO {

    private final Map<String, User> userMap;
    private final Map<String, List<String>> userGroupsMap;

    public UserDAO() {
        this.userGroupsMap = new HashMap<>();
        this.userMap = new HashMap<>();
    }

    public User getUserById(String id) {
        return userMap.getOrDefault(id, null);
    }

    public List<User> getUsersById(List<String> ids) {
        return ids.stream()
                .filter(userMap::containsKey)
                .map(userMap::get)
                .collect(Collectors.toList());
    }

    public Set<User> getAllUsers() {
        return new HashSet<>(userMap.values());
    }

    public void createUser(User user) {
        userMap.put(user.getEmail(), user);
    }

    public void removeUserById(String name) {
        userMap.remove(name);
    }

}
