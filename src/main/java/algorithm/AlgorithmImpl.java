package algorithm;

import data.City;
import dataForAlgorithm.Cell;
import generalData.ALLDATA;

import java.util.ArrayList;

public class AlgorithmImpl implements Algorithm{
    private TravelingSalesmanProblem travelingSalesmanProblem;
    private GraphCreator graphCreator;
    private ArrayList<City> wantedCities;

    private double minValue;

    //_____________________________________________________________________________________

    public AlgorithmImpl(TravelingSalesmanProblem travelingSalesmanProblem, GraphCreator graphCreator,
                         ArrayList<City> wantedCities){
        this.travelingSalesmanProblem = travelingSalesmanProblem;
        this.graphCreator = graphCreator;
        this.wantedCities = wantedCities;
    }


    //_____________________________________________________________________________________
    @Override
    public ArrayList<City> getWay( double money, double time) {


        return null;
    }


    //_____________________________________________________________________________________

    private ArrayList<Cell> findBestWay(double money, double time){
        double [][] graphByMoney = graphCreator.createGraphByMoney( ALLDATA.vehicles,wantedCities);
        double [][] graphByTime  = graphCreator.createGraphByTime( ALLDATA.vehicles,wantedCities);

        ArrayList<Cell> wayMoney = findAvailableWayByMinimum( money,time,graphByMoney,graphByTime );
        double minPrice = minValue;
        if(wayMoney!=null) return wayMoney;

        ArrayList<Cell> wayTime = findAvailableWayByMinimum( time, money, graphByTime, graphByMoney );
        double minTime = minValue;
        if(wayTime != null) return wayTime;

        double moneyDifference = money - minPrice;
        double timeDifference = time - minTime;

        if(moneyDifference<0&&timeDifference<0) return null;

        if(moneyDifference<0){

        }

        if (timeDifference<0){

        }

        return null;
    }
    //_____________________________________________________________________________________

    private ArrayList<Cell> findAvailableWayByMinimum(double optimizeValue, double secondValue,double[][]
                                                           optimizeGraph,double[][] secondGraph){
        ArrayList<Cell> way = travelingSalesmanProblem.getBestWay( wantedCities.size(),optimizeGraph );
        minValue = travelingSalesmanProblem.getMinValue();
        if(minValue <= optimizeValue){
            double secondValueReal = findOtherValue(secondGraph,way);
            if(secondValueReal<=secondValue) return way;
        }
        return null;
    }

    //_____________________________________________________________________________________

    private double findOtherValue(double [][] graph, ArrayList<Cell> way){
        double time =0;
        for (Cell aWay : way) {
            int a = Double.valueOf( graph[aWay.getI()][0] ).intValue();
            int b = Double.valueOf( graph[0][aWay.getJ()] ).intValue();
            time += graph[a][b];
        }
        return time;
    }

    //_____________________________________________________________________________________

}
