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
package com.example.android.weathero.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.weathero.R;
import com.example.android.weathero.fragment.ForecastPagerAdapter;

/**
 * Activity that holds the three result fragments
 */
public class ForecastActivity extends AppCompatActivity {
  public static final String firstPartUrl = "https://api.darksky" + "" +
          ".net/forecast/10502bdf099fcb73b1e958c8abbb757f/";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set the content of the activity to use the activity_forecast.xml layout file
    setContentView(R.layout.activity_forecast);

    // Find the view pager that will allow the user to swipe between fragments
    ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

    // Create an adapter that knows which fragment should be shown on each page
    ForecastPagerAdapter pagerAdapter = new ForecastPagerAdapter(getSupportFragmentManager(), this);

    // Set the adapter onto the view pager
    viewPager.setAdapter(pagerAdapter);

    // Give the TabLayout the ViewPager
    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
    tabLayout.setupWithViewPager(viewPager);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == R.id.choose_location) {
      startActivity(new Intent(ForecastActivity.this, CountriesActivity.class));
    }
    return true;
  }
}
