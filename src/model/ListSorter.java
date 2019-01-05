package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListSorter {

    private static final long hour = 3600*1000;

    public List<Program> sort(String time, List<Program> yesterday,
                              List<Program> today, List<Program> tomorrow) {
        List<Program> finalList = new ArrayList<>();
        List<Program> allDaysList = new ArrayList<>();
        allDaysList.addAll(yesterday);
        allDaysList.addAll(today);
        allDaysList.addAll(tomorrow);
        //DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Program p:allDaysList) {

            LocalDateTime currentTime = LocalDateTime.parse(time, dtf);
            LocalDateTime programStartTime = LocalDateTime.parse(p.getStartTime(), dtf);
            System.out.println("hej");

            if (programStartTime.compareTo(currentTime.minusHours(12)) >= 0 &&
                    programStartTime.compareTo(currentTime.plusHours(12)) <= 0) {
                finalList.add(p);
            }
        }
        System.out.println(finalList.size());

        return finalList;
    }

    public List<Program> hasBeenBroadcasted(List<Program> list, String time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Program> pastPrograms = new ArrayList<>();

        for (Program p:list) {
            LocalDateTime currentTime = LocalDateTime.parse(time, dtf);
            LocalDateTime programEndTime = LocalDateTime.parse(p.getEndTime(), dtf);

            if (currentTime.compareTo(programEndTime) > 0) {
                pastPrograms.add(p);
            }
        }

        return pastPrograms;
    }

    public List<Program> willBeBroadcasted(List<Program> list, String time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Program> futurePrograms = new ArrayList<>();

        for (Program p:list) {
            LocalDateTime currentTime = LocalDateTime.parse(time, dtf);
            LocalDateTime programStartTime = LocalDateTime.parse(p.getStartTime(), dtf);

            if (currentTime.compareTo(programStartTime) < 0) {
                futurePrograms.add(p);
            }
        }

        return futurePrograms;
    }

    public Program currentlyBroadcasting(List<Program> list, String time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Program p:list) {
            LocalDateTime currentTime = LocalDateTime.parse(time, dtf);
            LocalDateTime programStartTime = LocalDateTime.parse(p.getStartTime(), dtf);
            LocalDateTime programEndTime = LocalDateTime.parse(p.getEndTime(), dtf);

            if (currentTime.compareTo(programStartTime) > 0 && currentTime.compareTo(programEndTime) < 0) {
                return p;
            }
        }
        return null;
    }
}
