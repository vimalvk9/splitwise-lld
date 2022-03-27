package dao;

import entity.Group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroupDao {

    private final Map<String, Group> groupMap;

    public GroupDao() {
        this.groupMap = new HashMap<>();
    }

    public void addGroup(Group group) {
        groupMap.put(group.getName(), group);
    }

    public void addUsersToGroup(List<String> ids, String groupName) {
        Group group = groupMap.getOrDefault(groupName, null);
        if (Objects.nonNull(group)) {
            group.getParticipants().addAll(ids);
            groupMap.put(groupName, group);
        }
    }

    public Group getById(String groupName) {
        return groupMap.getOrDefault(groupName, null);
    }
}
