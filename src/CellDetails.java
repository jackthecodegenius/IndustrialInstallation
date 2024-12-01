import java.awt.*;

/**
 * Represents details of a cell, including its row, column, value, and background color.
 * Implements the Comparable interface to allow comparison based on the cell value.
 */
public class CellDetails implements Comparable<CellDetails>
{
    public int row;
    public int column;
    public int value;
    public Color background;
    /**
     * Compares this CellDetails object with another CellDetails object based on their values.
     *
     * @param o The CellDetails object to compare with.
     * @return Negative integer if this value is less than the other value, positive integer if greater, 0 if equal.
     */
    @Override
    public int compareTo(CellDetails o)
    {
        if(value < o.value)
        {
            return -1;
        }
        else if(value > o.value)
        {
            return 1;
        }

        return 0;
    }
}
