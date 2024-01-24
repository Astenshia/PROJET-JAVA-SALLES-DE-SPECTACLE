package src.main;

import src.algorithms.AbstractAlgo;
import src.problems.AbstractProblem;
import src.problems.Solution;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    private List<AbstractAlgo> algos;
    private List<AbstractProblem> problems;
    private List<Solution> solutions;

    public Runner(List<AbstractAlgo> algos, List<AbstractProblem> problems) {
        this.algos = algos;
        this.problems = problems;
    }

    public void runAll() {
        // création d'un nouveau tableau de solutions
        // on n'utilise pas directement l'attribut "solutions" car en cas de modifications des algos et des problèmes,
        // cela permet de remplacer automatiquement les anciennes solutions, sans avoir à les modifier dans le tableau
        List<Solution> solutions = new ArrayList<>();

        // exécution de chaque algo pour chaque problème et ajout de la solution trouvée dans la liste de solutions
        for (AbstractProblem problem : this.problems) {
            for (AbstractAlgo algo : this.algos) {
                solutions.add(algo.execute(problem));
            }
        }
        this.solutions = solutions;
    }
}
