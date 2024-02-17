package src.problems;

public class TemporarySolution {
    private int filledRows;
    private int sumDistance;
    private int filledSeats;
    private int totalSeats;
    private int totalUnplacedGroups;

    /**
     * Construit une solution "temporaire", ne prennant pas en compte le problème, l'algorithme et le temps d'exécution.
     * Permet par exemple de comparer des solutions dans un algorithme sans avoir besoin de créer un objet plus complexe.
     *
     * @param filledRows nombre de rangées engagées dans la Solution
     * @param sumDistance la somme des distances des rangées engagées
     * @param filledSeats le nombre de sièges engagés
     * @param totalSeats le nombre total de sièges des rangées engagées
     * @param totalUnplacedGroups nombre de groupes de personnes non placés
     */
    public TemporarySolution(
            int filledRows,
            int sumDistance,
            int filledSeats,
            int totalSeats,
            int totalUnplacedGroups) {
        this.filledRows = filledRows;
        this.sumDistance = sumDistance;
        this.filledSeats = filledSeats;
        this.totalSeats = totalSeats;
        this.totalUnplacedGroups = totalUnplacedGroups;
    }
    public int getFilledRows() {
        return this.filledRows;
    }

    public int getSumDistance() {
        return this.sumDistance;
    }

    public int getFilledSeats() {
        return filledSeats;
    }

    public int getTotalSeats() {
        return this.totalSeats;
    }

    public int getTotalUnplacedGroups() {
        return totalUnplacedGroups;
    }

    public void setFilledRows(int filledRows) {
        this.filledRows = filledRows;
    }

    public void setSumDistance(int sumDistance) {
        this.sumDistance = sumDistance;
    }

    public void setFilledSeats(int filledSeats) {
        this.filledSeats = filledSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public void setTotalUnplacedGroups(int totalUnplacedGroups) {
        this.totalUnplacedGroups = totalUnplacedGroups;
    }
}
