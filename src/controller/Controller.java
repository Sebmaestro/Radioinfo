package controller;

import model.*;
import view.Gui;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Gui gui;
    private Parser parser;
    private List<Channel> channelList;
    private String currentChannel;
    //private List<Program> currentDateList;

    private List<Program> pastPrograms;
    private List<Program> futurePrograms;
    private Program currentProgram;
    private List<Program> finalList;

    public Controller() {
        parser = new Parser();
        channelList = parser.parseChannels("http://api.sr.se/api/v2/channels");
        gui = new Gui(setChannelNames());
        //updateInfoAfterOneHour();
        queueUpdate();



        for (int i = 0; i < channelList.size(); i++) {
            setListenerForChannelButtons(channelList.get(i).getName());
        }
        setListenerForAuthorButton();
        setListenerForHelpButton();
        setListenerForUpdateButton();
    }


    /*
    private class Worker extends SwingWorker<Void,Void> {

        @Override
        protected Void doInBackground() {

            getProgramLists(currentChannel);

            return null;
        }

        @Override
        protected void done() {
            updateGuiInfo();
        }
    }
    */


    private void queueUpdate(){
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
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

    private void doUpdate() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
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


    private void updateInfoAfterOneHour() {
        TimeAndDate time = new TimeAndDate();
        LocalDateTime startTime = time.getCurrentTime();

        //When time has gone 1hr:
        while (true) {
            if (startTime.plusMinutes(5).compareTo(time.getCurrentTime()) < 0) {
                //Worker worker = new Worker();
                //worker.execute();

                //Måste uppdatera här för att den ska snurra i evighet
                getProgramLists(currentChannel);
                updateGuiInfo();
                //System.out.println("BOOM");
                JOptionPane.showMessageDialog(null, "refreshed");
                startTime = time.getCurrentTime();
            }
        }
    }

    private synchronized void getProgramLists(String channel) {
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
        LocalDateTime time = x.getCurrentTime();

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
        finalList = sorter.sort(time, yesterdaysDateList, currentDateList, tomorrowsDateList);

        pastPrograms = sorter.hasBeenBroadcasted(finalList, time);
        futurePrograms = sorter.willBeBroadcasted(finalList, time);
        currentProgram = sorter.currentlyBroadcasting(finalList, time);

        //updateGuiInfo(finalList, pastPrograms, futurePrograms, currentProgram);
    }

    private void updateGuiInfo() {
        gui.modifyTable(finalList, pastPrograms, futurePrograms, currentProgram);
        gui.refreshGui();
    }

    private ArrayList<String> setChannelNames() {
        ArrayList<String> channelNames = new ArrayList<>();

        for (int i = 0; i < channelList.size(); i++) {
            channelNames.add(channelList.get(i).getName());
        }
        return channelNames;
    }

    private void setListenerForUpdateButton() {
        gui.addActionListenerForUpdate(e -> {
            //Worker worker = new Worker();
            //worker.execute();
            //getProgramLists(currentChannel);
            doUpdate();
        });
    }

    private void setListenerForAuthorButton() {
        gui.addActionListenerForAuthor(e -> {
            gui.popupInfo("Made by Sebastian Arledal - c17sal");
        });
    }

    private void setListenerForHelpButton() {
        gui.addActionListenerForHelp(e -> {
            gui.popupInfo("This is a program that shows the desired tableau" +
                    " for a chosen channel from SR"+"\n\n"+
                    "To choose a channel, simply click on it from the channels menu.\n" +
                    "To Refresh the tableau, click the refresh button" +
                    " in the doUpdate menu.\n"+"If you wish to get more" +
                    " info about a program, simply click on the " +
                    "program from the tableau");
        });
    }

    private void setListenerForChannelButtons(String channel) {
        gui.addActionListenerForChannels(e -> {
            //Worker worker = new Worker();
            //worker.execute();
            doUpdate();

            //getProgramLists(channel);
            currentChannel = channel;

        }, channel);
    }
}
