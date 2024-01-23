package src.persons;

import src.roomComponents.Seat;

public class Person {
    private Seat seat;

    public Person() {
    }

    public void setSeat(Seat s) {
        this.seat = s;
    }

    public boolean hasSeat() {
        return seat != null;
    }
}
