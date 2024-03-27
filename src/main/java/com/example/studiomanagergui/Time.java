package com.example.studiomanagergui;

/**
 * The Time enum represents different time slots for fitness classes in a day.
 * It includes predefined time slots such as MORNING, AFTERNOON, and EVENING, each with a specific hour and minute.
 * The enum provides methods to retrieve the hour, minutes, and check if two Time objects represent the same time.
 * Additionally, it overrides the toString method to provide a formatted string representation of the time.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public enum Time {
    MORNING (9, 30),
    AFTERNOON (14, 0),
    EVENING (18, 30);

    private final int hour;
    private final int minutes;

    /**
     * Constructs a Time enum with a specified hour and minute.
     *
     * @param hour    the hour of the time slot.
     * @param minutes the minutes of the time slot.
     */
    Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    /**
     * Retrieves the hour of the time slot.
     *
     * @return the hour of the time slot.
     */
    public int getHour(){return this.hour;}

    /**
     * Retrieves the minutes of the time slot.
     *
     * @return the minutes of the time slot.
     */
    public int getMinutes(){return this.minutes;}

    /**
     * Checks if two Time objects represent the same time.
     *
     * @param obj the object to compare with.
     * @return true if the objects represent the same time, false otherwise.
     */
    public boolean sameTime(Object obj){
        if(obj instanceof Time){
            Time time =  (Time) obj;
            return (this.getHour() == time.getHour()) && (this.getMinutes() == time.getMinutes());
        }
        return false;
    }

    /**
     * Overrides the toString method to provide a formatted string representation of the time.
     *
     * @return a string representation of the time in the format "HH:MM".
     */
    @Override
    public String toString(){
        String minutes = this.getMinutes() == 0 ? "00" : Integer.toString(this.getMinutes());
        String output = this.getHour() + ":" + minutes;
        return output;
    }
}
