package src.roomComponents;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private int sceneDistance;
    private List<Seat> seats; // pas forcément utile
    private int availableSeats;
    private boolean used;

    public Row(List<Seat> s, int d) {
        this.sceneDistance = d;
        this.seats = s;
        this.availableSeats = s.size();
        this.used = false;
    }

    /**
     * Creates a blank copy of a Row, as if it was empty again.
     * Hence, the Persons that could be associated to different Seats in the Row won't be associated anymore.
     * Also, all variables giving information about the Row's fullness will be reset.
     * @param row the Row to copy
     */
    public Row(Row row) {
        this.sceneDistance = row.sceneDistance;
        this.seats = new ArrayList<>();

        for (int i = 0; i < row.seats.size(); i++) {
            this.seats.add(new Seat());
        }
        this.availableSeats = row.seats.size();
        this.used = false;
    }

    public boolean enoughFor(int nbPersons, int p){
        return availableSeats >= nbPersons;
    }

    public void add(int nbPersons, int p){
        availableSeats -= nbPersons+p;
        used = true;

    }

    public boolean isUsed(){
        return used;
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

}
