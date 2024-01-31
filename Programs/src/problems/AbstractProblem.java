package src.problems;

import src.persons.PersonsGroup;
import src.roomComponents.Room;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProblem {
    final private String name;
    private List<PersonsGroup> reservations;
    private int rowDistance; // p
    private int maxGroupSize; // k
    private int peopleDistance; // q

    private Room room; //la salle de spectacle

    public AbstractProblem(String name, List<PersonsGroup> r, int p, int k, int q, Room room) {
        this.name = name;
        this.reservations = r;
        this.rowDistance = p;
        this.maxGroupSize = k;
        this.peopleDistance = q;
        this.room = room;
    }

    /**
     * Creates a deep copy of an AbstractProblem.
     * Hence, all associations between Persons and Seats are reset.
     * @param abstractProblem the AbstractProblem to copy
     */
    public AbstractProblem(AbstractProblem abstractProblem) {
        this.name = abstractProblem.name;
        this.reservations = new ArrayList<>();
        for (PersonsGroup personsGroup : abstractProblem.reservations) {
            this.reservations.add(new PersonsGroup(personsGroup));
        }

        this.rowDistance = abstractProblem.rowDistance;
        this.maxGroupSize = abstractProblem.maxGroupSize;
        this.peopleDistance = abstractProblem.peopleDistance;
        this.room = new Room(abstractProblem.room);
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

    /**
     * Resets the problem by making a blank copy of it.
     */
    public static Problem resetProblem(Problem problem) {
        return new Problem(problem);
    }

}
