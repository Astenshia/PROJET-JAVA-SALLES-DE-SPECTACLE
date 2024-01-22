package interfaces;

import problems.*;

public interface Algo {
    public long getRunTime();

    public Solution execute(AbstractProblem problem);
}
