package src.algorithms;

import org.javatuples.Pair;
import src.persons.PersonsGroup;
import src.problems.AbstractProblem;
import src.problems.Solution;
import src.problems.TemporarySolution;
import src.roomComponents.Row;
import src.roomComponents.RowGroup;

import java.util.ArrayList;
import java.util.Collections;

public class AlgoEnumTotaleTauxRemplissage extends AbstractAlgo {

    /**
     * La meilleure solution est déterminée en fonction du nombre de rangées utilisées.
     *
     * @param problem
     * @return
     */
    @Override
    public Solution execute(AbstractProblem problem) {
        // calcul de toutes les répartitions possibles des rangées
        ArrayList<ArrayList<Pair<Integer, Integer>>> rowsRepartition = getAllRowsRepartitions((ArrayList<RowGroup>) problem.getRoom().getRowGroups(), problem.getRowDistance());

        // convertion des réservations de Groupes de Personnes vers une liste des tailles de chaque groupe de personne
        ArrayList<Integer> reservations;
        reservations = new ArrayList<>();
        for (PersonsGroup personsGroup : problem.getReservations()) {
            reservations.add(personsGroup.getNbPersons());
        }

        // déclaration des variables qui seront utilisées dans
        ArrayList<Integer> rowsOriginalCapacity;
        ArrayList<Integer> rowsDistances;
        Row row;
        int indexBestRowRepartition = 0;

        // variables utilisées pour garder en mémoire la meilleure solution et la solution temporaire (actuelle)
        Pair<ArrayList<Integer>, TemporarySolution> bestSolution;
        Pair<ArrayList<Integer>, TemporarySolution> tmpSolution;

        // initialisation d'une "fausse" meilleure solution, qui ne place par défaut aucun groupe
        bestSolution = new Pair<>(
                new ArrayList<>(Collections.nCopies(reservations.size(), -1)),
                new TemporarySolution(0, 0, 0, 0, reservations.size())
        );

        // pour chaque répartition de rangées possible, on trouve une solution, puis on la compare à la meilleure
        // solution pour savoir si la nouvelle est mieux ou non
        for (int i = 0; i < rowsRepartition.size(); i++) {
            // on recopie la capacité et la distance à la scène de chaque rangée dans la répartition actuelle
            // ces listes seront passées au worker récursif
            rowsOriginalCapacity = new ArrayList<>();
            rowsDistances = new ArrayList<>();
            for (Pair<Integer, Integer> pair : rowsRepartition.get(i)) {
                // on récupère la rangée à l'aide du numéro de groupe de rangées et du numéro de rangée
                row = problem.getRoom().getRow(pair);
                rowsOriginalCapacity.add(row.getCapacity());
                rowsDistances.add(row.getSceneDistance());
            }

            // fonction récursive trouvant la meilleure solution parmis cette répartition de rangées
            tmpSolution = findBestSolutionInRowRepartition(
                    problem.getPeopleDistance(),
                    rowsOriginalCapacity,
                    rowsDistances,
                    reservations
            );

            // si la solution actuelle est meilleure, alors on la sélectionne comme étant la meilleure
            // la solution est meilleure s'il y a plus de groupes placés,
            // ou si pour un même nombre de de groupes sont placés mais que le taux de remplissage est meilleur
            if (tmpSolution.getValue1().getTotalUnplacedGroups() < bestSolution.getValue1().getTotalUnplacedGroups()) {
                bestSolution = tmpSolution;
                indexBestRowRepartition = i;
            } else if (tmpSolution.getValue1().getTotalUnplacedGroups() == bestSolution.getValue1().getTotalUnplacedGroups()
                    && tmpSolution.getValue1().getFillingRate() > bestSolution.getValue1().getFillingRate()) {
                bestSolution = tmpSolution;
                indexBestRowRepartition = i;
            }
        }

        // transformation de la solution temporaire en véritable solution

        ArrayList<PersonsGroup> unplacedPersonsGroup = new ArrayList<>();

        Pair<Integer, Integer> rowAsIntegerPair;
        // pour chaque groupe à placer,
        for (int i = 0; i < reservations.size(); i++) {
            // s'il a été placé alors le placer sur la bonne rangée,
            // sinon l'ajouter aux groupes non placés
            if (bestSolution.getValue0().get(i) >= 0) {
                rowAsIntegerPair = rowsRepartition.get(indexBestRowRepartition).get(bestSolution.getValue0().get(i));
                problem.getRoom().getRow(rowAsIntegerPair).addPersonsGroup(problem.getReservations().get(i), problem.getPeopleDistance());
            } else {
                unplacedPersonsGroup.add(problem.getReservations().get(i));
            }
        }

        return new Solution(
                problem,
                this.getName(),
                bestSolution.getValue1().getFilledRows(),
                bestSolution.getValue1().getSumDistance(),
                bestSolution.getValue1().getFilledSeats(),
                bestSolution.getValue1().getTotalSeats(),
                unplacedPersonsGroup
        );
    }

    /**
     * Calcule toutes les répartitions possible des rangées dans une liste de groupes de rangées.
     * Fonction appelant son worker récursif getAllRowsRepartitionsWorker().
     * <p>
     * Exemple, pour les groupes G1 et G2 ayant chacun deux rangées : G1:[r1,r2], G2:[r1,r2],
     * et pour une distance minimale de 1 entre les rangées, on peut obtenir les répartitions possibles suivantes:
     * <pre>
     *     [G1:r1]
     *     [G1:r1, G2:r1]
     *     [G1:r1, G2:r2]
     *     [G1:r2]
     *     [G1:r2, G2:r1]
     *     [G1:r2, G2:r2]
     *     ...
     * </pre>
     * </p>
     *
     * @param rowGroups   liste des groupes de rangées à traiter
     * @param rowDistance distance minimum entre deux rangées d'un même groupe de rangées
     * @return la liste des répartitions possibles. Chaque rangée est identifiée par l'index de son groupe de rangées et par son index dans ce groupe.
     */
    public ArrayList<ArrayList<Pair<Integer, Integer>>> getAllRowsRepartitions(ArrayList<RowGroup> rowGroups, int rowDistance) {
        ArrayList<ArrayList<Pair<Integer, Integer>>> rowRepartitions = new ArrayList<>();

        // utilisation du Worker récursif pour calculer les répartitions possibles
        getAllRowsRepartitionsWorker(rowGroups, rowDistance, rowRepartitions, new ArrayList<>(), 0, 0);

        return rowRepartitions;
    }

    /**
     * Worker récursif calculant la liste des répartitions possibles de rangées dans un groupe de rangées.
     * <p>
     * Voir  getAllRowsRepartitions().
     * </p>
     * <p>
     * Algorithme récursif sur la base de "sélectionner une rangée ou non".
     * On parcourt chaque groupe de rangées et chaque rangée à l'intérieur. A chaque étape, on effectue un coup
     * gauche et un coup droit. Au coup gauche, on prend la rangée pour l'ajouter à une possibilité, et au coup droit
     * on passe juste cette rangée (soit ne pas la prendre).
     * On incrémente les index de parcours des groupes de rangées et des rangées en fonction des règles données.
     * Par exemple, pour le coup gauche, on incrémente en fonction du nombre de rangées minimales entre deux rangées
     * sélectionnées.
     * </p>
     *
     * @param rowGroups   la liste des groupes de rangées à traiter
     * @param rowDistance distance minimum entre deux rangées d'un même groupe de rangées
     * @param L           la liste des répartitions possibles. Chaque rangée est identifiée par l'index de son groupe de rangées et par son index dans ce groupe.
     * @param T           une liste de rangées, qui représente une répartition possible. Chaque rangée est identifiée par l'index de son groupe de rangées et par son index dans ce groupe.
     * @param g           index de parcours du groupe de rangées
     * @param r           index de parcours d'une rangée
     */
    private void getAllRowsRepartitionsWorker(ArrayList<RowGroup> rowGroups, int rowDistance,
                                              ArrayList<ArrayList<Pair<Integer, Integer>>> L, ArrayList<Pair<Integer, Integer>> T, int g, int r) {
        // si on a atteint la fin des groupes de rangées (plus aucune rangée à ajouter)
        // alors ajouter la répartition actuelle à la liste de répartitions
        if (g >= rowGroups.size()) {
            L.add(T);
            return;
        }
        // coup gauche
        // duplication de la liste de rangées (représentant une répartition) et ajout de la rangée actuelle
        ArrayList<Pair<Integer, Integer>> tmp = new ArrayList<>(T);
        tmp.add(new Pair<>(g, r));
        // calcul des nouveaux index
        int rg = (r + 1 + rowDistance >= rowGroups.get(g).getNbRows()) ? 0 : r + 1 + rowDistance;
        int gg = (r + 1 + rowDistance >= rowGroups.get(g).getNbRows()) ? g + 1 : g;
        getAllRowsRepartitionsWorker(rowGroups, rowDistance, L, tmp, gg, rg);
        // fin coup gauche

        // coup droit
        // calcul des nouveaux index
        int gd = (r + 1 >= rowGroups.get(g).getNbRows()) ? g + 1 : g;
        int rd = (r + 1 >= rowGroups.get(g).getNbRows()) ? 0 : r + 1;
        getAllRowsRepartitionsWorker(rowGroups, rowDistance, L, T, gd, rd);
        // fin coup droit
    }

    /**
     * Trouve la meilleure solution de placement des groupes de personnes dans une certaine répartition des rangées.
     * La meilleure solution maximise le nombre de groupes placés et minimise le nombre de rangées utilisées.
     * La répartition des rangées doit être telle qu'il n'y a plus à prendre en compte les contraintes de distance
     * entre les rangées : on peut insérer dans n'importe quelle rangée n'importe quand.
     *
     * @param peopleDistance       la distance minimale entre deux groupes de personnes
     * @param rowsOriginalCapacity la liste des capacitées de personnes de chaque rangée
     * @param rowsDistances        la liste des distances à la scène de chaque rangée
     * @param reservations         la liste des groupes de personnes à placer
     * @return un tuple contenant la liste des choix de rangées (par indices) et la meilleure solution trouvée.
     * Si l'index de choix de rangées vaut "-1" c'est que le groupe n'a pas été placé.
     */
    private Pair<ArrayList<Integer>, TemporarySolution> findBestSolutionInRowRepartition(
            final int peopleDistance,
            final ArrayList<Integer> rowsOriginalCapacity,
            final ArrayList<Integer> rowsDistances,
            final ArrayList<Integer> reservations
    ) {
        return findBestSolutionInRowRepartitionWorker(
                peopleDistance,
                0,
                0,
                0,
                0,
                0,
                new ArrayList<>(rowsOriginalCapacity),
                new ArrayList<>(Collections.nCopies(rowsOriginalCapacity.size(), false)),
                rowsOriginalCapacity,
                rowsDistances,
                reservations,
                0
        );
    }

    /**
     * Trouve récursivement la meilleure solution de placement des groupes de personnes, dans une certaine répartition
     * des rangées. La meilleure solution maximise le nombre de groupes placés et minimise le nombre de rangées utilisées.
     *
     * <p>
     * Idée de l'algorithme : on parcourt tous les groupes à placer, pour chaque groupe on essaye de le placer sur
     * chaque des rangées ou de ne le placer sur aucune rangée, puis on trouve la meilleure solution du sous problème
     * consistant à placer les prochains groupes (en enlevant la place prise par le groupe placé si tel est le cas).
     * On détermine la meilleure solution en remontant l'arbre de récursivité.
     * </p>
     *
     * @param peopleDistance       la distance minimum entre deux groupes de personnes
     * @param filledRows           le nombre de rangées engagées
     * @param sumDistance          la somme des distances des rangées engagées
     * @param totalSeats           le nombre total de sièges des rangées engagées
     * @param filledSeats          le nombre de sièges utilisés par une personne
     * @param totalUnplacedGroups  le nombre de groupes non placés
     * @param rowsCapacityLeft     liste du nombre de places restantes pour chaque rangée
     * @param rowsUsed             liste des rangées engagées
     * @param rowsOriginalCapacity liste de la capacité de chaque rangée
     * @param rowsDistances        liste des distances à la scène de chaque rangée
     * @param reservations         liste des réservations de groupes de personnes
     * @param indexReservation     index de parcours de la liste des réservations
     * @return un tuple contenant la liste des choix de rangées (par indices) et la meilleure solution trouvée.
     * Si l'index de choix de rangées vaut "-1" c'est que le groupe n'a pas été placé.
     */
    private Pair<ArrayList<Integer>, TemporarySolution> findBestSolutionInRowRepartitionWorker(
            final int peopleDistance,
            int filledRows,
            int sumDistance,
            int totalSeats,
            int filledSeats,
            int totalUnplacedGroups,
            ArrayList<Integer> rowsCapacityLeft,
            ArrayList<Boolean> rowsUsed,
            final ArrayList<Integer> rowsOriginalCapacity,
            final ArrayList<Integer> rowsDistances,
            final ArrayList<Integer> reservations,
            int indexReservation
    ) {
        // Cas de base, tous les groupes ont été placés, on retourne donc la solution actuelle
        if (indexReservation >= reservations.size()) {
            // ArrayList vide qui sera ajouté à un autre ArrayList
            ArrayList<Integer> choices = new ArrayList<>(0);
            TemporarySolution solution = new TemporarySolution(
                    filledRows,
                    sumDistance,
                    filledSeats,
                    totalSeats,
                    totalUnplacedGroups
            );
            return new Pair<>(choices, solution);
        }

        // Cas de récurrence, on essaye d'insérer dans chaque rangée qui a assez de place pour la réservation actuelle,
        // puis on garde la meilleure solution

        // déclaration des variables utilisées dans la boucle
        int indexBestRow = 0;
        Pair<ArrayList<Integer>, TemporarySolution> bestSolution;
        Pair<ArrayList<Integer>, TemporarySolution> currentSolution;
        ArrayList<Integer> rowsChoices; // rows chosen to place each group

        // on utilise des indicateurs temporaires à passer aux appels récursifs, car si la solution trouvée
        // récursivement n'est pas meilleure, on souhaite conserver les anciennes valeurs
        int tmpFilledRows;
        int tmpSumDistance;
        int tmpTotalSeats;
        ArrayList<Boolean> tmpRowsUsed;
        ArrayList<Integer> tmpRowsCapacityLeft;

        // initialisation de la meilleure solution par défaut : on ne rajoute pas le groupe actuel et on passe à la suite
        bestSolution = findBestSolutionInRowRepartitionWorker(
                peopleDistance,
                filledRows,
                sumDistance,
                totalSeats,
                filledSeats,
                totalUnplacedGroups + 1,
                rowsCapacityLeft,
                rowsUsed,
                rowsOriginalCapacity,
                rowsDistances,
                reservations,
                indexReservation + 1
        );

        // liste des choix effectués pour aboutir à cette solution
        rowsChoices = new ArrayList<>();
        rowsChoices.add(-1); // on ne choisit pas le groupe actuel
        rowsChoices.addAll(bestSolution.getValue0()); // puis on a traité les autres groupes
        bestSolution = bestSolution.setAt0(rowsChoices);

        // parcours de toutes les rangées
        for (int i = 0; i < rowsOriginalCapacity.size(); i++) {
            // si on peut placer le groupe actuel, alors on le place dans la rangée
            if (rowsCapacityLeft.get(i) >= reservations.get(indexReservation)) {
                // initialisation des indicateurs temporaires pour un éventuel appel récursif
                tmpFilledRows = filledRows;
                tmpSumDistance = sumDistance;
                tmpRowsUsed = new ArrayList<>(rowsUsed);
                tmpTotalSeats = totalSeats;
                // copie de la liste des capacités restantes de chaque rangée, et décrémentation de la capacité restante
                tmpRowsCapacityLeft = new ArrayList<>(rowsCapacityLeft);
                tmpRowsCapacityLeft.set(i, tmpRowsCapacityLeft.get(i) - (reservations.get(indexReservation) + peopleDistance));

                // si cette rangée n'est pas déjà utilisée
                // alors on met à jour les indicateurs dépendants de l'engagement d'une nouvelle rangée
                if (!rowsUsed.get(i)) {
                    tmpFilledRows += 1;
                    tmpSumDistance += rowsDistances.get(i);
                    tmpRowsUsed.set(i, true);
                    tmpTotalSeats += rowsOriginalCapacity.get(i);
                }

                // récursivité pour déterminer la meilleure sous-solution après avoir placé le groupe actuel
                currentSolution = findBestSolutionInRowRepartitionWorker(
                        peopleDistance,
                        tmpFilledRows,
                        tmpSumDistance,
                        tmpTotalSeats,
                        filledSeats + reservations.get(indexReservation),
                        totalUnplacedGroups,
                        tmpRowsCapacityLeft,
                        tmpRowsUsed,
                        rowsOriginalCapacity,
                        rowsDistances,
                        reservations,
                        indexReservation + 1
                );

                // liste des choix effectués pour aboutir à cette solution temporaire
                rowsChoices = new ArrayList<>();
                rowsChoices.add(i); // on place le groupe actuel sur la rangée i
                rowsChoices.addAll(currentSolution.getValue0()); // puis on a traité les autres groupes
                currentSolution = currentSolution.setAt0(rowsChoices);

                // si la solution temporaire est meilleure, alors on la sélectionne comme étant la meilleure
                // la solution est meilleure s'il y a plus de groupes placés,
                // ou si pour un même nombre de de groupes sont placés mais que le taux de remplissage est meilleur
                if (currentSolution.getValue1().getTotalUnplacedGroups() < bestSolution.getValue1().getTotalUnplacedGroups()) {
                    bestSolution = currentSolution;
                } else if (currentSolution.getValue1().getTotalUnplacedGroups() == bestSolution.getValue1().getTotalUnplacedGroups()
                        && currentSolution.getValue1().getFillingRate() > bestSolution.getValue1().getFillingRate()) {
                    bestSolution = currentSolution;
                }
            }
        }
        return bestSolution;
    }
}
