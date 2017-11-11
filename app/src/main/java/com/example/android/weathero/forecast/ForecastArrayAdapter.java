package com.example.android.weathero.forecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.weathero.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Array Adapter to hold countries
 */

public class ForecastArrayAdapter extends ArrayAdapter<Forecast> {

  public ForecastArrayAdapter(@NonNull Context context, @NonNull List objects) {
    super(context, 0, objects);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    // Check if the existing view is being reused, otherwise inflate the view
    View listItemView = convertView;
    if (listItemView == null) {
      listItemView = LayoutInflater.from(getContext()).inflate(R.layout.forecast_layout, parent,
              false);
    }

    // Get the {@link forecast} object located at this position in the list_layout
    Forecast currentForecast = getItem(position);

    // Find text views of forecast results and assign the results to them

    // Temperature View
    TextView temperatureText = (TextView) listItemView.findViewById(R.id.temperature);
    temperatureText.setText(new DecimalFormat("0.0").format(currentForecast.getTemperature()));

    // Summary view
    TextView summaryText = (TextView) listItemView.findViewById(R.id.summary);
    summaryText.setText(currentForecast.getSummary());

    // Format the date into Month Day , Year
    TextView dateText = (TextView) listItemView.findViewById(R.id.date);
    dateText.setText(new SimpleDateFormat("LLL dd, yyyy").format(new Date(currentForecast.getTime
            ())));

    // Format the date into Hours:Minutes Am/Pm
    TextView timeText = (TextView) listItemView.findViewById(R.id.time);
    timeText.setText(new SimpleDateFormat("h:mm a").format(new Date(currentForecast.getTime())));

    // Return the whole list_layout item layout
    return listItemView;
  }
}
