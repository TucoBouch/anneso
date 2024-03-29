
public class Person {

    private final String name;

    public Person(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(final Person p) {
        return name.equals(p.getName());
    }
}