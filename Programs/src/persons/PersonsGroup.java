package src.persons;

import java.util.List;

public class PersonsGroup {
    private List<Person> persons;

    public PersonsGroup(List<Person> persons) {
        this.persons = persons;
    }

    public int getNbPersons() {
        return persons.size();
    }

}
