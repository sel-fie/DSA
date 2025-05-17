// collaborators : Alexia (TA)
import java.io.*;
import java.util.*;

public class Open_Addressing {
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {

         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
         }
        else{
            this.A = A;
        }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }
         
     }
     
                 /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
        /**Implements the hash function g(k)*/
        public int probe(int key, int i) {
            //TODO: implement this function and change the return statement.
            // g(k,i) = (h(k) +i) mod 2^r
            int h = (A * key);
            h = h % power2(w);
            int minus = (w-r);
            h= h/power2(minus);
            int end = h + i;
            end = end % power2(r);
        return end;
     }
     
     
     /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int insertKey(int key){
            //TODO : implement this and change the return statement.
            int i = 0;
            int place = probe(key,i);
            while(true){
                // change to less than 0 instead of == -1, so that it accounts for the removekey error
                if(Table[place] < 0){
                    Table[place] = key;
                    break;
                }
                else{
                    i++;
                    place = probe(key,i);
                }
            }
            return i;
        }
        
        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public int insertKeyArray (int[] keyArray){
            int collision = 0;
            for (int key: keyArray) {
                collision += insertKey(key);
            }
            return collision;
        }
            
         /**Removes key k from the hash table. Returns the number of collisions encountered*/
        public int removeKey(int key){
            //TODO: implement this and change the return statement
            // count the slots where you've deleted something - using a different value
            int i = 0;
            int place = probe(key, i);

            while (true) {
                // if key is found, delete and return how many collisions it took
                if (Table[place] == key) {
                    Table[place] = -2;
                    break;
                    // if it goes through the entire table, break
                } else if(i == m) {
                        break;
                    // if the spot it's supposed to be is empty, return how many collisions it took to get there
                } else if (Table[place] == -1) {
                    i++;
                    break;
                    // and if neither are true, aka there's something where it's supposed to be --> add one to i and recalculate the posiition
                } else {
                    i++;
                    place = probe(key, i);
                }
            }
            return i;
        }
}
