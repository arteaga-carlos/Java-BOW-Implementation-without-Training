# Java-BOW-Implementation-Without-Training

Summary
-----------------------
Bag of Words model implementation in Java that doesn't require training. Uses cosine similarity as the measurement.
The purpose of this program is to link blocks of text that are similar in context.
This allows for an easy way to search knowledge bases without manual search!

For example:
  Given the text "the quick brown fox jumps over the lazy dog", the program finds similar blocks of text given a dictionary. A similar text would be "the dog and fox are animals". 
  

Implementation details:
-----------------------
This project uses a bag of words model to find similar blocks of text.
The similarity is based on the 'cosine similarity' of word vectors.
These vectors are made from word counts based on a main dictionary.


Usage
-----------------------
Create a csv file of unique dictionary words.
Create a csv file of data paragraphs. 
    Each file needs a key column.

then simply run: dictionary.findSimilar("paragraph needing a match");
