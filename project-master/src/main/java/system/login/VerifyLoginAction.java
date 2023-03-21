package system.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import system.Actions;
import system.users.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class represents an Action where the system will verify whether the users are login as a correct role when they
 * login to our system.
 */
public class VerifyLoginAction extends Actions {

    private User user;
    private String msg = "";

    /**
     * Constructor of VerifyLoginAction
     *
     * @param user user who trying to login to the system with a specific role
     */
    public VerifyLoginAction(User user) {
        this.user = user;
    }

    /**
     * Perform the logic action to check the users' role when the users logging in to our system
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    @Override
    public void execute(String myApiKey, String rootUrl) throws Exception {

        String usersUrl = rootUrl + "/user";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        for (ObjectNode node: jsonNodes) {
            if (user.getType().equals("customer") && node.get("userName").asText().equals(user.getUsername()) && !node.get("isCustomer").asBoolean()) {
                msg = "Incorrect login role, please select the correct login role.";
            }
            else if (user.getType().equals("customer") && node.get("userName").asText().equals(user.getUsername()) && node.get("isCustomer").asBoolean()) {
                msg = "";
            }
            else if (user.getType().equals("receptionist") && node.get("userName").asText().equals(user.getUsername()) && !node.get("isReceptionist").asBoolean()) {
                msg = "Incorrect login role, please select the correct login role.";
            }
            else if (user.getType().equals("receptionist") && node.get("userName").asText().equals(user.getUsername()) && node.get("isReceptionist").asBoolean()) {
                msg = "";
            }
            else if (user.getType().equals("healthcareworker") && node.get("userName").asText().equals(user.getUsername()) && !node.get("isHealthcareWorker").asBoolean()) {
                msg = "Incorrect login role, please select the correct login role.";
            }
            else if (user.getType().equals("healthcareworker") && node.get("userName").asText().equals(user.getUsername()) && node.get("isHealthcareWorker").asBoolean()) {
                msg = "";
            }
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
