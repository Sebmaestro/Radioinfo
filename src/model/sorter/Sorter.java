package model.sorter;

import model.Program;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: Sebastian Arledal c17sal
 * 2019-01-06
 *
 * Interface Sorter
 */
public interface Sorter {

    List<Program> sort(LocalDateTime time, List<Program> list1,
                       List<Program> list2, List<Program> list3);

    List<Program> hasBeenBroadcasted(List<Program> list,
                                     LocalDateTime time);

    List<Program> willBeBroadcasted(List<Program> list,
                                    LocalDateTime time);
}
