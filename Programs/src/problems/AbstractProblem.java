package src.problems;

import src.persons.PersonsGroup;
import src.roomComponents.Room;

import java.util.List;

public abstract class AbstractProblem {
    final private String name;
    private List<PersonsGroup> reservations;
    // contraint class ?
    private int rowDistance; // p
    private int peopleDistance; // q
    private int maxGroupSize; // k

    private Room room; //la salle de spectacle

    public AbstractProblem(String name, List<PersonsGroup> r, int p, int k, int q, Room room) {
        this.name = name;
        this.reservations = r;
        this.rowDistance = p;
        this.peopleDistance = q;
        this.maxGroupSize = k;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public List<PersonsGroup> getReservations() {
        return reservations;
    }

    public int getRowDistance() {
        return rowDistance;
    }

    public int getPeopleDistance() {
        return peopleDistance;
    }

    public int getMaxGroupSize() {
        return maxGroupSize;
    }
    public Room getRoom() {
        return room;
    }
    public int getNbReservations() {
        return reservations.size();
    }



}
