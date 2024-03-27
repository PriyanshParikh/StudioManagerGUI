package com.example.studiomanagergui;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the Date class isValid() method.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class DateTest {

    /**
     * Test case: Month is less than 1, making the date invalid.
     */
    @Test
    public void testMonth_Less_Than_One(){
        Date date = new Date(0, 1, 2022); // Invalid: month is less than 1
        assertFalse(date.isValid());
    }

    /**
     * Test case: Month is greater than 12 or day is greater than 31, making the date invalid.
     */
    @Test
    public void testMonth_Greater_Than_Twelve(){
        Date date = new Date(13, 15, 2023); // Invalid: month is greater than 12, day is greater than 31
        assertFalse(date.isValid());
    }

    /**
     * Test case: April 31st is an invalid date.
     */
    @Test
    public void test_April_Thirty_First(){
        Date date = new Date(4, 31, 2021); // Invalid: April 31st is invalid
        assertFalse(date.isValid());
    }

    /**
     * Test case: February 29th in a non-leap year is an invalid date.
     */
    @Test
    public void testDaysInFeb_NonLeap(){
        Date date = new Date(2, 29, 2021); // Invalid: Not a leap year, but February 29th
        assertFalse(date.isValid());
    }

    /**
     * Test case: February 23rd in a year that doesn't exist is an invalid date.
     */
    @Test
    public void testDaysInFeb_Leap(){
        Date date = new Date(2, 23, 0); // Invalid: year 0 does not exist
        assertFalse(date.isValid());
    }

    /**
     * Test case: A normal valid date.
     */
    @Test
    public void testValidDate(){
        Date date = new Date(6, 22, 2022); // Valid: a normal valid date
        assertTrue(date.isValid());
    }

    /**
     * Test case: Valid leap year date.
     */
    @Test
    public void testAnother_ValidDate(){
        Date date = new Date(2, 29, 2024); // Valid leap year date
        assertTrue(date.isValid());
    }

}