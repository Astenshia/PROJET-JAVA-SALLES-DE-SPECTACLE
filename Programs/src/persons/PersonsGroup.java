package src.persons;

import java.util.List;

public class PersonsGroup implements Comparable<PersonsGroup> {
    private List<Person> persons;
    private boolean seated;

    public PersonsGroup(List<Person> persons) {
        this.persons = persons;
        seated =false;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public int getNbPersons() {
        return persons.size();
    }

    public boolean isSeated(){
        return this.seated;
    }

    public void setSeated(boolean b){
        this.seated =b;
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
