import java.util.*;


// Collaborators Brian (TA)


public class A1_Q3 {
    // return a sorted list of keywords from most to least used
    // MUST BE USED BY EVERY USER
    // aka must show up in every string in posts[]
    // if frequency tie, sort alphabetically

    // use a hashmap? -> recommended
	public static ArrayList<String> Discussion_Board(String[] posts){
        // use username as key?
        // count the number of times the word is repeated
        ArrayList<String> myList = new ArrayList<>();
        HashMap<String, Integer> wordsRepeated = new HashMap<>(); // for repeated words --> counts them
        HashMap<String, HashSet<String>> users = new HashMap<>(); // use words are the key and then change the HashSet to the user
        HashSet<String> userTotal = new HashSet<>();


        // hopefully correct logic
        for (String post : posts) {
            String[] split = post.split(" ");
            // this is for when you have to check that the word was said by everyone
            userTotal.add(split[0]); // totals all usernames ---> no duplicates
            for (int j = 0; j < split.length; j++) {
                if (j != 0) {
                    // adds words as the key and list of usernames as the values
                    // this will make it easier when i have to check if every user has said the word
                    if(users.containsKey(split[j])){
                        HashSet<String> usernames = users.get(split[j]);
                        usernames.add(split[0]);
                        users.replace(split[j], usernames);
                    }
                    else{
                        HashSet<String> usernames = new HashSet<>();
                        usernames.add(split[0]);
                        users.put(split[j], usernames);
                    }
                    // changes word frequency
                    if (wordsRepeated.containsKey(split[j])) {
                        wordsRepeated.replace(split[j], wordsRepeated.get(split[j]) + 1);
                    }
                    else{
                        wordsRepeated.put(split[j], 1);
                    }
                }
            }
        }
        // need to check that the word was said by every user
        for(String key : users.keySet()){
            if (users.get(key).size() != userTotal.size()){
                wordsRepeated.remove(key);
            }
        }


        // this sorts them correctly --> using slowSort method i programmed last year
        // most likely extremely inefficient --- O(n^2)
        myList.addAll(wordsRepeated.keySet());
        int N = myList.size();
        for(int i=0; i<N-1; i++){
            for(int j=0; j<N-i-1; j++){
                if(wordsRepeated.get(myList.get(j)).compareTo(wordsRepeated.get(myList.get(j+1))) < 0){
                    String temp = myList.get(j);
                    myList.set(j, myList.get(j+1));
                    myList.set(j+1, temp);
                }
            }
        }
        
/*
                // HOW DO I IMPLEMENT THIS??
                class sorting implements Comparator<String, Integer>{
                    @Override
                    public int compare(String o1, String o2) {
                        return 0;
                    }
                }
                // need to sort by frequency --> sort the keys by their value --> HOW?????
                // do i use a priority queue? Comparator.sort? HeapSort?

                PriorityQueue<String> sorted = new PriorityQueue<>();
                sorted.addAll(wordsRepeated.keySet());
                // this just sorts them alphabetically...

*/
        
        // return list
        return myList;
	}
// will be graded on correctness and efficiency
    // use proper algorithms and data structures
}
