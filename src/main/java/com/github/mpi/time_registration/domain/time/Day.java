package com.github.mpi.time_registration.domain.time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day {

    private static Pattern DATE_FORMAT = Pattern.compile("(\\d{4})/(\\d{2})/(\\d{2})");

    private final String date;

    public static Day of(String date) {

        if(date == null){
            throw new IllegalArgumentException("Invalid date: null");
        }
        
        Matcher matcher = DATE_FORMAT.matcher(date);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(String.format("Invalid date format: %s. Expected format is: yyyy/mm/dd", date));
        }

        int year = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int day = Integer.parseInt(matcher.group(3));

        if (!DateValidator.isValidDay(year, month, day)) {
            throw new IllegalArgumentException(String.format("Invalid date: %s", date));
        }

        return new Day(date);
    }

    private Day(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public boolean equals(Object x) {

        if (!(x instanceof Day)) {
            return false;
        }

        Day other = (Day) x;

        return date.equals(other.date);
    }

    @Override
    public String toString() {
        return date;
    }

    public boolean before(Day day) {
        return date.compareTo(day.date) <= 0;
    }

    public boolean after(Day day) {
        return date.compareTo(day.date) >= 0;
    }

    public boolean in(Month month) {
        return after(month.firstDay()) && before(month.lastDay());
    }
}
