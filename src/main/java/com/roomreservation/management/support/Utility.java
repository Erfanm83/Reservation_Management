package com.roomreservation.management.support;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static List<LocalDate> GetDates(LocalDate initialDate, int repeatPerWeek) {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i <= repeatPerWeek; ++i) {
            LocalDate date = initialDate.plusWeeks(i); // arg(i): int -> long
            dates.add(date);
        }
        return dates;
    }
}