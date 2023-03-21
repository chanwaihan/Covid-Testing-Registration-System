package system.code;

/**
 * This class represents PINCode that will be generated when on-site booking is done.
 */
public class PINCode implements Code {

    private String pin;
    private String msg;

    /**
     * Constructor of PINCode
     *
     * @param pin A string of PIN
     */
    public PINCode(String pin) {
        setPin(pin);
    }

    /**
     * Getter of pin
     *
     * @return A string of PIN code
     */
    public String getPin() {
        return pin;
    }

    /**
     * Setter of pin
     *
     * @param pin A string of PIN code
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * Return a descriptive string of result or output after the PIN code has been generated.
     *
     * @return String of result or output
     */
    @Override
    public String menuDescription() {
        msg = "PIN Code generated is " + getPin();
        return msg;
    }
}
