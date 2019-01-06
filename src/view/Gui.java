package view;

import model.Program;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
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

    private JMenuItem updateData;
    private JMenuItem author;
    private JMenuItem help;


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

    public void popupInfo(String s) {
        JOptionPane.showMessageDialog(null, s);
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
        infoField.setFont(infoField.getFont().deriveFont(16f));
        infoField.setEditable(false);
        infoField.setLineWrap(true);
        infoField.setPreferredSize(new Dimension(950, 25));
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

                //System.out.println(row);
                //System.out.println(col);

                if (col == 0 || col == 1 || col == 2 || col == 3) {
                    try {
                        //infoField.setVisible(false);
                        URL picUrl = new URL(programList.get(row).getImg());
                        Image img = ImageIO.read(picUrl);
                        img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(img);
                        picture.setIcon(icon);
                        picture.setVisible(true);
                        infoField.setText(programList.get(row).getDescription());
                        infoField.setVisible(true);
                    } catch (IOException e) {
                        try {
                            BufferedImage img = ImageIO.read(new File("src/resources/cross.jpg"));
                            Image image = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                            ImageIcon icon = new ImageIcon(image);
                            picture.setIcon(icon);
                            infoField.setText(programList.get(row).getDescription());
                            infoField.setVisible(true);
                        } catch (IOException e1) {
                            picture.setVisible(false);
                            System.out.println("Could not find image");
                        }
                    }
                }
            }
        });
    }

    public JScrollPane buildTable() {
        Object[] columnNames = {"Title", "Starttime", "Endtime", "Status"};
        Object[][] data = new Object[channelNames.size()][4];

        model = new DefaultTableModel(data, columnNames);

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);

        addMouseListener();

        pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(1000, 550));

        return pane;
    }

    public void modifyTable(List<Program> allPrograms, List<Program> pastPrograms, List<Program> futurePrograms, Program currentProgram) {

        int i;
        programList = allPrograms;
        if (model != null) {
            model.setRowCount(0);
            model.setRowCount(allPrograms.size());
        }

        for (i = 0; i < allPrograms.size(); i++) {
            model.setValueAt(allPrograms.get(i).getTitle(), i, 0);
            model.setValueAt(allPrograms.get(i).getStartTime(), i, 1);
            model.setValueAt(allPrograms.get(i).getEndTime(), i, 2);
            //TableCellRenderer renderer = table.getDefaultRenderer(Object.class);
            //model.fireTableDataChanged();
        }

        for (i = 0; i < pastPrograms.size(); i++) {
            //model.setValueAt(pastPrograms.get(i).getTitle(), i, 0);
            //model.setValueAt(pastPrograms.get(i).getStartTime(), i, 1);
            //model.setValueAt(pastPrograms.get(i).getEndTime(), i, 2);
            model.setValueAt("Finished", i, 3);
        }

        model.setValueAt("Broadcasting", i, 3);

        for (int j = i+1; j < futurePrograms.size()+i+1; j++) {
            //model.setValueAt(futurePrograms.get(i).getTitle(), i, 0);
            //model.setValueAt(futurePrograms.get(i).getStartTime(), i, 1);
            //model.setValueAt(futurePrograms.get(i).getEndTime(), i, 2);
            model.setValueAt("Coming up", j, 3);
        }




        //tablePanel.updateUI();
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuItems = new ArrayList<>();

        JMenu firstMenu = new JMenu("Channel List");
        JMenu secondMenu = new JMenu("Update");
        JMenu thirdMenu = new JMenu("About");
        for (int i = 0; i < channelNames.size(); i++) {
            JMenuItem item = new JMenuItem(channelNames.get(i));
            menuItems.add(item);
            firstMenu.add(item);
        }
        updateData = new JMenuItem("Refresh");
        author = new JMenuItem("Author");
        help = new JMenuItem("Help");

        secondMenu.add(updateData);
        thirdMenu.add(author);
        thirdMenu.add(help);

        menuBar.add(firstMenu);
        menuBar.add(secondMenu);
        menuBar.add(thirdMenu);

        return menuBar;
    }

    public void addActionListenerForUpdate(ActionListener e) {
        updateData.addActionListener(e);
    }

    public void addActionListenerForAuthor(ActionListener e) {
        author.addActionListener(e);
    }

    public void addActionListenerForHelp(ActionListener e) {
        help.addActionListener(e);
    }

    public void addActionListenerForChannels(ActionListener e, String channel) {
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
