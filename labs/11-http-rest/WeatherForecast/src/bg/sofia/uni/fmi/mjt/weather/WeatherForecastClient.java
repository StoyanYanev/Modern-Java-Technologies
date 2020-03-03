package bg.sofia.uni.fmi.mjt.weather;

import bg.sofia.uni.fmi.mjt.weather.dto.DataPoint;
import bg.sofia.uni.fmi.mjt.weather.dto.Geocode;
import bg.sofia.uni.fmi.mjt.weather.dto.WeatherForecast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WeatherForecastClient {
    private static final String DARK_SKY_API_URL = "https://api.darksky.net";
    private static final String LOCATION_API_URL = "https://eu1.locationiq.com";
    private static final int NOT_FOUND_STATUS_CODE = 404;
    private static Gson gson = new Gson();

    private HttpClient client;
    private String secretKey;
    private String token;

    /**
     * @param client
     * @param secretKey Secret key for Dark Sky API
     * @param token     Token for LocationIQ
     */
    public WeatherForecastClient(HttpClient client, String secretKey, String token) {
        this.client = client;
        this.secretKey = secretKey;
        this.token = token;
    }

    /**
     * Fetches the weather forecast for the specified location.
     *
     * @param location
     * @return the forecast
     * @throws IOException
     * @throws InterruptedException
     */
    public WeatherForecast getForecast(String location) throws IOException, InterruptedException {
        Geocode geocode = getCoordinates(location);
        if (geocode == null) {
            return null;
        }

        String url = constructDarkSkyApiURL(geocode.getLat(), geocode.getLon());

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> weatherResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (weatherResponse.statusCode() == NOT_FOUND_STATUS_CODE) {
            return null;
        }

        return gson.fromJson(weatherResponse.body(), WeatherForecast.class);
    }

    /**
     * Fetches the current weather for the specified location.
     *
     * @return the current weather
     * @throws IOException
     * @throws InterruptedException
     */
    public DataPoint getCurrent(String location) throws IOException, InterruptedException {
        WeatherForecast weatherForecast = getForecast(location);
        if (weatherForecast == null) {
            return null;
        }

        return weatherForecast.getCurrently();
    }

    /**
     * Fetches the hourly weather forecast
     *
     * @return the hourly weather forecast
     * @throws IOException
     * @throws InterruptedException
     */
    public Collection<DataPoint> getHourlyForecast(String location) throws IOException, InterruptedException {
        WeatherForecast weatherForecast = getForecast(location);
        if (weatherForecast == null) {
            return Collections.emptyList();
        }

        return weatherForecast.getHourly().getData();
    }

    /**
     * Fetches the weekly weather forecast
     *
     * @return the weekly weather forecast
     * @throws IOException
     * @throws InterruptedException
     */
    public Collection<DataPoint> getWeeklyForecast(String location) throws IOException, InterruptedException {
        WeatherForecast weatherForecast = getForecast(location);
        if (weatherForecast == null) {
            return Collections.emptyList();
        }

        return weatherForecast.getDaily().getData();
    }

    private String constructDarkSkyApiURL(double latitude, double longitude) {
        String path = "/forecast/" + secretKey + "/" + latitude + "," + longitude;
        String query = "?units=si&lang=bg";

        return DARK_SKY_API_URL + path + query;
    }

    private Geocode getCoordinates(String location) throws IOException, InterruptedException {
        location = location.replace(" ", "%20%");
        String url = constructLocationApiURL(location);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        HttpResponse<String> locationResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (locationResponse.statusCode() == NOT_FOUND_STATUS_CODE) {
            return null;
        }

        List<Geocode> geocodes = gson.fromJson(locationResponse.body(), new TypeToken<List<Geocode>>() {
        }.getType());
        if (geocodes.isEmpty()) {
            return null;
        }
        return geocodes.get(0);
    }

    private String constructLocationApiURL(String location) {
        String path = "/v1/search.php";
        String query = "?key=" + token + "&q=" + location + "&format=json";

        return LOCATION_API_URL + path + query;
    }
}