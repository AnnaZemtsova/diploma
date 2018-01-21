package algorithm;

import data.City;
import data.Vehicle;
import java.util.ArrayList;

 public interface GraphCreator {
    public double[][] createGraphByMoney(ArrayList<Vehicle> vehicles, ArrayList<City> wantedCities);
    public double [][] createGraphByTime(ArrayList<Vehicle> vehicles,ArrayList<City> wantedCities);
    public double[][] createGraphByTimeMoney(ArrayList<Vehicle> vehicles,ArrayList<City> wantedCities,double m,double t);
}
