package com.example.studiomanagergui;

/**
 * The Family class represents a family fitness membership, extending the Member class.
 * It includes features for managing a guest pass and calculates the membership bill for a family.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Family extends Member{
    private boolean guest;
    private static final int CLASS_LIMIT = 4;

    /**
     * Constructs a Family membership with the provided profile, expiration date, and home studio location.
     *
     * @param profile      the profile information of the member
     * @param expire       the expiration date of the membership
     * @param homeStudio   the location of the home studio
     */
    public Family(Profile profile, Date expire, Location homeStudio){
        super(profile, expire, homeStudio);
        this.guest = true;
    }

    /**
     * Checks if a guest pass is available for the member.
     *
     * @return true if a guest pass is available, false otherwise
     */
    public boolean isGuestAvailable(){return this.guest;}

    /**
     * Disables the guest pass for the member.
     */
    public void falsifyGuestPass() {
        this.guest = false;
    }

    /**
     * Enables the guest pass for the member.
     */
    public void makeGuestPassAvailable(){
        this.guest = true;
    }

    /**
     * Calculates the membership bill for the family based on a base fee for each family member.
     *
     * @return the total bill for the family membership
     */
    @Override
    public double bill(){
        double baseFee = 49.99;
        double totalBill = baseFee * 3;
        return Math.round(totalBill * 100.0) / 100.0;
    }

    /**
     * Compares this Family membership to another member.
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
     * Checks if this Family membership is equal to another member.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    /**
     * Returns a string representation of the Family membership.
     *
     * @return a string representation of the Family membership
     */
    @Override
    public String toString(){
       return this.isExpired() ? super.toString() + "(Family) guest-pass remaining: not eligible" :
               this.isGuestAvailable() ? super.toString() + "(Family) guest-pass remaining: 1" :
                       super.toString() + "(Family) guest-pass remaining: 0" ;
    }

}
