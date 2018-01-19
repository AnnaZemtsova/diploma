package algorithm;

import data.City;
import data.Train;
import dataForAlgorithm.Cell;
import dataForAlgorithm.Matrix;
import generalData.ALLDATA;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class BranchBoundaryMethod implements  Algorithm{
    private ArrayList<Train> trains;
    private Matrix matrix;
    private static double INFINITY =4147483647.0;
    private double [][] currentGraph;
    private ArrayList<Cell> tmpway;
    double min;
    private static int from =1;
    public BranchBoundaryMethod(){
        trains = ALLDATA.trains;
        matrix = new Matrix();
    }


    //_____________________________________________________________________________________
    /*
    находим оптимальный путь методом ветвей и границ
     */
    //тут надо еще из matrix.getWay() сформировать нормальный путь в виде городов и вернуть его а не null!!!
    @Override
    public ArrayList<City> getBestWay(ArrayList<City> wantedCities) {
        matrix.setMatrix(createGraph(wantedCities));
        matrix.setWay( new ArrayList<>(  ) );
        currentGraph =matrix.getMatrix();
        double dMin = findMinWay();
        matrix.setMin( dMin );
        makeTree(matrix);
        Matrix res = findMatrix( getRoot( matrix )  );
        ArrayList<Cell> way = res.getWay();
        findFullWay(  way,wantedCities.size());
        deleteNotPresentedCells( way );
        ArrayList<Cell> resWay = sortCells(way);
        printWay( resWay);
        ArrayList<City> result = new ArrayList<>(  );
        result.add( wantedCities.get( resWay.get( 0 ).getI() -1) );
        for(int i=0;i<resWay.size();i++){
            result.add( wantedCities.get( resWay.get( i ).getJ() -1) );
        }
        return result;
    }
    
    //_____________________________________________________________________________________

    private ArrayList<Cell> sortCells(ArrayList<Cell> way) {
        ArrayList<Cell> result = new ArrayList<>(  );
        boolean isFullWay= false;
        int curr = 1;
        while(!isFullWay){
            for (Cell aWay : way) {
                if (aWay.getI() == curr) {
                    result.add( aWay );
                    curr = aWay.getJ();
                    if (curr == 1) {
                        isFullWay = true;
                    }
                    break;
                }
            }
        }
        return result;
    }

    //_____________________________________________________________________________________

    private  void deleteNotPresentedCells(ArrayList<Cell> way){
        int index = 0;
        while(index!=way.size()-1){
            if(!way.get( index ).isPresent()) {
                way.remove( index );
            }else index++;
        }
    }

    //_____________________________________________________________________________________

    private void findFullWay(ArrayList<Cell> way,int max){
        int[] i = new int[2];
        int iIndex = 0;
        int[] j = new int[2];
        int jIndex = 0;
        boolean isFoundAI ;
        boolean isFoundAJ ;
        for(int a=1;a<=max;a++){
            isFoundAI = false;
            isFoundAJ = false;
            for (Cell aWay : way) {
                if (aWay.getI() == a&&aWay.isPresent()) isFoundAI = true;
                if (aWay.getJ() == a&&aWay.isPresent()) isFoundAJ = true;
            }
            if(!isFoundAI){
                i[iIndex] = a;
                iIndex++;
            }
            if(!isFoundAJ){
                j[jIndex] = a;
                jIndex++;
            }
        }
        way.add( new Cell( i[0],j[0],true ) );
        way.add( new Cell( i[1],j[1],true ) );
        boolean isWayComplet = false;
        int amountInWay = 0;
        int curr = 1;
        while (!isWayComplet) {
            for (Cell aWay : way) {
                if (aWay.isPresent() && aWay.getI() == curr) {
                    curr = aWay.getJ();
                    if (curr == 1) isWayComplet = true;
                    amountInWay++;
                    break;
                }
            }
        }
        if(amountInWay!=max){
            way.remove( way.size()-1 );
            way.remove( way.size()-1 );
            way.add( new Cell(i[0],j[1],true  ) );
            way.add( new Cell( i[1],j[0],true ) );
        }
    }
    //_____________________________________________________________________________________

    private Matrix findMatrix(Matrix matrix){
        Deque<Matrix> matrixDeque = new ArrayDeque<>(  );
        matrixDeque.addLast( matrix );
        Matrix tmp = new Matrix();
        while (!matrixDeque.isEmpty()){
             tmp = matrixDeque.poll();
            if(tmp.getColor()==Matrix.RED) break;
            if(tmp.getLeftMatrix()!=null&&tmp.getRightMatrix()!=null){
                matrixDeque.addLast( tmp.getRightMatrix() );
                matrixDeque.addLast( tmp.getLeftMatrix() );
            }

        }
        return tmp;
    }


    //_____________________________________________________________________________________

     /*
     создаем дерево по которому потом найдем минимальный путь
     */
        private void makeTree( Matrix matrix){
        if(matrix.getMatrix().length<4) {
            tmpway = matrix.getWay();
            min = matrix.getMin();
            matrix.setColor( Matrix.RED );
            return ;
        }

        Matrix leftMatrix = new Matrix();                                 //левое поддерево (с включением критической точки)
        Matrix rightMatrix = new Matrix();                                //правое поддерево (с исключением критической точки)
        double[][] leftGraph;                                             //граф левого поддерева
        double[][] rightGraph;                                            //граф правого поддерева
        int[] toWayIJ;                                                    //номера городов, которые включим в путь
        int[][] posForBranching;                                          //позиция для разветвления
        int i,j;                                                          //позиции по строке/столбцу

        currentGraph = matrix.getMatrix();
        deductValuesFromColumns();                                        //вычитаем минимальные значения по столбцам(по строкам уже вычтено)
        posForBranching = findCellForBranching();                         //ищем позицию для разветвления
        i = posForBranching[0][0];
        j = posForBranching[0][1];

            printGraph( matrix.getMatrix() );
            printWay( matrix.getWay() );
        toWayIJ = getToWayIJ( i,j );                                      //смотрим какие города находятся в этой позиции


        leftGraph = getLeftGraph(i,j);                                    //создаем новый граф для левого поддерева
                                                                          //включая в путь позицию разветвления
        rightGraph = getRightGraph( i,j,toWayIJ[0],toWayIJ[1]);           //создаем новый граф для правого поддерева
                                                                          // исключая из пути позицию разветвления

        leftMatrix.setWay( getWay( matrix,false,toWayIJ ) );    //заполяем текущий путь(какие переходы войдут,
        rightMatrix.setWay( getWay( matrix,true,toWayIJ ) );    //какие нет)


            rightGraph = deleteLoopCell( rightGraph,rightMatrix.getWay() );   //удаляем из получившихся графов пути, которые
            leftGraph = deleteLoopCell( leftGraph,leftMatrix.getWay() );      //образуют цикл



            leftMatrix.setMin(matrix.getMin()+getLeftMinDistance( leftGraph ));   //и устанавливаем какие минимальные пути
            rightMatrix.setMin(matrix.getMin()+getRightMinDistance( rightGraph)); //могут быть в каждом из этих графов


        leftMatrix.setMatrix( leftGraph );                                //добавляем в левую и правую матрицы
        rightMatrix.setMatrix( rightGraph );                              //соответствующие графы

            matrix.setLeftMatrix( leftMatrix );
            matrix.setRightMatrix( rightMatrix );
            leftMatrix.setParent( matrix );
            rightMatrix.setParent( matrix );
           // printGraph( matrix.getMatrix() );
            System.out.println(leftMatrix.getMin()+" "+rightMatrix.getMin());
        makeTree(findMinResMatrix(matrix) );


       /* if(leftMatrix.getMin()<rightMatrix.getMin()) {                    //идем в то поддерево, где возможнен меньший путь
            //makeTree( matrix.getLeftMatrix() );
        }
        else {
            //makeTree( matrix.getRightMatrix() );
        }*/
       return;
    }

    //_____________________________________________________________________________________

    private Matrix findMinResMatrix(Matrix matrix) {
        ArrayList<Matrix> findLists =  new ArrayList<>(  );
        Matrix root = getRoot(matrix);
        findLists = findLists(findLists,root);
        return findMinMatrix( findLists );
    }

    //_____________________________________________________________________________________
    private Matrix getRoot(Matrix matrix) {
            Matrix res = matrix;
            while(res.getParent()!=null){
                res = res.getParent();
            }
            return res;
    }

    //_____________________________________________________________________________________

    private Matrix findMinMatrix(ArrayList<Matrix> lists){
            double min = lists.get( 0 ).getMin();
            Matrix res = lists.get( 0 );
            for(int i=1;i<lists.size();i++){
                if(lists.get( i ).getMin()<min){
                    min = lists.get( i ).getMin();
                    res = lists.get( i );
                }
            }
        System.out.println(min+" "+res.getMatrix().length);
            return res;
    }

    //_____________________________________________________________________________________
    private ArrayList<Matrix> findLists(ArrayList<Matrix> list,Matrix matrix){
        Deque<Matrix> matrixDeque = new ArrayDeque<>(  );
        matrixDeque.addLast( matrix );
       while (!matrixDeque.isEmpty()){
            Matrix tmp = matrixDeque.poll();
            if(tmp.getLeftMatrix()==null&&tmp.getRightMatrix()==null){
                list.add( tmp );
            }
           else {
                matrixDeque.addLast( tmp.getRightMatrix() );
                matrixDeque.addLast( tmp.getLeftMatrix() );
            }
       }
       return list;
    }
    //_____________________________________________________________________________________

    /*
    находим минимально возможный путь (он может быть не реалистичный,
    но меньше него точно не сможем сделать
     */
    private double findMinWay(){
        int[] minRows = findMinimumsByRows();                    //ищем позиции минимальных элементов по строчкам
        double sumRows=0;
        for(int i=from;i<minRows.length+1;i++){
            sumRows+=currentGraph[i][minRows[i-1]];                 //находим сумму всех минимумов по строчкам
        }
        deductValuesFromRows();                                     //вычитаем эти минимальные значения из каждой строчки
        int[] minColumns = findMinimumsByColumns();                 //ищем минимальные значения в колонках
        double sumColumns=0;
        for(int i=from;i<minColumns.length+1;i++){
            sumColumns+=currentGraph[minColumns[i-1]][i];           //находим сумму всех минимумов по столбцам
        }
        return sumRows+sumColumns;                                  //возвращаем сумму минимумов (меньше этого пути быть никак не может)
    }

    //_____________________________________________________________________________________

    /*
    смотрим какие возможные пути при даном могут образовать циклический путь,
    при котором другие точки не достижимы и удаляем их сразу (в граф на месте этого пути
    пишем бесконечность
    (по-хорошему нужно придумать что-то получше, код ужас)
    */
    private double[][] deleteLoopCell(double [][] graph,ArrayList<Cell> way){
        ArrayList<Integer[]> tmp = new ArrayList<>(  );
        boolean isStop;
        boolean isFound;
        for(int i=0;i<way.size();i++){                                  //проходимся по каждой точке содержащейся в пути
            isStop = false;
            if(way.get( i ).isPresent()) {                              //если точка присутствует в пути
                int tmpJ = way.get( i ).getJ();                         //запоминаем куда мы едем (позицию j)
                while (!isStop) {
                    isFound = false;
                    for (int j = 0; j < way.size(); j++) {              //проходимся по всем элементам этого же пути
                        if(way.get( j ).isPresent()) {                  //и пытаемся найти такое перемещение, чтобы i=j
                            if (i != j) {                               //(то есть въехали мы в город j, и смотрим
                                if (way.get( j ).getI() == tmpJ) {      //выезжаем ли мы куда-то в даном пути)
                                    tmpJ = way.get( j ).getJ();         //если нашли такой путь, то запоминаем что это за город
                                    isFound = true;                     //и выходим из цикла
                                    break;
                                }
                            }
                        }
                    }
                    if (!isFound) {                                     //если такой город найден то запоминаем
                        isStop = true;                                  //комбинацию которой не может быть (она будет
                        Integer[] r = new Integer[2];                   //циклической)
                        r[1] = way.get( i ).getI();                     //например, был путь 2->3 3->4, 4->2 -запрещенный путь
                        r[0] = tmpJ;                                    //потому что в таком случае мы сможем посетить только города 2 3 4
                        tmp.add( r );                                   //и никогда не уедем из них
                    }
                }
            }
        }
        int i=-1;
        while(true) {                                                   //но получается такая фигня, что мы добавляем в tmp
            if(i==tmp.size()-1) break;                                  //лишние переходы, которые цикл не образуют
            i++;                                                        //например 2->3 3->4 4->5, при моем агл добавятся и 4->2 и 5->3
            for (Cell aWay : way) {                                     //но 5->3 быть уже не может, потому что в 3 мы идем из 2
                if (tmp.get( i )[1] == aWay.getJ() &&                   //и эти лишние переходы нужно удалить
                        aWay.isPresent()) {                             //смотрим, если j в найденный переходах присутсвует в
                    tmp.remove( i );                                    //переходах пути то удаляем его
                    i--;
                    break;
                }
            }
        }
        for (Integer[] aTmp : tmp) {
            for (int a = 0; a < graph.length; a++) {                    //порисваиваем бесконечность(исключаем эти пути)
                for (int b = 0; b < graph.length; b++) {
                    if (aTmp[0] == graph[a][0] && aTmp[1] == graph[0][b]) {
                        graph[a][b] = INFINITY;
                        currentGraph = graph;
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

        for(int q =0;q<currentGraph.length;q++){                                //тут мы ищем какую ячейку нужно исключить
            if(currentGraph[q][0]==toWayJ){                                     //из пути. например путь из i->j присутствует,
                iIndexForChanging = q;                                          //значит из j->i быть не должен
            }                                                                   //его нужно удалить. мы ищем позицию этого
            if(currentGraph[0][q]==toWayI){                                     //этого пути в графе
                jIndexForChanging = q;
            }
        }
        if(iIndexForChanging!=-1 && jIndexForChanging!=-1) {                    //если такой путь есть
            currentGraph[iIndexForChanging][jIndexForChanging] = INFINITY;      //удаляем его
        }
        a=0;
        for(int k=0;k<currentGraph.length;k++){                                 //присваиваем новому графу текущий
            b=0;                                                                //но без строчки где город toWayI
            if(k!=i) {                                                          //и без столбца где город toWayJ
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
        if(toWayIJ[0]!=0||toWayIJ[1]!=0)currWay.add(new Cell(toWayIJ[0],toWayIJ[1],isPresent));
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
    вычитаем все минимальные значения из каждой строчки
     */
    private void deductValuesFromRows(){
        int[] mins  = findMinimumsByRows();                         //находим позиции минимальных значений по строчкам
        double[] minValues = new double[mins.length];
        for(int i=from;i<minValues.length+1;i++){
            minValues[i-1]=currentGraph[i][mins[i-1]];              //находим минимальные значения в каждой строчке
        }
        for(int i=from;i<currentGraph.length;i++){
            for(int j=from;j<currentGraph.length;j++) {
                currentGraph[i][j] -= minValues[i-1];               //вычитаем соответствующие минимумы из каждой строчки
            }
        }
    }

    //_____________________________________________________________________________________
    /*
     вычитаем все минимальные значения из каждого столбца
     */
    private void deductValuesFromColumns(){
        int[] mins  = findMinimumsByColumns();                         //находим позиции минимальных значений по столбцам
        double[] minValues = new double[mins.length];
        for(int i=from;i<minValues.length+1;i++){
            minValues[i-1]=currentGraph[mins[i-1]][i];                 //находим минимальные значения в каждом столбце
        }
        for(int i=from;i<currentGraph.length;i++){
            for(int j=from;j<currentGraph.length;j++) {
                currentGraph[j][i] -= minValues[i-1];                  //вычитаем соответствующие минимумы из каждого столбца
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

        for (int i = 0; i < zeroPositions.size(); i++) {

            minValueTmp = INFINITY;

            for (int j = from; j < currentGraph.length; j++) {                                  //находим в каком столбце позиция
                if (j != zeroPositions.get( i )[0][1]) {                                        //минимума для i-того нуля
                    if (currentGraph[zeroPositions.get( i )[0][0]][j] <
                            minValueTmp) {
                        minColumnPosition = j;
                        minValueTmp = currentGraph[zeroPositions.get( i )[0][0]][minColumnPosition];
                    }
                }
            }

            minValueTmp = INFINITY;

            for (int j = from; j < currentGraph.length; j++) {                                 //находим в какой строке позиция
                if (j != zeroPositions.get( i )[0][0]) {                                       //минимума для i-того нуля
                    if (currentGraph[j][zeroPositions.get( i )[0][1]] <
                            minValueTmp) {
                        minRowPosition = j;
                        minValueTmp = currentGraph[minRowPosition][zeroPositions.get( i )[0][1]];
                    }
                }
            }

            minPositions[2 * i][0] = minRowPosition;                                        //в один массив записываем и позицию
            minPositions[2 * i][1] = zeroPositions.get( i )[0][1];                          //минимума по строке и по столбцу

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
        int[][] minPositions = getMinPositions( zeroPositions );                    //находим минималные позиции
                                                                                    //для каждого нуля
        //ищем позицию нуля, в котором сумма минимум по строке
        //и столбцу даст максимальный результат
        int[][] cellForBoundary = new int[1][2];
        double tmp;
        double max =-1;
        for(int i=0;i<zeroPositions.size();i++){
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
        printGraph( graph );
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
        String str="";
        System.out.println("GRAPH");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for(int r=0;r<graph.length;r++){
            for(int p=0;p<graph.length;p++){
                if(graph[r][p]>100000000) {
                    str = "inf";
                }
                else {
                    str = String.valueOf(Double.valueOf(graph[r][p]).intValue());
                }
                System.out.printf(" %4s   |",str);
            }
            System.out.println();
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }
    //_____________________________________________________________________________________

}


