import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Groups {

    private final List<Group> groups;

    public Groups(final int nb, final int capacity) {
        groups = new ArrayList(nb);
        for (int i = 0; i < nb; i++) {
            groups.add(new Group(capacity));
        }
    }

    public Group getGroup(final int n) {
        return groups.get(n);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Group findFreeGroup() {
        for (Group group : groups) {
            if (!group.isFull()) {
                return group;
            }
        }
        return null;
    }

}