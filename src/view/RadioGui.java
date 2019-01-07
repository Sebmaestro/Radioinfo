package view;

import model.Program;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Sebastian Arledal c17sal
 * 2019-01-06
 *
 * Class RadioGui:
 *
 * GUI class to display the application as a graphical interface
 */
public class RadioGui extends JFrame implements Gui{
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

    private int menuitem;

    /**
     * Constructor:
     *
     * Builds the GUI
     * @param channelNames - List with channelnames for channelbuttons
     */
    public RadioGui(ArrayList<String> channelNames) {
        super();

        menuitem = 0;

        this.channelNames = channelNames;

        tablePanel = new JPanel();
        add(buildTablePanel(buildTable()), BorderLayout.NORTH);
        add(buildExtraInfoPanel(), BorderLayout.SOUTH);

        setJMenuBar(buildMenuBar());

        setSize(new Dimension(1080, 720));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * Method popupInfo:
     *
     * Notifies the user with a popup textbox
     * @param s - String to show in popup
     */
    public void popupInfo(String s) {
        JOptionPane.showMessageDialog(null, s);
    }

    /**
     * Method buildTablePanel:
     *
     * Creates a panel with the table holding all data on it
     * @param pane - the table with all data
     * @return tablePanel - the panel
     */
    private JPanel buildTablePanel(JScrollPane pane) {
        tablePanel = new JPanel();
        tablePanel.add(pane);


        return tablePanel;
    }

    /**
     * Method buildExtraInfoPanel:
     *
     * Creates a panel for the picture and description of the programs
     * @return extraInfoPanel - the panel
     */
    private JPanel buildExtraInfoPanel() {
        extraInfoPanel = new JPanel();
        extraInfoPanel.setLayout(new FlowLayout());

        picture = new JLabel();
        picture.setPreferredSize(new Dimension(100,100));
        extraInfoPanel.add(picture);

        infoField = new JTextArea();
        infoField.setFont(infoField.getFont().deriveFont(16f));
        infoField.setEditable(false);
        infoField.setLineWrap(true);
        infoField.setPreferredSize(new Dimension(600, 100));
        infoField.setWrapStyleWord(true);
        infoField.setVisible(false);
        infoField.setBackground(extraInfoPanel.getBackground());
        extraInfoPanel.add(infoField);

        return extraInfoPanel;
    }

    /**
     * Method refreshGui:
     *
     * Refreshes the GUI and reset the description and picture
     */
    public void refreshGui() {
        picture.setText(null);
        picture.setIcon(null);
        infoField.setText(null);
        infoField.setVisible(false);
    }

    /**
     * Method addMouseListener:
     *
     * Creates a mouselistener to get data from a specific program when you
     * click on it. The data shown is the programs picture and description
     */
    private void addMouseListener() {
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoField.setText(null);
                int row = table.rowAtPoint(evt.getPoint());
                try {
                    URL picUrl = new URL(programList.get(row).getImg());
                    Image img = ImageIO.read(picUrl);
                    img = img.getScaledInstance(100, 100,
                            Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    picture.setIcon(icon);
                    picture.setVisible(true);
                    infoField.append("Title: ");
                    infoField.append(programList.get(row).getTitle());
                    infoField.append("\n\n");
                    infoField.append(programList.get(row).getDescription());
                    infoField.setVisible(true);
                } catch (IOException e) {
                    try {
                        BufferedImage img = ImageIO.read(RadioGui.class.getResourceAsStream("/cross.jpg"));
                        Image image = img.getScaledInstance(100,
                                100, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(image);
                        picture.setIcon(icon);
                        infoField.append("Title: ");
                        infoField.append(programList.get(row).getTitle());
                        infoField.append("\n\n");
                        infoField.append(programList.get(row).getDescription());
                        infoField.setVisible(true);
                    } catch (IOException e1) {
                        picture.setVisible(false);
                        System.out.println("Could not find image");
                    }
                }
            }
        });
    }

    /**
     * Method buildTable:
     *
     * Builds a JScrollpane which holds a JPanel which in turn holds a
     * DefaultTableModel. The table holds all the
     * @return pane - the JScrollpane(table)
     */
    private JScrollPane buildTable() {
        Object[] columnNames = {"Title", "Starttime", "Endtime", "Status"};
        Object[][] data = new Object[channelNames.size()][4];

        model = new DefaultTableModel(data, columnNames);

        table = new JTable(model);
        table.setDefaultRenderer(Object.class, new CustomCellRenderer());
        table.getTableHeader().setReorderingAllowed(false);
        table.setEnabled(false);
        model.setRowCount(0);

        addMouseListener();

        pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(1000, 500));

        return pane;
    }

    /**
     * Method setDataForTable:
     *
     * Sets all the data for the table
     * @param allPrograms - List with all programs to insert in table
     * @param pastPrograms - List with past programs
     * @param futurePrograms - List with future programs
     */
    public void setDataForTable(List<Program> allPrograms,
                                List<Program> pastPrograms,
                                List<Program> futurePrograms) {

        int i;
        programList = allPrograms;

        //Reset model each time
        if (model != null) {

            model.setRowCount(0);
            model.setRowCount(allPrograms.size());
        }

        //Set core data
        for (i = 0; i < allPrograms.size(); i++) {
            model.setValueAt(allPrograms.get(i).getTitle(), i, 0);
            model.setValueAt(allPrograms.get(i).getStartTime(), i, 1);
            model.setValueAt(allPrograms.get(i).getEndTime(), i, 2);
        }

        //Set status for past programs
        for (i = 0; i < pastPrograms.size(); i++) {
            if (model != null) {
                model.setValueAt("Finished", i, 3);
            } else {
                popupInfo("Couldnt find data");
            }
        }

        //Set status for current program
        if (model != null) {
            model.setValueAt("Broadcasting", i, 3);
        } else {
            popupInfo("Couldnt find data");
        }

        //Set status for future programs
        for (int j = i+1; j < futurePrograms.size()+i+1; j++) {
            model.setValueAt("Coming up", j, 3);
        }
    }

    /**
     * Method buildMenuBar:
     *
     * Creates the menubar
     * @return menuBar - the menubar
     */
    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuItems = new ArrayList<>();

        JMenu firstMenu = new JMenu("Channel List");
        JMenu secondMenu = new JMenu("Update");
        JMenu thirdMenu = new JMenu("About");

        for (String channelName : channelNames) {
            JMenuItem item = new JMenuItem(channelName);
            item.setPreferredSize(new Dimension(150, 17));
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

    /**
     * Method addActionListenerForUpdate:
     *
     * Add actionlistener to updateData button
     * @param e - actionlistener
     */
    public void addActionListenerForUpdate(ActionListener e) {
        updateData.addActionListener(e);
    }

    /**
     * Method addActionListenerForAuthor:
     *
     * Add actionlistener to author button
     * @param e - actionlistener
     */
    public void addActionListenerForAuthor(ActionListener e) {
        author.addActionListener(e);
    }

    /**
     * Method addActionListenerForHelp:
     *
     * Add actionlistener to help button
     * @param e - actionlistener
     */
    public void addActionListenerForHelp(ActionListener e) {
        help.addActionListener(e);
    }

    /**
     * Method addActionListenerForChannels:
     *
     * Adds actionlisteners to a channel button
     * @param e - actionlistener
     */
    public void addActionListenerForChannels(ActionListener e) {
        menuItems.get(menuitem).addActionListener(e);
        menuitem++;
        /*
        for (JMenuItem m:menuItems) {
            m.addActionListener(e);
        }
        */
        /*
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
        */
    }
}
