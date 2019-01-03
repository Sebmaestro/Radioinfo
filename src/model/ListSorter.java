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
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //String str1 = time.substring(10);

        for (Program p:allDaysList) {
            //String str2 = p.getStartTime().substring(10);
            //String programStartTime = p.getStartTime();

            //try {
                LocalDateTime currentTime = LocalDateTime.parse(time, dtf);
                LocalDateTime programStartTime = LocalDateTime.parse(p.getStartTime(), dtf);
            System.out.println("hej");

                //currentTime.
                /*
                Date currentTime = sdf.parse(time);
                Date programStartTime = sdf.parse(p.getStartTime());

                Date bigTime = new Date(programStartTime.getTime() + 12*hour);
                */


                if (programStartTime.compareTo(currentTime.minusHours(12)) >= 0 &&
                        programStartTime.compareTo(currentTime.plusHours(12)) <= 0) {
                    finalList.add(p);
                }
                //if (programStartTime <= currentTime) {

               // }

                //Date currentTimee = new Date(currentTime.getTime() + 12*hour);

                /*
                if (currentTimee.compareTo(programStartTime) <= 0) {
                    finalList.add(p);
                }
                */


            //} catch (ParseException e) {
            //    e.printStackTrace();
           // }

        }
        System.out.println(finalList.size());

        return finalList;
    }
}
