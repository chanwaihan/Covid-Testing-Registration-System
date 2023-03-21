package system.login;

import system.Actions;
import system.users.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class represents an Action when users are trying to login to our system to perform some other actions.
 */
public class LoginAction extends Actions {

    private User user;
    private String msg = "";

    /**
     * Constructor of LoginAction
     *
     * @param user user who trying to login to the system
     */
    public LoginAction(User user) {
        this.user = user;
    }

    /**
     * Perform the logic action when the users logging in to our system
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     */
    @Override
    public void execute(String myApiKey, String rootUrl) throws Exception {

        String jsonString = "{" +
                "\"userName\":\"" + user.getUsername() + "\"," +
                "\"password\":\"" + user.getPassword() + "\"" +
                "}";

        String usersUrl = rootUrl + "/user";
        String usersLoginUrl = usersUrl + "/login";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(usersLoginUrl + "?jwt=true")) // Return a JWT so we can use it in Part 5 later.
                .setHeader("Authorization", myApiKey)
                .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // handle each response
        if (response.statusCode() == 200) {
            msg = "User credentials are valid. Successful login!";
            super.setStatus("valid");
        }
        else if (response.statusCode() == 400) {
            msg = "Request body could not be parsed or contains invalid fields.";
            super.setStatus("invalid");
        }
        else if (response.statusCode() == 401) {
            msg = "A valid API key was not provided in the request.";
            super.setStatus("invalid");
        }
        else if (response.statusCode() == 403) {
            msg = "User credentials are invalid. Please try again.";
            super.setStatus("invalid");
        }

        //verify login
        VerifyLoginAction verifier = new VerifyLoginAction(this.user);
        this.user.executeAction(verifier, myApiKey, rootUrl);

        String flag = verifier.menuDescription();
        if (!flag.equals("")) {
            msg = flag;
            super.setStatus("invalid");
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
