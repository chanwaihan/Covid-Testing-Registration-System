package system.testing;

import java.util.Scanner;

/**
 * This class is a CovidTest's controller class to control both CovidTest's view and model to follow the MVC pattern.
 */
public class CovidTestController {

    private CovidTestModel covidTestModel;
    private CovidTestView covidTestView;

    /**
     * Constructor of CovidTestController
     *
     * @param covidTestModel The instance of CovidTestModel
     * @param covidTestView The instance of CovidTestView
     */
    public CovidTestController(CovidTestModel covidTestModel, CovidTestView covidTestView) {
        this.covidTestModel = covidTestModel;
        this.covidTestView = covidTestView;
    }

    /**
     * This method is to print out question to user when user is going to perform is covid test.
     */
    public void requestInterview() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you have severe symptoms?");
        System.out.println("1) Yes");
        System.out.println("2) No");
        System.out.println("----------------");
        System.out.print("Select your option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        covidTestModel.interview(choice);
    }

    /**
     * This method is to print out statement to ask from user for booking id to verify the customer.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void requestPatchBookingId(String rootUrl, String myApiKey) throws Exception {
        // ask for bookingId
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter bookingId: ");
        String bookingId = scanner.nextLine();

        covidTestModel.patchBookingId(rootUrl, myApiKey, bookingId);
    }

    /**
     * This method is to update the view of the HomeBooking status.
     */
    public void updateView() {
        covidTestView.printDescription();
    }
}
