package src.problems;

import src.persons.PersonsGroup;
import src.roomComponents.Room;

import java.util.List;

public abstract class AbstractProblem {
    private String name;
    private List<PersonsGroup> reservations;
    // contraint class ?
    private int rowDistance; // p
    private int peopleDistance; // q
    private int maxGroupSize; // k

    private Room room; //la salle de spectacle

    public AbstractProblem(String name, List<PersonsGroup> r, int p, int q, int k, Room room) {
        this.name = name;
        this.reservations = r;
        this.rowDistance = p;
        this.peopleDistance = q;
        this.maxGroupSize = k;
        this.room = room;
    }

    public int getNbReservations() {
        return reservations.size();
    }
}
