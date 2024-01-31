package src.roomComponents;

import java.util.ArrayList;
import java.util.List;

public class RowGroup {
    private List<Row> rows;

    public RowGroup(List<Row> rows) {
        this.rows = rows;
    }

    /**
     * Creates a blank copy of a RowGroup, as if it was empty again.
     * Hence, the Persons that could be associated to different Seats in the RowGroup won't be associated anymore.
     * Also, all variables giving information about the RowGroup's fullness will be reset.
     * @param rowGroup the RowGroup to copy
     */
    public RowGroup(RowGroup rowGroup) {
        this.rows = new ArrayList<>();
        for (Row row : rowGroup.rows) {
            this.rows.add(new Row(row));
        }
    }

    public int getNbRows() {
        return rows.size();
    }

    public List<Row> getRows() {
        return rows;
    }
}
