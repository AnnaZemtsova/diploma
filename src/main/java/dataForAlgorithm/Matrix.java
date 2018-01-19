package dataForAlgorithm;

import java.util.ArrayList;

public class Matrix {
   private ArrayList<Cell> way;
   private double[][] matrix;
   private  double min;
   private   Matrix leftMatrix;
   private Matrix rightMatrix;
   private Matrix parent;
   private int color;

   public static int WHITE = 2;
   public static int RED = 1;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Matrix getParent() {
        return parent;
    }

    public void setParent(Matrix parent) {
        this.parent = parent;
    }

    public ArrayList<Cell> getWay() {
        return way;
    }

    public void setWay(ArrayList<Cell> way) {
        this.way = way;
    }

    public Matrix(){
        color=WHITE;
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
