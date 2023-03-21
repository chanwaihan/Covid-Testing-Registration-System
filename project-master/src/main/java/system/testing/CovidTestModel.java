package system.testing;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class is a CovidTest's model class which represent the Covid Test with stored information of a Covid Test.
 */
public class CovidTestModel {

    private String testType;
    private String description;

    /**
     * This method asked questions to customer when they go to on-site testing and based on their answer, provide a
     * suitable testing kit for them.
     *
     * @return A string of PCR or RAT
     */
    public void interview(int choice) {
        if (choice == 1) {
            testType = "PCR";
        }
        else if (choice == 2) {
            testType = "RAT";
        }
    }

    /**
     * This method request booking id from customer when they go for on-site testing.
     */
    public void patchBookingId(String rootUrl, String myApiKey, String bookingId) throws Exception {
        String bookingsUrl = rootUrl + "/booking/" + bookingId;

        String jsonPayloadString = "{\"notes\": \"" + testType + "\"}";
        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(jsonPayloadString);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingsUrl + "?jwt=true"))
                .setHeader("Authorization", myApiKey)
                .method("PATCH", jsonPayload)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        setResponse(response);
    }

    /**
     * This methos is to set the response from the API.
     *
     * @param response A string of response from the API based on user's input
     */
    public void setResponse(HttpResponse<String> response) {
        // handle each response
        if (response.statusCode() == 200) {
            description = "Existing booking successfully updated.";
        }
        else if (response.statusCode() == 400) {
            description = "Request body could not be parsed or contains invalid fields.";
        }
        else if (response.statusCode() == 401) {
            description = "A valid API key was not provided in the request.";
        }
        else if (response.statusCode() == 404) {
            description = "A booking, customer, and/or testing site with the provided ID was not found.";
        }
    }

    /**
     * Return a descriptive string of result or output after the function in this class has been executed.
     *
     * @return String of description
     */
    public String getDescription() {
        return description;
    }
}
