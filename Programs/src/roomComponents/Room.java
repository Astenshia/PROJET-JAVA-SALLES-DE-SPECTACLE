package src.roomComponents;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<RowGroup> rowGroups;

    public Room(List<RowGroup> groups) {
        this.rowGroups = groups;
    }

    /**
     * Creates a blank copy of a Room, as if it was empty again.
     * Hence, the Persons that could be associated to different Seats in the Room won't be associated anymore.
     * Also, all variables giving information about the Room's fullness will be reset.
     */
    public Room copy() {
        ArrayList<RowGroup> groupsList = new ArrayList<>();
        for (RowGroup rowGroup : this.rowGroups) {
            groupsList.add(rowGroup.copy());
        }
        return new Room(groupsList);
    }

    @Override
    public String toString() {
        int groupSize = rowGroups.size();
        StringBuilder res = new StringBuilder(groupSize + " RowGroups. \n");
        for (int i = 0; i < groupSize; i++) {
            res.append("RowGroup ").append(i).append(" has ").append(rowGroups.get(i).getNbRows()).append(" rows\n");
        }
        return res.toString();
    }

    public List<RowGroup> getRowGroups() {
        return rowGroups;
    }

    public int getNbRowGroups() {
        return this.rowGroups.size();
    }

    /**
     * Obtenir un groupe de rangées identifié par son index.
     * @param rowGroupIndex l'index du groupe de rangées
     * @return un objet RowGroup
     */
    public RowGroup getRowGroup(int rowGroupIndex) {
        return this.rowGroups.get(rowGroupIndex);
    }

    /**
     * Obtenir une rangée identifiée par son index de groupe de rangées et son index dans ce groupe.
     * @param rowGroupIndex l'index du groupe
     * @param rowIndex l'index de la rangée dans le groupe
     * @return un objet rangée
     */
    public Row getRow(int rowGroupIndex, int rowIndex){
        return this.getRowGroup(rowGroupIndex).getRow(rowIndex);
    }

    /**
     * Obtenir une rangée identifiée par son index de groupe de rangées et son index dans ce groupe.
     * @param rowLocation la paire d'entiers contenant l'index du groupe de rangées et l'index de la rangée dans ce groupe
     * @return un objet rangée
     */
    public Row getRow(Pair<Integer, Integer> rowLocation) {
        return this.getRow(rowLocation.getValue0(), rowLocation.getValue1());
    }
}
