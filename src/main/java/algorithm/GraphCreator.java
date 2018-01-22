package algorithm;

import data.City;
import data.Vehicle;
import java.util.ArrayList;

 public interface GraphCreator {
    public double[][] createGraphByMoney(ArrayList<City> wantedCities);
    public double [][] createGraphByTime(ArrayList<City> wantedCities);
    public double[][] createGraphByTimeMoney(ArrayList<City> wantedCities,double m,double t);
}
