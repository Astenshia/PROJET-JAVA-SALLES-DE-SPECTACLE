package src.main;

import src.algorithms.AbstractAlgo;
import src.algorithms.AlgoHeuristique1;
import src.algorithms.AlgoHeuristique2;
import src.problems.AbstractProblem;
import src.utils.Parser;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<AbstractAlgo> algos = new ArrayList<>();
        algos.add(new AlgoHeuristique1());
        algos.add(new AlgoHeuristique2());

        ArrayList<AbstractProblem> problems = new ArrayList<>();
        problems.add(Parser.createProblem("Salle01", 1));

        Runner r1 = new Runner(algos, problems);
        System.out.println(r1);

        r1.executeAllAlgorithms();
        r1.printAllSolutions();
        r1.storeAllSolutions();



    }
}
