package system.users;

/**
 * This class represents the role of Healthcare worker in the system.
 */
public class HealthcareWorker extends User {

    /**
     * Constructor of HealthCareWorker
     *
     * @param givenName first name of the healthcare worker
     * @param familyName last name of the healthcare worker
     * @param username username of the healthcare worker
     * @param password password of the healthcare worker's account
     * @param phoneNumber phone number of the healthcare worker's
     */
    public HealthcareWorker(String givenName, String familyName, String username, String password, String phoneNumber) {
        super(givenName, familyName, username, password, phoneNumber, "healthcareworker");
    }

    /**
     * Constructor of HealthCareWorker
     *
     * @param username username of the healthcare worker
     * @param password password of the healthcare worker's account
     */
    public HealthcareWorker(String username, String password) {
        super(username, password, "healthcareworker");
    }

}
