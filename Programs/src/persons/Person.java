package src.persons;

import src.roomComponents.Seat;

public class Person {
    private Seat seat;
    private PersonsGroup personsGroup;

    public Person(PersonsGroup personsGroup) {
        this.personsGroup = personsGroup;
    }

    public void setSeat(Seat s) {
        this.seat = s;
    }

    public boolean hasSeat() {
        return seat != null;
    }

    public PersonsGroup getPersonsGroup() {
        return personsGroup;
    }
}
