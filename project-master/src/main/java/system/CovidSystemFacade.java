package system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import system.booking.*;
import system.code.PINCode;
import system.facility.FacilityController;
import system.facility.FacilityModel;
import system.facility.FacilityView;
import system.testing.CovidTestModel;
import system.testing.CovidTestController;
import system.testing.CovidTestView;
import system.users.Customer;
import system.users.HealthcareWorker;
import system.users.Receptionist;
import system.login.LoginAction;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

/**
 * This class is to handle all the console menu and printing.
 */
public class CovidSystemFacade {
    private static final String myApiKey = "<INSERT YOUR API KEY HERE>";
    // Provide the root URL for the web service. All web service request URLs start with this root URL.
    //  private static final String rootUrl = "https://fit3077.com/api/v1";
    private static final String rootUrl = "https://fit3077.com/api/v2";

    /**
     * Print out the menu.
     */
    public void printMenu() {
        int selection = 0;
        int userSelection = 0;
        System.out.println("+---------------------------------------+\n" +
                           "|                                       |\n" +
                           "|    Welcome to FIT3077 Covid System!   |\n" +
                           "|    (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ ✧ﾟ･: *ヽ(◕ヮ◕ヽ)   |\n" +
                           "|                                       |\n" +
                           "+---------------------------------------+");
        do {
            try {
                selection = selectMenuItem();
                switch (selection) {
                    case 1:
                        System.out.println("Role: Customer");
                        userSelection = userSelection();
                        userAccountAction(selection, userSelection);
                        break;
                    case 2:
                        System.out.println("Role: Receptionist");
                        userAccountAction(selection, 0);
                        break;
                    case 3:
                        System.out.println("Role: Healthcare Worker");
                        userSelection = userSelection();
                        userAccountAction(selection, userSelection);
                        break;
                    case 4:
                        System.out.println("+---------------------------------------+\n" +
                                           "|                                       |\n" +
                                           "|         Tq for using the system!      |\n" +
                                           "|                (◕‿◕✿)               |\n" +
                                           "|                                       |\n" +
                                           "+---------------------------------------+");
                        break;
                    default:
                        System.out.println("Please enter an integer from 1 to 4!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (selection != 4) ;
    }

    /**
     * Print out options for users to choose whether to login to system or create new account.
     *
     * @return An integer of what users chosen where 1 is creating new account and 2 is to login
     */
    public int userSelection() {
        Scanner scanner = new Scanner(System.in);
        int userSelection = 0;
        boolean isValid = false;
        do {
            System.out.println("--------------------------------\n" +
                               "Please select your action below:\n" +
                               "1) Create New Account\n" +
                               "2) Login\n" +
                               "**Any other number to go back\n" +
                               "--------------------------------");
            System.out.print("Select your option: ");
            try {
                userSelection = Integer.parseInt(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer for selection!");
            }
        } while (!isValid);
        return userSelection;
    }

    /**
     * This method is to create new account or login based on users' choice
     *
     * @param selection integer of what users chosen to login as a role
     * @param userSelection integer of what users chosen where 1 is creating new account and 2 is to login
     */
    public void userAccountAction(int selection, int userSelection) throws Exception {
        // create new account
        if (selection == 1) {
            // create new account
            if (userSelection == 1) {
                System.out.println("1 is selected");
                System.out.println();
                String[] info = createNewAccountInfo();
                Customer customer = new Customer(info[0], info[1], info[2], info[3], info[4]);

                RegisterAction registerAction = new RegisterAction(customer);
                customer.executeAction(registerAction, myApiKey, rootUrl);
                System.out.println(registerAction.menuDescription());
            }
            // login
            else if (userSelection == 2) {
                System.out.println("2 is selected");
                System.out.println();
                String[] info = loginInfo();
                Customer customer = new Customer(info[0], info[1]);

                LoginAction loginAction = new LoginAction(customer);
                customer.executeAction(loginAction, myApiKey, rootUrl);
                System.out.println(loginAction.menuDescription());
                HomeBookingModel homeBookingModel = new HomeBookingModel(customer);
                HomeBookingView homeBookingView = new HomeBookingView(homeBookingModel);
                HomeBookingController homeBookingController = new HomeBookingController(homeBookingModel, homeBookingView);
                if (loginAction.getStatus().equals("valid")) {
                    // search or home booking or edit active booking
                    int customerSelection;
                    do {
                        customerSelection = customerAction();
                        switch (customerSelection) {
                            case 1:
                                // search suburb name or type of facility
                                System.out.println("---------------------------\n" +
                                                   "Search for testing site by:\n" +
                                                   "1) Suburb name\n" +
                                                   "2) Type of facility\n" +
                                                   "---------------------------");
                                System.out.print("Select your option: ");
                                Scanner scanner = new Scanner(System.in);
                                int searchSelection = Integer.parseInt(scanner.nextLine());
                                FacilityModel facilityModel = new FacilityModel();
                                FacilityView facilityView = new FacilityView(facilityModel);
                                FacilityController facilityController = new FacilityController(facilityModel, facilityView);
                                // suburb name
                                if (searchSelection == 1) {
                                    facilityController.requestSearchSuburb(myApiKey, rootUrl);
                                    facilityController.updateView();
                                }
                                // type of facility
                                else if (searchSelection == 2) {
                                    facilityController.requestSearchFacility(myApiKey, rootUrl);
                                    facilityController.updateView();
                                }
                                // Exception
                                else {
                                    System.out.println("Please enter an integer from 1 to 2!");
                                }
                                break;
                            case 2:
                                selectTestingSite(customer);
                                // create Booking
                                homeBookingModel.createBooking(myApiKey, rootUrl);
                                homeBookingController.updateView();
                                break;
                            case 3:
                                homeBookingController.requestEditBooking(myApiKey, rootUrl);
                                homeBookingController.updateView();
                                break;
                            case 4:
                                homeBookingModel.deleteBooking(myApiKey, rootUrl);
                                homeBookingController.updateView();
                            case 5:
                                break;
                            default:
                                System.out.println("Please enter an integer from 1 to 5!");
                        }
                    } while (customerSelection != 5);
                }
            }
        }
        // receptionist
        // cannot register, only login
        else if (selection == 2) {
            // login
            String[] info = loginInfo();
            Receptionist receptionist = new Receptionist(info[0], info[1]);

            LoginAction loginAction = new LoginAction(receptionist);
            receptionist.executeAction(loginAction, myApiKey, rootUrl);
            System.out.println(loginAction.menuDescription());
            if (loginAction.getStatus().equals("valid")) {
                int receptionistAction;
                do {
                    receptionistAction = receptionistAction();
                    switch (receptionistAction) {
                        case 1:
                            // create Booking
                            String[] onSiteBookingInfo = onSiteBookingInfo();
                            Customer customer = new Customer(onSiteBookingInfo[0], onSiteBookingInfo[1], "customer");
                            OnsiteBookingModel onsiteBookingModel = new OnsiteBookingModel(customer);
                            OnsiteBookingView onsiteBookingView = new OnsiteBookingView(onsiteBookingModel);
                            OnsiteBookingController onsiteBookingController = new OnsiteBookingController(onsiteBookingModel, onsiteBookingView);
                            onsiteBookingModel.createBooking(myApiKey, rootUrl);
                            onsiteBookingController.updateView();
                            break;
                        case 2:
                            // edit Booking
                            String[] editBookingCustomer = editBookingCustomerInfo();
                            customer = new Customer(editBookingCustomer[0], new PINCode(editBookingCustomer[1]));
                            OnsiteBookingModel onsiteBookingModel1 = new OnsiteBookingModel(customer);
                            OnsiteBookingView onsiteBookingView1 = new OnsiteBookingView(onsiteBookingModel1);
                            OnsiteBookingController onsiteBookingController1 = new OnsiteBookingController(onsiteBookingModel1, onsiteBookingView1);
                            onsiteBookingModel1.editBooking(myApiKey, rootUrl);
                            onsiteBookingController1.updateView();
                            break;
                        case 3:
                            // delete Booking
                            editBookingCustomer = editBookingCustomerInfo();
                            customer = new Customer(editBookingCustomer[0], new PINCode(editBookingCustomer[1]));
                            OnsiteBookingModel onsiteBookingModel2 = new OnsiteBookingModel(customer);
                            OnsiteBookingView onsiteBookingView2 = new OnsiteBookingView(onsiteBookingModel2);
                            OnsiteBookingController onsiteBookingController2 = new OnsiteBookingController(onsiteBookingModel2, onsiteBookingView2);
                            onsiteBookingModel2.deleteBooking(myApiKey, rootUrl);
                            onsiteBookingController2.updateView();
                            break;
                        case 4:
                            break;
                        default:
                            System.out.println("Please enter an integer from 1 to 4!");
                    }
                } while (receptionistAction != 4);

            }
        }
        // healthcare worker
        else if (selection == 3) {
            // create new account
            if (userSelection == 1) {
                System.out.println("1 is selected");
                System.out.println();
                String[] info = createNewAccountInfo();
                HealthcareWorker healthcareWorker = new HealthcareWorker(info[0], info[1], info[2], info[3], info[4]);
                RegisterAction registerAction = new RegisterAction(healthcareWorker);
                healthcareWorker.executeAction(registerAction, myApiKey, rootUrl);
                System.out.println(registerAction.menuDescription());
            }
            // login
            else if (userSelection == 2) {
                System.out.println("2 is selected");
                System.out.println();
                String[] info = loginInfo();
                HealthcareWorker healthcareWorker = new HealthcareWorker(info[0], info[1]);

                LoginAction loginAction = new LoginAction(healthcareWorker);
                healthcareWorker.executeAction(loginAction, myApiKey, rootUrl);
                System.out.println(loginAction.menuDescription());

                if (loginAction.getStatus().equals("valid")) {
                    int healthcareSelection;
                    do {
                        healthcareSelection = healthcareWorkerAction();
                        switch (healthcareSelection) {
                            case 1:
                                CovidTestModel covidTestModel = new CovidTestModel();
                                CovidTestView covidTestView = new CovidTestView(covidTestModel);
                                CovidTestController covidTestController = new CovidTestController(covidTestModel, covidTestView);
                                // interview
                                covidTestController.requestInterview();
                                // patch
                                covidTestController.requestPatchBookingId(rootUrl, myApiKey);
                                covidTestController.updateView();
                                break;
                            case 2:
                                break;
                            default:
                                System.out.println("Please enter an integer from 1 to 2!");
                        }
                    } while (healthcareSelection != 2);
                }
            }
        }
    }

    /**
     * This method let the customer choose what action to do which is searching or booking after login to the system.
     */
    public int customerAction() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isValid = false;
        do {
            System.out.println("--------------------------------\n" +
                               "Please select your action below:\n" +
                               "1) Search for Testing Sites\n" +
                               "2) Home Booking\n" +
                               "3) Edit Active Booking\n" +
                               "4) Delete Active Booking\n" +
                               "5) Log Out\n" +
                               "---------------------------------");
            System.out.print("Select your option: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer for selection!");
            }
        } while (!isValid);
        return choice;
    }

    /**
     * This method let the receptionist choose what action to do.
     */
    public int receptionistAction() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isValid = false;
        do {
            System.out.println("--------------------------------\n" +
                               "Please select your action below:\n" +
                               "1) Make Booking for Resident\n" +
                               "2) Edit Active Booking\n" +
                               "3) Delete Active Booking\n" +
                               "4) Log Out\n" +
                               "--------------------------------");
            System.out.print("Select your option: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer for selection!");
            }
        } while (!isValid);
        return choice;
    }

    /**
     * This method is to run the action when the healthcare workers login into the system.
     */
    public int healthcareWorkerAction() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isValid = false;
        do {
            System.out.println("--------------------------------\n" +
                    "Please select your action below:\n" +
                    "1) Interview Resident\n" +
                    "2) Log Out\n" +
                    "--------------------------------");
            System.out.print("Select your option: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer for selection!");
            }
        } while (!isValid);
        return choice;
    }

    /**
     * This method is provided a list of testing site for users to choose if they are going to make a home booking.
     *
     * @return An integer of what the customer choose for the testing sites
     */
    public int selectTestingSite(Customer customer) throws Exception {
        int i=1;
        String str = "";

        String testingSitesUrl = rootUrl + "/testing-site";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(testingSitesUrl))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        for (ObjectNode node: jsonNodes) {
            str += i + ") " + node.get("name").asText() + "\n";
            i += 1;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Please select a testing site:");
        System.out.println(str);
        System.out.print("Select your option: ");
        int testingSiteSelection = Integer.parseInt(scanner.nextLine());
        customer.setTestingSiteId(jsonNodes[testingSiteSelection-1].get("id").asText());
        System.out.println();

        return testingSiteSelection;
    }

    /**
     * This method print out the role for the users to choose when the system started to run.
     *
     * @return An integer of what the users choose to login as
     */
    public int selectMenuItem() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean isValid = false;
        do {
            System.out.println("------------------------------\n" +
                               "Please select your role below:\n" +
                               "1) Customer\n" +
                               "2) Receptionist\n" +
                               "3) Healthcare Worker\n" +
                               "4) Exit\n" +
                               "------------------------------");
            System.out.print("Select your option: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                isValid = true;
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter an integer for selection!");
            }
        } while (!isValid);
        return choice;
    }

    /**
     * This method is to ask details from customer when they decide to create a new account.
     *
     * @return An array list of information of the new customer
     */
    public String[] createNewAccountInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter given name: ");
        String givenName = scanner.nextLine();
        System.out.print("Enter family name: ");
        String familyName = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        String[] info = {givenName, familyName, username, password, phoneNumber};
        return info;
    }

    /**
     * This method ask for users' username and password when they are trying to login.
     *
     * @return An array list which contains username and password
     */
    public String[] loginInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        String[] info = {username, password};
        return info;
    }

    /**
     * This method ask for the customer id and testing site id when the receptionist try to make a booking for customer.
     *
     * @return An array list which contains customer id and testing site id
     */
    public String[] onSiteBookingInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter Testing Site ID: ");
        String testingSiteId = scanner.nextLine();
        String[] info = {customerId, testingSiteId};
        return info;
    }

    /**
     * This method ask for the booking id and pin code from customer when the customer is trying to edit booking from a
     * call
     *
     * @return An array list which contains booking id and pin code
     */
    public String[] editBookingCustomerInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Booking ID: ");
        String bookingId = scanner.nextLine();
        System.out.print("Enter PIN Code: ");
        String pinCode = scanner.nextLine();
        String[] info = {bookingId, pinCode};
        return info;
    }

}
