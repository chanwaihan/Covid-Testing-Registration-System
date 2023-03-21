package system.users;

import system.Actions;
import system.code.PINCode;

/**
 * This class represents the User of different roles such as Users, Administrators and Healthcare workers.
 */
public abstract class User {

    private String givenName;
    private String familyName;
    private String username;
    private String password;
    private String phoneNumber;
    private String type;
    private String id;
    private String testingSiteId;
    private String bookingId;
    private PINCode pinCode;

    /**
     * Constructor User
     *
     * @param givenName first name of the user
     * @param familyName last name of the user
     * @param username username of the user which used to login purpose
     * @param password password of the user's account
     * @param phoneNumber phone number of the user
     * @param type role of the user
     */
    public User(String givenName, String familyName, String username, String password, String phoneNumber, String type) {
        setGivenName(givenName);
        setFamilyName(familyName);
        setUsername(username);
        setPassword(password);
        setPhoneNumber(phoneNumber);
        setType(type);
    }

    /**
     * Constructor of User
     *
     * @param username username of the user which used to login purpose
     * @param password password of the user's account
     * @param type role of the user
     */
    public User(String username, String password, String type) {
        setUsername(username);
        setPassword(password);
        setType(type);
    }

    /**
     * Constructor of User
     *
     * @param id booking id generated after successfully booked
     * @param testingSiteId testing sites id
     */
    public User(String id, String testingSiteId) {
        setId(id);
        setTestingSiteId(testingSiteId);
        setType("customer");
    }

    /**
     * Constructor of User
     *
     * @param bookingId booking id of the user
     * @param PinCode pin code of the certain booking of the user to verify the user
     */
    public User(String bookingId, PINCode pinCode) {
        setBookingId(bookingId);
        setPinCode(pinCode);
    }

    /**
     * Getter of givenName
     *
     * @return A string of givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Setter of givenName
     *
     * @param givenName A string of givenName
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * Getter of familyName
     *
     * @return A string of familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Setter of familyName
     *
     * @param familyName A string of familyName
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * Getter of username
     *
     * @return A string of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of username
     *
     * @param username A string of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of password
     *
     * @return A string of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of password
     *
     * @param password A string of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter of phoneNumber
     *
     * @return A string of phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter of phoneNumber
     *
     * @param phoneNumber A string of phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter of type
     *
     * @return A string of type pf role
     */
    public String getType() {
        return type;
    }

    /**
     * Setter of type
     *
     * @param type A string of type of role
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter of id
     *
     * @return A string of booking id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter of id
     *
     * @param id A string of booking id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter of testingSiteId
     *
     * @return A string of testing sites id
     */
    public String getTestingSiteId() {
        return this.testingSiteId;
    }

    /**
     * Setter of testingSiteId
     *
     * @param testingSiteId A string of testing sites id
     */
    public void setTestingSiteId(String testingSiteId) {
        this.testingSiteId = testingSiteId;
    }

    /**
     * Setter of bookingId
     *
     * @param bookingId booking id
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Getter of bookingId
     *
     * @return A string of booking Id
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Setter of pinCode
     *
     * @param pinCode A PinCode type
     */
    public void setPinCode(PINCode pinCode) {
        this.pinCode = pinCode;
    }

    /**
     * Getter of pinCode
     *
     * @return An instance of pinCode
     */
    public PINCode getPinCode() {
        return pinCode;
    }

    /**
     * This method is to execute the action logic with the user interacted with
     *
     * @param actions the action that the user is going to have
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void executeAction(Actions actions, String myApiKey, String rootUrl) throws Exception {
        actions.execute(myApiKey, rootUrl);
    }
}
