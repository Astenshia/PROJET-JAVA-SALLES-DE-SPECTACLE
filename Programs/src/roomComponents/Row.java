package src.roomComponents;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private int sceneDistance;
    private RowGroup rowGroup;
    private List<Seat> seats; // pas forcément utile
    private int availableSeats;
    private boolean used;

    private int numRow;

    public Row(RowGroup rowGroup, List<Seat> s, int d, int numRow) {
        this.rowGroup = rowGroup;
        this.sceneDistance = d;
        this.seats = s;
        this.availableSeats = s.size();
        this.used = false;
        this.numRow = numRow;
    }

    /**
     * Creates a blank copy of a Row, as if it was empty again.
     * Hence, the Persons that could be associated to different Seats in the Row won't be associated anymore.
     * Also, all variables giving information about the Row's fullness will be reset.
     */
    public Row copy() {
        ArrayList<Seat> seatsList = new ArrayList<>();
        for (int i = 0; i < this.seats.size(); i++) {
            seatsList.add(new Seat());
        }
        return new Row(this.rowGroup, seatsList, this.getSceneDistance(), this.getNumRow());
    }

    public boolean enoughFor(int nbPersons, int p){
        return availableSeats >= nbPersons;
    }

    public void add(int nbPersons, int p) {
        availableSeats -= nbPersons + p;
        used = true;

    }

    public boolean isUsed() {
        return used;
    }

    public RowGroup getRowGroup() {
        return rowGroup;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int getSceneDistance() {
        return sceneDistance;
    }

    public int getCapacity() {
        return seats.size();
    }

    public int getNumRow() {
        return numRow;
    }

    public void setRowGroup(RowGroup rowGroup) {
        this.rowGroup = rowGroup;
    }

    @Override
    public String toString() {
        return "G" + this.getRowGroup().getNumGroup() + "-R" + this.getNumRow();
    }
}
