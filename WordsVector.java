package SimilarityFinder;

import java.util.ArrayList;
import SimilarityFinder.FirstClass.*;





// Container of ticket key and words vector list
public class WordsVector implements Comparable<WordsVector> {

    private ArrayList<Integer> vector = new ArrayList<Integer>();
    private String key;
    private int vectorSum;


    public WordsVector(String key, ArrayList<Integer> vector){

        this.key = key;
        this.vector = vector;

        this.calcVectorSum();
    }


    public double calcCosSim(WordsVector v2){

        double dotProd = 0;
        double v1Norm = 0;
        double v2Norm = 0;

        int value1 = 0;
        int value2 = 0;


        // iterate through each word vector value
        for ( int i = 0; i < this.vector.size(); i++ ){

            value1 = this.vector.get(i);
            value2 = v2.vector.get(i);

            dotProd += value1 * value2;

            v1Norm += Math.pow(value1, 2);
            v2Norm += Math.pow(value2, 2);
        }


        v1Norm = Math.sqrt(v1Norm);
        v2Norm = Math.sqrt(v2Norm);

        // Check for division by 0 before returning calculation
        if ( (v1Norm * v2Norm) == 0 ){

            return 0;
        }

        return ( dotProd / (v1Norm * v2Norm) );
    }


    @Override
    public int compareTo(WordsVector w) {

        // ascending order
        return this.vectorSum - w.getVectorSum();
    }



    private void calcVectorSum(){

        // add up each vector. sum will be used for sorting
        for (int i : this.vector){

            this.vectorSum += i;
        }
    }


    @Override
    public String toString() {

        String w = this.key + " | ";

        for ( Integer i : this.vector ){

            w =  w + i + ",";
        }

        return w;
    }



    public ArrayList<Integer> getVector() {
        return vector;
    }

    public String getKey() {
        return key;
    }

    public int getVectorSum() {
        return vectorSum;
    }
}
