package src.roomComponents;

import java.util.List;

public class Row {
    private int sceneDistance;
    private List<Seat> seats;

    public Row(List<Seat> s, int d) {
        this.sceneDistance = d;
        this.seats = s;
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
