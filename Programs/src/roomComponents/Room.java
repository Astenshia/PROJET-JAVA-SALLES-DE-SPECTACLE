package src.roomComponents;

import java.util.List;

public class Room {
    private List<RowGroup> groups;

    public Room(List<RowGroup> groups) {
        this.groups = groups;
    }

    public String toString() {
        int groupSize = groups.size();
        String res = groupSize + " RowGroups. \n";
        for (int i = 0; i < groupSize; i++) {
            res = res + "RowGroup " + i + " has " + groups.get(i).getNbRows() + " rows\n";
        }
        return res;
    }
}
