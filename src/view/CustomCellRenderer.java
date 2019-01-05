package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CustomCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row,int col) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        String s = table.getModel().getValueAt(row, col).toString();

        if (s.equalsIgnoreCase("yellow")) {
            c.setForeground(Color.YELLOW);
        }
        else {
            c.setForeground(Color.WHITE);
        }

        return c;
    }
}
