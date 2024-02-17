package src.problems;

import src.persons.PersonsGroup;
import src.roomComponents.Row;
import src.roomComponents.RowGroup;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.FileWriter;

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

    /**
     *
     * @param problem le problème que la solution résout
     * @param algoName l'algorithme utilisé pour résoudre
     * @param filledRows nombre de rangées engagées dans la Solution
     * @param sumDistance la somme des distances des rangées engagées
     * @param filledSeats le nombre de sièges engagés
     * @param totalSeats le nombre total de sièges des rangées engagées
     * @param unplacedGroups la liste des groupes de personnes non placés
     */
    public Solution(AbstractProblem problem, String algoName, int filledRows, int sumDistance, int filledSeats,
                    int totalSeats, List<PersonsGroup> unplacedGroups) {
        this.problem = problem;
        this.algoName = algoName;
        this.filledRows = filledRows;
        this.sumDistance = sumDistance;
        this.filledSeats = filledSeats;
        this.totalSeats = totalSeats;
        this.unplacedGroups = unplacedGroups;
    }

    public AbstractProblem getProblem() {
        return problem;
    }

    public String getAlgoName() {
        return String.valueOf(this.algoName);
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
        return this.runTime / 1000;
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
     * <pre>
     * 3 9 25/26    // Number of used rows, sum of distances from the scene, filling rate (used seats/total seats)
     * 1 1 1 2 7    // Groups 1 and 2 on Row 1 of RowGroup 1, for a total of 7 used seats
     * 1 3 3 4 11   // Groups 3 and 4 on Row 3 of RowGroup 1, for a total of 11 used seats
     * 1 5 5 6 7    // Groups 5 and 6 on Row 5 of RowGroup 1, for a total of 7 used seats
     * Non placés
     * -1           // Number of the unplaced Persons or -1 if all Persons have a Seat.
     * </pre>
     * </p>
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

        // System.out.println("\nFormated result :\n" + this.getFormatedResult() + "\n");

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


    public void solutionToFile() {
        //nommage du probleme au format : Remplissage-S<nbSalle>-R<nbReservation>-A<algorithme>(-D<date>)
        String folderNumber = problem.getFolderName().substring(problem.getFolderName().length() - 2);
        String reservation = "0" + Integer.toString(problem.getNumeroReservation());
        String time = java.time.LocalDateTime.now().toString();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH'h'mm");
        String dateStr = sdf.format(date);

        String name = "Remplissage-S" + folderNumber + "-R" + reservation + "-" + algoName + "-D" + dateStr;


        //creation fichier et ecriture
        String path = "./Results/" + name + ".res";
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(this.getFormatedResult());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}