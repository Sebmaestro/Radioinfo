package view;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame{
    private JPanel panel;
    private JMenuBar menuBar;
    private JTable table;

    public Gui() {
        super();
        add(buildPanel());

        setJMenuBar(buildMenuBar());

        setSize(new Dimension(1080, 720));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel buildPanel() {
        panel = new JPanel();
        table = new JTable(10, 10);
        panel.add(table);

        return panel;
    }

    private JMenuBar buildMenuBar() {
        menuBar = new JMenuBar();

        JMenu firstMenu = new JMenu("menu");
        firstMenu.add(new JMenuItem("Ralle"));
        firstMenu.add(new JMenuItem("second"));

        JMenu secondMenu = new JMenu("another menu");
        secondMenu.add("kalle");

        menuBar.add(firstMenu);
        menuBar.add(secondMenu);

        return menuBar;
    }
}
