package src.algorithms;

import src.persons.PersonsGroup;
import src.problems.AbstractProblem;
import src.problems.Solution;

import java.util.ArrayList;

public class AlgoHeuristique1 extends AbstractAlgo {

    @Override
    public Solution execute(AbstractProblem problem) {
        Solution solution = new Solution();

        //TODO: trier les groupes par ordre décroissant avant d'insérer
        ArrayList<PersonsGroup> unplacedPersonsGroups = new ArrayList<>(problem.getReservations());

        boolean roomIsFulled = false;
        int rowIndex = 0;
        while (unplacedPersonsGroups.size() > 0 || !roomIsFulled) {

        }

        return solution;
    }
}
