package src.roomComponents;

import java.util.ArrayList;
import java.util.List;

public class RowGroup {
    private List<Row> rows;

    private int numGroup;

    public RowGroup(List<Row> rows, int numGroup) {
        this.rows = rows;
        this.numGroup = numGroup;
    }

    /**
     * Creates a blank copy of a RowGroup, as if it was empty again.
     * Hence, the Persons that could be associated to different Seats in the RowGroup won't be associated anymore.
     * Also, all variables giving information about the RowGroup's fullness will be reset.
     */
    public RowGroup copy() {
        ArrayList<Row> rowsList = new ArrayList<>();
        Row rowTmp;
        RowGroup rowGroup = new RowGroup(null, this.numGroup);
        for (Row row : this.rows) {
            rowTmp = row.copy();
            rowTmp.setRowGroup(rowGroup);
            rowsList.add(rowTmp);
        }

        rowGroup.setRows(rowsList);
        return rowGroup;
    }

    public int getNbRows() {
        return rows.size();
    }

    public List<Row> getRows() {
        return rows;
    }

    public int getNumGroup() {
        return numGroup;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public Row getRow(int i) {
        return this.rows.get(i);
    }
}
