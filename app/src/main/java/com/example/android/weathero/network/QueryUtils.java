package com.example.android.weathero.network;

import android.util.Log;

import com.example.android.weathero.forecast.Forecast;
import com.example.android.weathero.forecast.ForecastResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving forecast data from "dark sky".
 */
public final class QueryUtils {

  /**
   * Tag for the log messages
   */
  public static final String LOG_TAG = QueryUtils.class.getSimpleName();

  /**
   * Create a private constructor because no one should ever create a {@link QueryUtils} object.
   * This class is only meant to hold static variables and methods, which can be accessed
   * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
   */
  private QueryUtils() {
  }

  /**
   * Query the "dark sky" and return an {@link ForecastResult} object
   */
  public static ForecastResult fetchEarthquakesData(String requestUrl) {
    // Create URL object
    URL url = createUrl(requestUrl);

    // Perform HTTP request to the URL and receive a JSON response back
    String jsonResponse = null;
    try {
      jsonResponse = makeHttpRequest(url);
    } catch (IOException e) {
      Log.e(LOG_TAG, "Error closing input stream", e);
    }

    // Extract relevant fields from the JSON response & Return the {@link forecast}
    return extractFeatureFromJson(jsonResponse);
  }

  /**
   * Returns new URL object from the given string URL.
   */
  private static URL createUrl(String stringUrl) {
    URL url = null;
    try {
      url = new URL(stringUrl);
    } catch (MalformedURLException e) {
      Log.e(LOG_TAG, "Error with creating URL ", e);
    }
    return url;
  }

  /**
   * Make an HTTP request to the given URL and return a String as the response.
   */
  private static String makeHttpRequest(URL url) throws IOException {
    String jsonResponse = "";

    // If the URL is null, then return early.
    if (url == null) {
      return jsonResponse;
    }

    HttpURLConnection urlConnection = null;
    InputStream inputStream = null;
    try {
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setReadTimeout(10000 /* milliseconds */);
      urlConnection.setConnectTimeout(15000 /* milliseconds */);
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      // If the request was successful (response code 200),
      // then read the input stream and parse the response.
      if (urlConnection.getResponseCode() == 200) {
        inputStream = urlConnection.getInputStream();
        jsonResponse = readFromStream(inputStream);
      } else {
        Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
      }
    } catch (IOException e) {
      Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (inputStream != null) {
        inputStream.close();
      }
    }
    return jsonResponse;
  }

  /**
   * Convert the {@link InputStream} into a String which contains the
   * whole JSON response from the server.
   */
  private static String readFromStream(InputStream inputStream) throws IOException {
    StringBuilder output = new StringBuilder();
    if (inputStream != null) {
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName
              ("UTF-8"));
      BufferedReader reader = new BufferedReader(inputStreamReader);
      String line = reader.readLine();
      while (line != null) {
        output.append(line);
        line = reader.readLine();
      }
    }
    return output.toString();
  }

  /**
   * Return a list_layout of {@link Forecast} objects that has been built up from
   * parsing a JSON response.
   */
  public static ForecastResult extractFeatureFromJson(String jsonResponse) {

    // Create an empty ArrayList that we can start adding forecasts to
    Forecast currentlyResult = null;
    ArrayList<Forecast> hourlyResult = new ArrayList<>();
    ArrayList<Forecast> dailyResult = new ArrayList<>();

    ForecastResult forecastResult;

    // Try to parse the jsonResponse. If there's a problem with the way the JSON
    // is formatted, a JSONException exception object will be thrown.
    // Catch the exception so the app doesn't crash, and print the error message to the logs.
    try {
      JSONObject root = new JSONObject(jsonResponse);
      String latitude = root.getString("latitude");
      String longitude = root.getString("longitude");
      String timezone = root.getString("timezone");

      JSONObject currently = root.optJSONObject("currently");
      JSONObject hourly = root.optJSONObject("hourly");
      JSONObject daily = root.optJSONObject("daily");

      if (currently != null) {
        currentlyResult = new Forecast(currently.getLong("time"), currently.getString("summary"),
                currently.getDouble("temperature"));

      }

      if (hourly != null) {
        JSONArray hourlyData = hourly.getJSONArray("data");
        for (int i = 0; i < hourlyData.length(); i++) {
          JSONObject forecast = hourlyData.getJSONObject(i);

          hourlyResult.add(new Forecast(forecast.getLong("time"), forecast.getString("summary"),
                  forecast.getDouble("temperature")));

        }
      }

      if (daily != null) {
        JSONArray dailyData = daily.getJSONArray("data");
        for (int i = 0; i < dailyData.length(); i++) {
          JSONObject forecast = dailyData.getJSONObject(i);

          dailyResult.add(new Forecast(forecast.getLong("time"), forecast.getString("summary"),
                  forecast.getDouble("temperatureMin")));

        }
      }

      forecastResult = new ForecastResult(latitude, longitude, timezone, currentlyResult,
              hourlyResult, dailyResult);

      return forecastResult;

    } catch (JSONException e) {
      // If an error is thrown when executing any of the above statements in the "try" block,
      // catch the exception here, so the app doesn't crash. Print a log message
      // with the message from the exception.
      Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
    }

    // Return the list_layout of forecasts
    return null;
  }
}