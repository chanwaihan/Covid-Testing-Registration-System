package system.booking;

import java.util.Scanner;

/**
 * This class is a HomeBooking's controller class to control both HomeBooking's view and model class to follow the MVC
 * pattern.
 */
public class HomeBookingController {

    private HomeBookingModel homeBookingModel;
    private HomeBookingView homeBookingView;

    /**
     * Constructor of HomeBookingController
     *
     * @param homeBookingModel The instance of HomeBookingModel
     * @param homeBookingView The instance of HomeBookingView
     */
    public HomeBookingController(HomeBookingModel homeBookingModel, HomeBookingView homeBookingView) {
        this.homeBookingModel = homeBookingModel;
        this.homeBookingView = homeBookingView;
    }

    /**
     * This method is to print out question to user when user is required to edit the valid booking.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void requestEditBooking(String myApiKey, String rootUrl) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isValid = false;
        do {
            System.out.println("--------------------------------\n" +
                    "Please select your action below:\n" +
                    "1) View Profile\n" +
                    "2) Search for Booking\n" +
                    "---------------------------------");
            System.out.print("Select your option: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer for selection!");
            }
        } while (!isValid);

        homeBookingModel.editBooking(myApiKey, rootUrl, choice);
    }

    /**
     * This method is to update the view of the HomeBooking status.
     */
    public void updateView() {
        homeBookingView.printDescription();
    }
}
