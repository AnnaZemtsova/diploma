package algorithm;

import data.City;

import java.util.ArrayList;

public interface Algorithm {
    ArrayList<City> getBestWay(ArrayList<City> wantedCities);
}
