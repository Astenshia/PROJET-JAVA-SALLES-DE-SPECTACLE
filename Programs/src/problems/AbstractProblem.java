package src.problems;

import src.persons.PersonsGroup;

import java.util.List;

public abstract class AbstractProblem {
    private String name;
    private List<PersonsGroup> reservations;
    // contraint class ?
    private int rowDistance; // p
    private int peopleDistance; // q
    private int maxGroupSize; // k

    public AbstractProblem(String name, List<PersonsGroup> r, int p, int q, int k) {
        this.name = name;
        this.reservations = r;
        this.rowDistance = p;
        this.peopleDistance = q;
        this.maxGroupSize = k;
    }

    public int getNbReservations() {
        return reservations.size();
    }
}
