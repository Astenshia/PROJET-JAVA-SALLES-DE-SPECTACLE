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
    private String folderName;
    private int numeroReservation;

    public AbstractProblem(String name, List<PersonsGroup> r, int p, int k, int q, Room room, String folderName, int numeroReservation) {
        this.name = name;
        this.reservations = r;
        this.rowDistance = p;
        this.maxGroupSize = k;
        this.peopleDistance = q;
        this.room = room;
        this.folderName = folderName;
        this.numeroReservation = numeroReservation;
    }

    abstract public Problem copy();

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

    public String getFolderName(){ return this.folderName;}

    public int getNumeroReservation(){ return numeroReservation;}

}
