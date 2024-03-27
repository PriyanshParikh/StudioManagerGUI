package com.example.studiomanagergui;
import java.util.Calendar;

/**
 * The  Date class represents a date in the calendar.
 * It provides methods to check the validity of a date and includes leap year calculations.
 * Implements the  Comparable interface for date comparisons.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int MIN_YEAR = 1;
    public static final int MAX_MONTH = 12;
    public static final int MIN_DAY = 1;
    public static final int MIN_MONTH = 1;
    public static final int MAX_DAY = 31;
    public static final int DAYS_IN_MONTH_WITH_THIRTY_ONE = 31;
    public static final int DAYS_IN_MONTH_WITH_THIRTY = 30;


    /**
     * Constructs a Date object with the specified month, day, and year.
     *
     * @param month The month of the date (1-12).
     * @param day   The day of the date.
     * @param year  The year of the date.
     */
    public Date(int month, int day, int year) {
        this.year = year;
        this.month = month;
        this.day = day;

    }


    /**
     * Gets the year of the date.
     *
     * @return The year of the date.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Gets the month of the date.
     *
     * @return The month of the date.
     */
    public int getMonth() {return this.month;}

    /**
     * Gets the day of the date.
     *
     * @return The day of the date.
     */
    public int getDay() {
        return this.day;
    }


    /**
     * Checks if the date is a valid calendar date.
     *
     * @return  true if the date is valid, false otherwise.
     */
    public boolean isValid(){
        if (year < MIN_YEAR || month < MIN_MONTH || month > MAX_MONTH || day < MIN_DAY || day > MAX_DAY) {
            return false;
        }
        if (day > new int[]{0, DAYS_IN_MONTH_WITH_THIRTY_ONE, 28 + (isLeapYear(year) ? 1 : 0),
                DAYS_IN_MONTH_WITH_THIRTY_ONE,
                DAYS_IN_MONTH_WITH_THIRTY, DAYS_IN_MONTH_WITH_THIRTY_ONE,
                DAYS_IN_MONTH_WITH_THIRTY, DAYS_IN_MONTH_WITH_THIRTY_ONE,
                DAYS_IN_MONTH_WITH_THIRTY_ONE, DAYS_IN_MONTH_WITH_THIRTY,
                DAYS_IN_MONTH_WITH_THIRTY_ONE, DAYS_IN_MONTH_WITH_THIRTY,
                DAYS_IN_MONTH_WITH_THIRTY_ONE}[month]) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the date is a future date or today.
     *
     * @return  true if the date is today or future date, false otherwise.
     */
    public boolean checkToday_Or_Future(){
        // Get today's date
        Calendar today = Calendar.getInstance();
        Calendar inputDate = Calendar.getInstance();
        inputDate.set(year, month - 1, day, 0, 0, 0);
        inputDate.set(Calendar.MILLISECOND, 0);

        if (inputDate.after(today) || inputDate.equals(today)) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a year is a leap year.
     *
     * @param year The year to check for leap year.
     * @return true if the year is a leap year, false otherwise.
     */
    public static boolean isLeapYear(int year) {
        return (year % QUADRENNIAL == 0) && ((year % CENTENNIAL != 0) || (year % QUATERCENTENNIAL == 0));
    }

    /**
     * Compares this date to another date.
     *
     * @param other The date to compare to.
     * @return A negative integer, zero, or a positive integer as this date is before, equal to, or after the
     * specified date.
     */

    public int compareTo(Date other) {
        // Compare year
        if (this.year < other.year) {
            return 1;
        } else if (this.year > other.year) {
            return -1;
        }

        // Compare month
        if (this.month < other.month) {
            return 1;
        } else if (this.month > other.month) {
            return -1;
        }

        // Compare day
        if (this.day < other.day) {
            return 1;
        } else if (this.day > other.day) {
            return -1;
        }

        // Dates are equal
        return 0;
    }

    /**
     * Formats a date as a string.
     *
     * @param date The date to format.
     * @return A formatted string representation of the date.
     */
    public static String formatDate(Date date) {
        int month = date.getMonth();
        int day = date.getDay();
        int year = date.getYear();

        return String.format("%d/%d/%04d", month, day, year);
    }

    /**
     * Test bed main function for the Date class to assess the isValid method
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args){
        Date test1 = new Date(0, 1, 2022); // Invalid: month is less than 1
        Date test2 = new Date(13, 15, 2023); // Invalid: month is greater than 12, day is greater than 31
        Date test3 = new Date(4, 31, 2021); // Inalid: April 31st is invalid
        Date test4 = new Date(10, 30, 0); // Invalid: 0 year
        Date test5 = new Date(2, 29, 2021); // Invalid: not a leap year but 2/29
        Date test6 = new Date(6, 22, 2022); // Valid: a normal valid date
        Date test7 = new Date(2, 20, 2024); // Valid:  valid leap yaer date

        System.out.print("Testing date: " + Date.formatDate(test1) + " " + test1.isValid() + "\n" + //false
                "Testing date: " + Date.formatDate(test2) + " " + test2.isValid() + "\n" +  //false
                "Testing date: " + Date.formatDate(test3) + " " + test3.isValid() + "\n" +  //false
                "Testing date: " + Date.formatDate(test4) + " " + test4.isValid() + "\n" +  //false
                "Testing date: " + Date.formatDate(test5) + " " + test5.isValid() + "\n" +  //false
                "Testing date: " + Date.formatDate(test6) + " " + test6.isValid() + "\n" +  //true
                "Testing date: " + Date.formatDate(test7) + " " + test7.isValid() + "\n" ); //true
    }

    //should be 5 false statements, followed by 2 true statements

    /**
     * Checks if someone is 18 years old older based on their birthday.
     *
     * @return  true if 18 or older, false otherwise.
     */
    public boolean checkProperAge() {
        // Get today's date
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - year;

        // Check if the member is 18 years or older based on the birthday month
        return age > 18 || (age == 18 && today.get(Calendar.MONTH) >= month - 1);
    }

}
