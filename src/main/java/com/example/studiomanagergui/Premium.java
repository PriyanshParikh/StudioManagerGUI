package com.example.studiomanagergui;

/**
 * The Premium class represents a premium fitness club member with additional features,
 * including guest passes and special billing.
 * It extends the Member class and provides methods to get and manage guest passes, calculate membership dues,
 * compare members for sorting, check membership expiration status, and generate a string representation of the member.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Premium extends Member{
    private int guestPass;
    private static final int CLASS_LIMIT = 4;

    /**
     * Constructs a Premium member with the provided profile, expiration date, and home studio location.
     * Initializes the guest passes to 3.
     *
     * @param profile     the profile information of the member
     * @param expire      the expiration date of the membership
     * @param homeStudio  the location of the home studio
     */
    public Premium(Profile profile, Date expire, Location homeStudio){
        super(profile, expire, homeStudio);
        this.guestPass = 3;
    }

    /**
     * Gets the remaining number of guest passes.
     *
     * @return the remaining number of guest passes
     */
    public int getGuestPass(){
        return this.guestPass;
    }

    /**
     * Decrements the number of guest passes by 1.
     */
    public void decrementGuestPass(){
        this.guestPass --;
    }

    /**
     * Increments the number of guest passes by 1.
     */
    public void incramentGuestPass(){
        this.guestPass ++;
    }

    /**
     * Calculates the membership dues for the premium member.
     *
     * @return the next due amount, considering special billing for annual membership
     */
    @Override
    public double bill(){
        double baseFee = 59.99;
        double totalBill = baseFee * 11; //one month free, billed annually

        return Math.round(totalBill * 100.0) / 100.0;
    }

    /**
     * Compares this premium member to another member for sorting purposes.
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
     * Checks if this premium member is equal to another object.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Returns a string representation of the Premium membership.
     *
     * @return a string representation of the Premium membership
     */
    @Override
    public String toString(){
        return this.isExpired() ? super.toString() + "(Premium) guest-pass remaining: not eligible" :
                super.toString() + "(Premium) guest-pass remaining: " + this.getGuestPass();
    }
}
