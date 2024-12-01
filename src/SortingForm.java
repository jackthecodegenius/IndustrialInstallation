import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
/**
 * The SortingForm class represents a form for displaying and sorting cell details.
 */
public class SortingForm extends JFrame
{

    /**
     * Constructs a new instance of the SortingForm class.
     * detailsList A LinkedList containing CellDetails objects to be displayed and sorted.
     */
    SpringLayout layout = new SpringLayout();

    public SortingForm(LinkedList<CellDetails> detailsList)
    {
        JLabel[][] detailsFields = new JLabel[detailsList.size()][3];

        Collections.sort(detailsList);
        Collections.reverse(detailsList);
        //Calculate the height of the form for a label height of 22 (20 plus +2 margin) as well
        // as some extra padding either side of the grid
        int formHeight = detailsList.size() * 22 + 80;

        setSize(225, formHeight);
        setLocationRelativeTo(null);
        setLayout(layout);
        // Populate the form with cell details
        for (int y = 0; y < detailsList.size(); y++)
        {
            // Calculate vertical position of each row
            int yPos = y * 20 + 50;

            detailsFields[y][0] = UIBuilderLibray.BuildJLabelWithNorthWestAnchor("", 20,yPos,layout,this);
            detailsFields[y][0].setText(detailsList.get(y).row + "");
            detailsFields[y][0].setPreferredSize(new Dimension(40,20));
            detailsFields[y][0].setOpaque(true);
            detailsFields[y][0].setBackground(detailsList.get(y).background);
            add(detailsFields[y][0]);


            detailsFields[y][1] = UIBuilderLibray.BuildJLabelWithNorthWestAnchor("", 60,yPos,layout,this);
            detailsFields[y][1].setText(detailsList.get(y).column + "");
            detailsFields[y][1].setPreferredSize(new Dimension(40,20));
            detailsFields[y][1].setOpaque(true);
            detailsFields[y][1].setBackground(detailsList.get(y).background);
            add(detailsFields[y][1]);

            detailsFields[y][2] = UIBuilderLibray.BuildJLabelWithNorthWestAnchor("", 100,yPos,layout,this);
            detailsFields[y][2].setText(detailsList.get(y).value + "");
            detailsFields[y][2].setPreferredSize(new Dimension(80,20));
            detailsFields[y][2].setOpaque(true);
            detailsFields[y][2].setBackground(detailsList.get(y).background);
            add(detailsFields[y][2]);

        }

    }

}