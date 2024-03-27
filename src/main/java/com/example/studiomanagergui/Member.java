package com.example.studiomanagergui;
import java.util.Calendar;

/**
 * The Member class represents a fitness club member with a profile, expiration date, and home studio location.
 * It provides methods to retrieve and modify member information, compare members for sorting,
 * check membership expiration status, calculate membership dues, and generate a string representation of the member.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Member implements Comparable<Member> {
        private Profile profile;
        private Date expire;
        private Location homeStudio;


        /**
         * Constructs a Member with the provided profile, expiration date, and home studio location.
         *
         * @param profile     the profile information of the member
         * @param expire      the expiration date of the membership
         * @param homeStudio  the location of the home studio
         */
        public Member(Profile profile, Date expire, Location homeStudio){
            this.profile = profile;
            this.expire = expire;
            this.homeStudio = homeStudio;
         }

        /**
         * Gets the profile information of the member.
         *
         * @return the profile of the member
         */
        public Profile getProfile(){
            return this.profile;
        }

        /**
         * Gets the expiration date of the membership.
         *
         * @return the expiration date
         */
        public Date getExpireDate(){
            return this.expire;
        }

        /**
         * Gets the home studio location of the member.
         *
         * @return the home studio location
         */
        public Location getHomeStudio(){
            return this.homeStudio;
        }

        /**
         * Compares this member to another member for sorting purposes.
         *
         * @param other the other member to compare
         * @return a negative integer, zero, or a positive integer as this member is less than, equal to, or greater than the other
         */
        @Override
        public int compareTo(Member other){
            int lastNameComparison = this.getProfile().getLname().compareToIgnoreCase(other.getProfile().getLname());
            if((lastNameComparison != 0)){return lastNameComparison;}

            int firstNameComparison = this.getProfile().getFname().compareToIgnoreCase(other.getProfile().getFname());
            if((firstNameComparison != 0)){return firstNameComparison;}

            return this.getProfile().getDob().compareTo(other.getProfile().getDob());
        }

        /**
         * Checks if this member is equal to another member.
         *
         * @param obj the object to compare
         * @return true if the objects are equal, false otherwise
         */
        @Override
        public boolean equals(Object obj){
            if(obj instanceof Member){
                Member member = (Member) obj;
                return this.getProfile().getFname().equalsIgnoreCase(member.getProfile().getFname()) &&
                        this.getProfile().getLname().equalsIgnoreCase(member.getProfile().getLname()) &&
                        this.getProfile().getDob().compareTo(member.getProfile().getDob()) == 0;
            }

            return false;
        }

        /**
         * Calculates the membership dues for the member.
         * The actual implementation happens in the subclasses where it matters
         * @return the next due amount
         */
        public double bill() {
            return 0.0;
        } //return the next due amount

        /**
         * Returns a string representation of the member, including profile, membership status, and home studio location.
         *
         * @return a string representation of the member
         */

        @Override
        public String toString(){
            if(this.isExpired()){
                return String.format(this.getProfile().toString() + ", Membership expired %s, Home Studio: %s, %s, %s, ",
                        Date.formatDate(this.getExpireDate()),
                        this.getHomeStudio().getCityName(),
                        this.getHomeStudio().getZipCode(),
                        this.getHomeStudio().getCounty());
            }
            return String.format(this.getProfile().toString() + ", Membership expires %s, Home Studio: %s, %s, %s, ",
                    Date.formatDate(this.getExpireDate()),
                    this.getHomeStudio().getCityName(),
                    this.getHomeStudio().getZipCode(),
                    this.getHomeStudio().getCounty());


        }

        /**
         * Checks if the membership of the member is expired.
         *
         * @return true if the membership is expired, false otherwise
         */
        public boolean isExpired(){
            Calendar currentDate = Calendar.getInstance();
            Calendar expireDate = Calendar.getInstance();
            expireDate.set(expire.getYear(), expire.getMonth() - 1, expire.getDay(), 0,0,0);
            expireDate.set(Calendar.MILLISECOND, 0);

            return expireDate.before(currentDate);
        }
}

