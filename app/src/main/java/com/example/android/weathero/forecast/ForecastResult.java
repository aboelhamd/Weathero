package com.example.android.weathero.forecast;

import java.util.List;

/**
 * An object to hold the forecast results
 */

public class ForecastResult {
  private String latitude;
  private String longitude;
  private String timezone;

  private Forecast currentlyResult;
  private List<Forecast> hourlyResult;
  private List<Forecast> dailyResult;

  public ForecastResult(String latitude, String longitude, String timezone, Forecast
          currentlyResult, List<Forecast> hourlyResult, List<Forecast> dailyResult) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.timezone = timezone;
    this.currentlyResult = currentlyResult;
    this.hourlyResult = hourlyResult;
    this.dailyResult = dailyResult;
  }

  public String getLatitude() {
    return latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public String getTimezone() {
    return timezone;
  }

  public Forecast getCurrentlyResult() {
    return currentlyResult;
  }

  public List<Forecast> getHourlyResult() {
    return hourlyResult;
  }

  public List<Forecast> getDailyResult() {
    return dailyResult;
  }
}
