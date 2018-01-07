package testingData;

import data.City;
import generalData.ALLDATA;
import inputData.InputUserData;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

public class UserDataFromCode implements InputUserData {
    ArrayList<City> cities;

    public UserDataFromCode(){
        cities =
        cities = ALLDATA.cities;
    }
    @Override
    public ArrayList<City> getWantedCities() {
            ArrayList<City> wanted = new ArrayList<>(  );
            wanted.add( cities.get( 0 ) );
            wanted.add( cities.get( cities.size()-1 ) );
            wanted.add( cities.get( 1 ) );
            wanted.add( cities.get( 3 ) );
            wanted.add( cities.get( 6 ) );
            wanted.add( cities.get( 8 ) );
            return wanted;
    }
}

