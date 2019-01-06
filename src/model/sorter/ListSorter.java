package model.sorter;

import model.Program;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Sebastian Arledal c17sal
 * 2019-01-06
 *
 * Class ListSorter:
 *
 * Class to sort lists
 */
public class ListSorter implements Sorter{

    /**
     * Method sort:
     *
     * Sorts the lists into a new list if the lists programs starttime is
     * in the span of (-12hours - Currenttime - +12hours)
     * @param time - current time
     * @param yesterday - list with yesterdays programs
     * @param today - list with todays programs
     * @param tomorrow - list with tomorrows programs
     * @return finalList - the sorted and completed list
     */
    public List<Program> sort(LocalDateTime time, List<Program> yesterday,
                              List<Program> today, List<Program> tomorrow) {
        List<Program> finalList = new ArrayList<>();
        List<Program> allDaysList = new ArrayList<>();
        allDaysList.addAll(yesterday);
        allDaysList.addAll(today);
        allDaysList.addAll(tomorrow);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss");

        for (Program p:allDaysList) {
            LocalDateTime programStartTime = LocalDateTime.parse(
                    p.getStartTime(), dtf);

            if (programStartTime.compareTo(time.minusHours(12)) >= 0 &&
                    programStartTime.compareTo(time.plusHours(12)) <= 0) {
                finalList.add(p);
            }
        }
        return finalList;
    }

    /**
     * Method hasBeenBroadcasted:
     *
     * Sorts a list and saves the programs that have been broadcasted to a new
     * list
     * @param list - list with programs
     * @param time - current time
     * @return pastPrograms - list with all past programs
     */
    public List<Program> hasBeenBroadcasted(List<Program> list,
                                            LocalDateTime time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss");
        List<Program> pastPrograms = new ArrayList<>();

        for (Program p:list) {
            LocalDateTime programEndTime = LocalDateTime.parse(
                    p.getEndTime(), dtf);

            if (time.compareTo(programEndTime) > 0) {
                pastPrograms.add(p);
            }
        }
        return pastPrograms;
    }

    /**
     * Method willBeBroadcasted:
     *
     * Sorts a list and saves the programs that have not been broadcasted yet
     * to a new list
     * @param list - list with programs
     * @param time - current time
     * @return futurePrograms - list with all future programs
     */
    public List<Program> willBeBroadcasted(List<Program> list,
                                           LocalDateTime time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss");
        List<Program> futurePrograms = new ArrayList<>();

        for (Program p:list) {
            LocalDateTime programStartTime = LocalDateTime.parse(
                    p.getStartTime(), dtf);

            if (time.compareTo(programStartTime) < 0) {
                futurePrograms.add(p);
            }
        }
        return futurePrograms;
    }
}
