package src.problems;

import src.persons.PersonsGroup;
import src.roomComponents.Room;

import java.util.ArrayList;
import java.util.List;

public class Problem extends AbstractProblem {

    public Problem(String name, List<PersonsGroup> r, int p, int k, int q, Room room, String folderName, int numeroReservation) {
        super(name, r, p, k, q, room, folderName, numeroReservation);
    }


    /**
     * Creates a blank copy of an AbstractProblem.
     * Hence, all associations between Persons and Seats are reset.
     */
    public Problem copy() {
        ArrayList<PersonsGroup> reservations = new ArrayList<>();
        for (PersonsGroup personsGroup : this.getReservations()) {
            reservations.add(personsGroup.copy());
        }
        return new Problem(this.getName(), reservations, this.getRowDistance(), this.getMaxGroupSize(),
                this.getPeopleDistance(), this.getRoom().copy(), this.getFolderName(), this.getNumeroReservation());
    }
}
