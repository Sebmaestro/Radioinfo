package view;

import model.Program;

import java.awt.event.ActionListener;
import java.util.List;

/**
 * Author: Sebastian Arledal c17sal
 * 2019-01-06
 *
 * Interface Gui
 */
public interface Gui {

    void popupInfo(String s);

    void refreshGui();

    void setDataForTable(List<Program> list1, List<Program> list2,
                         List<Program> list3);

    void addActionListenerForUpdate(ActionListener e);

    void addActionListenerForAuthor(ActionListener e);

    void addActionListenerForHelp(ActionListener e);

    void addActionListenerForChannels(ActionListener e, String channel);


}
