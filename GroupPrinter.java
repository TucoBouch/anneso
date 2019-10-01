
public class GroupPrinter {

    public String print(Group group) {
        StringBuilder sb = new StringBuilder();
        sb.append("TABLE: ("+group.size()+") ");

        for (Person p : group.getMembers()) {
            sb.append(String.format(" %s, ", p.getName()));
        }

        return sb.toString();
    }
}