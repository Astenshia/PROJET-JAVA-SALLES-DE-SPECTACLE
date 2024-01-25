package src.algorithms;

import src.interfaces.Algo;
import src.problems.AbstractProblem;
import src.problems.Solution;

public abstract class AbstractAlgo {
    private long runTime;

    public AbstractAlgo() {
    }

    public long getRunTime() {
        return runTime;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    abstract public Solution execute(AbstractProblem problem);
    // TODO: execute devra créer une solution, qui aura pour attributs l'Algo et le Problem (afin de s'y référer plus tard)
}
