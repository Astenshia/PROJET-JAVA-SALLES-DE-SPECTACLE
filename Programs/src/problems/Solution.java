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
}
