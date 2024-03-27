package com.example.studiomanagergui;

/**
 * The Profile class represents the personal information of a fitness club member.
 * It includes the first name, last name, and date of birth.
 * The class provides methods for comparing profiles, checking equality, generating a string representation,
 * and formatting the profile information for a command.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructs a Profile with the given first name, last name, and date of birth.
     *
     * @param fname the first name of the member
     * @param lname the last name of the member
     * @param dob   the date of birth of the member
     */
    public Profile(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Gets the first name of the member.
     *
     * @return the first name
     */
    public String getFname(){
        return this.fname;
    }

    /**
     * Gets the last name of the member.
     *
     * @return the last name
     */
    public String getLname(){
        return this.lname;
    }

    /**
     * Gets the date of birth of the member.
     *
     * @return the date of birth
     */
    public Date getDob(){
        return this.dob;
    }

    /**
     * Compares this profile to another profile for sorting purposes.
     *
     * @param other the other profile to compare
     * @return the comparison result based on last name, first name, and date of birth
     */
    public int compareTo(Profile other){
        int lastNameComparison = this.getLname().compareToIgnoreCase(other.getLname());
        if((lastNameComparison != 0)){return lastNameComparison;}

        int firstNameComparison = this.getFname().compareToIgnoreCase(other.getFname());
        if((firstNameComparison != 0)){return firstNameComparison;}

        return this.getDob().compareTo(other.getDob());
    }

    /**
     * Checks if this profile is equal to another object.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Profile){
            Profile profile = (Profile) obj;
            return this.getFname().equalsIgnoreCase(profile.getFname()) && this.getLname().equalsIgnoreCase(profile.getLname())
                    && this.getDob().compareTo(profile.getDob()) == 0;
        }

        return false;
    }

    /**
     * Generates a string representation of the profile.
     *
     * @return the string representation of the profile
     */
    @Override
    public String toString(){
        return String.format("%s:%s:%s",
                this.getFname(),
                this.getLname(),
                Date.formatDate(this.getDob()));
    }

    /**
     * Formats the profile information for a command in the format "fname lname MM/DD/YYYY".
     * Helper method for studioManager text representation
     * @return the formatted profile information for a command
     */
    public String R_Command_Profile_Format(){
        return String.format("%s %s %s",
                this.getFname(),
                this.getLname(),
                Date.formatDate(this.getDob()));
    }

    /**
     * Generates a string with the first name and last name only.
     * Helper method for studioManager text representation
     * @return the string with the first name and last name
     */
    public String first_And_Last(){
        return String.format("%s %s",
                this.getFname(),
                this.getLname());
    }
}