package algorithm;

import data.City;
import data.Train;
import dataForAlgorithm.Cell;
import dataForAlgorithm.Matrix;
import generalData.ALLDATA;

import java.util.ArrayList;

public class BranchBoundaryMethod implements  Algorithm{
    private ArrayList<Train> trains;
    private Matrix matrix;
    private static double INFINITY = 2147483647;
    private double [][] currentGraph;
    private static int from =1;

    public BranchBoundaryMethod(){
        trains = ALLDATA.trains;
        matrix = new Matrix();
    }


    //_____________________________________________________________________________________
    /*
    находим оптимальный путь методом ветвей и границ
     */
    @Override
    public ArrayList<City> getBestWay(ArrayList<City> wantedCities) {
        matrix.setMatrix(createGraph(wantedCities));
        matrix.setWay( new ArrayList<>(  ) );
        currentGraph =matrix.getMatrix();
        double dMin = findMinWay();
        matrix.setMin( dMin );
        makeTree( matrix );
        return null;
    }

    //_____________________________________________________________________________________
    /*
    создаем дерево по которому потом найдем минимальный путь
     */
        private void makeTree(Matrix matrix){
        if(matrix.getMatrix().length<2) return;

        Matrix leftMatrix = new Matrix();                                 //левое поддерево (с включением критической точки)
        Matrix rightMatrix = new Matrix();                                //правое поддерево (с исключением критической точки)
        double[][] leftGraph;                                             //граф левого поддерева
        double[][] rightGraph;                                            //граф правого поддерева
        int[] toWayIJ;                                                    //номера городов, которые включим в путь
        int[][] posForBranching;                                          //позиция для разветвления
        int i,j;                                                          //позиции по строке/столбцу

        currentGraph = matrix.getMatrix();
        deductValues();                                                   //вычитаем минимальные значения по строкам и столбцам
            printGraph( currentGraph );
        posForBranching = findCellForBranching();                         //ищем позицию для разветвления
        i = posForBranching[0][0];
        j = posForBranching[0][1];

        toWayIJ = getToWayIJ( i,j );                                      //смотрим какие города находятся в этой позиции

        leftGraph = getLeftGraph(i,j);                                    //создаем новый граф для левого поддерева
                                                                          //включая в путь позицию разветвления
        printWay( matrix.getWay() );
        rightGraph = getRightGraph( i,j,toWayIJ[0],toWayIJ[1]);           //создаем новый граф для правого поддерева
                                                                          // исключая из пути позицию разветвления

        leftMatrix.setMin(matrix.getMin()+getLeftMinDistance(leftGraph));                 //и устанавливаем какие минимальные пути
        rightMatrix.setMin(matrix.getMin()+getRightMinDistance(rightGraph));              //могут быть в каждом из этих графов


        leftMatrix.setWay( getWay( matrix,false,toWayIJ ) );    //заполяем текущий путь(какие переходы войдут,
        rightMatrix.setWay( getWay( matrix,true,toWayIJ ) );    //какие нет)

        rightGraph = deleteLoopCell( rightGraph,rightMatrix.getWay() );   //удаляем из получившихся графов пути, которые
        leftGraph = deleteLoopCell( leftGraph,leftMatrix.getWay() );      //образуют цикл

        leftMatrix.setMatrix( leftGraph );                                //добавляем в левую и правую матрицы
        rightMatrix.setMatrix( rightGraph );                              //соответствующие графы

        matrix.setLeftMatrix( leftMatrix );
        matrix.setRightMatrix( rightMatrix );

        if(leftMatrix.getMin()<rightMatrix.getMin()) {                    //идем в то поддерево, где возможнен меньший путь
            makeTree( matrix.getLeftMatrix() );
        }
        else {
            makeTree( matrix.getRightMatrix() );
        }
    }
    //_____________________________________________________________________________________

    /*
    находим минимально возможный путь (он может быть не реалистичный,
    но меньше него точно не сможем сделать
     */
    private double findMinWay(){
        int[] minRows = findMinimumsByRows();
        double sumRows=0;
        for(int i=from;i<minRows.length+1;i++){
            sumRows+=currentGraph[i][minRows[i-1]];
        }
        deductValuesFromRows();
        int[] minColumns = findMinimumsByColumns();
        double sumColumns=0;
        for(int i=from;i<minColumns.length+1;i++){
            sumColumns+=currentGraph[minColumns[i-1]][i];
        }
        return sumRows+sumColumns;
    }

    //_____________________________________________________________________________________

    /*
    смотрим какие возможные пути при даном могут образовать циклический путь,
    при котором другие точки не достижимы и удаляем их сразу (в граф на месте этого пути
    пишем бесконечность
    */
    private double[][] deleteLoopCell(double [][] graph,ArrayList<Cell> way){
        ArrayList<Integer[]> tmp = new ArrayList<>(  );
        boolean isStop;
        boolean isFound;
        for(int i=0;i<way.size();i++){
            isStop = false;
            if(way.get( i ).isPresent()) {
                int tmpJ = way.get( i ).getJ();
                while (!isStop) {
                    isFound = false;
                    for (int j = 0; j < way.size(); j++) {
                        if(way.get( j ).isPresent()) {
                            if (i != j) {
                                if (way.get( j ).getI() == tmpJ) {
                                    tmpJ = way.get( j ).getJ();
                                    isFound = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!isFound) {
                        isStop = true;
                        Integer[] r = new Integer[2];
                        r[1] = way.get( i ).getI();
                        r[0] = tmpJ;
                        tmp.add( r );
                    }
                }
            }
        }
        int i=-1;
        while(true) {
            if(i==tmp.size()-1) break;
            i++;
            for (Cell aWay : way) {
                if (tmp.get( i )[1] == aWay.getJ() && aWay.isPresent()) {
                    tmp.remove( i );
                    i--;
                    break;
                }
            }
        }
        for (Integer[] aTmp : tmp) {
            for (int a = 0; a < graph.length; a++) {
                for (int b = 0; b < graph.length; b++) {
                    if (aTmp[0] == graph[a][0] && aTmp[1] == graph[0][b]) {
                        graph[a][b] = INFINITY;
                    }
                }
            }
        }
        return graph;
    }

    //_____________________________________________________________________________________

    /*
    считаем что в правом графе путь из toWayI в toWayJ присутствует и удаляем строку i и столбец j
    а также пути toWayJ->toWayI
     */
    private double[][] getRightGraph(int i,int j,int toWayI,int toWayJ){
        double[][] rightGraph=new double[currentGraph.length-1][currentGraph.length-1];

        int a;
        int b;

        int iIndexForChanging = -1;
        int jIndexForChanging = -1;

        for(int q =0;q<currentGraph.length;q++){
            if(currentGraph[q][0]==toWayJ){
                iIndexForChanging = q;
            }
            if(currentGraph[0][q]==toWayI){
                jIndexForChanging = q;
            }
        }
        if(iIndexForChanging!=-1 && jIndexForChanging!=-1) {
            currentGraph[iIndexForChanging][jIndexForChanging] = INFINITY;
        }
        a=0;
        for(int k=0;k<currentGraph.length;k++){
            b=0;
            if(k!=i) {
                for (int u = 0; u < currentGraph.length; u++) {
                    if (u != j) {
                        rightGraph[a][b] = currentGraph[k][u];
                        b++;
                    }
                }
                a++;
            }
        }
        return rightGraph;
    }

    //_____________________________________________________________________________________

    /*
    считаем что пути i->j нету и удаляем его(присваиваем бесконечность)
     */
    private double [][] getLeftGraph(int i,int j){
        double[][] leftGraph=new double[currentGraph.length][currentGraph.length];
        for(int o=0;o<currentGraph.length;o++){
            System.arraycopy( currentGraph[o], 0, leftGraph[o], 0, currentGraph.length );
        }
        leftGraph[i][j] = INFINITY;
        return leftGraph;
    }


    //_____________________________________________________________________________________
    /*
    смотрим какие города соответствуют позиции i;j в графе
     */
    private int[] getToWayIJ(int i,int j){
        int [] res = new int[2];
        int toWayI = Double.valueOf(currentGraph[i][0]).intValue();
        int toWayJ = Double.valueOf(currentGraph[0][j]).intValue();
        res[0]=toWayI;
        res[1]=toWayJ;
        return res;
    }
    //_____________________________________________________________________________________

    /*
    находим минимально возможный путь для левого поддерева
     */
    private double getLeftMinDistance(double[][] leftGraph){
        currentGraph = leftGraph;
        return findMinWay();
    }
    //_____________________________________________________________________________________
     /*
    находим минимально возможный путь для правого поддерева
     */
    private double getRightMinDistance(double[][] rightGraph){
        currentGraph = rightGraph;
        return findMinWay();
    }


    //_____________________________________________________________________________________
    /*
    у нас есть ячейка toWayIJ которая хранит номера городов откуда едем и куда
    и то, должна ли присутствовать  эта ячейка в даной матрице
    добавляем ее  в путь
     */
    private ArrayList<Cell> getWay(Matrix matrix,boolean isPresent,int[] toWayIJ){
        ArrayList<Cell> currWay = new ArrayList<>(  );
        currWay.addAll( matrix.getWay() );
        currWay.add(new Cell(toWayIJ[0],toWayIJ[1],isPresent));
        return currWay;
    }
    //_____________________________________________________________________________________


    //_____________________________________________________________________________________
    /*
    находим позицию минимального элемента в каждой строчке
    */
    private int[] findMinimumsByRows() {
        int[] res = new int[currentGraph.length-1];
        int min;
        for(int i=from;i<currentGraph.length;i++) {
            min=from;
            for (int j = from; j < currentGraph.length; j++) {
                if (currentGraph[i][j] < currentGraph[i][min]) min = j;
            }
            res[i-1] = min;
        }
        return res;
    }

    //_____________________________________________________________________________________
    /*
    находим позицию минимального элемента в каждом столбце
    */
    private int[] findMinimumsByColumns() {
        int[] res = new int[currentGraph.length-1];
        int min;
        for(int i=from;i<currentGraph.length;i++) {
            min=from;
            for (int j = from; j < currentGraph.length; j++) {
                if (currentGraph[j][i] < currentGraph[min][i]) {
                    min = j;
                }
            }
            res[i-1] = min;
        }
        return res;
    }

    //_____________________________________________________________________________________

    /*
    вычитаем минимальные значения из строк и столбцов,
    чтобы в каждой строке и в каждом столбце был ноль
     */
    private void deductValues(){
        deductValuesFromRows();
        deductValuesFromColumns();
    }

    //_____________________________________________________________________________________
    /*
    вычитаем все минимальные значения из каждой строчки
     */
    private void deductValuesFromRows(){
        int[] mins  = findMinimumsByRows();
        double[] minValues = new double[mins.length];
        for(int i=from;i<minValues.length+1;i++){
            minValues[i-1]=currentGraph[i][mins[i-1]];
        }
        for(int i=from;i<currentGraph.length;i++){
            for(int j=from;j<currentGraph.length;j++) {
                currentGraph[i][j] -= minValues[i-1];
            }
        }
    }

    //_____________________________________________________________________________________
    /*
     вычитаем все минимальные значения из каждого столбца
     */
    private void deductValuesFromColumns(){
        int[] mins  = findMinimumsByColumns();
        double[] minValues = new double[mins.length];
        for(int i=from;i<minValues.length+1;i++){
            minValues[i-1]=currentGraph[mins[i-1]][i];
        }
        for(int i=from;i<currentGraph.length;i++){
            for(int j=from;j<currentGraph.length;j++) {
                currentGraph[j][i] -= minValues[i-1];
            }
        }
    }

    //_____________________________________________________________________________________
    /*
    находим позиции всех нулевых элементов
    */
    private ArrayList<int[][]> findZeroPositions(){
        ArrayList<int[][]> zeroPositions = new ArrayList<>(  );
        for(int i=from;i<currentGraph.length;i++){
            for(int j=from;j<currentGraph.length;j++){
                if(currentGraph[i][j]<0.01){
                    int [][] pos =new int[1][2];
                    pos[0][0]=i;
                    pos[0][1]=j;
                    zeroPositions.add(pos);
                }
            }
        }
        return zeroPositions;
    }
    //_____________________________________________________________________________________

    /*
    находим ноль
    для каждого нуля
     ищем позиции минимальных элементов по строке и по столбцу (не считая сам этот ноль)
     */
    private int[][] getMinPositions(ArrayList<int[][]> zeroPositions){
        int[][] minPositions = new int[zeroPositions.size() * 2][zeroPositions.size() * 2];
        int minColumnPosition=from;
        double minValueTmp;
        int minRowPosition = from;
        for (int i = from; i < zeroPositions.size(); i++) {

            minValueTmp = INFINITY;

            for (int j = from; j < currentGraph.length; j++) {
                if (j != zeroPositions.get( i )[0][1]) {
                    if (currentGraph[zeroPositions.get( i )[0][0]][j] <
                            minValueTmp) {
                        minColumnPosition = j;
                        minValueTmp = currentGraph[zeroPositions.get( i )[0][0]][minColumnPosition];
                    }
                }
            }

            minValueTmp = INFINITY;

            for (int j = from; j < currentGraph.length; j++) {
                if (j != zeroPositions.get( i )[0][0]) {
                    if (currentGraph[j][zeroPositions.get( i )[0][1]] <
                            minValueTmp) {
                        minRowPosition = j;
                        minValueTmp = currentGraph[minRowPosition][zeroPositions.get( i )[0][1]];
                    }
                }
            }

            minPositions[2 * i][0] = minRowPosition;
            minPositions[2 * i][1] = zeroPositions.get( i )[0][1];

            minPositions[i * 2 + 1][0] = zeroPositions.get( i )[0][0];
            minPositions[i * 2 + 1][1] = minColumnPosition;
        }
        return minPositions;
    }

    //_____________________________________________________________________________________
    /*
    находим ячейку, которую будем разветвлять
     */
    private int[][] findCellForBranching() {
        ArrayList<int[][]> zeroPositions ;
        zeroPositions = findZeroPositions();
        int[][] minPositions = getMinPositions( zeroPositions );

        //ищем позицию нуля, в котором сумма минимум по строке
        //и столбцу даст максимальный результат
        int[][] cellForBoundary = new int[1][2];
        double tmp;
        double max =0;

        for(int i=from;i<zeroPositions.size();i++){
           tmp=currentGraph[minPositions[2*i][0]][minPositions[2*i][1]]+
                    currentGraph[minPositions[2*i+1][0]][minPositions[2*i+1][1]];
            if(tmp>max) {
                max=tmp;
                cellForBoundary[0][0]=minPositions[2*i+1][0];
                cellForBoundary[0][1]=minPositions[2*i][1];
            }

        }
        return cellForBoundary;
    }

    //_____________________________________________________________________________________
    /*
    создаем взвешенный граф , содержащий все возможные переходы
    из одного города в другой
    всем невозможным путям присваиваем расстояние(цену) = бесконечности
     */
    private double[][] createGraph(ArrayList<City> wantedCities){
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
        for (Train train : trains) {
            for (int j = from; j < wantedCities.size()+1; j++) {
                if (train.getFrom().getName().equals( wantedCities.get( j-1).getName() )) {
                    for (int k = from; k < wantedCities.size()+1; k++) {
                        if (j != k) {
                            if (train.getTo().getName().equals( wantedCities.get( k-1).getName() )) {
                                graph[j][k] = train.getDestination();
                            }
                        }
                    }
                }
            }
        }
        return graph;
    }
    //_____________________________________________________________________________________



    //_______________________________РАСПЕЧАТКА___________________________________________
    //_____________________________________________________________________________________
    private void printWay(ArrayList<Cell> cells){
        for(int i=0;i<cells.size();i++){
            System.out.println(cells.get( i ).getI()+" "+cells.get( i ).getJ()+
                    " "+cells.get( i ).isPresent());
        }
        System.out.println("__________________________________________________");
    }
   //_____________________________________________________________________________________

    private void printGraph(double[][] graph){
        System.out.println("-----------------------------------------------");
        for(int r=0;r<graph.length;r++){
            for(int p=0;p<graph.length;p++){
                if(graph[r][p]>100000000) System.out.print("00000 ");
                else System.out.print(graph[r][p]+" ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------");
    }
    //_____________________________________________________________________________________

}


