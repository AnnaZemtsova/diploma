package dataForAlgorithm;

import java.util.ArrayList;

public class CorrelatedWay {
    private ArrayList<Cell> way;

    public CorrelatedWay(ArrayList<Cell> way, double coeficient) {
        this.way = way;
        this.coeficient = coeficient;
    }

    public ArrayList<Cell> getWay() {

        return way;
    }

    public void setWay(ArrayList<Cell> way) {
        this.way = way;
    }

    public double getCoeficient() {
        return coeficient;
    }

    public void setCoeficient(double coeficient) {
        this.coeficient = coeficient;
    }

    private double coeficient;
}
