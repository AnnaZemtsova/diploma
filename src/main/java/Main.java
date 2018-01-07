import algorithm.Algorithm;
import algorithm.BranchBoundaryMethod;
import data.City;
import data.Train;
import generalData.ALLDATA;
import inputData.InputCities;
import inputData.InputTrains;
import inputData.InputUserData;
import testingData.CitiesFromCode;
import testingData.TrainsFromCode;
import testingData.UserDataFromCode;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        InputCities inputCities = new CitiesFromCode();
        ArrayList<City> cities = inputCities.getCities();

        InputTrains inputTrains = new TrainsFromCode();
        ArrayList<Train> trains = inputTrains.getTrains();

        ALLDATA.cities = cities;
        ALLDATA.trains = trains;

        InputUserData inputUserData = new UserDataFromCode();
        ArrayList<City> wantedCity = inputUserData.getWantedCities();

        Algorithm algorithm = new BranchBoundaryMethod();
        algorithm.getBestWay( wantedCity );
    }

}
