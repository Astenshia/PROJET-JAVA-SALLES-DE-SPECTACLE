package src.roomComponents;

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
