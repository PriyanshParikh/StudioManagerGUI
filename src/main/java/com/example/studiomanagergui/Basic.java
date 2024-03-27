package com.example.studiomanagergui;
/**
 * The Basic class represents a basic fitness membership, extending the Member class.
 * It tracks the number of classes attended by the member and calculates the membership bill accordingly.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Basic extends Member{
    private int numClasses;
    private static final int CLASS_LIMIT = 4;
    private static final double ADDITIONAL_CHARGE = 10.0;


    /**
     * Constructs a Basic membership with the provided profile, expiration date, and home studio location.
     *
     * @param profile      the profile information of the member
     * @param expire       the expiration date of the membership
     * @param homeStudio   the location of the home studio
     */
    public Basic(Profile profile, Date expire, Location homeStudio){
        super(profile, expire, homeStudio);
        this.numClasses = 0;
    }

    /**
     * Gets the number of classes attended by the member.
     *
     * @return the number of classes attended
     */
    public int getNumClasses(){
        return this.numClasses;
    }

    /**
     * Sets the number of classes attended by the member.
     *
     * @param numClasses the number of classes attended
     */
    public void setNumClasses(int numClasses) {
        this.numClasses = numClasses;
    }

    /**
     * Calculates the membership bill based on a base fee and additional charges for extra classes attended.
     *
     * @return the total bill for the membership
     */
    @Override
    public double bill(){
        double baseFee = 39.99;


        double totalBill = baseFee;
        int classesAttended = Math.max(0, numClasses - CLASS_LIMIT);

        if(classesAttended > 0){
            totalBill += ADDITIONAL_CHARGE * classesAttended;
        }

        return Math.round(totalBill * 100.0) / 100.0;

    }

    /**
     * Compares this Basic membership to another member for sorting purposes.
     *
     * @param other the other member to compare
     * @return a negative integer, zero, or a positive integer as this member is less than, equal to, or greater than the other
     */
    @Override
    public int compareTo(Member other) {
        int superComparison = super.compareTo(other);
        if (superComparison != 0) {
            return superComparison;
        }

        return 0;
    }

    /**
     * Checks if this Basic membership is equal to another member.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
       return super.equals(obj);
    }

    /**
     * Returns a string representation of the Basic membership.
     *
     * @return a string representation of the Basic membership
     */
    @Override
    public String toString(){
        return super.toString() + "(Basic) number of classes attended: " + numClasses;
    }

}
