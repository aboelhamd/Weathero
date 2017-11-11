package com.example.android.weathero.network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.weathero.forecast.ForecastResult;

/**
 * Loader to do the network in the background thread
 */

public class ForecastLoader extends AsyncTaskLoader<ForecastResult> {
  private String url;

  public ForecastLoader(Context context, String url) {
    super(context);
    this.url = url;
  }

  @Override
  public ForecastResult loadInBackground() {
    // Don't perform the request if there are no URLs, or the first URL is null.
    if (url == null) {
      return null;
    }

    // Perform the HTTP request for forecast data and process the response.
    // Extract the list_layout of forecast results
    return QueryUtils.fetchEarthquakesData(url);
  }

  @Override
  protected void onStartLoading() {
    forceLoad();
  }
}
