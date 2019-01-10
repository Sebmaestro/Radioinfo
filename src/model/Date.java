package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: Sebastian Arledal c17sal
 * 2019-01-06
 *
 * Class Date:
 *
 * Class to get times and dates
 */
public class Date {
    private LocalDateTime currentTime;

    public Date() {
        currentTime = LocalDateTime.now();
    }

    /**
     * Method getCurrentTime:
     *
     * Gets the current time
     * @return currentTime - current time as LocalDateTime object
     */
    public LocalDateTime getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss");
        currentTime.format(dtf);
        return currentTime;
    }

    /**
     * Method getCurrentDate:
     *
     * Gets current date on format yyyy-MM-dd
     * @return currentdate - todays date as string
     */
    public String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentTime.format(dtf);
    }

    /**
     * Method getYesterdaysDate:
     *
     * Gets yesterdays date on format yyyy-MM-dd
     * @return yesyerdaysdate - yesterdays date as string
     */
    public String getYesterdaysDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentTime.minusDays(1).format(dtf);
    }

    /**
     * Method getTomorrowsDate
     *
     * Gets tomorrows date on format yyyy-MM-dd
     * @return tomorrowsdate - tomorrows date as string
     */
    public String getTomorrowsDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentTime.plusDays(1).format(dtf);
    }
}
