package system.code;

/**
 * This class represents QRCode that will be generated when home booking is confirmed.
 */
public class QRCode implements Code {

    private String url;
    private String msg;

    /**
     * Constructor of QRCode
     *
     * @param url A string of URL link
     */
    public QRCode(String url) {
        setUrl(url);
    }

    /**
     * Getter of url
     *
     * @return A string of URL link
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter of url
     *
     * @param url A string of URL link
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Return a descriptive string of result or output after the QR code has been generated.
     *
     * @return String of result or output
     */
    @Override
    public String menuDescription() {
        msg = "QR Code generated at " + getUrl();
        return msg;
    }
}
