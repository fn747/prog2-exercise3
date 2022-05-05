package weather.ctrl;


import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.*;

import java.util.List;

public class WeatherController {

    private String apiKey = "ab5c55091bfde0864c41b337f1c66af5";;
    private DarkSkyJacksonClient client = new DarkSkyJacksonClient();

    public void process(GeoCoordinates location) {
        System.out.println("process "+location); //$NON-NLS-1$
        //Forecast data = getData();

        //https://github.com/fn747/prog2-exercise3

        //TODO implement Error handling

        //TODO implement methods for
        // highest temperature
        // average temperature
        // count the daily values

        Forecast forecast = my_try_catch(client,apiRequest(apiKey,location));
        double highTemp = getHighestTemp(location);
        double averageTemp = getAverageTemp(location);


        // implement a Comparator for the Windspeed

    }
    public Forecast my_try_catch(DarkSkyJacksonClient client, ForecastRequest forecastRequest) {
        Forecast forecast = new Forecast();
        try {
            forecast = client.forecast(forecastRequest);

        }
        catch (Exception e) {
            System.out.println("try_catch_test error");
        }
        return forecast;
    }
    public ForecastRequest apiRequest(String apiKey, GeoCoordinates location) {
        ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey("ab5c55091bfde0864c41b337f1c66af5"))
                .location(location)
                .build();

        return request;
    }
    public Double getHighestTemp(GeoCoordinates location) {

        DarkSkyJacksonClient client = new DarkSkyJacksonClient();
        Forecast forecast = my_try_catch(client,apiRequest(apiKey,location));
        List<DailyDataPoint> dailyDataPoints = forecast.getDaily().getData();

        return dailyDataPoints.stream().mapToDouble(DailyDataPoint::getTemperatureHigh).max().orElseThrow();
    }

    public Double getAverageTemp(GeoCoordinates location) {

        DarkSkyJacksonClient client = new DarkSkyJacksonClient();
        Forecast forecast = my_try_catch(client,apiRequest(apiKey,location));
        List<DailyDataPoint> dailyDataPoints = forecast.getDaily().getData();

        return dailyDataPoints.stream().mapToDouble(DailyDataPoint::getTemperatureHigh).average().orElseThrow();
    }

    public GeoCoordinates getLocation(Longitude x, Latitude y) {
        return new GeoCoordinates(x,y);
    }



    public static void main(String[] args) {
        WeatherController wc = new WeatherController();

        Longitude x = new Longitude(40.0);
        Latitude y = new Latitude(18.0);

        GeoCoordinates location = wc.getLocation(x,y);
        System.out.println(wc.getHighestTemp(location));
        System.out.println(wc.getAverageTemp(location));
    }
}
