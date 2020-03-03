package bg.sofia.uni.fmi.mjt.weather;

import bg.sofia.uni.fmi.mjt.weather.dto.DataBlock;
import bg.sofia.uni.fmi.mjt.weather.dto.DataPoint;
import bg.sofia.uni.fmi.mjt.weather.dto.Geocode;
import bg.sofia.uni.fmi.mjt.weather.dto.WeatherForecast;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastClientTest {
    private static final String VALID_LOCATION = "Sofia";
    private static final String INVALID_LOCATION = "!";
    private static final String EMPTY_BODY = "[]";
    private static final double DELTA = 0.1;

    @Mock
    private HttpClient httpClientMock;
    @Mock
    private HttpResponse<String> httpResponseMock;

    private WeatherForecastClient client;

    @Before
    public void setUp() throws Exception {
        client = new WeatherForecastClient(httpClientMock, null, null);

        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);

        final List<Geocode> geocode = List.of(new Geocode(42.6978634, 23.3221789),
                new Geocode(42.6, 23.3));
        final String geocodeToJson = new Gson().toJson(geocode);
        final WeatherForecast weatherForecast = getConstructedWeatherForecast();
        final String weatherForecastToJson = new Gson().toJson(weatherForecast);

        when(httpResponseMock.body()).thenReturn(geocodeToJson, weatherForecastToJson);
    }

    @Test
    public void testGetForecastWithValidLocationShouldReturnValidWeatherForecast()
            throws IOException, InterruptedException {
        final WeatherForecast weatherForecastExpected = getConstructedWeatherForecast();
        final WeatherForecast weatherForecastActual = client.getForecast(VALID_LOCATION);

        assertEquals(weatherForecastExpected, weatherForecastActual);
    }

    @Test
    public void testGetForecastWithInvalidLocationShouldReturnNull() throws Exception {
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        when(httpResponseMock.body()).thenReturn(EMPTY_BODY);

        final WeatherForecast weatherForecastActual = client.getForecast(INVALID_LOCATION);

        assertNull(weatherForecastActual);
    }

    @Test
    public void testGetCurrentWithValidLocationShouldReturnValidCurrentWeather()
            throws IOException, InterruptedException {
        final int timeExpected = 1578864543;
        final String summaryExpected = "Облачно";
        final double precipProbabilityExpected = 0.48;
        final double temperatureExpected = 0.48;
        final DataPoint dataPointActual = client.getCurrent(VALID_LOCATION);

        assertNotNull(dataPointActual);
        assertEquals(timeExpected, dataPointActual.getTime());
        assertEquals(summaryExpected, dataPointActual.getSummary());
        assertEquals(precipProbabilityExpected, dataPointActual.getPrecipProbability(), DELTA);
        assertEquals(temperatureExpected, dataPointActual.getTemperature(), DELTA);
    }

    @Test
    public void testGetHourlyForecastWithValidLocationShouldReturnValidHourlyWeatherForecast()
            throws IOException, InterruptedException {
        final Collection<DataPoint> hourlyForecastActual = client.getHourlyForecast(VALID_LOCATION);

        assertFalse(hourlyForecastActual.isEmpty());

        final int expectedNumberOfData = 2;
        final int actualNumberOfData = hourlyForecastActual.size();

        assertEquals(expectedNumberOfData, actualNumberOfData);
    }

    @Test
    public void testGetWeeklyForecastWithValidLocationShouldReturnValidWeeklyWeatherForecast()
            throws Exception {
        final Collection<DataPoint> weeklyForecastActual = client.getWeeklyForecast(VALID_LOCATION);

        assertFalse(weeklyForecastActual.isEmpty());

        final int expectedNumberOfData = 2;
        final int actualNumberOfData = weeklyForecastActual.size();

        assertEquals(expectedNumberOfData, actualNumberOfData);
    }

    private WeatherForecast getConstructedWeatherForecast() {
        final double latitude = 42.6978634;
        final double longitude = 23.3221789;
        final String timezone = "Europe/Sofia";

        final DataBlock hourly = getConstructedDataBlock();
        final DataBlock daily = getConstructedDataBlock();
        final DataPoint currently = getConstructedDataPoint();

        return new WeatherForecast(latitude, longitude, timezone, currently, hourly, daily);
    }

    private DataBlock getConstructedDataBlock() {
        final String summary = "Ясно през целия ден.";
        final List<DataPoint> data = List.of(getConstructedDataPoint(), getConstructedDataPoint());

        return new DataBlock(summary, data);
    }

    private DataPoint getConstructedDataPoint() {
        final int time = 1578864543;
        final String summary = "Облачно";
        final double precipProbability = 0.48;
        final double temperature = 0.48;

        return new DataPoint(time, summary, precipProbability, temperature);

    }
}