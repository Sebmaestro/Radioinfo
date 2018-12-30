package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Channels {
    private URL radioUrl;

    /*
    public Channels(String url) {
        try {
            radioUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(radioUrl.openStream()));
            while (reader.readLine() != null) {
                System.out.println(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    public Channels() {

    }

    public JSONArray tjena(String url) {
        JSONArray jsonArray = null;
        try {
            radioUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) radioUrl.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();
            Scanner scanner = new Scanner(radioUrl.openStream());

            String inline = scanner.nextLine();

            /*
            while(scanner.hasNext()) {
                System.out.println(scanner.nextLine());
                inline+=scanner.nextLine();
            }
            */
            //scanner.close();

            JSONParser parser = new JSONParser();
            JSONObject jobj = (JSONObject)parser.parse(inline);
            jsonArray = (JSONArray) jobj.get("channels");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}
