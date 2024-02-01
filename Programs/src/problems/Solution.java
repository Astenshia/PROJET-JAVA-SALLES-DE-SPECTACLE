package src.problems;

import src.persons.PersonsGroup;
import src.roomComponents.Row;
import src.roomComponents.RowGroup;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private AbstractProblem problem;
    private String algoName;
    private int filledRows;
    private int sumDistance;
    private int filledSeats;
    private int totalSeats;
    private List<PersonsGroup> unplacedGroups;
    /**
     * Stores the runtime for this solution in nanoseconds.
     */
    private long runTime;

    public Solution(AbstractProblem problem, String algoName, int filledRows, int sumDistance, int filledSeats,
                    int totalSeats, List<PersonsGroup> unplacedGroups, long runTime) {
        this.problem = problem;
        this.algoName = algoName;
        this.filledRows = filledRows;
        this.sumDistance = sumDistance;
        this.filledSeats = filledSeats;
        this.totalSeats = totalSeats;
        this.unplacedGroups = unplacedGroups;
        this.runTime = runTime;
    }

    public AbstractProblem getProblem() {
        return problem;
    }

    public String getAlgoName() {
        return this.algoName;
    }

    public int getFilledRows() {
        return this.filledRows;
    }

    public int getSumDistance() {
        return this.sumDistance;
    }

    public int getFilledSeats() {
        return filledSeats;
    }

    public int getTotalSeats() {
        return this.totalSeats;
    }

    public List<PersonsGroup> getUnplacedGroups() {
        return this.unplacedGroups;
    }

    public long getRunTime() {
        return this.runTime;
    }

    public long getRunTimeMicroSeconds() {
        return this.runTime/1000;
    }

    //TODO: remove all setters ?
    public void setFilledRows(int filledRows) {
        this.filledRows = filledRows;
    }

    public void setSumDistance(int sumDistance) {
        this.sumDistance = sumDistance;
    }

    public void setFilledSeats(int filledSeats) {
        this.filledSeats = filledSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public void setProblem(AbstractProblem problem) {
        this.problem = problem;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    public void setUnplacedGroups(List<PersonsGroup> unplacedGroups) {
        this.unplacedGroups = unplacedGroups;
    }

    /**
     * Builds a string that respects the required output format of a solution.
     * <p>
     * Returned String format:
     * 3 9 25/26    // Number of used rows, sum of distances from the scene, filling rate (used seats/total seats)
     * 1 1 1 2 7    // Groups 1 and 2 on Row 1 of RowGroup 1, for a total of 7 used seats
     * 1 3 3 4 11   // Groups 3 and 4 on Row 3 of RowGroup 1, for a total of 11 used seats
     * 1 5 5 6 7    // Groups 5 and 6 on Row 5 of RowGroup 1, for a total of 7 used seats
     * Non placés
     * -1           // Number of the unplaced Persons or -1 if all Persons have a Seat.
     *
     * @return the formated string
     */
    public String getFormatedResult() {
        StringBuilder sb = new StringBuilder();
        // en-tête du fichier avec le nombre de rangées utilisées, la distance totale et le taux de remplissage
        sb.append(this.filledRows).append(" ").append(this.sumDistance).append(" ").append(this.filledSeats).append("/").append(this.totalSeats).append("\n");

        // déclaration des variables utilisées lors du parcours
        int groupNumber;
        int filledSeatsInRow;
        ArrayList<Integer> groupsInRow;
        StringBuilder tmpRow;

        // parcours de chaque groupe de rangées
        for (RowGroup rowGroup : this.problem.getRoom().getRowGroups()) {
            // parcours de chaque rangées dans le groupe de rangées
            for (Row row : rowGroup.getRows()) {
                tmpRow = new StringBuilder(rowGroup.getNumGroup() + " " + row.getNumRow() + " ");

                // mémorisation des groupes déjà rencontrés dans un arraylist (pour ne pas les afficher plusieurs fois)
                groupsInRow = new ArrayList<>();
                // comptage du nombre de sièges remplis
                filledSeatsInRow = 0;
                // parcours de tous les sièges d'une rangée
                for (int i = 0; i < row.getCapacity(); i++) {
                    // pour chaque siège non vide, on incrémente le nombre de sièges utilisés
                    if (!row.getSeats().get(i).isEmpty()) {
                        filledSeatsInRow++;
                        groupNumber = row.getSeats().get(i).getPerson().getPersonsGroup().getNumGroup();

                        // si le groupe n'est pas déjà rencontré, ajout du numéro de groupe aux groupes déjà rencontrés
                        if (!groupsInRow.contains(groupNumber)) {
                            groupsInRow.add(groupNumber);
                            tmpRow.append(groupNumber).append(" ");
                        }
                    }
                }
                // affichage de la rangée uniquement si au moins un siège est utilisé dedans
                if (filledSeatsInRow > 0) {
                    sb.append(tmpRow).append(filledSeatsInRow).append("\n");
                }
            }
        }

        sb.append("Non placés\n");
        // si tous les groupes ont été placés, affichage de "-1",
        // sinon affichage des numéros des groupes
        if (this.unplacedGroups.isEmpty()) {
            sb.append("-1");
        } else {
            for (PersonsGroup pg : this.unplacedGroups) {
                sb.append(pg.getNumGroup()).append(" ");
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        System.out.println("### Row Groups de solution : " + this.problem.getName() + " | " + this.getAlgoName() + " ###\n");
        for (RowGroup rowGroup : this.problem.getRoom().getRowGroups()) {
            System.out.println("\nRow Group:");
            for (Row row : rowGroup.getRows()) {
                System.out.println(row.getSeats());
            }
        }

        System.out.println("\nFormated restult :\n" + this.getFormatedResult() + "\n");

        return "### [Solution][" + this.problem.getName() + "][" + this.getAlgoName() + "] ###\n"
                + "Nombre de rangées utilisées : "
                + this.filledRows
                + "\nSomme des distances à la scène : "
                + this.sumDistance
                + "\nTaux de remplissage : "
                + this.filledSeats + "/" + this.totalSeats
                + "\n\nExécutée en " + this.getRunTimeMicroSeconds() + " microseconds."
                + "\n############";
    }
}
