package system.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import system.users.User;
import system.code.QRCode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

/**
 * This class is a HomeBooking's model class which represent the Home Booking with stored information of a Home Booking.
 */
public class HomeBookingModel extends Booking {

    /**
     * Constructor of HomeBookingModel
     */
    public HomeBookingModel(User user) {
        super(user);
    }

    /**
     * This method is to create a booking for customer.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void createBooking(String myApiKey, String rootUrl) throws Exception {
        User user = getUser();

        // get user id
        String usersUrl = rootUrl + "/user";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl + "?jwt=true"))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        // check whole db
        for (ObjectNode node: jsonNodes) {
            if (node.get("userName").asText().equals(user.getUsername())) {
                user.setId(node.get("id").asText());
            }
        }

        // creating booking
        String id = user.getId();
        String testingSiteId = user.getTestingSiteId();
        String startTime = Instant.now().toString();
        String notes = "Home Booking and ";

        String randomQRCodeURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/QR_code_for_mobile_English_Wikipedia.svg/800px-QR_code_for_mobile_English_Wikipedia.svg.png";
        setCode(new QRCode(randomQRCodeURL));

        String randomURL = generateRandomURL();
        String customerUsername = user.getUsername();
        String homeBookingURLMenuDescription = homeBookingURLMenuDescription(randomURL, customerUsername);
        setMsg(getCode().menuDescription());
        setMsg(homeBookingURLMenuDescription);

        int answer = askHomeBookingTestKitQuestion();
        if (answer == 1) {
            notes += "possesses own testing kit. ";
        }
        else if (answer == 2) {
            notes += "does not possess own testing kit. ";
        }

        notes += "QR Code: " + randomQRCodeURL;
        notes += ", URL: " + randomURL;

        setCustomerId(id);
        setTestingSiteId(testingSiteId);
        setStartTime(startTime);
        setNotes(notes);

        // store booking
        // https://attacomsian.com/blog/jackson-create-json-object
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode booking = mapper.createObjectNode();

        booking.put("customerId", getCustomerId());
        booking.put("testingSiteId", getTestingSiteId());
        booking.put("startTime", getStartTime());
        booking.put("notes", getNotes());

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);

        String bookingsUrl = rootUrl + "/booking";
        client = HttpClient.newHttpClient();
        request = HttpRequest
                .newBuilder(URI.create(bookingsUrl + "?jwt=true"))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // handle each response
        if (response.statusCode() == 201) {
            super.setMsg("New booking successfully created.\n");
        }
        else if (response.statusCode() == 400) {
            super.setMsg("Request body could not be parsed or contains invalid fields.");
        }
        else if (response.statusCode() == 401) {
            super.setMsg("A valid API key was not provided in the request.");
        }
        else if (response.statusCode() == 404) {
            super.setMsg("A customer and/or testing site with the provided ID was not found.");
        }
    }

    /**
     * This method is to get information from the user when he/she books for a covid test and an action will be told
     * to the users what to do if he/she chooses a certain option.
     *
     * @return an integer which represents whether the user posses his/her own testing kit
     */
    public int askHomeBookingTestKitQuestion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you possess your own testing kit?");
        System.out.println("1) Yes");
        System.out.println("2) No");
        System.out.print("Select your option: ");
        int answer = Integer.parseInt(scanner.nextLine());
        System.out.println();

        if (answer == 1) {
            System.out.println("You do not need to use the generated QR code.");
            System.out.println();
        }
        else if (answer == 2) {
            System.out.println("Please present the generated QR code at the testing site.");
            System.out.println();
        }
        else {
            System.out.println("Please enter an integer from 1 to 2!");
            System.out.println();
        }
        return answer;
    }

    /**
     * Generate a random URL link (invalid and just a fake url) when the users successfully booked for a covid test
     *
     * @return URL link
     */
    public String generateRandomURL() {
        String randomURL = "https://randomFIT3077URLForBookingDoNotClick";
        return randomURL;
    }

    /**
     * Return a details of booking after users successfully booked.
     *
     * @param randomURL URL link
     * @param user user's username
     * @return A string of details of booking
     */
    public String homeBookingURLMenuDescription(String randomURL, String user) {
        String homeBookingURLMenuDescription = "\nURL for testing at " + randomURL +
                "\nURL has been successfully emailed and texted to " + user + "\n";
        return homeBookingURLMenuDescription;
    }

    /**
     * This method is to edit the valid booking in the system requested by the customer.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void editBooking(String myApiKey, String rootUrl, int choice) throws Exception {
        int counter = 0;
        User user = getUser();

        Scanner scanner = new Scanner(System.in);
        Date currentDate = new Date();

        // print out all active bookingS
        // https://attacomsian.com/blog/jackson-create-json-object
        String bookingUrl = rootUrl + "/booking";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingUrl))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        System.out.println("\n" + user.getUsername() + "'s active bookings: ");
        for (ObjectNode node: jsonNodes) {
            if (node.get("customer").get("userName").asText().equals(user.getUsername())) {
                counter += 1;
                // only print out if customer wants to view
                if (choice == 1) {
                    String tempStartDateString = node.get("startTime").toString().substring(1,11);
                    Date tempStartDate= new SimpleDateFormat("yyyy-MM-dd").parse(tempStartDateString);

                    // https://stackoverflow.com/questions/1439779/how-to-compare-two-dates-without-the-time-portion
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    if (sdf.format(tempStartDate).equals(sdf.format(currentDate))) {
                        System.out.println(node.toString());
                    }
                }
            }
        }

        if (counter == 0) {
            System.out.println(user.getUsername() + " has no active bookings.");
        }

        else if (counter > 0) {
            // get booking id
            System.out.print("Enter Booking Id to be modified: ");
            String tempBookingId = scanner.nextLine();

            // get new testing site and time
            String newTestingSiteId = selectTestingSite(user, myApiKey, rootUrl);
            setTestingSiteId(newTestingSiteId);
            String newStartTime = Instant.now().toString();
            setStartTime(newStartTime);

            // modify new testing site id to existing booking
            // store booking
            // https://attacomsian.com/blog/jackson-create-json-object
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode newBooking = mapper.createObjectNode();
            newBooking.put("testingSiteId", getTestingSiteId());
            newBooking.put("startTime", getStartTime());

            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newBooking);

            bookingUrl = rootUrl + "/booking/" + tempBookingId;
            client = HttpClient.newHttpClient();
            request = HttpRequest
                    .newBuilder(URI.create(bookingUrl + "?jwt=true"))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                    .setHeader("Authorization", myApiKey)
                    .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // handle each response
            if (response.statusCode() == 200) {
                super.setMsg("Existing booking successfully updated.\n");
            }
            else if (response.statusCode() == 400) {
                super.setMsg("Request body could not be parsed or contains invalid fields.");
            }
            else if (response.statusCode() == 401) {
                super.setMsg("A valid API key was not provided in the request.");
            }
            else if (response.statusCode() == 404) {
                super.setMsg("A booking, customer, and/or testing site with the provided ID was not found.");
            }
        }
    }

    /**
     * This method is provided a list of testing site for users to choose if they are going to make a home booking.
     *
     * @return An integer of what the customer choose for the testing sites
     */
    public String selectTestingSite(User user, String myApiKey, String rootUrl) throws Exception {
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
        String newTestingSiteId = jsonNodes[testingSiteSelection-1].get("id").asText();
        user.setTestingSiteId(newTestingSiteId);
        System.out.println();

        return newTestingSiteId;
    }

    /**
     * This method is to delete a valid booking from the system requested by the customer.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void deleteBooking(String myApiKey, String rootUrl) throws Exception {
        int counter = 0;
        User user = getUser();
        Scanner scanner = new Scanner(System.in);
        Date currentDate = new Date();

        // print out all active bookingS
        // https://attacomsian.com/blog/jackson-create-json-object
        String bookingUrl = rootUrl + "/booking";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingUrl))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        System.out.println("\n" + user.getUsername() + "'s active bookings: ");
        for (ObjectNode node: jsonNodes) {
            if (node.get("customer").get("userName").asText().equals(user.getUsername())) {
                String tempStartDateString = node.get("startTime").toString().substring(1,11);
                Date tempStartDate= new SimpleDateFormat("yyyy-MM-dd").parse(tempStartDateString);

                // https://stackoverflow.com/questions/1439779/how-to-compare-two-dates-without-the-time-portion
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                if (sdf.format(tempStartDate).equals(sdf.format(currentDate))) {
                    counter += 1;
                    System.out.println(node.toString());
                }
            }
        }

        if (counter == 0) {
            System.out.println(user.getUsername() + " has no active bookings.");
        }

        else if (counter > 0) {
            // get booking id
            System.out.print("Enter Booking Id to be deleted: ");
            String tempBookingId = scanner.nextLine();

            // delete booking based on id

            bookingUrl = rootUrl + "/booking/" + tempBookingId;
            client = HttpClient.newHttpClient();
            request = HttpRequest
                    .newBuilder(URI.create(bookingUrl + "?jwt=true"))
                    .setHeader("Authorization", myApiKey)
                    .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                    .DELETE()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // handle each response
            if (response.statusCode() == 204) {
                super.setMsg("The COVID test was successfully deleted.\n");
            }
            else if (response.statusCode() == 400) {
                super.setMsg("Request body could not be parsed or contains invalid fields.");
            }
            else if (response.statusCode() == 401) {
                super.setMsg("A valid API key was not provided in the request.");
            }
            else if (response.statusCode() == 404) {
                super.setMsg("A booking, customer, and/or testing site with the provided ID was not found.");
            }
        }
    }
}
