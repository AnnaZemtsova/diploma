package dataForAlgorithm;

public class Cell {
    private int i;
    private int j;
    private boolean isPresent;

    public Cell(int i,int j,boolean isPresent){
        this.i =i;
        this.j=j;
        this.isPresent =isPresent;
    }
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
