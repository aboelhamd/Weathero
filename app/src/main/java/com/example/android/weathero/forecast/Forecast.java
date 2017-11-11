package com.example.android.weathero.forecast;

/**
 * Forecast object to hold its properties
 */

public class Forecast {
  private long time;
  private String summary;
  private double temperature;

  public Forecast(long time, String summary, double temperature) {
    // Convert seconds into milli-seconds
    this.time = time * 1000;
    this.summary = summary;
    this.temperature = temperature;
  }

  public Long getTime() {
    return time;
  }

  public String getSummary() {
    return summary;
  }

  public Double getTemperature() {
    return temperature;
  }
}
