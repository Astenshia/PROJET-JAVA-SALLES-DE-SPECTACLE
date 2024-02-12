package src.algorithms;

import src.persons.Person;
import src.persons.PersonsGroup;
import src.problems.AbstractProblem;
import src.problems.Solution;
import src.roomComponents.Row;
import src.roomComponents.RowGroup;

import java.util.ArrayList;

public class AlgoHeuristique1 extends AbstractAlgo {

    @Override
    public Solution execute(AbstractProblem problem) {

        // déclaration des variables utilisée pour la construction d'une Solution
        int filledRows = 0;
        int sumDistance = 0;
        int totalSeats = 0;
        int filledSeats = 0;
        ArrayList<PersonsGroup> unplacedPersonsGroups;

        // Déclaration des variables utilisées au cours de l'exécution
        RowGroup rowGroup;
        Row row;
        boolean rowIsFull;
        boolean rowIsUsed;
        int g; // index du group
        int r; // index de la row
        int s; // index des seats
        int indexGroup; // index des groupes de personnes à placer

        g = 0;
        indexGroup = 0;
        // parcours des groupes de rangées tant qu'il reste des groupes de personnes à placer ou que la salle n'est pas remplie
        while (indexGroup < problem.getNbReservations() && g < problem.getRoom().getNbRowGroups()) {

            rowGroup = problem.getRoom().getRowGroups().get(g);
            r = 0;
            // parcours des rangées tant qu'il reste des groupes de personnes à placer ou que le groupe de rangées n'est pas rempli
            while (indexGroup < problem.getNbReservations() && r < rowGroup.getNbRows()) {

                row = rowGroup.getRows().get(r);
                rowIsFull = false;
                rowIsUsed = false;
                s = 0;
                // parcours des sièges tant qu'il reste des groupes de personnes à placer ou que la rangée n'est pas remplie
                while (indexGroup < problem.getNbReservations() && !rowIsFull) {
                    // si le groupe à placer est de taille inférieure ou égale à l'espace restant sur la rangée,
                    // alors le placer sur la rangée actuelle,
                    // sinon passer à la ligne suivante
                    if (problem.getReservations().get(indexGroup).getNbPersons() <= row.getCapacity() - s) {
                        // marquer la rangée actuelle comme engagée
                        rowIsUsed = true;

                        // placer chaque personne une à une
                        for (Person p : problem.getReservations().get(indexGroup).getPersons()) {
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

                        indexGroup++;
                    } else {
                        rowIsFull = true;
                    }
                }
                // si la rangée est utilisée
                if (rowIsUsed) {
                    // incrémenter le nombre de rangées engagées
                    filledRows++;
                    // incrémenter le nombre de places total des rangées engagées
                    totalSeats += row.getCapacity();
                    // ajouter la distance à la scène à la somme des distances de la scène
                    sumDistance += row.getSceneDistance();

                    // ajouter autant de rangées vides entre deux rangées de spectateurs que nécessaire
                    // (selon les contraintes du problème)
                    r += problem.getRowDistance();
                }
                r++;
            }
            g++;
        }
        // récupération des groupes non placés
        unplacedPersonsGroups = new ArrayList<>();
        for (int i = indexGroup; i < problem.getNbReservations(); i++) {
            unplacedPersonsGroups.add(problem.getReservations().get(i));
        }

        return new Solution(problem, this.getClass().getSimpleName(),
                filledRows, sumDistance, filledSeats, totalSeats, unplacedPersonsGroups);
    }
}
