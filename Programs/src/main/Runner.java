package main;

import src.interfaces.Algo;
import src.problems.AbstractProblem;
import src.problems.Solution;
import java.util.List;

public class Runner {
    private Algo algo;
    private AbstractProblem problem;

    public Runner(Algo algo, AbstractProblem problem) {
        this.algo = algo;
        this.problem = problem;
    }

    public Solution run() {
        return algo.execute(problem);
    };

    public long getRuntime() {
        return algo.getRunTime();
    }
}
