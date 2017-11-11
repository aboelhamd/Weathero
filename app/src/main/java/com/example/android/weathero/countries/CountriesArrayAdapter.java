package com.example.android.weathero.countries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.weathero.R;

import java.util.List;

/**
 * Array Adapter to hold countries
 */

public class CountriesArrayAdapter extends ArrayAdapter<String> {

  public CountriesArrayAdapter(@NonNull Context context, @NonNull List objects) {
    super(context, 0, objects);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    // Check if the existing view is being reused, otherwise inflate the view
    View listItemView = convertView;
    if (listItemView == null) {
      listItemView = LayoutInflater.from(getContext()).inflate(R.layout.country_layout, parent,
              false);
    }

    // Get the country located at this position in the list_layout
    String currentCountry = getItem(position);

    // Setting this country to its text view
    TextView countryName = (TextView) listItemView.findViewById(R.id.country_name);
    countryName.setText(currentCountry);

    // Return the whole list_layout item layout
    return listItemView;
  }

}
