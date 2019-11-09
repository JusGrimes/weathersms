package info.justingrimes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Properties;

public class WeatherGatherer implements Gatherer {
    final static private Logger log = LogManager.getLogger(WeatherGatherer.class);

    final private String cityName;
    final private String apiKey;
    final private String baseURL = "http://api.openweathermap.org/data/2.5/weather";

    WeatherGatherer(String cityName, String apiKey) {
        this.apiKey = apiKey;
        this.cityName = cityName;
        log.debug("Weather Gatherer Object created");
    }

    @Override
    public String getMessage() {
        int temperature;

        try {
            temperature = convertJsonResponseToTemp(getTempString());
        } catch (IOException | InterruptedException e) {
            log.error("Error while getting temperature", e);
            return "Unable to get temperature";
        }
        return "The current temperature is " + temperature + " degrees.";
    }

    private String getTempString() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString()))
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        return response.body();
    }

    private String urlString() {
        return baseURL + "?"
                + "q=" + cityName +
                "&"
                + "APPID=" + apiKey;
    }

    private int convertJsonResponseToTemp(String rawJson) throws IOException {
        // extract the current temperature from the json string
        ObjectMapper objectMapper = new ObjectMapper();
        String content;
        JsonNode node = objectMapper.readTree(rawJson);
        node = node.get("main");
        double temperature = node.get("temp").asDouble();

        return convertToFahrenheit(temperature);
    }

    private int convertToFahrenheit(double kelvin) {
        return (int) ((1.8*(kelvin - 273)) + 32);

    }

}
