package src.main;

import src.algorithms.*;
import src.utils.Parser;
import src.problems.AbstractProblem;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<AbstractAlgo> algos = new ArrayList<>();
        algos.add(new AlgoHeuristique1());
        // algos.add(new AlgoHeuristique2());
        // algos.add(new AlgoEnumTotaleSumDistance());
        // algos.add(new AlgoEnumTotaleTauxRemplissage());
        // algos.add(new AlgoEnumTotaleNbRangees());
        // algos.add(new AlgoEnumImpliciteNbRangees());

        ArrayList<AbstractProblem> problems = new ArrayList<>();
        // problems.add(Parser.createProblem("Salle00", 1));

        for (int numSalle = 1; numSalle <= 6; numSalle++) {
            for (int numResa = 1; numResa <= 5; numResa++) {
                problems.add(Parser.createProblem("Salle0" + numSalle, numResa));
            }
        }

        Runner r1 = new Runner(algos, problems);
        System.out.println(r1);

        r1.executeAllAlgorithms();
        r1.printAllSolutions();
        r1.storeAllSolutions();
        r1.printSynthesis();


    }
}
