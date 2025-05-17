/****************************
*
* COMP251 template file
*
* Assignment 1, Question 2b
*
*****************************/

/* contructor: creates a partition of n elements. */
/* Each element is in a separate disjoint set */
public class DisjointSetsB {

    private int[] par;
    private int[] rank;

    /* contructor: creates a partition of n elements. */
    /* Each element is in a separate disjoint set */
    DisjointSetsB(int n) {
        if (n>0) {
            par = new int[n];
            rank = new int[n];
            for (int i=0; i<this.par.length; i++) {
                par[i] = i;
            }
        }
    }

    public String toString(){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if (setstrings[pari]==null) {
                setstrings[pari] = String.valueOf(i);
                countsets+=1;
            } else {
                setstrings[pari] += "," + i;
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }

    /* find resentative of element i */
    public int find(int i) {
        if (par[i]==i){
            return i;
        } else {
            par[i] = find(par[i]);
            return par[i];
        }

    }

    /* merge sets containing elements i and j */
    public int union(int i, int j) {
        // remember that when you start, everything is 0
        if(rank[find(j)] < rank[find(i)] ) {
            par[find(j)] = find(i);
            //rank[i]++; // increment both
            //rank[j]++;
            return find(i);
        }
        else if(rank[find(j)] > rank[find(i)]){
            par[find(i)] = find(j);
            //rank[i]++;
            //rank[j]++;
            return find(j);
        }
        else{
            par[find(i)] = find(j);
            //rank[i]++;
            //rank[j]++;
            rank[find(j)] ++;
            return find(j);
        }
    }

    /* move i to the set containing j */
    public void move(int i, int j) {
        // add one to j's rank
        // change i's rank to rank j
        // change the rank of the original set it was in
        // have to change representative of all nodes if the rep is the one that gets moved
        // i thought every node in a tree was supposed to have the same rank
        if(find(i) != find(j)) { // if they're not in the same set, then
            // changes rank for set j and set i
            // if i is the representative
            if(par[i] == i){
                for(int f =0 ; f < par.length; f ++){
                    if((par[f] == i) && (f != i)) {
                        par[f] = f;
                        for(int k = f ; k < par.length; k++){
                            if(par[k] == i){
                                par[k] = f;
                            }
                        }
                        rank[f] = rank[i];
                        rank[f] --;
                        rank[i] = 0;
                        break;
                    }
                }
            }
            // if i is NOT the representative, but still a parent
            else if(par[i] != i){
                for(int f = 0 ; f < par.length; f ++){
                    if((par[f] == i) && (f != i)) {
                        par[f] = par[i];
                    }
                }
                rank[find(i)] --;
            }
            rank[find(j)] ++;
            par[i] = find(j); // change the parent of i to be the representative of j
        }
    }


    /* return the sum of elements in the set of i */
    public int sum_elements(int i) {
        int sum = 0;
        // find every element that have the set as a parent, then add them to an accumulator
        for(int j = 0 ; j < par.length; j++){
            if (find(j) == find(i)){
                sum += j;
            }
        }
        return sum;
    }

    public static void main(String[] args) {

        DisjointSetsB myset = new DisjointSetsB(6);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 1");
        myset.union(2,1);
        System.out.println(myset);
        System.out.println("-> Union 4 and 5");
        myset.union(4,5);
        System.out.println(myset);
        System.out.println("-> Union 3 and 1");
        myset.union(3,1);
        System.out.println(myset);
        System.out.println("-> Union 2 and 4");
        myset.union(2,4);
        System.out.println(myset);

    }

}
