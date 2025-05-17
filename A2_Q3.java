
import java.util.*;

public class A2_Q3 {
    public static String directions(int[] distances) {
        // memoization
        // input smaller arrays of distances
        // if the optimal structure has already been computed for that sub-array, return the optimal structure

        int[][] array = fillArray(distances); //fills the array

        //testing code
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0 ; j < 1001; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
        if (array[distances.length-1][0] == 0 ){ // if there is no solution in the bottom left corner of 2D array
            return "IMPOSSIBLE";
        }
        // for every solution, need to populate with U's and D's
        // how do i find the most optimal solution?
        return findoptimal(array, distances.length - 1, 0 );
    }

    // uses knapsack logic to fill the array
    public static int[][] fillArray(int[] distances){
        int[][] array = new int[distances.length][1001]; // number of row x number of columns
        array[0][distances[0]] = distances[0];

        for (int i = 1; i < distances.length; i++) {
            for (int j = 0; j < 1001; j++) {
                if (array[i - 1][j] != 0) {
                    if(j - distances[i] >= 0) {
                        array[i][j - distances[i]] = distances[i];
                    }
                    if(j + distances[i] < 1001) {
                        array[i][j + distances[i]] = distances[i];
                    }
                }
            }
        }
        return array;
    }

    public static String findoptimal (int [][] array, int row , int column){
        if (row == 0){
            return "U";
        }
        if(array[row][column] != 0 ) {
            int spot = array[row][column];
            if (column - spot >= 0 && array[row - 1][column - spot] != 0) {
                return findoptimal(array, row - 1, column - spot) + "U";
            } else if (column + spot < 1001 && array[row - 1][column + spot] != 0) {
                return findoptimal(array, row - 1, column + spot) + "D";
            }
        }
        return "";
    }
}



