package algorithms;

import interfaces.Algo;

public abstract class AbstractAlgo implements Algo {
    private String name;
    private long runTime;

    public AbstractAlgo(String name) {
        this.name = name;
    }

    public long getRunTime() {
        return runTime;
    }

}
