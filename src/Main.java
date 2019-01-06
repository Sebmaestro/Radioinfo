import controller.Controller;
import model.TimeAndDate;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Parser c = new Parser();
        //c.parseChannels("http://api.sr.se/api/v2/channels?format=json");
        //Gui gui = new Gui();
        SwingUtilities.invokeLater(()-> {
            new Controller();
        });

        /*
        new TimeAndDate().getCurrentTime();
        new TimeAndDate().getCurrentDate();
        new TimeAndDate().getTomorrowsDate();
        new TimeAndDate().getYesterdaysDate();
        */
    }
}