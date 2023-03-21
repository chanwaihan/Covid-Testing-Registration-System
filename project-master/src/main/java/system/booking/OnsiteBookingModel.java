package system.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import system.users.User;
import system.code.PINCode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Scanner;

/**
 * This class is a OnsiteBooking's model class which represents the Onsite Booking with stored information about the
 * customer which made by the administrator.
 */
public class OnsiteBookingModel extends Booking {

    /**
     * Constructor of OnsiteBookingModel
     */
    public OnsiteBookingModel(User user) {
        super(user);
    }

    /**
     * This method is to create a booking for customer by the administrator.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void createBooking(String myApiKey, String rootUrl) throws Exception {
        User user = getUser();

        String randomPIN = "0123";
        setCode(new PINCode(randomPIN));

        String id = user.getId();
        String testingSiteId = user.getTestingSiteId();
        String startTime = Instant.now().toString();
        String notes = "Onsite Booking and " + getCode().menuDescription();

        setCustomerId(id);
        setTestingSiteId(testingSiteId);
        setStartTime(startTime);
        setNotes(notes);

        // https://attacomsian.com/blog/jackson-create-json-object
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode booking = mapper.createObjectNode();

        booking.put("customerId", getCustomerId());
        booking.put("testingSiteId", getTestingSiteId());
        booking.put("startTime", getStartTime());
        booking.put("notes", getNotes());

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);

        String usersUrl = rootUrl + "/booking";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl + "?jwt=true"))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // handle each response
        if (response.statusCode() == 201) {
            super.setMsg("New booking successfully created.\n");
            super.setMsg(getCode().menuDescription());
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
     * This method is to edit the valid booking in the system for customer by the administrator.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void editBooking(String myApiKey, String rootUrl) throws Exception {
        int counter = 0;
        User user = getUser();
        String tempBookingId = user.getBookingId();
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

        for (ObjectNode node: jsonNodes) {
            if (node.get("id").asText().equals(user.getBookingId()) && node.get("smsPin").asText().equals(user.getPinCode().getPin())) {
//                System.out.println(node.toString());
                counter += 1;
            }
        }

        if (counter == 0) {
            System.out.println("No such booking.");
        }

        else if (counter > 0) {
            // edit booking
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
                    .header("Content-Type", "application/json") // This header needs to be set when sending a JSON request body.
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // handle each response
            if (response.statusCode() == 200) {
                super.setMsg("Existing booking successfully updated.\n");
            } else if (response.statusCode() == 400) {
                super.setMsg("Request body could not be parsed or contains invalid fields.");
            } else if (response.statusCode() == 401) {
                super.setMsg("A valid API key was not provided in the request.");
            } else if (response.statusCode() == 404) {
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
     * This method is to delete a valid booking from the system for the customer by the administrator.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void deleteBooking(String myApiKey, String rootUrl) throws Exception {
        User user = getUser();

        String bookingUrl = rootUrl + "/booking/" + user.getBookingId();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingUrl))
                .setHeader("Authorization", myApiKey)
                .header("Content-Type", "application/json") // This header needs to be set when sending a JSON request body.
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // handle each response
        if (response.statusCode() == 204) {
            super.setMsg("The COVID test was successfully deleted.\n");
        } else if (response.statusCode() == 400) {
            super.setMsg("Request body could not be parsed or contains invalid fields.");
        } else if (response.statusCode() == 401) {
            super.setMsg("A valid API key was not provided in the request.");
        } else if (response.statusCode() == 404) {
            super.setMsg("A booking, customer, and/or testing site with the provided ID was not found.");
        }
    }

}
