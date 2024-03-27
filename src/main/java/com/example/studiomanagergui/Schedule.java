package com.example.studiomanagergui;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The Schedule class represents the schedule of fitness classes in the fitness club.
 * It manages the list of fitness classes, loading classes from a file, and providing methods to add and grow the class list.
 * The class also includes a method to parse class information from a formatted line in the file.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Schedule {
    private FitnessClass [] classes;
    private int numClasses;
    private static final int INCREASE_CAPACITY = 4;

    /**
     * Constructs a Schedule object with an initial capacity for fitness classes.
     */
    public Schedule(){
        classes = new FitnessClass[6];
        numClasses = 0;
    }

    /**
     * Retrieves the array of fitness classes.
     *
     * @return an array of FitnessClass objects representing the fitness classes in the schedule.
     */
    public FitnessClass[] getClasses(){
        return this.classes;
    }

    /**
     * Retrieves the number of fitness classes.
     *
     * @return an array of FitnessClass objects representing the fitness classes in the schedule.
     */
    public int getNumClasses(){
        return this.numClasses;
    }

    /**
     * Loads fitness classes from a specified file and adds them to the schedule.
     *
     * @param file the File object representing the file containing fitness class information.
     * @throws IOException if an I/O error occurs during file reading.
     */
    public void load(File file) throws IOException {
        numClasses = 0;
        classes = new FitnessClass[6]; // random initiall capacity
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    FitnessClass fitnessClass = parseSchedule(line) ;
                    addClass(fitnessClass);
                }
            }
        }catch(IOException e){
        }
    }

    /**
     * Increases the capacity of the fitness class array by a fixed amount.
     */
    private void grow(){   //helper method to increase the capacity by 4
        FitnessClass[] newClasses = new FitnessClass[classes.length + INCREASE_CAPACITY];
        for (int i = 0; i < classes.length; i++) {
            newClasses[i] = classes[i];
        }
        classes = newClasses;
    }

    /**
     * Adds a fitness class to the schedule.
     *
     * @param fitnessClass the FitnessClass object to be added to the schedule.
     * @return true if the class is successfully added, false otherwise.
     */
    public boolean addClass(FitnessClass fitnessClass) {
        if (numClasses == classes.length){
            grow();
        }
        classes[numClasses] = fitnessClass;
        numClasses++;
        return true;
    }

    /**
     * Parses fitness class information from a formatted line in the file.
     *
     * @param line the line containing fitness class information in a specific format.
     * @return a FitnessClass object representing the parsed fitness class information.
     */
    private FitnessClass parseSchedule(String line){
        String[] tokens = line.split("\\s+");
        Offer offer = Offer.valueOf(tokens[0].toUpperCase());
        Instructor instructor  = Instructor.valueOf(tokens[1].toUpperCase());
        Time time = Time.valueOf(tokens[2].toUpperCase());
        Location location = Location.valueOf(tokens[3].toUpperCase());

        return new FitnessClass(offer, instructor, location, time);
    }
}
