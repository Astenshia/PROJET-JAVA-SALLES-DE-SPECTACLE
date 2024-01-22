package src.problems;

import src.persons.PersonsGroup;
import src.roomComponents.Room;

import java.util.List;

public class Problem extends AbstractProblem{

    public Problem(String name, List<PersonsGroup> r, int p, int q, int k, Room room) {
        super(name,r,p,q,k,room);
    }
}
