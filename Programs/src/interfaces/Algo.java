package src.interfaces;

import src.problems.Solution;
import src.problems.AbstractProblem;

public interface Algo {
    public long getRunTime();

    public Solution execute(AbstractProblem problem);
    // TODO: execute devra créer une solution, qui aura pour attributs l'Algo et le Problem (afin de s'y référer plus tard)
}
