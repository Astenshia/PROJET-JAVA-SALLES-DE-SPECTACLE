package src.roomComponents;

import java.util.List;

public class Row {
    private int sceneDistance;
    private List<Seat> seats; // pas forcément utile
    private int availableSpace;

    public Row(List<Seat> s, int d) {
        this.sceneDistance = d;
        this.seats = s;
        this.availableSpace = s.size();
    }

    public boolean enoughFor(int nbPersons, int p){
        return availableSpace >= nbPersons;
    }

    public void add(int nbPersons, int p){
        availableSpace -= nbPersons+p;

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
