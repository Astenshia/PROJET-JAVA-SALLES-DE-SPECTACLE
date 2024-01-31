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
     * @param room the Room to copy
     */
    public Room(Room room) {
        this.rowGroups = new ArrayList<>();
        for (RowGroup rowGroup : room.rowGroups) {
            this.rowGroups.add(new RowGroup(rowGroup));
        }
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
