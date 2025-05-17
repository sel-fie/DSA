import java.util.*;
import java.lang.*;

public class A3Q3 {
    public static double arduino(double[][] locations) {
        // use Prim's algorthim
        // start at a node and calculate distance to every node, choose the shortest one, then chose that one
        // keep track of which node's have already been hit

        // locations store the x,y value of each node
        // so calculate distance to every other node
        // choose shortest
        // mark node as visited
        double wire = 0.0;
        boolean[] visited = new boolean[locations.length];
        double[] length = new double[locations.length]; // keeps track of the shortest length
        Arrays.fill(length, Double.MAX_VALUE);
        length[0] = 0.0;

        double[][] cache = new double[locations.length][locations.length];
        for (int i = 0; i < locations.length; i++) {
            double x1 = locations[i][0];
            double y1 = locations[i][1];
            for (int j = 0; j < locations.length; j++) {
                double x = x1 - locations[j][0];
                double y = y1 - locations[j][1];
                cache[i][j] = x * x + y * y;
            }
        }
        int count = 0;
        while (count < locations.length) {
            int node = -1;
            double smallest = Double.MAX_VALUE;
            // compute all distances and store them in hashmap
            // d = √((x2 - x1)² + (y2 - y1)²)
            for (int j = 0; j < locations.length; j++) {
                if(!visited[j] && length[j] < smallest){
                    smallest = length[j];
                    node = j;
                }
            }
            if (node == -1) break;
            visited[node] = true;
            wire += Math.sqrt(length[node]);
            count ++;
            // update neighbours
            for (int n = 0; n < locations.length; n++){
                if(!visited[n] && cache[node][n] < length[n]){
                    length[n] = cache[node][n];
                }
            }
//            for (int i = 0; i < locations.length; i++) {
//                //visited.add(locations[i]); // mark as visited
//
//                // Add the length to a number to keep track of how long the shortest length is
//                // go to next node
//                // if edge goes to already visited, skip to next
//            }
        }
        //wire = Math.floor(wire * 100) / 100;
        return wire;
    }

            public static void main(String[] args) {
                // Test case 1: Small triangle (from your example)
                double[][] testCase1 = {
                        {1.0, 1.0},
                        {2.0, 2.0},
                        {2.0, 4.0}
                };

                // Test case 2: Square shape
                double[][] testCase2 = {
                        {0.0, 0.0},
                        {1.0, 0.0},
                        {1.0, 1.0},
                        {0.0, 1.0}
                };

                // Test case 3: Random points
                double[][] testCase3 = {
                        {1.2, 3.4},
                        {5.6, 7.8},
                        {9.0, 1.2},
                        {3.4, 5.6},
                        {7.8, 9.0}
                };

                // Test case 4: Straight line
                double[][] testCase4 = {
                        {0.0, 0.0},
                        {1.0, 0.0},
                        {2.0, 0.0},
                        {3.0, 0.0},
                        {4.0, 0.0}
                };

                //arduino(locations);
                System.out.println(arduino(testCase1));
                System.out.println(arduino(testCase2));
                System.out.println(arduino(testCase3));
                System.out.println(arduino(testCase4));

                //Test Case 1 (Triangle) MST Weight: 3.414213562373095
                //Test Case 2 (Square) MST Weight: 3.0
                //Test Case 3 (Random Points) MST Weight: 16.79285562374667
                //Test Case 4 (Straight Line) MST Weight: 4.0

            }
        }
