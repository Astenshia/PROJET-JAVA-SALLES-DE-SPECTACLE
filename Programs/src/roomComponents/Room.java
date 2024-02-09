package src.roomComponents;

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
}
