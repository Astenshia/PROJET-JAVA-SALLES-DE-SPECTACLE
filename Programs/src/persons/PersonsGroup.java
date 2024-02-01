package src.persons;

import java.util.ArrayList;
import java.util.List;

public class PersonsGroup implements Comparable<PersonsGroup> {
    private List<Person> persons;
    private boolean seated;

    private int numGroup;

    public PersonsGroup(int nbPersons, int numGroup) {
        this.persons = new ArrayList<>();
        for (int i = 0; i < nbPersons; i++) {
            this.persons.add(new Person(this));
        }
        this.numGroup = numGroup;
        seated = false;
    }

    /**
     * Creates a blank copy of a PersonsGroup.
     * Hence, the Seats that could be associated to different Persons in the PersonsGroup won't be associated anymore.
     *
     * @param personsGroup the PersonsGroup to copy
     */
    public PersonsGroup(PersonsGroup personsGroup) {
        this.persons = new ArrayList<>();
        for (int i = 0; i < personsGroup.persons.size(); i++) {
            this.persons.add(new Person(this));
        }
        seated = false;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public int getNbPersons() {
        return persons.size();
    }

    public boolean isSeated() {
        return this.seated;
    }

    public void setSeated(boolean b) {
        this.seated = b;
    }

    public int getNumGroup() {
        return numGroup;
    }

    @Override
    public String toString() {
        return String.valueOf(persons.size());
    }

    @Override
    public int compareTo(PersonsGroup o) {
        return getNbPersons() - o.getNbPersons();
    }
}
