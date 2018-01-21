package algorithm;

import data.City;
import data.Vehicle;
import java.util.ArrayList;
import java.util.Date;

public class Graph implements GraphCreator {

    private static final double INFINITY =4147483647.0;
    private static final int from =1;



    //_____________________________________________________________________________________
    @Override
    public double[][] createGraphByMoney(ArrayList<Vehicle> Vehicles,ArrayList<City> wantedCities) {
        return createGraph( Vehicles,wantedCities,true,false ,0,0);
    }

    //_____________________________________________________________________________________
    @Override
    public double[][] createGraphByTime(ArrayList<Vehicle> Vehicles,ArrayList<City> wantedCities) {
        return createGraph(Vehicles, wantedCities,false,true ,0,0);
    }

    //_____________________________________________________________________________________
    @Override
    public double[][] createGraphByTimeMoney(ArrayList<Vehicle> Vehicles,ArrayList<City> wantedCities, double m, double t) {
        return createGraph( Vehicles,wantedCities,false,false ,m,t);
    }

    //_____________________________________________________________________________________
    /*создаем взвешенный граф , содержащий все возможные переходы
    из одного города в другой
    всем невозможным путям присваиваем расстояние(цену) = бесконечности
     */
    private double[][] createGraph(ArrayList<Vehicle> Vehicles,ArrayList<City> wantedCities,boolean isMoney,
                                   boolean isTime,double m,double t){
        double[][] graph = new double[wantedCities.size()+1][wantedCities.size()+1];
        for(int i=0;i<wantedCities.size()+1;i++){
            for(int j=0;j<wantedCities.size()+1;j++){
                graph[i][j] = INFINITY;
            }
        }

        for(int i=0;i<wantedCities.size()+1;i++){
            graph[i][0]=i;
            graph[0][i]=i;
        }
        for (Vehicle Vehicle : Vehicles) {
            for (int j = from; j < wantedCities.size()+1; j++) {
                if (Vehicle.getFrom().getName().equals( wantedCities.get( j-1).getName() )) {
                    for (int k = from; k < wantedCities.size()+1; k++) {
                        if (j != k) {
                            if (Vehicle.getTo().getName().equals( wantedCities.get( k-1).getName() )) {
                               if(isMoney) graph[j][k] = Vehicle.getPrice();
                               else if(isTime)  graph[j][k] = getTimeInWay( Vehicle.getDateTo(),Vehicle.getDatefrom());
                               else graph[j][k] = m*Vehicle.getPrice()+t*getTimeInWay( Vehicle.getDatefrom(),Vehicle.getDateTo() );
                            }
                        }
                    }
                }
            }
        }
        return graph;
    }

    //_____________________________________________________________________________________

    private double getTimeInWay(Date to, Date from){
        return ((to.getTime() - from.getTime())/(1000*3600));
    }

}
