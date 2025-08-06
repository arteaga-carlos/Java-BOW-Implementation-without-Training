package SimilarityFinder;
import java.util.*;
import static SimilarityFinder.FirstClass.*;


/*

    Explanation for function: searchVectorList
    __________________________________________

    This function will search for a vector with the highest cosine similarity to our vector of choice.
    Vectors are sorted by the sum of their entries.
    searchVectorList will split the sorted list in halves, and find the cosine similarity of the halves's mid values.
    This process will iterate until the list cannot be partitioned any longer.
    Once this point is reached, all remaining vectors will have their similarity calculated and sorted by it,
    with the highest similarity at index 0.

*/

// main dictionary of words
// BAG of Words
public class Dict {

    private List<Row> dataRows;
    private List dictionaryWords;

    private ArrayList<WordsVector> dataVectorizedList;

    private String dictionaryDir;
    private String dataDir;


    public Dict(){

        dataVectorizedList = new ArrayList<WordsVector>();
    }


    public void findSimilar(String[] paragraph){

        WordsVector wordsVector = this.vectorize(new Row(paragraph));

        // sort vectorized list by sum of vector entries
        Collections.sort(this.dataVectorizedList);

        System.out.println(this.searchVectorList(wordsVector, this.dataVectorizedList, null, null));
    }



    private WordsVector searchVectorList(WordsVector w, ArrayList<WordsVector> vectorList, Indices indices, WordsVector winningVector){

        if ( indices != null && indices.listTop < 2 ) return winningVector;


        int listHalf1Bottom = 0;
        int listHalf1Top    = 0;
        int listHalf1Mid    = 0;

        int listHalf2Bottom = 0;
        int listHalf2Top    = 0;
        int listHalf2Mid    = 0;


        // first run. indices not set
        if ( indices == null ){

            listHalf1Bottom = 0;
            listHalf1Top    = (int) Math.ceil(vectorList.size() / 2);
            listHalf1Mid    = (int) Math.ceil(listHalf1Top / 2);

            listHalf2Bottom = listHalf1Top + 1;
            listHalf2Top    = vectorList.size();
            listHalf2Mid    = (int) Math.ceil(listHalf2Bottom + ( (listHalf2Top - listHalf2Bottom) / 2));
        }

        else {

            // bottom is the same
            listHalf1Bottom = indices.listBottom;
            listHalf1Top    = (int) Math.ceil(indices.listBottom + ( (indices.listTop - indices.listBottom) / 2));
            listHalf1Mid    = (int) Math.ceil( listHalf1Bottom + (listHalf1Top - listHalf1Bottom) / 2);

            listHalf2Bottom = listHalf1Top + 1;
            listHalf2Top    = indices.listTop;
            listHalf2Mid    = (int) Math.ceil(listHalf2Bottom + ( (listHalf2Top - listHalf2Bottom) / 2));


        }


        // use indices to find cos sim for mid values
        // list1
        double list1CosSim = w.calcCosSim(vectorList.get(listHalf1Mid));
        // list2
        double list2CosSim = w.calcCosSim(vectorList.get(listHalf2Mid));


        // list1 cos sim is better
        if (list1CosSim > list2CosSim){

            indices = new Indices();

            indices.listBottom  = listHalf1Bottom;
            indices.listMid     = listHalf1Mid;
            indices.listTop     = listHalf1Top;
        }

        else {// list2 cos sim is better

            indices = new Indices();

            indices.listBottom  = listHalf2Bottom;
            indices.listMid     = listHalf2Mid;
            indices.listTop     = listHalf2Top;
        }


        // list cannot be partitioned any further. sort remaining vectors by cos sim
        if ( (indices.listBottom+1) == indices.listMid ){

            ArrayList<SimilarVectors> similarVectorsList = new ArrayList<>();

            for (int i = indices.listBottom; i < indices.listTop; i++ ){

                SimilarVectors similarVectors = new SimilarVectors(vectorList.get(i), w.calcCosSim(vectorList.get(i)));

                similarVectorsList.add(similarVectors);
            }

            Collections.sort(similarVectorsList);


            return similarVectorsList.get(0).w;
        }


        return searchVectorList(w, vectorList, indices, vectorList.get(indices.listMid));
    }



    public void loadDictionary(){

        // Get single row of dictionary words and convert to list
        List<Row> dictRow = readRowFromCSV(this.dictionaryDir);
        Row row = dictRow.get(0);

        this.dictionaryWords = Arrays.asList(row.getWords());
    }




    public void loadData(){

        this.dataRows = readRowFromCSV(this.dataDir);

        for ( Row row : this.dataRows ) {

            this.dataVectorizedList.add(vectorize(row));
        }
    }


    public WordsVector vectorize(Row row){

        ArrayList<Integer> wordFrequencyList;
        wordFrequencyList = new ArrayList<Integer>();

        // Separate Set key and word list
        String[] words = removeKeyFromRow(row);

        // get word frequency in dictionary then put into wordFrequencyList
        for (Object dictWord : this.dictionaryWords) {

            wordFrequencyList.add(Collections.frequency(Arrays.asList(words), dictWord));
        }

        // Store paragraph vector (wordFrequencyList) in list
        return new WordsVector(row.getWords()[0], wordFrequencyList);
    }




    private String[] removeKeyFromRow(Row row){

        String[] words = new String[row.getWords().length];

        // remove first column/item from words array
        for ( int i = 1; i < row.getWords().length; i++ ) {

            words[i-1] = row.getWords()[i];
        }

        return words;
    }



    public String getDictionaryDir() {
        return dictionaryDir;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDictionaryDir(String dictionaryDir) {
        this.dictionaryDir = dictionaryDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    public ArrayList<WordsVector> getDataVectorizedList() {
        return dataVectorizedList;
    }

}
