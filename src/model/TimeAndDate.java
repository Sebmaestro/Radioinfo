package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

public class TimeAndDate {

    public LocalDateTime getCurrentTime() {

        //Calendar cal = Calendar.getInstance();
        //cal.setTime(new SimpleDateFormat("HH:mm:ss").parse());
        //cal.add(Calendar.DATE, 0);

        //DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        //System.out.println("Time is = "+dateFormat.format(cal.getTime()));
        //DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        currentTime.format(dtf);
        //currentTime
        //Date date = new Date();


        //return currentTime.truncatedTo(ChronoUnit.SECONDS);
        return currentTime;
        //return new Date();
    }

    public String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

        return dateFormat.format(cal.getTime());
    }

    public String getYesterdaysDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

        return dateFormat.format(cal.getTime());
    }

    public String getTomorrowsDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

        return dateFormat.format(cal.getTime());
    }
}
