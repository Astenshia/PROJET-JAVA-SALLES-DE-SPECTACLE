package src.main;

import src.algorithms.*;
import src.problems.AbstractProblem;
import src.utils.Parser;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<AbstractAlgo> algos = new ArrayList<>();
        algos.add(new AlgoHeuristique1());
        algos.add(new AlgoHeuristique2());
        // algos.add(new AlgoHeuristique2());
        // algos.add(new AlgoEnumTotale());

        ArrayList<AbstractProblem> problems = new ArrayList<>();
        problems.add(Parser.createProblem("Salle01", 1));
        problems.add(Parser.createProblem("Salle02", 1));
        problems.add(Parser.createProblem("Salle03", 1));

        Runner r1 = new Runner(algos, problems);
        System.out.println(r1);

        r1.executeAllAlgorithms();
        r1.printAllSolutions();
        //r1.storeAllSolutions();
        r1.printSynthesis();




    }
}
