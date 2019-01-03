package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeAndDate {

    public String getCurrentTime() {

        //Calendar cal = Calendar.getInstance();
        //cal.setTime(new SimpleDateFormat("HH:mm:ss").parse());
        //cal.add(Calendar.DATE, 0);

        //DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        //System.out.println("Time is = "+dateFormat.format(cal.getTime()));
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        Date date = new Date();


        return dateFormat.format(date);
        //return new Date();
    }

    public String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        System.out.println("Todays date = "+dateFormat.format(cal.getTime()));

        return dateFormat.format(cal.getTime());
    }

    public String getYesterdaysDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        System.out.println("Yesterdays date = "+dateFormat.format(cal.getTime()));

        return dateFormat.format(cal.getTime());
    }

    public String getTomorrowsDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        System.out.println("Tomorrows date = "+dateFormat.format(cal.getTime()));

        return dateFormat.format(cal.getTime());
    }
}
