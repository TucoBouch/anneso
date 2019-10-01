import java.util.HashSet;
import java.util.Set;

public class Group {

    private final Set<Person> persons = new HashSet<>();

    private final int capacity;

    public Group(int capacity) {
        this.capacity = capacity;
    }

    public boolean isFull() {
        return persons.size() >= capacity;
    }

    public boolean isIn(final Person p) {
        return persons.contains(p);
    }

    public void add(final Person p) {
        persons.add(p);
    }

    public Set<Person> getMembers() {
        return persons;
    }

    public boolean hasCommon(final Group group) {
        for (Person person : persons) {
            if (group.isIn(person)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return persons.size();
    }
}