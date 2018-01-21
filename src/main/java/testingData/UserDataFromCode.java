package testingData;

import data.City;
import generalData.ALLDATA;
import inputData.InputUserData;

import javax.jws.soap.SOAPBinding;
import java.sql.Time;
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
            wanted.add( cities.get( 1 ) );
            wanted.add( cities.get( 2 ) );
            wanted.add( cities.get( 3 ) );
            wanted.add( cities.get( 4 ) );
        wanted.add( cities.get( 5 ) );

        wanted.add( cities.get( 6 ) );
        wanted.add( cities.get( 7 ) );
        wanted.add( cities.get( 8 ) );
        wanted.add( cities.get( 9 ) );

        wanted.add( cities.get( 10 ) );
         wanted.add( cities.get( 11) );
        wanted.add( cities.get( 12 ) );
        wanted.add( cities.get( 13 ) );
        wanted.add( cities.get( 14 ) );

        wanted.add( cities.get( 15 ) );
        wanted.add( cities.get( 16) );
        wanted.add( cities.get( 17 ) );
        wanted.add( cities.get( 18 ) );
        wanted.add( cities.get( 19 ) );

            return wanted;
    }

    @Override
    public double getMoney() {
        return 2400;
    }

    @Override
    public Time getTime() {
        Time time = new Time(Long.getLong(String.valueOf(3*24*60*60*100)));
        return time;
    }
}

