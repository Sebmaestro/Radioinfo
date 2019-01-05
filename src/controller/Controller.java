package controller;

import model.*;
import view.Gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Gui gui;
    private Parser parser;
    private List<Channel> channelList;
    //private List<Program> currentDateList;

    public Controller() {
        //currentDateList = new ArrayList<>();
        parser = new Parser();
        channelList = parser.parseChannels("http://api.sr.se/api/v2/channels");
        gui = new Gui(setChannelNames());


        for (int i = 0; i < channelList.size(); i++) {
            setListenerForChannels(channelList.get(i).getName());
        }
        setListenerForAuthor();
        setListenerForHelp();
        setListenerForUpdate();


        //setListenerForChannels("p1");

        //channelNames = setChannelNames();
    }

    private ArrayList<String> setChannelNames() {
        ArrayList<String> channelNames = new ArrayList<>();

        for (int i = 0; i < channelList.size(); i++) {
            channelNames.add(channelList.get(i).getName());
        }
        return channelNames;
    }

    private void setListenerForUpdate() {

    }

    private void setListenerForAuthor() {
        gui.addActionListenerForAuthor(e -> {
            JOptionPane.showMessageDialog(null,
                    "Made by Sebastian Arledal - c17sal");

        });
    }

    private void setListenerForHelp() {
        gui.addActionListenerForHelp(e -> {
            JOptionPane.showMessageDialog(null,
                    "This is a program that shows the desired tableau" +
                            " for a chosen channel from SR"+"\n\n"+
            "To choose a channel, simply click on it from the channels menu.\n" +
                            "To Refresh the tableau, click the refresh button" +
                            " in the update menu.\n"+"If you wish to get more" +
                            " info about a program, simply click on the " +
                            "program from the tableau");
        });
    }

    private void setListenerForChannels(String channel) {
        gui.addActionListenerForChannels(e -> {
            String id = null;
            for (int i = 0; i < channelList.size(); i++) {
                if (channelList.get(i).getName().equals(channel)) {
                    id = channelList.get(i).getID();
                }
            }

            TimeAndDate x = new TimeAndDate();
            String yesterdaysDate = x.getYesterdaysDate();
            String todaysDate = x.getCurrentDate();
            String tomorrowsDate = x.getTomorrowsDate();
            String time = x.getCurrentTime();

            List<Program> yesterdaysDateList = parser.parseTableau(
                    "http://api.sr.se/api/v2/scheduledepisodes?channelid="+
                            id+"&pagination=false&date="+yesterdaysDate);

            List<Program> currentDateList = parser.parseTableau(
                    "http://api.sr.se/api/v2/scheduledepisodes?channelid="+
                            id+"&pagination=false&date="+todaysDate);

            List<Program> tomorrowsDateList = parser.parseTableau(
                    "http://api.sr.se/api/v2/scheduledepisodes?channelid="+
                    id+"&pagination=false&date="+tomorrowsDate);

            ListSorter sorter = new ListSorter();
            List<Program> finalList = sorter.sort(time, yesterdaysDateList, currentDateList, tomorrowsDateList);

            List<Program> pastPrograms = sorter.hasBeenBroadcasted(finalList, time);
            List<Program> futurePrograms = sorter.willBeBroadcasted(finalList, time);
            Program currentProgram = sorter.currentlyBroadcasting(finalList, time);

            gui.modifyTable(finalList, pastPrograms, futurePrograms, currentProgram);
            gui.refreshGui();

        }, channel);
    }
}
