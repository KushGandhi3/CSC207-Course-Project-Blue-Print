package use_case.display_checker;

import java.util.List;

import entity.weather.hour_weather.HourWeatherData;
import entity.weather.hourly_weather.HourlyWeatherData;
import exception.ApiCallException;
import exception.InvalidLocationException;

/**
 * The Checker Interactor class.
 */
public class DisplayCheckerInteractor implements DisplayCheckerInputBoundary {

    private final DisplayCheckerOutputBoundary displayCheckerOutputBoundary;
    private final DisplayCheckerDAI displayCheckerDAI;

    public DisplayCheckerInteractor(DisplayCheckerDAI displayCheckerDAI,
                                    DisplayCheckerOutputBoundary displayCheckerOutputBoundary) {
        this.displayCheckerDAI = displayCheckerDAI;
        this.displayCheckerOutputBoundary = displayCheckerOutputBoundary;
    }

    /**
     * This method is used to check the weather data.
     * @param inputData the input data for the Checker use case.
     */
    @Override
    public void execute(DisplayCheckerInputData inputData) {
        final String location = inputData.getLocation();
        final String weatherConditionOptions = inputData.getWeatherConditionOptions();
        final int startChecking = inputData.getStartChecking();
        final int stopChecking = inputData.getStopChecking();

        // check if the location is not empty
        if (location.isEmpty()) {
            displayCheckerOutputBoundary.prepareLocationEmptyView();
            return;
        }

        try {
            final boolean condMet = checkWeatherData(location, weatherConditionOptions, startChecking, stopChecking);

            // pass the output data to the output boundary
            if (condMet) {
                displayCheckerOutputBoundary.prepareCondMetView();
            }
            else {
                displayCheckerOutputBoundary.prepareCondNotMetView();
            }
        }
        catch (InvalidLocationException exception) {
            exception.printStackTrace();
            displayCheckerOutputBoundary.prepareInvalidLocationView();
        }
    }

    @Override
    public void switchToHomeView() {
        displayCheckerOutputBoundary.prepareHomeView();
    }

    // Helper method to check the weather data
    private boolean checkWeatherData(String location, String weatherConditionOptions, int startChecking,
                                     int stopChecking) throws InvalidLocationException {
        try {
            final HourlyWeatherData hourlyWeatherData = displayCheckerDAI.getHourlyWeatherData(location);

            if (hourlyWeatherData == null) {
                // Throw exception when no weather data is retrieved
                throw new InvalidLocationException("Failed to retrieve hourly weather data for " + location);
            }

            final List<HourWeatherData> hourlyForecast = hourlyWeatherData.getHourWeatherDataList();

            // loop through the hourly data between the start time until the (stop time + start time)
            for (int i = startChecking; i < (stopChecking + startChecking); i++) {
                final HourWeatherData hourData = hourlyForecast.get(i);
                final String weatherCondition = hourData.getCondition();

                // check if the weather condition at that hour matches the desired condition
                if (weatherCondition.equals(weatherConditionOptions)) {
                    return true;
                }
            }
        }
        catch (ApiCallException exception) {
            exception.printStackTrace();
            // Throw exception for API-related errors
            throw new InvalidLocationException("API error for location: " + location, exception);
        }
        // If no matching condition is found
        return false;
    }
}
