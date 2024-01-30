package src.algorithms;

import src.persons.Person;
import src.persons.PersonsGroup;
import src.problems.AbstractProblem;
import src.problems.Solution;
import src.roomComponents.Row;
import src.roomComponents.RowGroup;

import java.util.ArrayList;
import java.util.Collections;

public class AlgoHeuristique1 extends AbstractAlgo {

    @Override
    public Solution execute(AbstractProblem problem) {
        Solution solution = new Solution();
        solution.setAlgoName(this.getClass().getSimpleName());
        solution.setProblem(problem);

        // tri décroissant des groupes de personnes selon leur taille avant de remplir la salle
        ArrayList<PersonsGroup> unplacedPersonsGroups = new ArrayList<>(problem.getReservations());
        Collections.sort(unplacedPersonsGroups);
        Collections.reverse(unplacedPersonsGroups);

        int filledRows = 0;
        int sumDistance = 0;
        int totalSeats = 0;
        int filledSeats = 0;

        boolean roomIsFull = false;
        int rowIndex = 0;
        int g = 0; // index du group
        // parcours des groupes de rangées tant qu'il reste des groupes de personnes à placer ou que la salle n'est pas remplie
        while (unplacedPersonsGroups.size() > 0 && !roomIsFull) {

            RowGroup rowGroup = problem.getRoom().getRowGroups().get(g);
            boolean groupIsFull = false;
            int r = 0; // index de la row
            // parcours des rangées tant qu'il reste des groupes de personnes à placer ou que le groupe de rangées n'est pas rempli
            while (unplacedPersonsGroups.size() > 0 && !groupIsFull) {

                Row row = rowGroup.getRows().get(r);
                boolean rowIsFull = false;
                boolean rowIsUsed = false;
                int s = 0; // index des seats
                // parcours des sièges tant qu'il reste des groupes de personnes à placer ou que la rangée n'est pas remplie
                while (unplacedPersonsGroups.size() > 0 && !rowIsFull) {

                    // le groupe à placer est toujours le premier de la liste de groupes de personnes à placer
                    // si le groupe à placer est de taille inférieure ou égale à l'espace restant sur la rangées,
                    // alors le placer dessus,
                    // sinon passer à la ligne suivante
                    if (unplacedPersonsGroups.get(0).getNbPersons() <= row.getCapacity() - s) {
                        // marquer la rangée actuelle comme engagée
                        rowIsUsed = true;

                        // placer chaque personne une à une
                        for (Person p : unplacedPersonsGroups.get(0).getPersons()) {
                            // la personne est associée au siège
                            row.getSeats().get(s).setPerson(p);
                            // le siège est associé à la personne
                            p.setSeat(row.getSeats().get(s));

                            // incrémenter le nombre de sièges remplis
                            filledSeats++;

                            s++;
                        }
                        // marquer les sièges comptant comme un espace vide séparant les spectateurs
                        for (int i = 0; i < problem.getPeopleDistance(); i++) {
                            if (s < row.getCapacity()) {
                                row.getSeats().get(s).setOutOfOrder(true);
                                s++;
                            }
                        }
                    }
                    s++;
                    rowIsFull = (s >= rowGroup.getNbRows());
                }
                // si la rangée est utilisée
                if (rowIsUsed) {
                    // incrémenter le nombre de rangées engagées
                    filledRows++;
                    // incrémenter le nombre de places total des rangées engagées
                    totalSeats += row.getCapacity();
                    // ajouter la distance à la scène à la somme des distances de la scène
                    sumDistance += row.getSceneDistance();

                    // ajouter une rangée vide entre deux rangées de spectateurs
                    r++;
                }
                r++;
                groupIsFull = (r >= rowGroup.getNbRows());
            }
            g++;
            roomIsFull = (g >= problem.getRoom().getNbRowGroups());
        }

        solution.setFilledRows(filledRows);
        solution.setFilledSeats(filledSeats);
        solution.setSumDistance(sumDistance);
        solution.setTotalSeats(totalSeats);
        solution.setUnplacedGroups(unplacedPersonsGroups);

        return solution;
    }
}
