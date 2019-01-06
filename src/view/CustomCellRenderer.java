package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Author: Sebastian Arledal c17sal
 * 2019-01-06
 *
 * Class CustomCellRenderer:
 *
 * Renders a cells text in a JTable to change color
 */
public class CustomCellRenderer extends DefaultTableCellRenderer {

    /**
     * Method getTableCellRendererComponent:
     *
     * Recolors a string in the JTable
     * @param table - the JTable
     * @param value - the value from the table
     * @param isSelected - always false
     * @param hasFocus - always false
     * @param row - current row
     * @param col - current col
     * @return c - the component
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        Object o = table.getModel().getValueAt(row, 3);

        c.setForeground(Color.black);
        if (o != null) {
            if (o.toString().equals("Finished")){
                c.setForeground(Color.GRAY);
            } else if (o.toString().equals("Broadcasting")){
                c.setForeground(Color.BLUE);
            }
        }
        return c;
    }
}
