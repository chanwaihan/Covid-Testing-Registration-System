package system.facility;

/**
 * This class is a Facility's View class to handle all the view of the UI when dealing with Facility.
 */
public class FacilityView {

    private FacilityModel facilityModel;

    /**
     * Constructor of FacilityView
     *
     * @param facilityModel The instance of FacilityModel
     */
    public FacilityView(FacilityModel facilityModel) {
        this.facilityModel = facilityModel;
    }

    /**
     * Return a descriptive string of result or output after the function in this class has been executed.
     */
    public void printDescription() {
        System.out.println(facilityModel.getDescription());
    }
}
