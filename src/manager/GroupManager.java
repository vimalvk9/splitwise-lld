package manager;

import dao.GroupDao;
import entity.Group;
import entity.User;
import utils.CollectionUtils;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class GroupManager {

    private final UserManager userManager;
    private final GroupDao groupDao;

    public GroupManager(UserManager userManager, GroupDao groupDao) {
        this.userManager = userManager;
        this.groupDao = groupDao;
    }

    public void addGroup(String groupName, String groupDesc, List<String> participants) {
        if (StringUtils.isNotEmpty(groupName) && StringUtils.isNotEmpty(groupDesc) && CollectionUtils.isNotEmpty(participants)) {
            Group group = new Group(groupName, groupDesc, participants);
            groupDao.addGroup(group);
        }
    }

    public void addUsersToGroup(List<String> ids, String groupName) {
        if (StringUtils.isNotEmpty(groupName)) {

            Group group = groupDao.getById(groupName);

            if (Objects.nonNull(group) && CollectionUtils.isNotEmpty(ids)) {
                groupDao.addUsersToGroup(ids, groupName);
            }
        }
    }

    public void removeUsersFromGroup(List<String> userIds, String groupName) {
        if (StringUtils.isNotEmpty(groupName)) {

            Group group = groupDao.getById(groupName);

            if (Objects.nonNull(group) && CollectionUtils.isNotEmpty(userIds)) {

                List<User> users = userManager.getUsersByIds(userIds);
                List<String> validUserIds = Optional.ofNullable(users).orElseGet(ArrayList::new)
                        .stream()
                        .map(User::getName)
                        .collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(validUserIds)) {
                    groupDao.addUsersToGroup(userIds, groupName);
                }
            }
        }
    }

    public Group getGroupById(String groupId) {
        return groupDao.getById(groupId);
    }

}
