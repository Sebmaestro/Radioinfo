package controller;

import model.Channel;
import model.Parser;
import model.Program;
import model.TimeAndDate;
import org.json.simple.JSONArray;
import view.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Gui gui;
    private Parser parser;
    private JSONArray json;
    private List<Channel> channelList;
    private List<Program> programList;

    public Controller() {
        programList = new ArrayList<>();
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
            String date = x.getCurrentDate();
            String time = x.getCurrentTime();
            programList = parser.parseTableau("http://api.sr.se/api/v2/scheduledepisodes?channelid="+id+"&date="+
                    date);

            gui.modifyTable(programList);
            gui.refreshGui();

        }, channel);
    }
}
