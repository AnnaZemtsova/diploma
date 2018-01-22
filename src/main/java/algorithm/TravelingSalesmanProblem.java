package algorithm;

import dataForAlgorithm.Cell;

import java.util.ArrayList;

public interface TravelingSalesmanProblem {

    public ArrayList<Cell> getMinimumWay(int amountOfCity,double[][] graph);
    public double getMinValue();
}
