package system.booking;

/**
 * This class is a HomeBooking's view class to handle all the view of the UI when dealing with HomeBooking.
 */
public class HomeBookingView {

    private HomeBookingModel homeBookingModel;

    /**
     * Constructor of HomeBookingView
     *
     * @param homeBookingModel The instance of HomeBookingModel
     */
    public HomeBookingView(HomeBookingModel homeBookingModel) {
        this.homeBookingModel = homeBookingModel;
    }

    /**
     * Return a descriptive string of result or output after the function in this class has been executed.
     */
    public void printDescription() {
        System.out.println(homeBookingModel.menuDescription());
    }
}
