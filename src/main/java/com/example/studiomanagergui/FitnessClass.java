package com.example.studiomanagergui;

/**
 * The FitnessClass class represents a fitness class with information such as class details,
 * instructor, location, time, and lists of members and guests attending the class.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class FitnessClass {
        private Offer classInfo;
        private Instructor instructor;
        private Location studio;
        private Time time;
        private MemberList members;
        private MemberList guests;

        /**
         * Constructs a FitnessClass with the provided class information, instructor, studio location, and time.
         *
         * @param classInfo   the details of the fitness class
         * @param instructor  the instructor leading the class
         * @param studio      the location of the studio
         * @param time        the time at which the class is scheduled
         */
        public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time) {
            this.classInfo = classInfo;
            this.instructor = instructor;
            this.studio = studio;
            this.time = time;
            this.members = new MemberList();
            this.guests = new MemberList();
        }

        /**
         * Gets the class information of the fitness class.
         *
         * @return the class information
         */
        public Offer getClassInfo() {
            return classInfo;
        }

        /**
         * Gets the instructor leading the fitness class.
         *
         * @return the instructor of the fitness class
         */
        public Instructor getInstructor() {
            return this.instructor;
        }

        /**
         * Gets the location of the studio where the fitness class is held.
         *
         * @return the location of the studio
         */
        public Location getStudio() {
            return this.studio;
        }

        /**
         * Gets the time at which the fitness class is scheduled.
         *
         * @return the time of the fitness class
         */
        public Time getTime() {
            return this.time;
        }

        /**
         * Gets the list of members attending the fitness class.
         *
         * @return the list of members
         */
        public MemberList getMembers() {
            return this.members;
        }

        /**
         * Gets the list of guests attending the fitness class.
         *
         * @return the list of guests
         */
        public MemberList getGuests() {
            return guests;
        }


        /**
         * Returns a string representation of the FitnessClass, including class details, instructor, time, and location.
         *
         * @return a string representation of the FitnessClass
         */
        @Override
        public String toString(){
            String minutes = this.getTime().getMinutes() == 0 ? "00" : Integer.toString(this.getTime().getMinutes());
            String output = this.getClassInfo() + " - " + this.getInstructor() + ", " + this.getTime().getHour()
                    + ":" + minutes  + ", " + this.getStudio().getCityName().toUpperCase();
            return output;
        }

}
