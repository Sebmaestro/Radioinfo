package view;

import model.Program;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Gui extends JFrame{
    private JPanel tablePanel;
    private JPanel extraInfoPanel;

    private List<String> channelNames;
    private List<JMenuItem> menuItems;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane pane;
    private JLabel picture;
    private JTextArea infoField;

    private List<Program> programList;


    public Gui(ArrayList<String> channelNames) {
        super();

        this.channelNames = channelNames;

        //add(buildTablePanel(buildTable()));
        tablePanel = new JPanel();
        add(buildTablePanel(buildTable()), BorderLayout.NORTH);
        add(buildExtraInfoPanel(), BorderLayout.SOUTH);

        setJMenuBar(buildMenuBar());

        setSize(new Dimension(1080, 720));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JPanel buildTablePanel(JScrollPane pane) {
        tablePanel = new JPanel();
        tablePanel.add(pane);


        return tablePanel;
    }

    public JPanel buildExtraInfoPanel() {
        extraInfoPanel = new JPanel();
        extraInfoPanel.setLayout(new FlowLayout());

        picture = new JLabel();
        picture.setPreferredSize(new Dimension(100,100));
        extraInfoPanel.add(picture);

        infoField = new JTextArea();
        infoField.setEditable(false);
        infoField.setLineWrap(true);
        infoField.setPreferredSize(new Dimension(950, 20));
        infoField.setVisible(false);
        extraInfoPanel.add(infoField);

        return extraInfoPanel;
    }

    public void refreshGui() {
        //extraInfoPanel.removeAll();
        picture.setText(null);
        picture.setIcon(null);
        infoField.setText(null);
        infoField.setVisible(false);
    }

    public void addMouseListener() {
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());

                System.out.println(row);
                System.out.println(col);

                if (col == 0) {
                    try {
                        URL picUrl = new URL(programList.get(row).getImg());
                        Image img = ImageIO.read(picUrl);
                        img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(img);
                        picture.setIcon(icon);
                        infoField.setText(programList.get(row).getDescription());
                        infoField.setVisible(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
    }

    public JScrollPane buildTable() {
        Object[] columnNames = {"Title", "Starttime", "Endtime"};
        Object[][] data = new Object[100][3];

        model = new DefaultTableModel(data, columnNames);

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);
        addMouseListener();

        pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(1000, 500));

        return pane;
    }

    public void modifyTable(List<Program> list) {

        programList = list;
        if (model != null) {
            model.setRowCount(0);
            model.setRowCount(list.size());
        }

        for (int i = 0; i < list.size(); i++) {
            model.setValueAt(list.get(i).getTitle(), i, 0);
            model.setValueAt(list.get(i).getStartTime(), i, 1);
            model.setValueAt(list.get(i).getEndTime(), i, 2);
            model.fireTableDataChanged();
        }
        //tablePanel.updateUI();
    }

/*
    public void updatePanel(JScrollPane pane) {
        tablePanel.removeAll();
        tablePanel.add(pane);
        tablePanel.updateUI();
    }
*/
    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuItems = new ArrayList<>();

        JMenu firstMenu = new JMenu("Channel List");
        for (int i = 0; i < channelNames.size(); i++) {
            JMenuItem item = new JMenuItem(channelNames.get(i));
            menuItems.add(item);
            firstMenu.add(item);
        }

        menuBar.add(firstMenu);

        return menuBar;
    }

    public void addActionListener(ActionListener e, String channel) {
        switch (channel) {
            case "P1":
                menuItems.get(0).addActionListener(e);
                break;
            case "P2":
                menuItems.get(1).addActionListener(e);
                break;
            case "P3":
                menuItems.get(2).addActionListener(e);
                break;
            case "P4 Blekinge":
                menuItems.get(3).addActionListener(e);
                break;
            case "P4 Dalarna":
                menuItems.get(4).addActionListener(e);
                break;
            case "P4 Gotland":
                menuItems.get(5).addActionListener(e);
                break;
            case "P4 Gävleborg":
                menuItems.get(6).addActionListener(e);
                break;
            case "P4 Göteborg":
                menuItems.get(7).addActionListener(e);
                break;
            case "P4 Halland":
                menuItems.get(8).addActionListener(e);
                break;
            case "P4 Jämtland":
                menuItems.get(9).addActionListener(e);
                break;
            default:
                System.out.println();
                break;
        }
    }
}
