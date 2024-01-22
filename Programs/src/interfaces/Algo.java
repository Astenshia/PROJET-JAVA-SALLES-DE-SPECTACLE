package src.interfaces;

import src.problems.Solution;
import src.problems.AbstractProblem;

public interface Algo {
    public long getRunTime();

    public Solution execute(AbstractProblem problem);
}
