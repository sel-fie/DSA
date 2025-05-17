import java.util.*;
public class A3Q2 {
    // find a cycle between the cities list, if no cycle return fail at the city that fails it
    // strongly connected components, DAGS, and DFS
    public static String[] time_pass(String[][] itinerary, String[] cities) {
        String[] answer = new String[cities.length];
        // for every unique index in itinerary, that's a vertex in the graph
        HashMap<String, LinkedList<String>> graph = new HashMap<>(); // key is the nodes, values are the connections
        // adjacency list

        HashMap<String, Boolean> visited = new HashMap<>(); // whether we've already visited that node
        HashMap<String, Boolean> check = new HashMap<>(); // checking for cycles

        for (int i = 0; i < itinerary.length; i++) { // for loop that builds the graph
            if (!graph.containsKey(itinerary[i][0])) {
                LinkedList<String> connections = new LinkedList<>();
                connections.add(itinerary[i][1]);
                graph.put(itinerary[i][0], connections);

            } else {
                graph.get(itinerary[i][0]).add(itinerary[i][1]);
            }
        } // builds graph using hashmap of linked lists
        // this is disgusting


        for(int i = 0; i < cities.length; i++){ // performs DFS and pulls results
            if (!check.containsKey(cities[i])){
                boolean result = DFS(graph, visited, check, cities[i]); //looks for cycles if we haven't checked that city yet
                check.put(cities[i], result);
            }
            if(check.get(cities[i])){ //otherwise if we've checked the city before, we return the result
                answer[i] = "succeed";
            }
            else {
                answer[i] = "failed";
            }

        }
        return answer;
    }

    public static boolean DFS (HashMap<String, LinkedList<String>> graph, HashMap<String,Boolean> visited, HashMap<String,Boolean> check, String city){
        // Uses DFS variant to search for cycles
        if(check.containsKey(city)){ // if we've already checked this city
            return check.get(city);
        }
        if(visited.containsKey(city)){ // if we've visited this city, then there must be a way to reach it
            return true;
        }
        if(!graph.containsKey(city) || graph.get(city).isEmpty()){
            return false; // no outgoing edges, so can never reach a cycle -> dead end
        }
        visited.put(city, true); // visit the city
        for(String out : graph.get(city)){ // for every one of it's edges
            if(DFS(graph, visited, check, out)){ // go through and look for a cycle, if there is one
                check.put(city, true); //register it as true for a cycle
                return true; // then return true
            }
        }
        visited.remove(city); // then remove the city
        check.put(city, false); // put it as false since there is no cycle
        return false; // and return false
    }
}