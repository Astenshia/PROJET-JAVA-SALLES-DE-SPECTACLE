package src.algorithms;

import src.problems.AbstractProblem;
import src.problems.Solution;

public abstract class AbstractAlgo {
    public AbstractAlgo() {
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    abstract public Solution execute(AbstractProblem problem);
}
