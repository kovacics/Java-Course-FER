package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Class represents constraints for the {@link CalcLayout} class.
 *
 * @author Stjepan Kovačić
 */
public class RCPosition {

    /**
     * Row in the layout grid.
     */
    private int row;

    /**
     * Column in the layout grid.
     */
    private int column;

    /**
     * Constructs rc position with the specified row and column.
     *
     * @param row    row in the layout grid
     * @param column column in the layout grid
     * @throws CalcLayoutException if row is not in [1,5] range or column is not in [1,7] range or
     *                             forbidden position (row = 1, column in [2,5]) is chosen
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Getter for the row.
     *
     * @return row of the position
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter for the column.
     *
     * @return column of the position
     */
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPosition that = (RCPosition) o;
        return row == that.row &&
                column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
