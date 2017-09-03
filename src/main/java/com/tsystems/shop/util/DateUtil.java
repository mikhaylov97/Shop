package com.tsystems.shop.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate getLocalDateNow() {
        return LocalDate.now();
    }

    public static String getLocalDateNowInDtfFormat() {
        return dtf.format(LocalDateTime.now());
    }

    public static LocalDate getLocalDateFromString(String expression) {
        return LocalDate.parse(expression, dtf);
    }

    public static DateTimeFormatter getDtf() {
        return dtf;
    }

    public static void setDtf(DateTimeFormatter dtf) {
        DateUtil.dtf = dtf;
    }
}
