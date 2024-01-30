package src.problems;

import src.persons.PersonsGroup;

import java.util.ArrayList;

public class Solution {
    private long runTime;
    private AbstractProblem problem;
    private String algoName;
    private int filledRows;
    private int sumDistance;
    private int totalSeats;
    private int filledSeats;
    private ArrayList<PersonsGroup> unplacedGroups;


    public Solution() {
        this.filledRows = 0;
        this.sumDistance = 0;
        this.totalSeats = 0;
        this.filledSeats = 0;
    }

    public long getRunTime() {
        return runTime;
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

    public int getTotalSeats() {
        return this.totalSeats;
    }

    public int getFilledSeats() {
        return filledSeats;
    }


    public void setFilledRows(int filledRows) {
        this.filledRows = filledRows;
    }

    public void setSumDistance(int sumDistance) {
        this.sumDistance = sumDistance;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public void setFilledSeats(int filledSeats) {
        this.filledSeats = filledSeats;
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

    public void setUnplacedGroups(ArrayList<PersonsGroup> unplacedGroups) {
        this.unplacedGroups = unplacedGroups;
    }
}
