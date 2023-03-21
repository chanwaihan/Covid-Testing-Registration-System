package system;

/**
 * This class is an abstract Action class that will contain methods to be executed in the Menu.java class.
 */
public abstract class Actions {

    private String status = "";

    /**
     * Execute a specific action that needed to be performed.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public abstract void execute(String myApiKey, String rootUrl) throws Exception;

    /**
     * Return a descriptive string of result or output after the action has been performed.
     *
     * @return String of result or output
     */
    public abstract String menuDescription();

    /**
     * Setter of status
     *
     * @param status A string of status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter of status
     *
     * @return A string of status
     */
    public String getStatus() {
        return this.status;
    }
}
