package algorithm;

import data.City;
import dataForAlgorithm.Cell;
import dataForAlgorithm.CorrelatedWay;

import java.util.ArrayList;

public class CorrelationMoneyTime implements Algorithm {
    private TravelingSalesmanProblem travelingSalesmanProblem;
    private GraphCreator graphCreator;
    private ArrayList<City> wantedCities;
    private final double eps = 0.000001;

    //_____________________________________________________________________________________

    public CorrelationMoneyTime(TravelingSalesmanProblem travelingSalesmanProblem, GraphCreator graphCreator,
                                ArrayList<City> wantedCities){
        this.travelingSalesmanProblem = travelingSalesmanProblem;
        this.graphCreator = graphCreator;
        this.wantedCities = wantedCities;
    }


    //_____________________________________________________________________________________
    @Override
    public ArrayList<City> getWay( double money, double time) {
        ArrayList<Cell> bestWay = findBestWay( money,time );
        double moneyCurrVal = findOtherValue(graphCreator.createGraphByMoney( wantedCities ),bestWay );
        double timeCurrVal = findOtherValue( graphCreator.createGraphByTime(wantedCities ),bestWay );
        ArrayList<City> result = getCities(bestWay);
        printCities( result );
        System.out.println("Цена поездки: "+moneyCurrVal+" ,время"+timeCurrVal);
        return null;
    }


    //_____________________________________________________________________________________

    private ArrayList<City> getCities(ArrayList<Cell> way){
        ArrayList<City> result = new ArrayList<>(  );
        result.add( wantedCities.get( way.get( 0 ).getI() -1) );         //получаем из массива номеров городов сами города
        for (Cell aResWay : way) {
            result.add( wantedCities.get( aResWay.getJ() - 1 ) );
        }
        return result;
    }


    /*
    Находим лучший из возможных путь.
    Лучшим считается путь, который:
    1. цена которого меньше или равна money и время меньше или равно time
    2. Если в эти рамки вместиться не возможно, то лучшим считается путь, у которого наименьшее отклонение
       о заданных по обеим параметрам
     */
    private ArrayList<Cell> findBestWay(double money, double time){
        double [][] graphByMoney = graphCreator.createGraphByMoney( wantedCities);
        double [][] graphByTime  = graphCreator.createGraphByTime(wantedCities);
        /*
           сначала ищем, может быть есть путь, в котором минимальная цена, а время в рамках допустимого
         */
        ArrayList<Cell> wayMoney = findAvailableWayByMinimum( money,time,graphByMoney,graphByTime );
        if(wayMoney!=null) return wayMoney;

        /*
           или если нет такого пути, может быть есть путь, в котором минимально время, а путь допустимый
         */

        graphByMoney = graphCreator.createGraphByMoney(wantedCities);
        graphByTime  = graphCreator.createGraphByTime( wantedCities);
        ArrayList<Cell> wayTime = findAvailableWayByMinimum( time, money, graphByTime, graphByMoney );

        if(wayTime != null) return wayTime;
        /*
        если нет таких путей, ищем путь, цена и время которого ближе всех к допустимым
         */
        return findCorrelatedWay( money,time );
    }
    //_____________________________________________________________________________________

    /*
    находим путь в котором цена и время которого ближе всех к допустимым
     */
    private ArrayList<Cell>  findCorrelatedWay(double money,double time){
        ArrayList<CorrelatedWay> correlatedWays = new ArrayList<>(  );
        findAllowableWay(correlatedWays,0.5,0.5,money,time,0.5 );
        int min = 0;
        for (int i=1;i<correlatedWays.size();i++){
            if(correlatedWays.get( i ).getCoeficient()<correlatedWays.get( min ).getCoeficient()){
                min = i;
            }
        }
        return correlatedWays.get( min ).getWay();
    }


    //_____________________________________________________________________________________

    /*
    ищем путь который ближе всего к допустимым значениям.
    ищем по такому принципу.
    сначала заполняем граф, в котором вес времени и цены одинаковый.
    дальше смотрим отношение заданной цены к получившейся и заданного времени к получившемуся
    какое значение больше, к тому мы прибалвяем e/2 (e изначально равно 0.5)
    из-за того что е все время уменьшается в 2 раза, точность наших вычислений увеличивается
    прекращаем в двух случаях:
     1.  найден путь, в котором мы вмещаемся в заданные границы
     2. достигнута заданная точность
     */

    private ArrayList<CorrelatedWay>  findAllowableWay(ArrayList<CorrelatedWay> correlatedWays, double moneyPer,
                                                      double timePer, double money, double time, double e){
        ArrayList<Cell> way = travelingSalesmanProblem.getMinimumWay( wantedCities.size() ,
                graphCreator.createGraphByTimeMoney( wantedCities,moneyPer,timePer ) );
        double moneyCurrVal = findOtherValue(graphCreator.createGraphByMoney(wantedCities ),way );
        double timeCurrVal = findOtherValue( graphCreator.createGraphByTime(wantedCities ),way );
        double moneyPercentage = (( moneyCurrVal/money ));
        double timePercentage = ((timeCurrVal/time));
        correlatedWays.add(new CorrelatedWay(way,Math.abs(findQuotient( moneyPercentage,timePercentage ))));
        if(moneyCurrVal<=money&&timeCurrVal<=time) return correlatedWays;
            else{
                if(moneyPercentage>=timePercentage){
                    if(e<eps) return correlatedWays;
                    else findAllowableWay( correlatedWays,moneyPer+e,timePer-e,money,time,e/2 );
                }else {
                    if(e<eps) return  correlatedWays;
                    else findAllowableWay(correlatedWays, moneyPer-e,timePer+e,money,time,e/2);
                }
            }
        return null;
    }

    //_____________________________________________________________________________________
    /*
    находим отношение разности в деньгах к разности во времени
     */
    private double findQuotient(double moneyPercentage,double timePercentage){
        double quotient = 0;
        if(moneyPercentage<=1&&timePercentage>=1) quotient = moneyPercentage/timePercentage;
        if(timePercentage<=1&&moneyPercentage>=1) quotient = timePercentage/moneyPercentage;
        if(moneyPercentage>=1&&timePercentage>=1&&moneyPercentage>=timePercentage)
            quotient = moneyPercentage/timePercentage;
        else
        if(moneyPercentage>=1&&timePercentage>=1) quotient = timePercentage/moneyPercentage;
        if(quotient!=0) quotient = quotient-1;
        return quotient;
    }

    //_____________________________________________________________________________________

    /*
    находим оптимальный путь по заданному  графу
     */
    private ArrayList<Cell> findAvailableWayByMinimum(double optimizeValue, double secondValue,double[][]
                                                           optimizeGraph,double[][] secondGraph){
        ArrayList<Cell> way = travelingSalesmanProblem.getMinimumWay( wantedCities.size(),optimizeGraph );
       // min = travelingSalesmanProblem.getMinValue();
        double secondValueReal = findOtherValue(secondGraph,way);
            if(secondValueReal<=secondValue) {
                return way;
            }
        return null;
    }

    //_____________________________________________________________________________________
    /*
    когда мы нашли оптимальный путь по одному из значений, нам интересно чему равно в таком пути второе значение
    например, оптимизировали цену - нашли путь, и нужно найти сколько времени займет этот путь
     */
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
    private void printCities(ArrayList<City> cities){
        for (City city : cities) {
            System.out.println( city.getName() );
        }
    }
    //_____________________________________________________________________________________


}
