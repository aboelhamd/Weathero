/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.weathero.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.weathero.R;
import com.example.android.weathero.countries.CountriesUtils;
import com.example.android.weathero.forecast.ForecastResult;
import com.example.android.weathero.network.ForecastLoader;
import com.example.android.weathero.view.ForecastActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Fragment for the current results
 */
public class CurrentlyFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<ForecastResult> {

  /** Excluding minutely, hourly & daily results to speed up the query */
  private static final String EXCLUDE = "?units=si&exclude=minutely,hourly,daily";

  private static final int EARTHQUAKE_LOADER_ID = 1;

  private ForecastLoader forecastLoader;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
          savedInstanceState) {

    View rootView = inflater.inflate(R.layout.current_forecast, container, false);

    String country = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE).getString
            ("country", "Japan");
    String coordinates = new CountriesUtils().countries.get(country);
    String requestURL = ForecastActivity.firstPartUrl + coordinates + EXCLUDE;

    forecastLoader = new ForecastLoader(rootView.getContext(), requestURL);

    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
    // because this activity implements the LoaderCallbacks interface).
    getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);

    return rootView;
  }

  // update the text views with the results
  private void updateUI(ForecastResult data) {
    TextView location = (TextView) getView().findViewById(R.id.location);
    location.setText(data.getTimezone());

    TextView latitude = (TextView) getView().findViewById(R.id.latitude);
    latitude.setText(data.getLatitude());

    TextView longitude = (TextView) getView().findViewById(R.id.longitude);
    longitude.setText(data.getLongitude());

    // Temperature View
    TextView temperatureText = (TextView) getView().findViewById(R.id.temperature);
    temperatureText.setText(new DecimalFormat("0.0").format(data.getCurrentlyResult()
            .getTemperature()));

    // Summary view
    TextView summaryText = (TextView) getView().findViewById(R.id.summary);
    summaryText.setText(data.getCurrentlyResult().getSummary());

    // Format the date into Month Day , Year
    TextView dateText = (TextView) getView().findViewById(R.id.date);
    dateText.setText(new SimpleDateFormat("LLL dd, yyyy").format(new Date(data.getCurrentlyResult
            ().getTime())));

    // Format the date into Hours:Minutes Am/Pm
    TextView timeText = (TextView) getView().findViewById(R.id.time);
    timeText.setText(new SimpleDateFormat("h:mm a").format(new Date(data.getCurrentlyResult()
            .getTime())));
  }

  @Override
  public Loader<ForecastResult> onCreateLoader(int id, Bundle args) {
    return forecastLoader;
  }

  @Override
  public void onLoadFinished(Loader<ForecastResult> loader, ForecastResult data) {
    updateUI(data);
  }

  @Override
  public void onLoaderReset(Loader<ForecastResult> loader) {

  }

}