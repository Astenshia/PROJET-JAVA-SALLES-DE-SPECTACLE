package src.main;

import src.algorithms.AbstractAlgo;
import src.algorithms.AlgoHeuristique2;
import src.problems.AbstractProblem;
import src.utils.Parser;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<AbstractAlgo> algos = new ArrayList<>();
        algos.add(new AlgoHeuristique2());

        ArrayList<AbstractProblem> problems = new ArrayList<>();
        problems.add(Parser.createProblem("Salle01", 1));

        Runner r1 = new Runner(algos, problems);

        r1.executeAllAlgorithms();

        System.out.println(r1);
    }
}
