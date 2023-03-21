package system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import system.users.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class represents an Action when users are trying to register for an account in the system.
 */
public class RegisterAction extends Actions {

    private User user;
    private String msg;

    /**
     * Constructor of RegisterAction
     *
     * @param user user who register the account
     */
    public RegisterAction(User user) {
        this.user = user;
    }

    /**
     * Perform the logic action when the users registering the account
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    public void execute(String myApiKey, String rootUrl) throws Exception {
        // https://attacomsian.com/blog/jackson-create-json-object
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode customer = mapper.createObjectNode();
        customer.put("givenName", user.getGivenName());
        customer.put("familyName", user.getFamilyName());
        customer.put("userName", user.getUsername());
        customer.put("password", user.getPassword());
        customer.put("phoneNumber", user.getPhoneNumber());
        customer.put("isCustomer", true);
        customer.put("isReceptionist", false);
        customer.put("isHealthcareWorker", false);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customer);

        String usersUrl = rootUrl + "/user";
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
            msg = "Account for " + user.getUsername() + " has been successfully created!";
        }
        else if (response.statusCode() == 400) {
            msg = "Request body could not be parsed or contains invalid fields.";
        }
        else if (response.statusCode() == 401) {
            msg = "A valid API key was not provided in the request.";
        }
        else if (response.statusCode() == 409) {
            msg = "The requested username " + user.getUsername() + " is already in use. Please try another username.";
        }

    }

    /**
     * Return a descriptive string of result or output after the action has been performed.
     *
     * @return String of result or output
     */
    @Override
    public String menuDescription() {
        return msg;
    }
}
