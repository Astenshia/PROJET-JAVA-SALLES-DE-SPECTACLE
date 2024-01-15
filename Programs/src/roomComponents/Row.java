package roomComponents;

import java.util.List;

public class Row {
    private int sceneDistance;
    private List<Seat> seats;

    public Row(List<Seat> s, int d){
        this.sceneDistance = d;
        this.seats =s;
    }

    public int getCapacity(){
        return seats.size();
    }
    
}
