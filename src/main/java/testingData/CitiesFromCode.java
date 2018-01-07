package testingData;

import data.City;
import generalData.ALLDATA;
import inputData.InputCities;

import java.util.ArrayList;

public class CitiesFromCode implements InputCities {

    private ArrayList<City> cities;



    public void fullCities(){
        cities = new ArrayList<>(  );
        City cityK = new City( "Kyiv" );
        City cityL = new City( "Lviv" );
        City cityH = new City( "Kharkiv" );
        City cityO = new City( "Odessa" );
        City cityD = new City( "Donetsk" );
        City cityZh = new City( "Zhitomir" );
        City cityCh = new City( "Chernigov" );
        City cityKr = new City( "Krakow" );
        City cityM = new City( "Moskva" );
        City cityU = new City( "Uzhgorod" );
        City cityY = new City( "Yalta" );
        City cityMe = new City( "Melitopol" );
        City cityP = new City( "Poltava" );
        City cityS = new City( "Sumi" );


        cities.add( cityK );
        cities.add( cityL );
        cities.add( cityH );
        cities.add( cityO );
        cities.add( cityD );
        cities.add( cityZh );
        cities.add( cityCh );
        cities.add( cityKr );
        cities.add( cityM );
        cities.add( cityU );
        cities.add( cityY );
        cities.add( cityMe );
        cities.add( cityP );
        cities.add( cityS );
    }
    @Override
    public ArrayList<City> getCities() {
        fullCities();
        return cities;
    }
}
