package src.problems;

import src.persons.PersonsGroup;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

    @Override
    public String toString() {
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateStr = sdf.format(date);

        String name = "Remplissage-S" + folderNumber + "-R" + reservation + "-A" + algoName + "D" + dateStr ;


        //creation fichier et ecriture
        String path = "/Results/" + name + ".res";


        try {
            FileWriter writer = new FileWriter(path);

            //writer.write(contenu);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}