package src.roomComponents;

import java.util.List;

public class Room {
    private List<RowGroup> rowGroups;

    public Room(List<RowGroup> groups) {
        this.rowGroups = groups;
    }

    public String toString() {
        int groupSize = rowGroups.size();
        String res = groupSize + " RowGroups. \n";
        for (int i = 0; i < groupSize; i++) {
            res = res + "RowGroup " + i + " has " + rowGroups.get(i).getNbRows() + " rows\n";
        }
        return res;
    }

    public List<RowGroup> getRowGroups() {
        return rowGroups;
    }

    public int getNbRowGroups() {
        return this.rowGroups.size();
    }
}
