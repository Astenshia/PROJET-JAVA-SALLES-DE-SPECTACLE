package src.problems;

import src.persons.PersonsGroup;
import src.roomComponents.Room;

import java.util.List;

public class Problem extends AbstractProblem{

    public Problem(String name, List<PersonsGroup> r, int p, int k, int q, Room room, String folderName, int numeroReservation) {
        super(name,r,p,k,q,room,folderName,numeroReservation);
    }

    public Problem(Problem problem) {
        super(problem);
    }
}
