package system.users;

import system.code.PINCode;

/**
 * This class represents the role of Customer in the system.
 */
public class Customer extends User {

    /**
     * Constructor of Customer
     *
     * @param givenName first name of the customer
     * @param familyName last name of the customer
     * @param username username of the customer which used to login purpose
     * @param password password of the customer's account
     * @param phoneNumber phone number of the customer
     */
    public Customer(String givenName, String familyName, String username, String password, String phoneNumber) {
        super(givenName, familyName, username, password, phoneNumber, "customer");
    }

    /**
     * Constructor of Customer
     *
     * @param username username of the customer which used to login purpose
     * @param password password of the customer's account
     */
    public Customer(String username, String password) {
        super(username, password, "customer");
    }

    /**
     * Constructor of Customer
     *
     * @param userId customer id
     * @param testingSiteId testing site id
     * @param type rol
     */
    public Customer(String userId, String testingSiteId, String type) {
        super(userId, testingSiteId);
    }

    /**
     * Constructor of Customer
     *
     * @param bookingId booking id of the customer
     * @param pinCode pin code of the certain booking of the customer to verify the customer
     */
    public Customer(String bookingId, PINCode pinCode) {
        super(bookingId, pinCode);
    }
}
