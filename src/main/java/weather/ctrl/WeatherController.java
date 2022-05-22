package weather.ctrl;


import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.Forecast;

import java.util.ArrayList;
import java.util.List;

public class WeatherController {

    public static String apiKey = "ab5c55091bfde0864c41b337f1c66af5";


    public void process(List<GeoCoordinates> location) throws MyExecption {
        List<Forecast> data = getData(location);
        List<GeoCoordinates> listOfCity = new ArrayList<>();
        try{
            for (var item : data) {
                Double highestTemp = getHighestTemp(item);
                Double averageTemp = getAverageTemp(item);
                Double highestWind = getHighestWind(item);
            }

            for (var item : location) {
                listOfCity.add(item);
            }

            SequentialDownloader sequentialDownloader = new SequentialDownloader();
            sequentialDownloader.process(listOfCity);

            ParallelDownloader parallelDownloader = new ParallelDownloader();
            parallelDownloader.process(listOfCity);

        }catch (Exception e){
            throw new MyExecption();
        }
    }

    private double  getHighestTemp(Forecast data) {
        double getHighestTempDouble;
        getHighestTempDouble = data.getDaily().getData().stream().mapToDouble(temp-> temp.getTemperatureHigh()).max().getAsDouble();
        System.out.println("Höchste Temperatur der letzten Tage : " + getHighestTempDouble);
        return getHighestTempDouble;
    }

    private double  getAverageTemp(Forecast data) {
        double getAvarageTempDouble;
        getAvarageTempDouble = data.getDaily().getData().stream().mapToDouble(temp-> temp.getTemperatureHigh()).average().getAsDouble();
        System.out.println("Durchsnittliche Temperatur der letzten Tage : " + getAvarageTempDouble);
        return getAvarageTempDouble;
    }

    private double  getHighestWind(Forecast data) {
        double getHighestWindDouble;
        getHighestWindDouble = data.getHourly().getData().stream().mapToDouble(temp-> temp.getWindSpeed()).max().getAsDouble();
        System.out.println("Höchste Windgeschwindigkeit der letzten Tage : " + getHighestWindDouble);
        return getHighestWindDouble;

    }




    public List<Forecast> getData(List<GeoCoordinates> location) throws MyExecption {
        List<Forecast> forecasts = new ArrayList<>();
        for (var city : location) {
            ForecastRequest request = new ForecastRequestBuilder()
                    .key(new APIKey(apiKey))
                    .location(city)
                    .build();

            DarkSkyJacksonClient client = new DarkSkyJacksonClient();

            try {
                Forecast forecast = client.forecast(request);
                forecasts.add(forecast);
                return forecasts;
            } catch (ForecastException e) {
                throw new MyExecption();
            }
        }
        return null;
    }
}
