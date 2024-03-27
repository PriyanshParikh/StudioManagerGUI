package com.example.studiomanagergui;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

/**
 * The MemberList class manages a list of fitness club members.
 * It provides methods for adding, removing, and loading members,
 * as well as sorting and printing the list based on different criteria.
 *
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class MemberList {
    private static final int NOT_FOUND = -1;
    private Member [] members;
    private int size;
    private static final int INITIAl_CAPACITY = 4;
    private static final int INCREASE_CAPACITY = 4;

    /**
     * Constructs a MemberList with an initial capacity.
     */
    public MemberList(){
        members = new Member[INITIAl_CAPACITY];
        size = 0;
    }

    /**
     * Gets the size of the member list.
     *
     * @return the size of the member list
     */
    public int getSize() { return this.size; }

    /**
     * Gets the array of members in the list.
     *
     * @return the array of members
     */
    public Member[] getMembersList() { return this.members; }

    /**
     * Finds the index of a member in the list.
     *
     * @param member the member to find
     * @return the index of the member, or -1 if not found
     */
    private int find(Member member){
        for(int i = 0; i < size; i++){
            if(members[i].equals(member)){
                return i; //member found
            }
        }
        return NOT_FOUND; //member not found
    }

    /**
     * Increases the capacity of the member array by 4.
     */
    private void grow(){   //helper method to increase the capacity by 4
        Member[] newMembers = new Member[members.length + INCREASE_CAPACITY];
        for (int i = 0; i < members.length; i++) {
            newMembers[i] = members[i];
        }
        members = newMembers;
    }


    /**
     * Checks if the member list contains a specific member.
     *
     * @param member the member to check for
     * @return true if the member is found, false otherwise
     */
    public boolean contains(Member member){
        return find(member) != NOT_FOUND;
    }

    /**
     * Adds a member to the list.
     *
     * @param member the member to add
     * @return true if the member is successfully added, false if the member already exists
     */
    public boolean add(Member member) {
        if(find(member) != NOT_FOUND) {
            return false;
        }
        if (size == members.length){
            grow();
        }
        members[size] = member;
        size++;
        return true;
    }

    /**
     * Removes a member from the list.
     *
     * @param member the member to remove
     * @return true if the member is successfully removed, false if the member doesn't exist
     */
    public boolean remove(Member member) {//false if the album doesn’t exist
        int index = find(member);
        if (index == NOT_FOUND) {
            return false; // Album doesn't exist
        }

        // Shift elements to the left to fill the gap
        for (int i = index; i < size - 1; i++) {
            members[i] = members[i + 1];
        }
        members[size - 1] = null; // Nullify the last element
        size--; // Decrease size
        return true;
    }

    /**
     * Loads members from a file and adds them to the list.
     *
     * @param file the file containing member information
     * @throws IOException if an I/O error occurs
     */
    public void load(File file) throws IOException{
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Member member = parseMemberForLoad(line);
                    add(member);
                }
            }

        }catch (IOException e){
            System.out.println("File not found");
        }

    }

    /**
     * Parses a line of member information from a file and creates a Member object.
     *
     * @param line the line containing member information
     * @return the Member object created from the line
     */
    private Member parseMemberForLoad(String line){
        String[] tokens = line.split("\\s+");
        String membershipType = tokens[0];
        String firstName = tokens[1];
        String lastName = tokens[2];
        Date dob = parseDate(tokens[3]);
        Date expireDate = parseDate(tokens[4]);
        Location homeStudio = Location.valueOf(tokens[5].toUpperCase());


        switch (membershipType) {
            case "B":
                return new Basic(new Profile(firstName, lastName, dob), expireDate, homeStudio);
            case "F":
                return new Family(new Profile(firstName, lastName, dob), expireDate, homeStudio);
            case "P":
                return new Premium(new Profile(firstName, lastName, dob), expireDate, homeStudio);
            default:
                throw new IllegalArgumentException("Invalid membership type: " + membershipType);
        }
    }

    /**
     * Prints the list of members sorted by member profiles.
     */
    public String printByMember() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < members.length - 1; i++) {
            for (int j = 0; j < members.length - i - 1; j++) {
                if (members[j] == null || members[j + 1] == null) {
                    continue;
                }

                int lnameComparison = members[j].getProfile().getLname().compareTo(members[j+1].getProfile().getLname());
                int fnameComparison = members[j].getProfile().getFname().compareTo(members[j+1].getProfile().getFname());
                int dobComparison = members[j].getProfile().getDob().compareTo(members[j+1].getProfile().getDob());

                if(lnameComparison > 0 ||
                (lnameComparison == 0 && fnameComparison > 0) ||
                (lnameComparison == 0 && fnameComparison == 0 && dobComparison < 0)){
                    Member temp = members[j];
                    members[j] = members[j + 1];
                    members[j + 1] = temp;
                }
            }
        }
        for(Member m : members){
            if(m != null){
                result.append(m.toString()).append("\n");
            }
        }
        return result.toString();
    }


    /**
     * Prints the list of members sorted by county and then zipcode.
     */
    public String printByCounty() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < members.length - 1; i++) {
            for (int j = 0; j < members.length - i - 1; j++) {
                if (members[j] == null || members[j + 1] == null) {
                    continue;
                }

                int countyComparison = members[j].getHomeStudio().getCounty().compareTo(members[j+1].getHomeStudio().getCounty());
                int zipcodeComparison = members[j].getHomeStudio().getZipCode().compareTo(members[j+1].getHomeStudio().getZipCode());

                if(countyComparison > 0 ||
                        (countyComparison == 0 && zipcodeComparison > 0)){
                    Member temp = members[j];
                    members[j] = members[j + 1];
                    members[j + 1] = temp;
                }
            }
        }
        for(Member m : members){
            if(m != null){
                result.append(m.toString()).append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Prints the list of members with their next dues.
     */
    public String printFees(){
        StringBuilder result = new StringBuilder();
        for (Member m : members) {
            if (m != null) {
                result.append(m.toString()).append(" [next due: $").append(m.bill()).append("]\n");
            }
        }
        return result.toString();

    }


    /**
     * Parses a date from a string in the format "MM/DD/YYYY".
     *
     * @param dateToBeParsed the string containing the date
     * @return the Date object created from the string
     */
    private Date parseDate(String dateToBeParsed){
        String[] dateTokens = dateToBeParsed.split("/");
        int Month = Integer.parseInt(dateTokens[0]);
        int Date = Integer.parseInt(dateTokens[1]);
        int Year = Integer.parseInt(dateTokens[2]);
        Date date = new Date(Month, Date, Year);
        return date;
    }
}
