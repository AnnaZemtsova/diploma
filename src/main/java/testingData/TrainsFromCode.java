package testingData;

import data.City;
import java.util.ArrayList;
import java.util.Date;
import data.Train;
import generalData.ALLDATA;
import inputData.InputTrains;

public class TrainsFromCode implements InputTrains {
    ArrayList<Train> trains;
    ArrayList<City> cities;


    @Override
    public ArrayList<Train> getTrains() {
        fullTrains();
        return trains;
    }


    public  void fullTrains(){
        trains = new ArrayList<>();
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

        Date dateFrom10 = new Date(2017,10,12,10,15);
        Date dateTo10 = new Date(2017,10,13,6,15);


        Date dateFrom1 = new Date(2017,10,12,10,15);
        Date dateTo1 = new Date(2017,10,13,3,15);


        Date dateFrom2 = new Date(2017,10,12,10,15);
        Date dateTo2 = new Date(2017,10,13,12,15);


        Date dateFrom3 = new Date(2017,10,12,10,15);
        Date dateTo3 = new Date(2017,10,13,15,15);


        Date dateFrom4 = new Date(2017,10,12,10,15);
        Date dateTo4 = new Date(2017,10,13,2,15);


        Date dateFrom5 = new Date(2017,10,12,10,15);
        Date dateTo5 = new Date(2017,10,13,0,15);


        Date dateFrom6 = new Date(2017,10,12,10,15);
        Date dateTo6 = new Date(2017,10,12,11,15);


        Date dateFrom7 = new Date(2017,10,12,10,15);
        Date dateTo7 = new Date(2017,10,14,6,15);


        Date dateFrom8 = new Date(2017,10,12,10,15);
        Date dateTo8 = new Date(2017,10,15,0,15);


        Date dateFrom9 = new Date(2017,10,12,10,15);
        Date dateTo9 = new Date(2017,10,12,17,15);


        Train trainKL = new Train(cityK,cityL,dateFrom7,dateTo7,800 ,300 );
        Train trainKH = new Train( cityK,cityH,dateFrom5,dateTo5,600,200  );
        Train trainKO = new Train(cityK,cityO,dateFrom4,dateTo4,900 ,850  );
        Train trainKCh = new Train( cityK,cityCh,dateFrom6,dateTo6,100  ,120);
        Train trainKZh = new Train( cityK,cityZh,dateFrom3,dateTo3,500 ,400 );
        Train trainKP = new Train( cityK,cityP,dateFrom10,dateTo10,400  ,40);
        Train trainKKr = new Train( cityK,cityKr,dateFrom8,dateTo8,1800 ,2000 );
        Train trainKS = new Train(  cityK,cityS,dateFrom4,dateTo4,780,50);

        Train trainLO = new Train(cityL,cityO,dateFrom1,dateTo1,960 , 600);
        Train trainLP = new Train(cityL,cityP,dateFrom9,dateTo9,400 ,500  );
        Train trainLS = new Train( cityL,cityS,dateFrom6,dateTo6,400 ,500 );
        Train trainLM = new Train( cityL,cityM,dateFrom2,dateTo2,100,2000 );
        Train trainLKr = new Train( cityL,cityKr,dateFrom6,dateTo6,300,200 );
      //  Train trainLK = new Train( cityL,cityK,dateFrom6,dateTo6,300,200 );

        Train trainMeZh = new Train( cityMe,cityZh,dateFrom6,dateTo6,300,100 );
        Train trainMeB = new Train( cityMe,cityD,dateFrom10,dateTo10,1000,30 );
        Train trainMeD = new Train( cityMe,cityD,dateFrom6,dateTo6,100 ,100);

        Train trainKhY = new Train( cityH,cityY,dateFrom8,dateTo8,2100,1200 );
        Train trainKhU = new Train(  cityH,cityU,dateFrom8,dateTo8,1100,900);
        Train trainKhP = new Train( cityH,cityP,dateFrom3,dateTo3,700 ,100);
        Train trainKhO = new Train(cityH,cityO,dateFrom5,dateTo5,400 ,200 );
        Train trainKhS = new Train(  cityH,cityS,dateFrom4,dateTo4,780,50);

        Train trainSU = new Train( cityS,cityU,dateFrom6,dateTo6,100,400 );
        Train trainSM = new Train( cityS,cityM,dateFrom1,dateTo1,800 ,1200);
        Train trainSK = new Train( cityS,cityK,dateFrom6,dateTo6,300 ,70);

        Train trainYZh = new Train(  cityY,cityZh,dateFrom9,dateTo9,900,700);
        Train trainYCh = new Train( cityY,cityCh,dateFrom8,dateTo8,1000,900 );
        Train trainYL = new Train( cityY,cityL,dateFrom5,dateTo5,600,900 );

        Train trainDKr = new Train( cityD,cityKr,dateFrom8,dateTo8,3100 ,2000);

        Train trainPU = new Train( cityP,cityU,dateFrom6,dateTo6,100 ,400);
        Train trainPM = new Train( cityP,cityM,dateFrom1,dateTo1,800 ,1200);

        Train trainPK = new Train( cityP,cityK,dateFrom6,dateTo6,300 ,100);
        Train trainPZh = new Train(  cityP,cityZh,dateFrom9,dateTo9,900,200);
        Train trainPCh = new Train( cityP,cityCh,dateFrom8,dateTo8,1000,80 );
        Train trainPL = new Train( cityP,cityL,dateFrom5,dateTo3,600 ,200);
        Train trainPKr = new Train( cityP,cityKr,dateFrom8,dateTo8,3100 ,1000);

        Train trainOU = new Train( cityO,cityU,dateFrom6,dateTo2,100,400 );
        Train trainOM = new Train( cityO,cityM,dateFrom1,dateTo1,800 ,1200);
        Train trainOZh = new Train(  cityO,cityZh,dateFrom6,dateTo9,900,300);
        Train trainOCh = new Train( cityO,cityCh,dateFrom8,dateTo7,1000,300 );
        Train trainOL = new Train( cityO,cityL,dateFrom5,dateTo6,600,600 );
        Train trainOKr = new Train( cityO,cityKr,dateFrom8,dateTo8,3100,1700 );

        Train trainChL = new Train( cityCh,cityL,dateFrom8,dateTo8,3100,1700 );
        Train trainChO = new Train( cityCh,cityO,dateFrom8,dateTo8,3100,1700 );

        Train trainZhU = new Train( cityZh,cityU,dateFrom6,dateTo10,100,160 );
        Train trainZhM = new Train( cityZh,cityM,dateFrom1,dateTo4,800,1400 );
        Train trainZhK = new Train( cityZh,cityK,dateFrom6,dateTo9,300 ,150);

        Train trainMZh = new Train(  cityM,cityZh,dateFrom9,dateTo9,900,70);

        Train trainMCh = new Train( cityM,cityCh,dateFrom8,dateTo8,1000 ,200);
        Train trainML = new Train( cityM,cityL,dateFrom5,dateTo5,600 ,600);
        Train trainMKr = new Train( cityM,cityKr,dateFrom8,dateTo8,3100,1100 );


        trains.add( trainPU);
        trains.add( trainPM );

        trains.add( trainYZh );
        trains.add( trainKhU );
        trains.add( trainKhY );
        trains.add( trainMeD);
        trains.add( trainDKr );
        trains.add( trainYL );
        trains.add( trainYCh );

        trains.add( trainSK );
        trains.add( trainSM );
        trains.add( trainSU );

        trains.add( trainKhS );
        trains.add( trainKhO );
        trains.add( trainKhP );

        trains.add( trainKS );
        trains.add( trainKH );
        trains.add( trainKO );
        trains.add( trainKCh );
        trains.add( trainKZh );
        trains.add( trainKP );
        trains.add( trainKKr );
        trains.add( trainKL );

        trains.add( trainLO );
        trains.add( trainLP );
        trains.add( trainLS );
        trains.add( trainLM );
        trains.add( trainLKr );

        trains.add( trainMeZh);
        trains.add( trainMeB );
        trains.add( trainDKr );
        trains.add( trainMCh );

        trains.add( trainML );
        trains.add( trainMKr );

        trains.add( trainZhU );
        trains.add( trainZhM);
        trains.add( trainZhK );
        trains.add( trainMZh );

        trains.add( trainOKr );
        trains.add( trainOL );
        trains.add( trainOCh );
        trains.add( trainOZh );
        trains.add( trainOM );
        trains.add( trainOU );

        trains.add( trainPKr);
        trains.add( trainPL );
        trains.add( trainPCh );
        trains.add( trainPZh );
        trains.add( trainPK );

        trains.add( trainChL );
        trains.add( trainChO );

    }
}
