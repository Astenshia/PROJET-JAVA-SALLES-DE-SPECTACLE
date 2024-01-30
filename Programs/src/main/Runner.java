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

    public void executeAllAlgorithms() {
        // création d'un nouveau tableau de solutions
        // on n'utilise pas directement l'attribut "solutions" car en cas de modifications des algos et des problèmes,
        // cela permet de remplacer automatiquement les anciennes solutions, sans avoir à les modifier dans le tableau
        List<Solution> solutions = new ArrayList<>();

        // exécution de chaque algo pour chaque problème et ajout de la solution trouvée dans la liste de solutions
        for (AbstractProblem problem : this.problems) {
            for (AbstractAlgo algo : this.algos) {
                solutions.add(algo.execute(problem));
                // TODO: make a copy of the problem when creating a Solution, and reset the room for other algorithms after execution of an algorithm.
            }
        }
        this.solutions = solutions;
    }

    public void printAllSolutions() {
        for (Solution solution : this.solutions) {
            System.out.println(solution);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Problèmes du Runner :\n");
        for (AbstractProblem p : this.problems) {
            sb.append(p.getName());
            sb.append("\n");
        }

        sb.append("Algorithmes du Runner : \n");

        for (AbstractAlgo a : this.algos) {
            sb.append(a.getName());
            sb.append("\n");
        }

        return sb.toString();
    }

    // TODO: ajouter les getters et setters de algos, problems et solutions
    // algos et problems peuvent être édités en dehors du runner en cas d'une IHM
    // on doit pouvoir récupérer les solutions (pas forcément les edit)
}
