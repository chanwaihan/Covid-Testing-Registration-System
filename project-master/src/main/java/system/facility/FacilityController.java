package system.facility;

import java.util.Scanner;

/**
 * This class is a Facility's controller class to control both Facility's view and class to follow the MVC pattern.
 */
public class FacilityController {

    private FacilityModel facilityModel;
    private FacilityView facilityView;

    /**
     * Constructor of FacilityController.
     *
     * @param facilityModel The instance of FacilityModel
     * @param facilityView The instance of FacilityView
     */
    public FacilityController(FacilityModel facilityModel, FacilityView facilityView) {
        this.facilityModel = facilityModel;
        this.facilityView = facilityView;
    }

    /**
     * This method is to print out the question to user when user is requested to search for facility by facility type.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void requestSearchFacility(String myApiKey, String rootUrl) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter type of facility: ");
        String typeOfFacility = scanner.nextLine().toLowerCase();
        typeOfFacility = typeOfFacility.replaceAll("[^a-zA-Z0-9]", " ");

        facilityModel.searchFacility(myApiKey, rootUrl, typeOfFacility);
    }

    /**
     * This method is to print out the question to user when user is requested to search for facility by facility name.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void requestSearchSuburb(String myApiKey, String rootUrl) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter suburb name: ");
        String suburbName = scanner.nextLine().toLowerCase();

        facilityModel.searchSuburb(myApiKey, rootUrl, suburbName);
    }

    /**
     * This method is to update the view of the HomeBooking status.
     */
    public void updateView() {
        facilityView.printDescription();
    }
}
