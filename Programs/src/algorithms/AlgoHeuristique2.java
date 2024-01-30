package src.algorithms;

import src.persons.PersonsGroup;
import src.problems.AbstractProblem;
import src.problems.Solution;
import src.roomComponents.Row;
import src.roomComponents.RowGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgoHeuristique2 extends AbstractAlgo {

    @Override
    public Solution execute(AbstractProblem problem) {
        Solution solution = new Solution();

        //TODO: trier les groupes par ordre décroissant avant d'insérer


        ArrayList<PersonsGroup> unplacedPersonsGroups = new ArrayList<>(problem.getReservations());
        Collections.sort(unplacedPersonsGroups);
        Collections.reverse(unplacedPersonsGroups);
        List<RowGroup> rowGroups = problem.getRoom().getRowGroups();
        int p = problem.getPeopleDistance();

        for (PersonsGroup personsGroup : unplacedPersonsGroups){
            int i=0;
            while (!personsGroup.isSeated() &&  i < rowGroups.size()){
                    List<Row> rows = rowGroups.get(i).getRows();
                    int j =0;
                    while (!personsGroup.isSeated() && j < rows.size()){
                        if (rows.get(j).enoughFor(personsGroup.getNbPersons(), p) ){
                            rows.get(j).add(personsGroup.getNbPersons(), p);
                            personsGroup.setSeated(true);
                        }
                }
            }



        }

        return solution;
    }
}
