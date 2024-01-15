package persons;

import roomComponents.Seat;

public class Person {
    private Seat seat;

    public Person(Seat s){
        seat=s;
    }
    public void setSeat(Seat s){
        this.seat = s;
    }

    public boolean hasSeat(){
        return seat != null;
    }
}
