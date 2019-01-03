package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeAndDate {

    public String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        System.out.println("Current time of the day using TimeAndDate - 12 hour format: " + formattedDate);

        return formattedDate;
    }

    public String getCurrentDate() {
        /*
        Date date = new Date();
        String strDate = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(strDate);
        String formatted = dateFormat.format(date);
        System.out.println(dateFormat.format(formatted)); //2016/11/16 12:08:43

        return date.toString();
        */
        Date date = new Date();
        String strDateFormat = "YYYY-MM-DD";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        System.out.println(formattedDate);

        return formattedDate;
    }
}
