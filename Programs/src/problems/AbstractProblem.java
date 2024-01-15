package problems;

public abstract class AbstractProblem {
    private String name;
    //contraint class ?
    private int rowDistance; //p
    private int peopleDistance; //q
    private int maxGroupSize; //k


    public AbstractProblem(String name, int p, int q , int k){
        this.name=name;
        this.rowDistance = p;
        this.peopleDistance = q;
        this.maxGroupSize = k;
    }
}
