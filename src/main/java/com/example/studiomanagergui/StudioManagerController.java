package com.example.studiomanagergui;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * The StudioManagerController class is responsible for managing the collection of fitness memberships through GUI input.
 * It contains the initialize method which sets up the GUI and then many other methods to handle the buttons and
 * text fields along with proper error messages in the text area on the bottom.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class StudioManagerController {
    MemberList memberList = new MemberList();
    Schedule schedule = new Schedule();

    @FXML
    private TextField guestPassesTextField, fName, lName, CA_fName, CA_lName;
    @FXML
    private ToggleGroup membershipType, homeStudio, offer, instructorGroup, classStudio;
    @FXML
    private RadioButton basicRadioButton, familyRadioButton, premiumRadioButton;
    @FXML
    private TextArea output;
    @FXML
    private DatePicker datePicker, CA_datePicker;
    @FXML
    private TableView<FitnessClass> scheduleTable;
    @FXML
    private TableColumn<FitnessClass, String> time;
    @FXML
    private TableColumn<FitnessClass, String> className;
    @FXML
    private TableColumn<FitnessClass, String> instructor;
    @FXML
    private TableColumn<FitnessClass, String> studio;
    @FXML
    private TableView<Location> studioLocationTable;
    @FXML
    TableColumn<Location, String> cityCol;
    @FXML
    TableColumn<Location, String> countyCol;
    @FXML
    TableColumn<Location, String> zipCodeCol;

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is called automatically by the FXMLLoader
     * In this implementation:
     * - The studio table is populated with data.
     * - Event handlers are attached to radio buttons for updating guest passes.
     * - Text property listeners are added to first name and last name text fields
     *   for validating input and showing alerts if the input is invalid.
     */
    @FXML
    public void initialize() {
        populateStudioTable();
        basicRadioButton.setOnAction(event -> updateGuestPasses(0));
        familyRadioButton.setOnAction(event -> updateGuestPasses(1));
        premiumRadioButton.setOnAction(event -> updateGuestPasses(3));
        fName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidName(newValue)) {
                showAlert("Invalid First Name", "Please enter a valid first name (only alphabetic characters allowed).");
                fName.setText("");
            }
        });
        // Add listener to last name text field
        lName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidName(newValue)) {
                showAlert("Invalid Last Name", "Please enter a valid last name (only alphabetic characters allowed).");
                lName.setText("");
            }
        });
        CA_fName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidName(newValue)) {
                showAlert("Invalid First Name", "Please enter a valid first name (only alphabetic characters allowed).");
                CA_fName.setText("");
                return;
            }
        });

        // Add listener to last name text field
        CA_lName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidName(newValue)) {
                showAlert("Invalid Last Name", "Please enter a valid last name (only alphabetic characters allowed).");
                CA_lName.setText("");
                return;
            }
        });
    }

    /**
     * Populates the studioLocationTable with data from the Location enum.
     * This method configures the cell value factories for each column
     * and adds Location enum values to the table.
     * Each row in the table represents a Location object.
     */
    @FXML
    public void populateStudioTable(){
        cityCol.setCellValueFactory(cellData ->  new SimpleStringProperty((cellData.getValue().getCityName())));

        countyCol.setCellValueFactory(cellData ->  new SimpleStringProperty((cellData.getValue().getCounty())));

        zipCodeCol.setCellValueFactory(cellData -> new SimpleStringProperty((cellData.getValue().getZipCode())));


            for (Location location : Location.values()) {
                studioLocationTable.getItems().add(location);
            }

    }

    /**
     *Helper method that updates the guest pass text field to the right amount of guest passes depending
     * on the selected membership plan
     * @param passes number of guest passes to display
     */
    @FXML
    private void updateGuestPasses(int passes) {
        guestPassesTextField.setText(String.valueOf(passes));
    }

    /**
     *Helper method that checks for a valid name, used in initialize() to see if an alert needs to be
     * displayed if an invlaid name type is entered
     * @param  name String value of the name being chekced
     * @return true if valid name, false otherwise
     */
    private boolean isValidName(String name) {
        if(name.equals("")){return true;}
        return name.matches("[a-zA-Z]+");
    }

    /**
     *Helper method that procces and shows an alert on the screen -- used mainly for invalid name entries
     * @param title title/type of alert
     * @param message descriptive message explaining alert
     */
    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     *Helper method that converts the local date from the date picker to a date object and checks if it's a valid date
     * @param localDate localDate object from date picker
     * @return true if valid date, false otherwise
     */
    private boolean checkDate(LocalDate localDate) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        Date dob = new Date(month, day, year);

        if (dob.isValid() && !dob.checkToday_Or_Future()) {
            output.appendText("DOB " + Date.formatDate(dob) + ": cannot be today or a future date! \n");
            return true;
        } else if (dob.isValid() && !dob.checkProperAge()) {
            output.appendText( "DOB " + Date.formatDate(dob) + ": must be 18 or older to join! \n");
            return true;
        }

        return false;
    }

    /**
     * Handles the action event when the user clicks the "Add New" button.
     * This method retrieves the selected date, membership type, and home studio
     * from the user interface components and validates the input data.
     * If the input is valid, a new member is created based on the selected
     * membership type and added to the system. If any required information
     * is missing or invalid, an appropriate message is displayed.
     *
     * @param actionEvent The action event triggered by clicking the "Add New" button.
     */
    @FXML
    public void handleAddNewButton(ActionEvent actionEvent) {
        LocalDate selectedDate = datePicker.getValue();
        RadioButton selectedMembershipButton = (RadioButton) membershipType.getSelectedToggle();
        RadioButton selectedStudioButton = (RadioButton) homeStudio.getSelectedToggle();

        if (selectedDate != null) {
            // Call the checkDate method to validate the selected date
            if(checkDate(selectedDate)){
                return;
            }
            if (!checkDate(selectedDate) && !fName.getText().isBlank()  && !lName.getText().isBlank() && selectedMembershipButton != null
                    && selectedStudioButton != null) {
                Date dob = convertLocalDateToDate(selectedDate);
                Profile profile = new Profile(fName.getText(), lName.getText(), dob);
                Location location = Location.valueOf(selectedStudioButton.getText().toUpperCase());
                switch (selectedMembershipButton.getText()) {
                    case "Basic":
                        Date b_expire = calculateExpirationDate("basic");
                        Basic basicMem = new Basic(profile, b_expire, location);
                        addNewMember(basicMem);
                        return;
                    case "Family":
                        Date f_expire = calculateExpirationDate("family");
                        Family familyMem = new Family(profile, f_expire, location);
                        addNewMember(familyMem);
                        return;
                    case "Premium":
                        Date p_expire = calculateExpirationDate("premium");
                        Premium premiumMem = new Premium(profile, p_expire, location);
                        addNewMember(premiumMem);
                        return;
                }
            }
        }
        if (fName.getText().isBlank() ||  lName.getText().isBlank() || selectedDate == null ||
                         selectedStudioButton  == null || selectedMembershipButton == null) {
            output.appendText( "Missing Information \n");
            return;
        }
    }

    /**
     * Handles the action event when the user clicks the "Cancel" button.
     * This method checks if the required information (first name, last name, and date of birth)
     * is provided to remove a member. If any of these fields are blank or null, an error message
     * is displayed. Otherwise, it attempts to remove the member from the system. If the member
     * is found and successfully removed, a confirmation message is displayed. If the member
     * is not found in the database, an appropriate message is displayed.
     *
     * @param actionEvent The action event triggered by clicking the "Cancel" button.
     */
    @FXML
    public void handleCancelButton(ActionEvent actionEvent){
        if(fName.getText().isBlank() ||  lName.getText().isBlank() || datePicker.getValue() == null){
            output.appendText("Not enough information to remove a member \n");
            return;
        }
        String f_name = fName.getText();
        String l_name = lName.getText();
        Date dob = convertLocalDateToDate(datePicker.getValue());

        Profile profile = new Profile(f_name, l_name, dob);

        for(Member m : memberList.getMembersList()){
            if(m != null && m.getProfile().equals(profile)){
                memberList.remove(m);
                output.appendText(m.getProfile().getFname() + " " + m.getProfile().getLname() + " removed. \n");
                return;
            }
        }
        output.appendText(f_name + " " + l_name + " is not in the member database. \n");
        return;
    }

    /**
     * Helper method for the add commands that handles if the member is already in the database
     * @param member member to be added.
     */
    private void addNewMember(Member member) {
        if (memberList.contains(member)) {
            output.appendText(member.getProfile().getFname() + " " + member.getProfile().getLname()
                    + " is already in the member database. \n");
            return;
        } else {
            memberList.add(member);
            output.appendText(member.getProfile().getFname() + " " + member.getProfile().getLname()
                    + " added. \n");
            return;
        }
    }


    /**
     * Converts a  LocalDate object to a Date object.
     * This method takes a LocalDate object representing a date and converts it
     * into a Date object. It extracts the year, month, and day components from
     * the LocalDate and creates a new Date object using these components.
     *
     * @param localDate The LocalDate object to be converted to a Date.
     * @return The Date object representing the same date as the given LocalDate.
     */
    private Date convertLocalDateToDate(LocalDate localDate) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        Date date = new Date(month, day, year);
        return date;
    }


    /**
     * Calculates the expiration date based on the membership type.
     *
     * @param membershipType The type of membership.
     * @return The calculated expiration date.
     */
    private Date calculateExpirationDate (String membershipType){
        Calendar currentDate = Calendar.getInstance();

        int originalDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int originalMonth = currentDate.get(Calendar.MONTH) + 1; // Months are zero-based
        int originalYear = currentDate.get(Calendar.YEAR);

        switch (membershipType.toLowerCase()) {
            case "basic":
                currentDate.add(Calendar.MONTH, 1);
                break;
            case "family":
                currentDate.add(Calendar.MONTH, 3);
                break;
            case "premium":
                currentDate.add(Calendar.YEAR, 1);
                break;
            default:
        }
        int lastDayOfMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Adjust the day of the month to match the original day if possible
        if (originalDay <= lastDayOfMonth) {
            currentDate.set(Calendar.DAY_OF_MONTH, originalDay);
        } else {
            // If the original day is beyond the last day of the month, set to the last day
            currentDate.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
        }

        // Check if the original date is in a leap year and towards the end of January
        if (originalMonth == 1 && originalDay > 28 && Date.isLeapYear(originalYear)) {
            // Set expiration date to February 29th for leap years
            currentDate.set(Calendar.MONTH, Calendar.FEBRUARY);
            currentDate.set(Calendar.DAY_OF_MONTH, 29);
        }

        return new Date(
                currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.DAY_OF_MONTH),
                currentDate.get(Calendar.YEAR)
        );
    }

    /**
     * Handles the event when the "Load Schedule" button is clicked.
     * This method prompts the user to select a text file containing the schedule data
     * using a file chooser dialog. It then attempts to load the schedule data from
     * the selected file. If successful, it populates the schedule table view with the
     * loaded schedule data.
     *
     * @param actionEvent The ActionEvent representing the button click event.
     * @throws IOException If an I/O error occurs while loading the schedule data from the file.
     */
    @FXML
    public void handleLoadScheduleButton(ActionEvent actionEvent) throws IOException {
        if(schedule.getNumClasses() > 0){
            output.appendText("Schedule already loaded \n");
            return;
        }
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage); //get the reference of the source file
        try {
            schedule.load(sourceFile);
            // rest of the code
        } catch (RuntimeException e) {
            output.appendText("Incorrect file for loading schedule, try again: \n");
            return;
        }

        time.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime().toString()));
        instructor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInstructor().getInstructor()));
        className.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClassInfo().getOffer()));
        studio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudio().toString()));

        for (FitnessClass fitnessClass : schedule.getClasses()) {
            if (fitnessClass != null) {
                scheduleTable.getItems().add(fitnessClass);
            }
        }
        output.appendText("Schedule Loaded \n");
    }

    /**
     * Handles the event when the "Load Members" button is clicked.
     * This method prompts the user to select a text file containing the member data
     * using a file chooser dialog. It then attempts to load the member data from
     * the selected file into the member list. If successful, it displays a message
     * indicating that the members have been loaded.
     *
     * @throws IOException If an I/O error occurs while loading the member data from the file.
     */
    @FXML
    public void handleLoadMembersButton() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage); //get the reference of the source file

        try {
            memberList.load(sourceFile);
        }
        catch (RuntimeException e){
            output.appendText("File does not have proper format for loading members, try again.");
            return;
        }
        output.appendText("Members Loaded \n");
    }


    /**
     * Handles the event when the "Add" button is clicked for creating a new fitness class attendance record.
     * This method validates the input fields and ensures that all required information is provided.
     * If any required information is missing or invalid, it displays an error message.
     * If the provided information is valid, it adds the attendance record for the member to the fitness class.
     * If the member is not found in the member database or the membership has expired, it displays an error message.
     *
     * @param actionEvent The action event representing the user's click on the "Add" button.
     */
    @FXML
    public void handleCA_Add(ActionEvent actionEvent){
        RadioButton selectedOfferButton = (RadioButton) offer.getSelectedToggle();
        RadioButton selectedInstructorButton = (RadioButton) instructorGroup.getSelectedToggle();
        RadioButton selectedClassStudioButton = (RadioButton) classStudio.getSelectedToggle();
        LocalDate dob = CA_datePicker.getValue();

        if(dob != null  && checkDate(dob)){
            return;
        }
        if(CA_lName.getText().isBlank() || CA_fName.getText().isBlank() || selectedClassStudioButton == null
                || selectedInstructorButton == null || selectedOfferButton == null || dob == null){
            output.appendText("Missing information to add member \n");
            return;
        }

        Profile profile = new Profile(CA_fName.getText(), CA_lName.getText(), convertLocalDateToDate(dob));
        boolean contains = false;
        Member memberToProcess = null;
        for(Member m : memberList.getMembersList()){
            if(m != null && m.getProfile().equals(profile)){contains = true; memberToProcess = m;}
        }

        if(!contains){
            output.appendText(profile.R_Command_Profile_Format() + " is not in the member database. \n");
            return;
        }
        if(memberToProcess.isExpired()){output.appendText(profile.R_Command_Profile_Format() + " membership expired. \n");
            return;
        }
        FitnessClass fitnessClass = return_FitnessClass_toProcess(selectedOfferButton,
                selectedInstructorButton, selectedClassStudioButton );
        if(fitnessClass == null){
            output.appendText(  selectedOfferButton.getText() + " by " + selectedInstructorButton.getText()
                    + " does not exist at " + selectedClassStudioButton.getText() + " \n");
            return;
        }
        handle_Other_R_Cases(fitnessClass, memberToProcess);
    }

    /**
     * This is the last helper method for the R command method which constructs the fitness class someone is attending
     * The reason this method isn't boolean it is places at the end of the R command method after all conditions have
     * been checked, meaning that if this method is called we already know that this fitness class exists because
     * the other helper methods have checked that by matching the data tokens of the command line input to the possible
     * options seen in the enumb classes realting to a fitness class.
     * @param selectedClassStudioButton Radiobutton chosen for class
     * @param selectedInstructorButton Radiobutton chosen for instructor
     * @param selectedOfferButton Radiobutton chosen for offer
     * @return FitnessClass object that is to be processed
     */
    @FXML
    private FitnessClass return_FitnessClass_toProcess(RadioButton selectedOfferButton, RadioButton selectedInstructorButton,
                                                       RadioButton selectedClassStudioButton){

        Offer className = Offer.valueOf(selectedOfferButton.getText().toUpperCase());
        Instructor instructor = Instructor.valueOf(selectedInstructorButton.getText().toUpperCase());
        Location studio = Location.valueOf(selectedClassStudioButton.getText().toUpperCase());

        for(FitnessClass c : schedule.getClasses()){
            if(c!= null && c.getClassInfo().sameOffer(className) && c.getInstructor().sameInstructor(instructor) &&
                    c.getStudio().sameLocation(studio)){
                return c;
            }
        }
        return null;
    }

    /**
     * This is a helper method for the process_R_Command.
     * This method helps reduce the length of the process_R_Command method
     * by checking if many of the conditions required to attend a class are met
     * @param fitnessClass the class to be attended
     * @param member the member attending the class if all conditions are met
     */
    private void handle_Other_R_Cases(FitnessClass fitnessClass, Member member){
        Member memberToProcess = member;
        if(memberToProcess instanceof Basic && !memberToProcess.getHomeStudio().equalsOther(fitnessClass.getStudio())){
            output.appendText(String.format("%s %s is attending a class at %s - [BASIC] home studio at %s \n",
                    memberToProcess.getProfile().getFname(), memberToProcess.getProfile().getLname(),
                    fitnessClass.getStudio().getCityName(), memberToProcess.getHomeStudio().getCityName()));
            return;
        }
        if(fitnessClass.getMembers().contains(memberToProcess)){
            output.appendText(memberToProcess.getProfile().first_And_Last() + " is already in the class. \n");
            return;
        }
        for(FitnessClass c : schedule.getClasses()){
            if(c != null && c.getTime().sameTime(fitnessClass.getTime()) && c.getMembers().contains(memberToProcess)){
                output.appendText("Time conflict - " + memberToProcess.getProfile().first_And_Last() +
                        " is in another class held at " + c.getTime().toString() + " - " + fitnessClass.getInstructor() + ", " +
                        c.getTime().toString() + ", " + fitnessClass.getStudio().getCityName().toUpperCase() + "\n");
                return;
            }
        }
        fitnessClass.getMembers().add(memberToProcess);
        if(memberToProcess instanceof Basic){
            ((Basic) memberToProcess).setNumClasses(((Basic) memberToProcess).getNumClasses()+1);}
        output.appendText(memberToProcess.getProfile().first_And_Last() + " attendance recorded "
                + fitnessClass.getClassInfo() + " at " + fitnessClass.getStudio().toString() +"\n");
        return;
    }

    /**
     * Handles the event when the "Remove" button is clicked for removing a member from a fitness class attendance record.
     * This method validates the input fields and ensures that all required information is provided.
     * If any required information is missing or invalid, it displays an error message.
     * If the provided information is valid, it removes the attendance record for the member from the specified fitness class.
     * If the fitness class or the member is not found, it displays an error message.
     *
     * @param actionEvent The action event representing the user's click on the "Remove" button.
     */
    @FXML
    public void handleCA_Remove(ActionEvent actionEvent) {
        RadioButton selectedOfferButton = (RadioButton) offer.getSelectedToggle();
        RadioButton selectedInstructorButton = (RadioButton) instructorGroup.getSelectedToggle();
        RadioButton selectedClassStudioButton = (RadioButton) classStudio.getSelectedToggle();
        LocalDate dob = CA_datePicker.getValue();
        if(dob != null  && checkDate(dob)){
            return;
        }
        if(CA_lName.getText().isBlank() || CA_fName.getText().isBlank() || selectedClassStudioButton == null
                || selectedInstructorButton == null || selectedOfferButton == null || dob == null){
            output.appendText("Missing information to remove member \n");
            return;
        }
        Offer className = Offer.valueOf(selectedOfferButton.getText().toUpperCase());
        Instructor instructor = Instructor.valueOf(selectedInstructorButton.getText().toUpperCase());
        Location studio = Location.valueOf(selectedClassStudioButton.getText().toUpperCase());
        Profile profile = new Profile(CA_fName.getText(), CA_lName.getText(), convertLocalDateToDate(dob));


        for(FitnessClass c : schedule.getClasses()){
            if(c != null && c.getClassInfo().sameOffer(className) && c.getInstructor().sameInstructor(instructor) &&
                    c.getStudio().sameLocation(studio)){
                for (Member m : c.getMembers().getMembersList()){
                    if(m != null && m.getProfile().equals(profile)){
                        c.getMembers().remove(m);
                        output.appendText(m.getProfile().first_And_Last() + " is removed from " +
                                c.getInstructor() + ", " + c.getTime().toString() + ", " + c.getStudio().toString() + " \n");
                        return;
                    }
                }
                output.appendText(profile.first_And_Last() + " is not in " +
                        c.getInstructor() + ", " + c.getTime().toString() + ", " + c.getStudio().toString() + " \n");
                return;
            }

        }
            output.appendText("This fitness class does not exist \n");
            return;
    }

    /**
     * Handles the event when the "Add Guest" button is clicked to add a guest to a fitness class.
     * This method validates the input fields and ensures that all required information is provided.
     * If any required information is missing or invalid, it displays an error message.
     * If the provided information is valid, it adds the guest to the specified fitness class.
     * If the fitness class or the member is not found, it displays an error message.
     *
     * @param actionEvent The action event representing the user's click on the "Add Guest" button.
     */
    @FXML
    public void handleAddGuest(ActionEvent actionEvent) {
        RadioButton selectedOfferButton = (RadioButton) offer.getSelectedToggle();
        RadioButton selectedInstructorButton = (RadioButton) instructorGroup.getSelectedToggle();
        RadioButton selectedClassStudioButton = (RadioButton) classStudio.getSelectedToggle();
        LocalDate dob = CA_datePicker.getValue();

        if(dob != null  && checkDate(dob)){
            return;
        }
        if(CA_lName.getText().isBlank() || CA_fName.getText().isBlank() || selectedClassStudioButton == null
                || selectedInstructorButton == null || selectedOfferButton == null || dob == null){
            output.appendText("Missing information to add guest \n");
            return;
        }
        Profile profile = new Profile(CA_fName.getText(), CA_lName.getText(), convertLocalDateToDate(dob));
        boolean contains = false;
        Member memberToProcess = null;
        for (Member m : memberList.getMembersList()) {
            if (m != null && m.getProfile().equals(profile)) {
                contains = true;
                memberToProcess = m;
            }
        }
        if (!contains) {
            output.appendText(profile.R_Command_Profile_Format() + " is not in the member database. \n");
            return;
        }
        if (memberToProcess.isExpired()) {
            output.appendText(profile.R_Command_Profile_Format() + " membership expired. \n");
            return;
        }
        FitnessClass fitnessClass = return_FitnessClass_toProcess(selectedOfferButton,
                selectedInstructorButton, selectedClassStudioButton);

        if(fitnessClass == null){
            output.appendText(  selectedOfferButton.getText() + " by " + selectedInstructorButton.getText()
                    + " does not exist at " + selectedClassStudioButton.getText() + " \n");
            return;
        }
        handle_Add_Guest_Cases(fitnessClass, memberToProcess);
    }

    /**
     * Helper method for the RG command method helps with adding the proper instance of a member to the fitness class
     * @param fitnessClass class to be added to
     * @param member member added to fitness class
     */
    private void handle_Add_Guest_Cases(FitnessClass fitnessClass, Member member){
        Member memberToProcess = member;
        if (memberToProcess instanceof Basic) {
            output.appendText(memberToProcess.getProfile().first_And_Last() + " [BASIC] - no guest pass. \n");
            return;
        }
        if ((memberToProcess instanceof Family || memberToProcess instanceof Premium)
                && !memberToProcess.getHomeStudio().equalsOther(fitnessClass.getStudio())) {
            output.appendText(String.format("%s (guest) is attending a class at %s - home studio at %s \n",
                    memberToProcess.getProfile().first_And_Last(), fitnessClass.getStudio().getCityName(),
                    memberToProcess.getHomeStudio().getCityName()));
            return;
        }
        if (memberToProcess instanceof Premium premium_memb) {
            if(premium_memb.getGuestPass() == 0){
                output.appendText(memberToProcess .getProfile().first_And_Last() +
                        " guest pass not available. \n");
                return;
            }
            else{
                premium_memb.decrementGuestPass();
            }
        }
        if (memberToProcess instanceof Family family_memb) {
            if(family_memb.isGuestAvailable() == false){
                output.appendText(memberToProcess .getProfile().first_And_Last() +
                        " guest pass not available. \n");
                return;
            }
            else{
                family_memb.falsifyGuestPass();
            }
        }
        fitnessClass.getGuests().add(memberToProcess);
        output.appendText(memberToProcess.getProfile().first_And_Last() + " (guest) attendance recorded "
                + fitnessClass.getClassInfo() + " at " + fitnessClass.getStudio().toString() + "\n");
        return;
    }

    /**
     * Handles the event when the "Remove Guest" button is clicked to remove a guest member.
     * This method validates the input fields to ensure all required information is provided.
     * If any required information is missing, it displays an error message.
     * Otherwise, it constructs a profile object with the guest's information and attempts to remove
     * the guest member from the corresponding fitness class.
     *
     * @param actionEvent the action event triggered by clicking the "Remove Guest" button.
     */
    @FXML
    public void handleRemoveGuest(ActionEvent actionEvent) {
        RadioButton selectedOfferButton = (RadioButton) offer.getSelectedToggle();
        RadioButton selectedInstructorButton = (RadioButton) instructorGroup.getSelectedToggle();
        RadioButton selectedClassStudioButton = (RadioButton) classStudio.getSelectedToggle();
        LocalDate dob = CA_datePicker.getValue();
        if(dob != null  && checkDate(dob)){
            return;
        }
        if(CA_lName.getText().isBlank() || CA_fName.getText().isBlank() || selectedClassStudioButton == null
                || selectedInstructorButton == null || selectedOfferButton == null || dob == null){
            output.appendText("Missing information to remove guest \n");
            return;
        }
        Profile profile = new Profile(CA_fName.getText(), CA_lName.getText(), convertLocalDateToDate(dob));

        ug_simplifier(profile, selectedOfferButton, selectedInstructorButton, selectedClassStudioButton);

    }

    /**
     * Helper method that finishes the UG command by checking if the member/guest is apart of the fitness class
     * and removing them accordingly from the fitness class guest list or displaying that they are not in the class
     * to begin with.
     * @param profile member profile of the guest to be removed
     * @param selectedClassStudioButton class name of fitnessClass object from radioButton selection
     * @param selectedInstructorButton instructor of fitnessClass object from radioButton selection
     * @param selectedOfferButton studio of fitnessClass object from radioButton selection
     */
    private void ug_simplifier(Profile profile, RadioButton selectedOfferButton, RadioButton selectedInstructorButton,
                               RadioButton selectedClassStudioButton){

        Offer className = Offer.valueOf(selectedOfferButton.getText().toUpperCase());
        Instructor instructor = Instructor.valueOf(selectedInstructorButton.getText().toUpperCase());
        Location studio = Location.valueOf(selectedClassStudioButton.getText().toUpperCase());

        for(FitnessClass c : schedule.getClasses()){
            if(c != null && c.getClassInfo().sameOffer(className) && c.getInstructor().sameInstructor(instructor) &&
                    c.getStudio().sameLocation(studio)){
                for (Member m : c.getGuests().getMembersList()){
                    if(m != null && m.getProfile().equals(profile)){
                        if(m instanceof Premium premium_memb){
                            premium_memb.incramentGuestPass();
                        }
                        else if(m instanceof Family family_memb){
                            family_memb.makeGuestPassAvailable();
                        }
                        c.getGuests().remove(m);
                        output.appendText(m.getProfile().first_And_Last() + " (guest) is removed from " +
                                c.getInstructor() + ", " + c.getTime().toString() + ", " + c.getStudio().toString() + "\n");
                        return;
                    }
                }
                output.appendText((profile.first_And_Last() + " (guest) is not in " +
                        c.getInstructor() + ", " + c.getTime().toString() + ", " + c.getStudio().toString()) + "\n");
                return;
            }

        }
        output.appendText("This fitness class does not exist \n");
        return;

    }

    /**
     * Prints the fitness class schedule to the output area.
     * This method iterates through each fitness class in the schedule and prints its details,
     * including the class information and the attendees (both members and guests).
     * If a fitness class has no attendees, it only prints the class information.
     * If a fitness class has attendees, it prints the class information followed by the list of attendees.
     * If a fitness class has guests, it prints the list of guests separately.
     * The output is appended to the output area in the GUI.
     *
     * @param actionEvent the action event triggered by clicking the "Print Class Schedule" button.
     */
    @FXML
    public void printClassSchedule(ActionEvent actionEvent) {
        output.appendText("\n-Fitness classes-\n");
        for(FitnessClass c : schedule.getClasses()){
            if(c != null && c.getMembers().getSize() == 0){
                output.appendText(c.toString() +"\n");
            }
            else if(c != null && c.getMembers().getSize() > 0) {
                output.appendText(c.toString() +"\n");
                output.appendText("[Attendees]\n");
                for (Member m : c.getMembers().getMembersList()) {
                    if (m != null) {
                        output.appendText("   ");
                        output.appendText(m.toString() + "\n");
                    }

                }
            }
            if(c != null && c.getGuests().getSize() > 0){
                output.appendText("[Guests]\n");
                for (Member guest : c.getGuests().getMembersList()) {
                    if (guest != null) {
                        output.appendText("   ");
                        output.appendText(guest.toString() +"\n");
                    }
                }
            }
        }
        output.appendText("-end of class list.\n");

    }

    /**
     * Prints the list of members sorted by member profiles.
     * If the member database is not empty, this method prints the list of members sorted by their profiles,
     * including their first name, last name, and date of birth.
     * If the member database is empty, it displays a message indicating that the member database is empty.
     * The output is appended to the output area in the GUI.
     *
     * @param actionEvent the action event triggered by clicking the corresponding button.
     */
    @FXML
    public void printByMember(ActionEvent actionEvent) {
        if(memberList.getSize() > 0)
        {
            output.appendText("\n" + "-list of members sorted by member profiles-" + "\n" + memberList.printByMember() + "-end of list-\n");
        }
        else
        {
            output.appendText("Member Database is empty!" + "\n");
        }

    }

    /**
     * Prints the list of members sorted by county then zipcode.
     * If the member database is not empty, this method prints the list of members sorted by their county and then by zipcode.
     * If the member database is empty, it displays a message indicating that the member database is empty.
     * The output is appended to the output area in the GUI.
     *
     * @param actionEvent the action event triggered by clicking the corresponding button.
     */
    @FXML
    public void printByCounty(ActionEvent actionEvent) {
        if(memberList.getSize() > 0)
        {
            output.appendText("\n" + "-list of members sorted by county then zipcode-" + "\n" + memberList.printByCounty() + "-end of list-\n");
        }
        else
        {
            output.appendText("Member Database is empty!" + "\n");
        }
    }

    /**
     * Prints the list of members with next dues.
     * If the member database is not empty, this method prints the list of members who have dues pending,
     * including their profile information and the date when their dues are due.
     * If the member database is empty, it displays a message indicating that the member database is empty.
     * The output is appended to the output area in the GUI.
     *
     * @param actionEvent the action event triggered by clicking the corresponding button.
     */
    @FXML
    public void printFees(ActionEvent actionEvent) {
        if(memberList.getSize() > 0)
        {
            output.appendText("\n" + "-list of members with next dues-" + "\n" + memberList.printFees() + "-end of list-\n");
        }
        else
        {
            output.appendText("Member Database is empty!" + "\n");
        }
    }
}