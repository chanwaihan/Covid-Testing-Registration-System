package system.booking;

/**
 * This class is a OnsiteBooking's controller class to control both OnsiteBooking's view and model class to follow the
 * MVC pattern.
 */
public class OnsiteBookingController {

    private OnsiteBookingModel onsiteBookingModel;
    private OnsiteBookingView onsiteBookingView;

    /**
     * Constructor of OnsiteBookingController.
     *
     * @param onsiteBookingModel The instance of OnsiteBookingModel
     * @param onsiteBookingView The instance of OnsiteBookingView
     */
    public OnsiteBookingController(OnsiteBookingModel onsiteBookingModel, OnsiteBookingView onsiteBookingView) {
        this.onsiteBookingModel = onsiteBookingModel;
        this.onsiteBookingView = onsiteBookingView;
    }

    /**
     * This method is to update the view of the HomeBooking status.
     */
    public void updateView() {
        onsiteBookingView.printDescription();
    }
}
