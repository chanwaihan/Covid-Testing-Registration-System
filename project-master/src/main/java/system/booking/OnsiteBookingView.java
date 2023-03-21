package system.booking;

/**
 * This class is a OnsiteBooking's view class to handle all the view of the UI when dealing with OnsiteBooking.
 */
public class OnsiteBookingView {

    private OnsiteBookingModel onsiteBookingModel;

    /**
     * Constructor of OnsiteBookingModel.
     *
     * @param onsiteBookingModel The instance of OnsiteBookingModel
     */
    public OnsiteBookingView(OnsiteBookingModel onsiteBookingModel) {
        this.onsiteBookingModel = onsiteBookingModel;
    }

    /**
     * Return a descriptive string of result or output after the function in this class has been executed.
     */
    public void printDescription() {
        System.out.println(onsiteBookingModel.menuDescription());
    }
}
