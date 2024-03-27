package com.example.studiomanagergui;

/**
 * The Instructor enum represents different fitness class instructors.
 * Each instructor has a corresponding name.
 * It provides methods to get the instructor's name, check if two instructors are the same (ignoring case),
 * and check if the current instructor equals another instructor directly.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public enum Instructor {
    JENNIFER("Jennifer"),
    KIM("Kim"),
    DENISE("Denise"),
    DAVIS("Davis"),
    EMMA("Emma");


    private final String instructor;

    /**
     * Constructs an Instructor enum with the given instructor name.
     *
     * @param instructor the name of the instructor
     */
    Instructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * Gets the name of the instructor.
     *
     * @return the name of the instructor
     */
    public String getInstructor(){return this.instructor;}

    /**
     * Checks if the current instructor is the same as another instructor (ignoring case).
     *
     * @param obj the object to compare
     * @return true if the instructors have the same name (ignoring case), false otherwise
     */
    public boolean sameInstructor(Object obj){
        if(obj instanceof Instructor){
            Instructor instructorCompared = (Instructor) obj;
            return this.getInstructor().equalsIgnoreCase(instructorCompared.getInstructor());
        }
        return false;
    }
}
