package com.tsystems.shop.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This util helps to work with date
 */
public class DateUtil {

    /**
     * Empty constructor
     */
    private DateUtil() {

    }

    /**
     * DateFormatter field with prepared date pattern - dd-MM-yyyy(14-09-1997) - 14 September 1997
     */
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * @return current date in the system
     */
    public static LocalDate getLocalDateNow() {
        return LocalDate.now();
    }

    /**
     * @return current date as string in prepared format
     */
    public static String getLocalDateNowInDtfFormat() {
        return dtf.format(LocalDateTime.now());
    }

    /**
     * Method try to parse some string expression and return LacalDate
     * @param expression - the string which needs to be parsed
     * @return LocalDate object of the parsed expression
     */
    public static LocalDate getLocalDateFromString(String expression) {
        return LocalDate.parse(expression, dtf);
    }

    /**
     * Method try to parse some string expression and return String
     * @param expression - the string which needs to be parsed
     * @return String object in dtf format of the parsed expression
     */
    public static String getDateFromStringDtfFormat(String expression) {
        return dtf.format(LocalDate.parse(expression, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    /**
     * Method returns DateTimeFormatter with pattern which use browsers
     * @return DateTimeFormatter in yyyy-MM-dd format
     */
    public static DateTimeFormatter getDefaultDateFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    /**
     * @return DateTimeFormatter with the prepared date pattern. See above
     */
    public static DateTimeFormatter getDtf() {
        return dtf;
    }

    /**
     * Get a possibility to set custom format of the date
     * @param dtf - DateTimeFormatter with the prepared pattern
     */
    public static void setDtf(DateTimeFormatter dtf) {
        DateUtil.dtf = dtf;
    }
}
