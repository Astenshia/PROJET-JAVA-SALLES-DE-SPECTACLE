package src.algorithms;

import org.javatuples.Pair;
import src.problems.AbstractProblem;
import src.problems.Solution;
import src.roomComponents.Row;
import src.roomComponents.RowGroup;

import java.util.ArrayList;

public class AlgoEnumTotaleNbRangees extends AbstractAlgo {

    /**
     * La meilleure solution est déterminée en fonction du nombre de rangées utilisées.
     *
     * @param problem
     * @return
     */
    @Override
    public Solution execute(AbstractProblem problem) {
        ArrayList<ArrayList<Pair<Integer, Integer>>> rowsRepartition = getAllRowsRepartitionsAsIndices((ArrayList<RowGroup>) problem.getRoom().getRowGroups(), problem.getRowDistance());

        Solution bestSolution = findBestSolutionInRowRepartition(problem.copy(), rowsRepartition.get(0));
        Solution tmpSolution;
        for (int i = 1; i < rowsRepartition.size(); i++) {
            tmpSolution = findBestSolutionInRowRepartition(problem, rowsRepartition.get(i));

            if (bestSolution.getFilledRows() > tmpSolution.getFilledRows()) {
                bestSolution = tmpSolution;
            }
        }

        return bestSolution;
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
    public ArrayList<ArrayList<Pair<Integer, Integer>>> getAllRowsRepartitionsAsIndices(ArrayList<RowGroup> rowGroups, int rowDistance) {
        ArrayList<ArrayList<Pair<Integer, Integer>>> rowRepartitions = new ArrayList<>();

        // utilisation du Worker récursif pour calculer les répartitions possibles
        getAllRowsRepartitionsAsIndicesWorker(rowGroups, rowDistance, rowRepartitions, new ArrayList<>(), 0, 0);

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
    private void getAllRowsRepartitionsAsIndicesWorker(ArrayList<RowGroup> rowGroups, int rowDistance,
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
        tmp.add(new Pair(g, r));
        // calcul des nouveaux index
        int rg = (r + 1 + rowDistance >= rowGroups.get(g).getNbRows()) ? 0 : r + 1 + rowDistance;
        int gg = (r + 1 + rowDistance >= rowGroups.get(g).getNbRows()) ? g + 1 : g;
        getAllRowsRepartitionsAsIndicesWorker(rowGroups, rowDistance, L, tmp, gg, rg);
        // fin coup gauche

        // coup droit
        // calcul des nouveaux index
        int gd = (r + 1 >= rowGroups.get(g).getNbRows()) ? g + 1 : g;
        int rd = (r + 1 >= rowGroups.get(g).getNbRows()) ? 0 : r + 1;
        getAllRowsRepartitionsAsIndicesWorker(rowGroups, rowDistance, L, T, gd, rd);
        // fin coup droit
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
     * @return la liste des répartitions possibles
     */
    public ArrayList<ArrayList<Row>> getAllRowsRepartitionsAsRows(ArrayList<RowGroup> rowGroups, int rowDistance) {
        ArrayList<ArrayList<Row>> rowRepartitions = new ArrayList<>();

        // utilisation du Worker récursif pour calculer les répartitions possibles
        getAllRowsRepartitionsAsRowsWorker(rowGroups, rowDistance, rowRepartitions, new ArrayList<>(), 0, 0);

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
     * @param L           la liste des répartitions possibles
     * @param T           une liste de rangées, qui représente une répartition possible
     * @param g           index de parcours du groupe de rangées
     * @param r           index de parcours d'une rangée
     */
    private void getAllRowsRepartitionsAsRowsWorker(ArrayList<RowGroup> rowGroups, int rowDistance,
                                                    ArrayList<ArrayList<Row>> L, ArrayList<Row> T, int g, int r) {
        // si on a atteint la fin des groupes de rangées (plus aucune rangée à ajouter)
        // alors ajouter la répartition actuelle à la liste de répartitions
        if (g >= rowGroups.size()) {
            L.add(T);
            return;
        }
        // coup gauche
        // duplication de la liste de rangées (représentant une répartition) et ajout de la rangée actuelle
        ArrayList<Row> tmp = new ArrayList<>(T);
        tmp.add(rowGroups.get(g).getRows().get(r));
        // calcul des nouveaux index
        int rg = (r + 1 + rowDistance >= rowGroups.get(g).getNbRows()) ? 0 : r + 1 + rowDistance;
        int gg = (r + 1 + rowDistance >= rowGroups.get(g).getNbRows()) ? g + 1 : g;
        getAllRowsRepartitionsAsRowsWorker(rowGroups, rowDistance, L, tmp, gg, rg);
        // fin coup gauche

        // coup droit
        // calcul des nouveaux index
        int gd = (r + 1 >= rowGroups.get(g).getNbRows()) ? g + 1 : g;
        int rd = (r + 1 >= rowGroups.get(g).getNbRows()) ? 0 : r + 1;
        getAllRowsRepartitionsAsRowsWorker(rowGroups, rowDistance, L, T, gd, rd);
        // fin coup droit
    }

    /**
     * La meilleure solution est déterminée en fonction du nombre de rangées utilisées.
     *
     * @param problem
     * @param rowList
     * @return
     */
    private Solution findBestSolutionInRowRepartition(AbstractProblem problem, ArrayList<Pair<Integer, Integer>> rowList) {
        int filledRows = 0;
        int sumDistance = 0;
        int totalSeats = 0;
        int filledSeats = 0;
        return findBestSolutionInRowRepartitionWorker(problem, rowList, 0,
                filledRows, sumDistance, totalSeats, filledSeats);
    }

    /**
     * Trouve récursivement la meilleure solution de placement des groupes de personnes.
     * La meilleure solution est déterminée en fonction du nombre de rangées utilisées.
     *
     * @param problem  le problème étudié
     * @param rowList  liste de rangées où placer les groupes de personnes
     * @param rowIndex index de parcours de la liste de rangées
     * @return
     */
    private Solution findBestSolutionInRowRepartitionWorker(AbstractProblem problem,
                                                            ArrayList<Pair<Integer, Integer>> rowList, int rowIndex,
                                                            int filledRows, int sumDistance, int totalSeats, int filledSeats) {
        // TODO : créer un constructeur par recopie (un vrai) de Problème afin de reset problem entre deux appels
        //  (pour pouvoir à nouveau travailler dessus)

        if (problem.getReservations().isEmpty()) {
            return new Solution(problem, this.getName(), rowList.size(), filledRows, sumDistance, totalSeats, null);
        }

        ArrayList<Solution> solutions = new ArrayList<>();

        // for (int i = 0; i < rowList.size(); i++) {
        //     if (problem.getRoom().getRowGroup(rowList.get(i).getValue0()).getRow(rowList.get(i).getValue1()).enoughFor());
        // }
        return null;
    }
}
