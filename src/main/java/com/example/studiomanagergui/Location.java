package com.example.studiomanagergui;

/**
 * The Location enum represents different fitness class locations.
 * Each location has a corresponding city name, zip code, and county.
 * It provides methods to get the city name, zip code, and county of the location,
 * as well as check if two locations are the same, either by comparing their attributes or directly.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public enum Location {
    BRIDGEWATER("Bridgewater", "08807", "Somerset"),
    EDISON("Edison", "08837", "Middlesex"),
    FRANKLIN("Franklin", "08873", "Somerset"),
    PISCATAWAY("Piscataway", "08854", "Middlesex"),
    SOMERVILLE("Somerville", "08876", "Somerset");

    private final String cityName;
    private final String zipCode;
    private final String county;

    /**
     * Constructs a Location enum with the given city name, zip code, and county.
     *
     * @param cityName the name of the city
     * @param zipCode   the zip code of the location
     * @param county    the county of the location
     */
    Location(String cityName, String zipCode, String county) {
        this.cityName = cityName.toUpperCase();
        this.zipCode = zipCode;
        this.county = county.toUpperCase();;
    }

    /**
     * Gets the city name of the location.
     *
     * @return the city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Gets the zip code of the location.
     *
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Gets the county of the location.
     *
     * @return the county
     */
    public String getCounty() {
        return county;
    }

    /**
     * Returns a formatted string representation of the location with city name, zip code, and county.
     *
     * @return a string representation of the location
     */
    @Override
    public String toString(){
        return String.format("%s, %s, %s",
                this.getCityName().toUpperCase(),
                this.getZipCode().toUpperCase(),
                this.getCounty().toUpperCase());

    }

    /**
     * Checks if the current location is the same as another location by comparing attributes.
     *
     * @param obj the object to compare
     * @return true if the locations have the same city name, zip code, and county (ignoring case), false otherwise
     */
    public boolean sameLocation(Object obj){
        if(obj instanceof Location){
            Location location = (Location) obj;
            return this.getCityName().equalsIgnoreCase(location.getCityName()) &&
                    this.getCounty().equalsIgnoreCase(location.getCounty()) &&
                    this.getZipCode().equalsIgnoreCase(location.getZipCode());
        }
        return false;
    }


    /**
     * Checks if the current location is equal to another location directly.
     *
     * @param location the other location to compare
     * @return true if the locations are the same, false otherwise
     */
    public boolean equalsOther(Location location){
        return this == location;
    }
}