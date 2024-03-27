package com.example.studiomanagergui;

/**
 * The Offer enum represents different fitness class offers, such as Pilates, Spinning, and Cardio.
 * Each offer has a corresponding name.
 * It provides methods to get the name of the offer, check if two offers are the same by comparing names,
 * and check if the current offer is equal to another offer directly.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public enum Offer {
    PILATES("Pilates"),
    SPINNING("Spinning"),
    CARDIO("Cardio");

    private final String offer;

    /**
     * Constructs an Offer enum with the given name.
     *
     * @param offer the name of the offer
     */
    Offer(String offer) {
        this.offer = offer;
    }

    /**
     * Gets the name of the offer.
     *
     * @return the name of the offer
     */
    public String getOffer(){return this.offer;}

    /**
     * Checks if the current offer is the same as another offer by comparing names.
     *
     * @param obj the object to compare
     * @return true if the offers have the same name (ignoring case), false otherwise
     */
    public boolean sameOffer(Object obj){
        if(obj instanceof Offer){
            Offer offerCompared = (Offer) obj;
            return this.getOffer().equalsIgnoreCase(offerCompared.getOffer());
        }
        return false;
    }
}


