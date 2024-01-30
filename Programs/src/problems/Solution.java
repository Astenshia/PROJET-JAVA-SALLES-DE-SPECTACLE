package src.problems;

import src.algorithms.AbstractAlgo;
import src.persons.PersonsGroup;

import java.util.ArrayList;

public class Solution {
    private long runTime;
    private AbstractProblem problem;
    private String algoName;
    private int filledRows;
    private int sumDistance;
    private int emptySeats;
    private int filledSeats;
    private ArrayList<PersonsGroup> unplacedGroups;


    public long getRunTime() {
        return runTime;
    }
    public String getAlgoName(){
        return this.algoName;
    }
    public int getFilledRows() {
        return this.filledRows;
    }
    public int getSumDistance(){
        return this.sumDistance;
    }
    public int getEmptySeats(){
        return this.emptySeats;
    }
    public int getFilledSeats(){
        return filledSeats;
    }


    public void setFilledRows(int filledRows) {
        this.filledRows =filledRows;
    }
    public void setSumDistance(int sumDistance){
        this.sumDistance = sumDistance;
    }
    public void setEmptySeats(int emptySeats){
        this.emptySeats = emptySeats;
    }
    public void setFilledSeats(int filledSeats){
        this.filledSeats = filledSeats;
    }
}
