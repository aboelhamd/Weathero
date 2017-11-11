package com.example.android.weathero.countries;

import java.util.Hashtable;

/**
 * Class to convert countries (names) into (latitude,longitude).
 */

public class CountriesUtils {
  public Hashtable<String, String> countries = new Hashtable();

  public CountriesUtils() {
    fillTable();
  }

  public void fillTable() {
    countries.put("Argentina", "-38.416097,-63.616672");
    countries.put("Australia", "-25.274398,133.775136");
    countries.put("Canada", "56.130366,-106.346771");
    countries.put("Switzerland", "46.818188,8.227512");
    countries.put("Chile", "-35.675147,-71.542969");
    countries.put("Cameroon", "7.369722,12.354722");
    countries.put("China", "35.86166,104.195397");
    countries.put("Germany", "51.165691,10.451526");
    countries.put("Denmark", "56.26392,9.501785");
    countries.put("Egypt", "26.820553,30.802498");
    countries.put("India", "20.593684,78.96288");
    countries.put("Iceland", "64.963051,-19.020835");
    countries.put("Japan", "36.204824,138.252924");
    countries.put("US", "37.09024,-95.712891");
  }

}
