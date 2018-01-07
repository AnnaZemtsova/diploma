package dataForAlgorithm;

import java.util.ArrayList;

public class Matrix {
   private ArrayList<Cell> way;
   private double[][] matrix;
   private  double min;
   private   Matrix leftMatrix;
   private Matrix rightMatrix;

    public ArrayList<Cell> getWay() {
        return way;
    }

    public void setWay(ArrayList<Cell> way) {
        this.way = way;
    }

    public Matrix(){
   }
    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public Matrix getLeftMatrix() {
        return leftMatrix;
    }

    public void setLeftMatrix(Matrix leftMatrix) {
        this.leftMatrix = leftMatrix;
    }

    public Matrix getRightMatrix() {
        return rightMatrix;
    }

    public void setRightMatrix(Matrix rightMatrix) {
        this.rightMatrix = rightMatrix;
    }

}
