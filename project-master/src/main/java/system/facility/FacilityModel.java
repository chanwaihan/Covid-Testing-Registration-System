package system.facility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class is a Facility's model class which represent the Facility with stored information of a Facility.
 */
public class FacilityModel {

    private String description = "";

    /**
     * This method is to search for a facility provided a type of facility by customer.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     * @param typeOfFacility a string of type of facility
     */
    public void searchFacility(String myApiKey, String rootUrl, String typeOfFacility) throws Exception {
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
            String comparedValue = node.get("name").asText().toLowerCase();
            comparedValue = comparedValue.replaceAll("[^a-zA-Z0-9]", " ");
            if (comparedValue.contains(typeOfFacility)) {
                description += "\nName: " + node.get("name").asText() + "\n";
                description += "Description: " + node.get("description").asText() + "\n";
                description += "Street: " + node.get("address").get("street").asText() + "\n";
                description += "Suburb: " + node.get("address").get("suburb").asText() + "\n";
                description += "Phone number: " + node.get("phoneNumber").asText() + "\n";
                description += "Opening hours: 9am - 5pm\n";
                description += "Waiting time: 15 minutes per person\n";
                break;
            }
        }
        if (description.isBlank()) {
            description += "No results found for \"" + typeOfFacility + "\"\n";
        }
    }

    /**
     * This method is to search for a facility provided a name of facility by customer.
     *
     * @param myApiKey the web service API key to the particular tester's repository
     * @param rootUrl the root url link to API keys portal
     * @param suburbName a string of name of facility
     */
    public void searchSuburb(String myApiKey, String rootUrl, String suburbName) throws Exception {
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
            String comparedValue = node.get("address").get("suburb").asText().toLowerCase();
            comparedValue = comparedValue.replaceAll("[^a-zA-Z0-9]", " ");
            if (comparedValue.contains(suburbName)) {
                description += "\nName: " + node.get("name").asText() + "\n";
                description += "Description: " + node.get("description").asText() + "\n";
                description += "Street: " + node.get("address").get("street").asText() + "\n";
                description += "Suburb: " + node.get("address").get("suburb").asText() + "\n";
                description += "Phone number: " + node.get("phoneNumber").asText() + "\n";
                description += "Opening hours: 9am - 5pm\n";
                description += "Waiting time: 15 minutes per person\n";
                break;
            }
        }
        if (description.isBlank()) {
            description += "No results found for \"" + suburbName + "\"\n";
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
