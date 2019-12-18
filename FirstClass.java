package SimilarityFinder;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.*;
import java.lang.Math;
import SimilarityFinder.Dict;
import static java.nio.file.Files.*;


/*
    Explanation
    ______________________________________________________________________
    This project uses a bag of words model to find similar blocks of text.
    The similarity is based on the 'cosine similarity' of word vectors.
    These vectors are made from word counts based on a main dictionary.

    Create test files for dictionary and data, then tet the directories below
    ______________________________________________________________________
*/



public class FirstClass {


    public static void main(String[] args){

        // load dictionary and data

        Dict dictionary = new Dict();
        dictionary.setDictionaryDir("C:\\Users\\Documents\\dictionary.csv");
        dictionary.setDataDir("C:\\Users\\Documents\\data.csv");
        dictionary.loadDictionary();
        dictionary.loadData();

        // get paragraph
        String[] testParagraph = new String[] {"test", "paragraph", "for", "comparison"};

        // find most similar vector
         dictionary.findSimilar(testParagraph);

         // for creating a file of vectorized data
        //writeVectors("_path", dictionary.getDataVectorizedList());
    }




    public static void writeVectors(String outputFile, ArrayList<WordsVector> dataVectorizedList){

        // list containing comma separated vector string
        ArrayList<String> rows = new ArrayList<String>();

        for (WordsVector w : dataVectorizedList ) {

            String str = w.getKey() + ",";

            for ( Integer i : w.getVector() ){

                str += i + ",";
            }

            rows.add(str);
        }

        writeToFile(outputFile, rows);
    }


    // csv file row of words
    static class Row {

        private String[] words;


        public Row(String[] words) {

            this.words = words;
        }



        @Override
        public String toString() {

            String w = "";

            for (String word : this.words){

                w = w + word.trim() + ",";
            }

            return w;
        }

        public String[] getWords(){

            return this.words;
        }
    }


    public static List<Row> readRowFromCSV(String fileName) {

        List<Row> rows = new ArrayList<Row>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] words = line.split(",");

                // row contains an array of words
                Row row = createRow(words);

                // adding book into ArrayList
                rows.add(row);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return rows;
    }


    public static Row createRow(String[] words) {

        // create and return row containing string array
        return new Row(words);
    }


    public static void writeToFile(String outputFile, ArrayList<String> data){

        Writer writer = null;

        try {

            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "utf-8"));

            for ( String row : data ){

                writer.write(row + "\n");
            }

        } catch (IOException ex) {

            System.out.print(ex.getMessage());

        } finally {

            try {writer.close();} catch (Exception ex) {System.out.print(ex.getMessage());}
        }
    }
}