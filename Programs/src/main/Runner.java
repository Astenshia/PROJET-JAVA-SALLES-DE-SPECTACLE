package main;

import interfaces.Algo;
import problems.AbstractProblem;
import problems.Solution;

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
