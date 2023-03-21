package system.booking;

import system.users.User;
import system.code.Code;

/**
 * This class is an abstract Booking class that will contain methods and stored variables for users to make booking,
 * modify booking and delete booking.
 */
public abstract class Booking {

    private User user;
    private String msg;
    private String customerId;
    private String testingSiteId;
    private String startTime;
    private String notes;
    private Code code;

    /**
     * Constructor of Booking
     */
    public Booking(User user) {
        this.user = user;
    }

    /**
     * Getter of user
     *
     * @return A user type
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter of message
     *
     * @return A string of message to be returned
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Setter of message
     *
     * @param msg A string of message
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Getter of customerId
     *
     * @return A string of customer id
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Setter of customerId
     *
     * @param customerId A string of customer id
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter of testingSiteId
     *
     * @return A string of testing site id
     */
    public String getTestingSiteId() {
        return testingSiteId;
    }

    /**
     * Setter of testingSiteId
     *
     * @param testingSiteId A string of testing site id
     */
    public void setTestingSiteId(String testingSiteId) {
        this.testingSiteId = testingSiteId;
    }

    /**
     * Getter of startTime
     *
     * @return A string of booking start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Setter of startTime
     *
     * @param startTime A string of booking start time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter of notes
     *
     * @return A string of extra notes to the booking
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Setter of notes
     *
     * @param notes A string of extra notes to the booking
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Getter of code
     *
     * @return A code type
     */
    public Code getCode() {
        return code;
    }

    /**
     * Setter of code
     *
     * @param code A code type
     */
    public void setCode(Code code) {
        this.code = code;
    }

    /**
     * Return a descriptive string of result or output after the function in this class has been executed.
     *
     * @return String of result or output
     */
    public String menuDescription() {
        String msg = getMsg();
        if (msg == null) {
            msg = "";
        }
        return msg;
    }
}
