package src.problems;

import src.persons.PersonsGroup;

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
}
