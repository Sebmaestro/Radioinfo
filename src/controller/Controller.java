package controller;

import model.*;
import model.parser.Parser;
import model.parser.RadioParser;
import model.sorter.ListSorter;
import model.sorter.Sorter;
import view.Gui;
import view.RadioGui;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Sebastian Arledal c17sal
 * 2019-01-06
 *
 * Class Controller:
 *
 * Handles the communication between model and view
 */
public class Controller {
    private Gui radioGui;
    private Parser radioParser;
    private String currentChannel;
    private Sorter sorter;
    private Date date;

    private List<Channel> channelList;
    private List<Program> pastPrograms;
    private List<Program> futurePrograms;
    private List<Program> finalList;

    private boolean safeToUpdate = false;

    /**
     * Constructor:
     *
     * Initializes model and view objects and sets all buttonlisteners. Also
     * queues a pending radiotableau update.
     */
    public Controller() {
        sorter = new ListSorter();
        radioParser = new RadioParser();
        date = new Date();
        channelList = radioParser.parseChannels(
                "http://api.sr.se/api/v2/channels/?pagination=false");
        radioGui = new RadioGui(setChannelNames());
        Timer timer = new Timer(3600 * 1000, E -> queueUpdate());
        timer.start();


        //radioGui.addActionListenerForChannels();

        for (Channel aChannelList : channelList) {
            setListenerForChannelButtons(aChannelList.getName());
        }
        setListenerForAuthorButton();
        setListenerForHelpButton();
        setListenerForUpdateButton();
    }

    /**
     * Method queueUpdate:
     *
     * Creates a swingworker that will update the tableau each hour.
     */
    private void queueUpdate(){
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                updateInfoAfterOneHour();
                return null;
            }

            @Override
            protected void done() {
                updateGuiInfo();
            }
        };
        worker.execute();
    }

    /**
     * Method doUpdate:
     *
     * Creates a swingworker that will update the tableau instantly.
     */
    private void doUpdate() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                getProgramLists(currentChannel);
                return null;
            }

            @Override
            protected void done() {
                updateGuiInfo();
            }
        };
        worker.execute();
    }

    /**
     * Method updateInfoAfterOneHour:
     *
     * Updates the tableau each hour by a swingworker.
     */
    private synchronized void updateInfoAfterOneHour() {
        getProgramLists(currentChannel);
        radioGui.popupInfo("Refreshed!");
    }

    /**
     * Method getProgramLists:
     *
     * Gets 3 parsed lists with programs from the used ID to then use to show
     * programs in GUI.
     * @param channel - Channel to get programs from
     */
    private synchronized void getProgramLists(String channel) {
        String id = null;
        for (Channel aChannelList : channelList) {
            if (aChannelList.getName().equals(channel)) {
                id = aChannelList.getID();
            }
        }

        String yesterdaysDate = date.getYesterdaysDate();
        String todaysDate = date.getCurrentDate();
        String tomorrowsDate = date.getTomorrowsDate();
        LocalDateTime time = date.getCurrentTime();

        //Parses all programs from yesterday
        List<Program> yesterdaysDateList = radioParser.parsePrograms(
                "http://api.sr.se/api/v2/scheduledepisodes?channelid="+
                        id+"&pagination=false&date="+yesterdaysDate);

        //Parses all programs from today
        List<Program> currentDateList = radioParser.parsePrograms(
                "http://api.sr.se/api/v2/scheduledepisodes?channelid="+
                        id+"&pagination=false&date="+todaysDate);

        //Parses all programs from tomorrow
        List<Program> tomorrowsDateList = radioParser.parsePrograms(
                "http://api.sr.se/api/v2/scheduledepisodes?channelid="+
                        id+"&pagination=false&date="+tomorrowsDate);


        finalList = sorter.sort(time, yesterdaysDateList, currentDateList,
                tomorrowsDateList);
        pastPrograms = sorter.hasBeenBroadcasted(finalList, time);
        futurePrograms = sorter.willBeBroadcasted(finalList, time);

        safeToUpdate = true;
    }

    /**
     * Method updateGuiInfo:
     *
     * Updates the GUI with relevant programs.
     */
    private synchronized void updateGuiInfo() {
        radioGui.setDataForTable(finalList, pastPrograms, futurePrograms);
        radioGui.refreshGui();
    }

    /**
     * Method setChannelNames:
     *
     * Gets a list with only channel names to be used when building the channel
     * buttons in GUI.
     * @return channelNames - list of channelnames
     */
    private ArrayList<String> setChannelNames() {
        ArrayList<String> channelNames = new ArrayList<>();

        for (Channel aChannelList : channelList) {
            channelNames.add(aChannelList.getName());
        }
        return channelNames;
    }

    /**
     * Method setListenerForUpdateButton:
     *
     * Sets the listener for the update button. When clicked the GUI will
     * refresh and a popuptext will notify the user.
     */
    private void setListenerForUpdateButton() {
        radioGui.addActionListenerForUpdate(e -> {
            if (safeToUpdate) {
                doUpdate();
                radioGui.popupInfo("Refreshed!");
            }
        });
    }

    /**
     * Method setListenerForAuthorButton:
     *
     * Sets the listener for the author button. When clicked a popuptext will
     * notify the user.
     */
    private void setListenerForAuthorButton() {
        radioGui.addActionListenerForAuthor(e ->
                radioGui.popupInfo("Made by Sebastian Arledal - c17sal"));
    }

    /**
     * Method setListenerForHelpButton:
     *
     * Sets the listener for the help button. When clicked a popuptext will
     * notify the user.
     */
    private void setListenerForHelpButton() {
        radioGui.addActionListenerForHelp(e ->
                radioGui.popupInfo("This is a program that shows the desired "
                        + "tableau"+ " for a chosen channel from SR"+"\n\n"+
                        "To choose a channel, simply click on it from the " +
                        "channels menu.\n" +
                        "To Refresh the tableau, click the refresh button" +
                        " in the update menu.\n"+"If you wish to get more" +
                        " info about a program, simply click on the " +
                        "program from the tableau\n"+"The tableau will update" +
                        " by itself every hour"));
    }

    /**
     * Method setListenerForChannelButtons:
     *
     * Sets the listener for a channel button. When clicked the chosen channels
     * programs will be displayed.
     * @param channel - The channel to display
     */
    private void setListenerForChannelButtons(String channel) {
        radioGui.addActionListenerForChannels(e -> {
            currentChannel = channel;
            doUpdate();
        });
    }
}
