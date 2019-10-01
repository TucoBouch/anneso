import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Compute {

    public static void main(String[] args) {

        int groupsNumber = 7;
        int personsNumber = 42;
        int groupCapacity = 6;

        // int groupsNumber = 3;
        // int personsNumber = 9;
        // int groupCapacity = 3;

        final List<Person> personsPool = new ArrayList<>(personsNumber);
        final Map<Person, Group> personsInContact = new HashMap<>();

        for (int i = 0; i < personsNumber; i++) {
            Person p = new Person("Person " + (i + 1));
            personsPool.add(p);
            personsInContact.put(p, new Group(personsNumber));
        }

        List<Groups> turns = new ArrayList<>();
        GroupPrinter printer = new GroupPrinter();

        int turn = 1;
        int completed = 0;
        int lastCompleted = -1;
        while (((completed = everyBodyContact(personsInContact)) == 0 || completed != lastCompleted) && turn < 20) {
            lastCompleted = completed;
            System.out.println(String.format("******* TURN %s ******%n", turn));

            Groups currentTurn = new Groups(groupsNumber, groupCapacity);
            Group personsRemaining = new Group(personsNumber);
            for (Person person : personsPool) {

                Group personInContact = personsInContact.get(person);
                Group foundedGroup = findGroupFor(currentTurn, person, personInContact);
                if (foundedGroup != null) {
                    for (Person inGroup : foundedGroup.getMembers()) {
                        personInContact.add(inGroup);
                        personsInContact.get(inGroup).add(person);
                    }
                    foundedGroup.add(person);
                } else {
                    personsRemaining.add(person);
                }
            }

            for (Person remaining : personsRemaining.getMembers()) {
                Group freeGroup = currentTurn.findFreeGroup();
                Group personInContact = personsInContact.get(remaining);

                for (Person inGroup : freeGroup.getMembers()) {
                    personInContact.add(inGroup);
                    personsInContact.get(inGroup).add(remaining);
                }
                freeGroup.add(remaining);
            }

            for (Group group : currentTurn.getGroups()) {
                System.out.println(printer.print(group));
            }

            Set<Person> persons = personsInContact.keySet();
            for (Person person : persons) {
                if (!verifyPerson(currentTurn, person))
                {
                    System.out.println("Failed for "+person);
                }
                
            }

            turn++;
        }

        Set<Person> persons = personsInContact.keySet();
        System.out.println("persons " + persons.size());
        for (Person person : persons) {
            Group contacts = personsInContact.get(person);
            System.out.println(String.format("%s = %s", person.getName(), contacts.size()));
        }
    }

    public static boolean verifyPerson(Groups groups, Person p) {
        int nb = 0;
        for (Group group : groups.getGroups()) {
            if (group.isIn(p)) {
                nb++;
            }
        }
        return nb == 1;
    }

    public static int everyBodyContact(Map<Person, Group> personsInContact) {
        Set<Person> persons = personsInContact.keySet();

        int nb = 0;
        for (Person person : persons) {
            Group contacts = personsInContact.get(person);

            if (contacts.size() >= persons.size() - 1) {
                nb++;
            }
        }
        return nb;
    }

    public static Group findGroupFor(final Groups groups, final Person p, Group personInContact) {
        for (Group group : groups.getGroups()) {
            if (group.isFull()) {
                continue;
            }
            if (group.isIn(p)) {
                continue;
            }

            if (!group.hasCommon(personInContact)) {
                return group;
            }

        }
        return null;
    }
}