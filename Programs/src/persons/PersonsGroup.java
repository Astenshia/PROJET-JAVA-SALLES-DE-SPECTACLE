package src.persons;

import java.util.List;

public class PersonsGroup implements Comparable<PersonsGroup> {
    private List<Person> persons;

    public PersonsGroup(List<Person> persons) {
        this.persons = persons;
    }

    public int getNbPersons() {
        return persons.size();
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
