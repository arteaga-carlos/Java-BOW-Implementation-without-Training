package SimilarityFinder;

public class SimilarVectors implements Comparable<SimilarVectors> {

    WordsVector w;
    double cosSim;

    public SimilarVectors(WordsVector w, double cosSim){

        this.w = w;
        this.cosSim = cosSim;
    }

    @Override
    public int compareTo(SimilarVectors similarVectors) {

        if(this.cosSim > similarVectors.cosSim)
            return -1;

        else if(similarVectors.cosSim > this.cosSim)
            return 1;

        return 0;
    }
}
