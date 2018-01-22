
import algorithm.BranchBoundaryMethod;

import algorithm.Graph;
import algorithm.TravelingSalesmanProblem;
import data.City;
import data.Vehicle;
import generalData.ALLDATA;
import inputData.InputCities;
import inputData.InputUserData;
import inputData.InputVehicle;
import testingData.CitiesFromCode;
import testingData.UserDataFromCode;
import testingData.VehiclesFromCode;
import java.util.ArrayList;
import algorithm.*;

public class Main {
    public static void main(String[] args) {
        InputCities inputCities = new CitiesFromCode();
        ArrayList<City> cities = inputCities.getCities();

        InputVehicle inputTrains = new VehiclesFromCode();
        ArrayList<Vehicle> vehicles = inputTrains.getVehicles();

        ALLDATA.cities = cities;
        ALLDATA.vehicles = vehicles;

        InputUserData inputUserData = new UserDataFromCode();
        double money = inputUserData.getMoney();
        double time = (double)(inputUserData.getTime()).getTime();
        ArrayList<City> wantedCity = inputUserData.getWantedCities();
        GraphCreator graphCreator = new Graph(ALLDATA.vehicles);
        TravelingSalesmanProblem travelingSalesmanProblem = new BranchBoundaryMethod();
        Algorithm algorithm = new CorrelationMoneyTime(travelingSalesmanProblem,graphCreator,wantedCity );
       algorithm.getWay(money,time  );
    }

}
