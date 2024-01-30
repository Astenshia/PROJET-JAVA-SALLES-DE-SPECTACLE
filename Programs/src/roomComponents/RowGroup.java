package src.roomComponents;

import java.util.List;

public class RowGroup {
    private List<Row> rows;

    public RowGroup(List<Row> rows) {
        this.rows = rows;
    }

    public int getNbRows() {
        return rows.size();
    }

    public List<Row> getRows() {
        return rows;
    }
}
