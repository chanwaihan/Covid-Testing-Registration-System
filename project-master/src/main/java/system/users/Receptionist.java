package system.users;

/**
 * This class represents the role of Eeceptionist in the system.
 */
public class Receptionist extends User {

    /**
     * Constructor of Receptionist
     *
     * @param username username of the receptionist
     * @param password password of the receptionist's account
     */
    public Receptionist(String username, String password) {
        super(username, password, "receptionist");
    }
}
