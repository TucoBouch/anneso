
public class GroupPrinter {

    public String print(Group group) {
        StringBuilder sb = new StringBuilder();
        sb.append("**** GROUPE ****\n");

        for (Person p : group.getMembers()) {
            sb.append(String.format(" %s%n", p.getName()));
        }

        return sb.toString();
    }
}