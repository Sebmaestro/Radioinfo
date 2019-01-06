package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Author: Sebastian Arledal c17sal
 * 2019-01-06
 *
 * Class Date:
 *
 * Class to get times and dates
 */
public class Date {

    /**
     * Method getCurrentTime:
     *
     * Gets the current time
     * @return currentTime - current time as LocalDateTime object
     */
    public LocalDateTime getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        currentTime.format(dtf);

        return currentTime;
    }

    /**
     * Method getCurrentDate:
     *
     * Gets current date on format YYYY-MM-DD
     * @return currentdate - todays date as string
     */
    public String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

        return dateFormat.format(cal.getTime());
    }

    /**
     * Method getYesterdaysDate:
     *
     * Gets yesterdays date on format YYYY-MM-DD
     * @return yesyerdaysdate - yesterdays date as string
     */
    public String getYesterdaysDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

        return dateFormat.format(cal.getTime());
    }

    /**
     * Method getTomorrowsDate
     *
     * Gets tomorrows date on format YYYY-MM-DD
     * @return tomorrowsdate - tomorrows date as string
     */
    public String getTomorrowsDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");

        return dateFormat.format(cal.getTime());
    }
}
