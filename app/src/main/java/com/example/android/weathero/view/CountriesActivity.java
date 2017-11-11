package com.example.android.weathero.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.weathero.R;
import com.example.android.weathero.countries.CountriesArrayAdapter;
import com.example.android.weathero.countries.CountriesUtils;

import java.util.ArrayList;

/**
 * Activity of countries list
 */

public class CountriesActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list_layout);

    // Find the ListView which will be populated with the countries names
    ListView listView = (ListView) findViewById(R.id.list);

    // Find and set empty view on the ListView, so that it only shows when the list_layout has 0
    // items.
    View emptyView = findViewById(R.id.empty_view);
    listView.setEmptyView(emptyView);

    // Setup an Adapter to create a list_layout for the countries
    CountriesArrayAdapter arrayAdapter = new CountriesArrayAdapter(this, new ArrayList(new
            CountriesUtils().countries.keySet()));
    listView.setAdapter(arrayAdapter);

    // Setup click listener
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(CountriesActivity.this, ForecastActivity.class);

        // make the selected country shared via the preferences with other activities and fragments
        SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("country", adapterView.getItemAtPosition(position).toString());
        editor.commit();

        startActivity(intent);
      }
    });
  }
}
