package src.problems;

import src.persons.PersonsGroup;
import src.roomComponents.Room;

import java.util.List;

public class Problem extends AbstractProblem{

    public Problem(String name, List<PersonsGroup> r, int p, int k, int q, Room room) {
        super(name,r,p,k,q,room);
    }

    public Problem(Problem problem) {
        super(problem);
    }
}
