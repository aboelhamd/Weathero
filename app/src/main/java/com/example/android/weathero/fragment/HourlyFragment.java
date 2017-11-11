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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.weathero.R;
import com.example.android.weathero.countries.CountriesUtils;
import com.example.android.weathero.forecast.ForecastArrayAdapter;
import com.example.android.weathero.forecast.ForecastResult;
import com.example.android.weathero.network.ForecastLoader;
import com.example.android.weathero.view.ForecastActivity;

import java.util.ArrayList;

/**
 * Fragment for the hourly(48 hours) results
 */
public class HourlyFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<ForecastResult> {

  /** Excluding currently, minutely & daily results to speed up the query */
  private static final String EXCLUDE = "?units=si&exclude=currently,minutely,daily";

  private static final int EARTHQUAKE_LOADER_ID = 1;

  /** Adapter for the list_layout of earthquakes */
  private ForecastArrayAdapter adapter;

  private ForecastLoader forecastLoader;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
          savedInstanceState) {

    View rootView = inflater.inflate(R.layout.list_layout, container, false);

    // Taking the current location from the preferences , Japan is the default
    String country = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE).getString
            ("country", "Japan");
    // Converting the location into coordinates (longitude and latitude)
    String coordinates = new CountriesUtils().countries.get(country);
    // Concatenating the whole url request
    String requestURL = ForecastActivity.firstPartUrl + coordinates + EXCLUDE;

    forecastLoader = new ForecastLoader(rootView.getContext(), requestURL);

    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
    // because this activity implements the LoaderCallbacks interface).
    getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);

    return rootView;
  }

  private void updateUI(final ForecastArrayAdapter adapter) {
    // Find a reference to the {@link ListView} in the layout
    ListView earthquakeListView = (ListView) getView().findViewById(R.id.list);

    // Check connectivity
    ConnectivityManager manager = (ConnectivityManager) getView().getContext().getSystemService
            (Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
    boolean isConnected = (activeNetwork != null && activeNetwork.isConnectedOrConnecting());

    /** TextView that is displayed when the list_layout is empty */
    TextView emptyText = (TextView) getView().findViewById(R.id.empty_view);
    if (!isConnected)
      emptyText.setText(R.string.no_internet_connection);

    earthquakeListView.setEmptyView(emptyText);

    // Remove the progress bar
    ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.loading_spinner);
    progressBar.setVisibility(View.GONE);


    // Set the adapter on the {@link ListView}
    // so the list_layout can be populated in the user interface
    earthquakeListView.setAdapter(adapter);
  }

  @Override
  public Loader<ForecastResult> onCreateLoader(int id, Bundle args) {
    return forecastLoader;
  }

  @Override
  public void onLoadFinished(Loader<ForecastResult> loader, ForecastResult data) {
    if (adapter == null)
      adapter = new ForecastArrayAdapter(getView().getContext(), new ArrayList());

    // Clear the adapter of previous data
    adapter.clear();

    // If there is a valid list_layout of {@link Forecast}s, then add them to the adapter's
    // data set. This will trigger the ListView to update.
    if (data != null && !data.getHourlyResult().isEmpty()) {
      adapter.addAll(data.getHourlyResult());
    }

    // Update the UI
    updateUI(adapter);
  }

  @Override
  public void onLoaderReset(Loader<ForecastResult> loader) {
    adapter.clear();
  }

}