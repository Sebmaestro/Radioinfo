import model.Channels;
import view.Gui;

public class Main {
    public static void main(String[] args) {
        Channels c = new Channels();
        c.tjena("http://api.sr.se/api/v2/channels?format=json");
        //Gui gui = new Gui();
    }
}