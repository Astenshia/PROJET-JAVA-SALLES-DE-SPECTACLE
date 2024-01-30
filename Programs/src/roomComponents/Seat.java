package src.roomComponents;

import src.persons.Person;

public class Seat {
    private boolean outOfOrder;
    private boolean empty;
    private Person person;

    public Seat() {
        outOfOrder = false;
        empty = true;
    }

    public boolean isOutOfOrder() {
        return outOfOrder;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setOutOfOrder(boolean outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

    public void setPerson(Person p) {
        this.person = p;
        this.empty = false;
    }

    @Override
    public String toString() {
        if (empty) {
            return "0";
        } else if (outOfOrder) {
            return "2";
        } else {
            return "1";
        }
    }
}
