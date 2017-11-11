package com.example.android.weathero.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class ForecastPagerAdapter extends FragmentPagerAdapter {
  private Context context;

  public ForecastPagerAdapter(FragmentManager fm, Context context) {
    super(fm);
    this.context = context;
  }


  @Override
  public Fragment getItem(int position) {
    if (position == 0) {
      return new CurrentlyFragment();
    } else if (position == 1) {
      return new HourlyFragment();
    } else {
      return new DailyFragment();
    }
  }

  @Override
  public int getCount() {
    return 3;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    if (position == 0) {
      return "Currently";
    } else if (position == 1) {
      return "Hourly";
    } else {
      return "Daily";
    }
  }
}
