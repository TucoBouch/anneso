import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Compute {

    public static void main(String[] args) {

        int groupsNumber = 6;
        int personsNumber = 39;
        int groupCapacity = 7;

        // int groupsNumber = 3;
        // int personsNumber = 9;
        // int groupCapacity = 3;

        String names[] = { "Adamski Yoaan", "Sanches Bérangère", "Schneider Pierre", "Barbosa Laurie", "Job Margot",
                "Weyders Valentine", "Frem Hayat", "El Filali Camilia", "Smili Catherine", "Dubois-Julien Maeva",
                "Lemal Daniel", "Garbi Anne-Sohpie", "Malaj Cléa", "Loukili El Mehdi", "Ze Npoah Rose Christelle",
                "Escure Tatiana", "Pignatelli Gina", "Rock Mélissa", "Sauter Léa", "Gillet Sabrina",
                "Habibova Banovsha", "Stelitano Yoann", "Casanova Samuel", "Csehi Mégane", "Skrijelj Sabrina",
                "Beck Nicolas", "Lett Eva", "Ziri Nabila", "Gravejat Mélanie", "Laria Claire", "Taube Fabienne",
                "Denis Justine", "Mascarell Mélanie", "Delaleux Héléna", "Ndombelé Aicha", "Mangin Alice",
                "Bret Brandon", "Garattoni Charlène", "Scholtus Tiphaine" };

        final List<Person> personsPool = new ArrayList<>(personsNumber);
        final Map<Person, Group> personsInContact = new HashMap<>();

        for (int i = 0; i < personsNumber; i++) {
            Person p = new Person(names[i]);
            personsPool.add(p);
            personsInContact.put(p, new Group(personsNumber));
        }

        List<Groups> turns = new ArrayList<>();
        GroupPrinter printer = new GroupPrinter();

        int turn = 1;
        int completed = 0;
        int lastCompleted = -1;
        while (((completed = everyBodyContact(personsInContact)) == 0 || completed != lastCompleted) && turn < 8) {
            lastCompleted = completed;
            System.out.println(String.format("** ROUND %s", turn));

            Groups currentTurn = new Groups(groupsNumber, groupCapacity);
            Group personsRemaining = new Group(personsNumber);

            int randomStart = (int) (Math.random() * personsPool.size());
            for (int p = 0; p<personsPool.size(); p++) {

                Person person = personsPool.get((randomStart + p) % personsPool.size());

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
                if (!verifyPerson(currentTurn, person)) {
                    System.out.println("Failed for " + person);
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