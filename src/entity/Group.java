package entity;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private final String name;
    private final String description;
    private final List<String> participants;

    public Group(String name, String description, List<String> participants) {
        this.name = name;
        this.description = description;
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", participants=" + participants +
                '}';
    }
}
