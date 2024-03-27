package com.example.studiomanagergui;
import org.testng.annotations.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for the MemberList add and remove methods.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class MemberListTest {

    /**
     * Test case: Adding a valid Basic member to the member list.
     */
    @Test
    public void addValidBasicMember() {
        MemberList members = new MemberList();
        Profile profile = new Profile("Pri", "Parikh", new Date(3,18,2003));
        Basic basic = new Basic(profile, new Date(5,23,2025), Location.PISCATAWAY);
        assertTrue(members.add(basic));
    }

    /**
     * Test case: Adding an already existing Basic member to the member list.
     */
    @Test
    public void addNotValidBasicMember() {
        MemberList members = new MemberList();
        Profile profile = new Profile("Pri", "Parikh", new Date(3,18,2003));
        Basic basic = new Basic(profile, new Date(5,23,2025), Location.PISCATAWAY);
        members.add(basic);

        //This basic member already exists --> therefore false
        assertFalse(members.add(basic));
    }

    /**
     * Test case: Adding a valid Family member to the member list.
     */
    @Test
    public void addValidFamilyMember() {
        MemberList members = new MemberList();
        Profile profile = new Profile("John", "Walter", new Date(2,20,2001));
        Family family = new Family(profile, new Date(5,23,2206), Location.SOMERVILLE);
        assertTrue(members.add(family));

    }

    /**
     * Test case: Adding an already existing Family member to the member list.
     */
    @Test
    public void addNotValidFamilyMember() {
        MemberList members = new MemberList();
        Profile profile = new Profile("John", "Walter", new Date(2,20,2001));
        Family family = new Family(profile, new Date(5,23,2206), Location.SOMERVILLE);
        assertTrue(members.add(family));

        //This family member already exists --> therefore false
        assertFalse(members.add(family));
    }

    /**
     * Test case: Adding a valid Premium member to the member list.
     */

    @Test
    public void addValidPremiumMember() {
        MemberList members = new MemberList();
        Profile profile = new Profile("Amy", "Stein", new Date(12,20,200));
        Premium premium = new Premium(profile, new Date(5,23,2206), Location.EDISON);
        assertTrue(members.add(premium));

    }

    /**
     * Test case: Adding an already existing Premium member to the member list.
     */
    @Test
    public void addNotValidPremiumMember() {
        MemberList members = new MemberList();
        Profile profile = new Profile("Amy", "Stein", new Date(12,20,200));
        Premium premium = new Premium(profile, new Date(5,23,2206), Location.EDISON);
        members.add(premium);

        //This premium member already exists --> therefore false
        assertFalse(members.add(premium));
    }


    /**
     * Test case: removing a valid member from the member list
     */
    @Test
    public void validRemove() {
        MemberList members = new MemberList();
        Profile profile = new Profile("Amy", "Stein", new Date(12,20,200));
        Premium premium = new Premium(profile, new Date(5,23,2206), Location.EDISON);
        members.add(premium);

        assertTrue(members.remove(premium));
    }

    /**
     * Test case: removing a member that does not exist
     * */
    @Test
    public void removeSomeoneNotInList(){
        MemberList members = new MemberList();
        Profile profile = new Profile("Amy", "Stein", new Date(12,20,200));
        Premium premium = new Premium(profile, new Date(5,23,2206), Location.EDISON);
        members.add(premium);

        Profile profile2 = new Profile("John", "Walter", new Date(2,20,2001));
        Family family = new Family(profile2, new Date(5,23,2206), Location.SOMERVILLE);

        assertFalse(members.remove(family));

    }


}