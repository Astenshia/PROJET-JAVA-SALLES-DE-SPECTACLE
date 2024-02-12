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

        ArrayList<PersonsGroup> unplacedPersonsGroups = new ArrayList<>(problem.getReservations());
        Collections.sort(unplacedPersonsGroups);
        Collections.reverse(unplacedPersonsGroups);
        List<RowGroup> rowGroups = problem.getRoom().getRowGroups();
        int p = problem.getPeopleDistance();

        int filledSeats = 0;
        int sumDistance = 0;
        int filledRows = 0;
        int totalSeats = 0;

        for (PersonsGroup personsGroup : unplacedPersonsGroups) {
            int i = 0;
            while (!personsGroup.isSeated() && i < rowGroups.size()) {
                List<Row> rows = rowGroups.get(i).getRows();
                int j = 0;
                boolean securityRow = false;
                while (!personsGroup.isSeated() && j < rows.size()) {
                    if (j > 0) {
                        securityRow = rows.get(j - 1).isUsed();
                    }

                    if (j < rows.size()) {
                        securityRow = securityRow || rows.get(j + 1).isUsed();
                    }


                    if (!securityRow) {
                        if (rows.get(j).enoughFor(personsGroup.getNbPersons())) {
                            rows.get(j).addPersonsGroup(personsGroup, p);
                            filledSeats += personsGroup.getNbPersons();

                            if (!rows.get(j).isUsed()) {
                                filledRows++;
                                sumDistance += rows.get(j).getSceneDistance();
                                totalSeats += rows.get(j).getCapacity();
                            }

                            personsGroup.setSeated(true);
                        }
                    }
                    j++;
                }
                i++;
            }


        }

        return new Solution(problem, this.getClass().getSimpleName(),
                filledRows, sumDistance, filledSeats, totalSeats, unplacedPersonsGroups);
    }
}
