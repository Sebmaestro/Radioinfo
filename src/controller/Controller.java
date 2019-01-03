package controller;

import model.*;
import view.Gui;

import java.util.ArrayList;
import java.util.Date;
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
            setJMenuListener(channelList.get(i).getName());
        }


        //setJMenuListener("p1");

        //channelNames = setChannelNames();
    }

    private ArrayList<String> setChannelNames() {
        ArrayList<String> channelNames = new ArrayList<>();

        for (int i = 0; i < channelList.size(); i++) {
            channelNames.add(channelList.get(i).getName());
        }
        return channelNames;
    }

    private void setJMenuListener(String channel) {
        gui.addActionListener(e -> {
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

            gui.modifyTable(finalList);
            gui.refreshGui();

        }, channel);
    }
}
