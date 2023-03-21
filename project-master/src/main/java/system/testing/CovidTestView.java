package system.testing;

/**
 * This class is a CovidTest's view class to handle all the view of the UI when dealing with CovidTest.
 */
public class CovidTestView {

    private CovidTestModel covidTestModel;

    /**
     * Constructor of CovidTestModel
     *
     * @param covidTestModel The instance of CovidTestModel
     */
    public CovidTestView(CovidTestModel covidTestModel) {
        this.covidTestModel = covidTestModel;
    }

    /**
     * Return a descriptive string of result or output after the function in this class has been executed.
     */
    public void printDescription() {
        System.out.println(covidTestModel.getDescription());
    }
}
