# ValidAnagrams

A program that takes one or more words as input, and prints the anagrams of those words that appear in a dictionary file. The user can provide their own dictionary file if they wish. 

For example, if the dictionary file contains all English words:

Given


    snag three pods


prints


    For word: snag
  
    nags
  
    sang
  
    snag
  
  
    For word: three
  
    ether
  
    there
  
    three
  
  
    For word: pods
  
    pods


Note: the order that the anagrams of the given words appear in may be different depending on which algorithm is chosen for finding anagrams (see 'on the strategy' section below).

----

### To run:

1. Clone the repository into some location A.

1. Using the command line, navigate to A.

1. do 'cd ValidAnagrams/src/main/java'
       
       (navigate to the java folder under main under src)

1. do 'javac vaf/*.java'

       (compiles the program)

1. do 'java vaf/ProgramStartVAF'

       (runs the program)

1. The program itself will ask you for input, and give you the instructions you need. For an outline, see the section 'While it's running' below. 

Note: vaf as in 'valid anagram finder'.

On steps 3-5: I've tested these instructions using Mac OS X and Linux. If you're on a Windows machine, as far as I can tell instances of '/' should be switched to '\'. If you're using something else I suggest trying to apply the steps you usually follow for compiling and running a java program.

----

### On the Javadocs

If you'd like to take a look at the javadocs for my classes, the easiest way is probably to navigate to the javadocs folder in the project you just cloned (ValidAnagrams/javadocs/), and open up index.html in a browser.

----

### On the Tests

I have included JUnit tests for my project (ValidAnagrams/src/tests). Looking through them will probably give you an idea of what kind of cases I looked at and how I tested them. If you'd like to run the tests, I recommend using an IDE (I used IntelliJ).

----

### On the Structure

The program starts at the ProgramStartVAF class, which sets things in motion:

An InteractionHandler is created. It navigates the communication between program and user. 

When the user provides input words, the InteractionHandler creates an InputWordListProcessor.

The InputWordListProcessor brings together the behaviour of the IAnagramMaker (that finds potential anagrams) and the Trie (which checks if stings were in the provided dictionary file), to produce the required output.

The DictProcessor is in charge of processing the dictionary file being used. 

Note: two classes implement IAnagramMaker - GraphAnagramMaker and IterAnagramMaker. See section 'on the strategy' below for more information on anagram finding strategies.

I've added a class diagram (ValidAnagrams/VAFClassDiagram.png) to the project. The green star marks where the program starts. 

----

### On the Strategy

----

There were two main problems to tackle: finding potential anagrams for a given word, and figuring out which of those anagrams appear in the dictionary file.

##### Finding anagrams

I considered two options for finding anagrams.

##### Option 1 - Graph based

The first was constructing a graph out of the word, and using a search to find anagrams.

Each letter is a node, and there are edges between all nodes (any letter from a word can follow any other letter from that word in an anagram). Moving from one node to another represents adding the letter corresponding to the second node to the substring ending in the letter corresponding to the first.

The search is similar to depth-first search. However, instead of adding nodes to a visited set, and not visiting them again if they've ever been visited before, all neighbors of a node are visited, unless they are on the path that was taken to get there (visiting them even then would be the equivalent of using a letter more times than it appears in a word, e.g. making "lada" from "lad", and would mean the search would never end).*

Each neighbor of a node that is visited gets its own copy of the path taken to get to its parent that it can add itself to. This allows the program to differentiate between the different orders in which all the nodes can be visited (i.e. the various letter permutations possible), and allows me to reconstruct the word associated with a given order.

The results from starting this search at every node in the graph are collected, to ensure that anagrams starting with any letter in the word are found. When the results are accumulated, a given permutation is not added to the final list if it is already there.

*To make this more efficient, the search is not always passed along to all of a node's neighbors. This only happens when the path ending at the current node corresponds to a string that is the prefix of some word in the dictionary (which can be checked using the trie structure that keeps track of the words in the dictionary list - see the 'searching the dictionary file' section below). For example, if my path so far is {g,r,z} there is no need to build the path further by exploring z's neighbors (assuming no words in the dictionary start with "grz"), so the program can return to searching 'r''s other neighbors, and building paths starting with "gr".

##### Option 2

The second option I considered was about building on all the letter pemutations of a substring of the given word to construct potential anagrams of the full word.

I ended up favoring the graph option above instead. Especially for larger words, using the graph-based search described above is much faster than the option described briefly below. If you're curious, you can choose to have the program conduct its search using this algorithm instead: after asking you to provide a list of words, it will ask you whether you want to change from the default search algorithm (graph-based), to the other option (a more iterative version). If you respond 'yes' or 'y', it will honor your wishes. To really see the difference, input an eight-letter word first (e.g. "throwing"), have the program run the search, and then input a nine-letter word (e.g. "throbbing"). The graph-based algorithm finishes almost immediately in both cases. The other option takes a moment for the eight-letter word, and takes much longer for the nine-letter word.

A brief description of the second option:

It is probably best understood through an example. Suppose the word "gee"is given:

1. Substring of length 0 = {""}.

1. Using the first character of the word, make substrings of length 1 from substrings of length 0: {"g"}.

1. Using the next character of the word, make substrings of length 2 from substrings of length 1: {"eg, "ge"} - insert 'e' before and after 'g'.

1. Using the next character of the word, make substrings of length 3 from substrings of length 2: {"eeg", "eeg", "ege", "ege", "gee", "gee"} - insert 'e' before the first character of the first string, between the two characters and after the two characters, do the same for the second string.

1. We're out of characters, we have our permutations.

I added a few more steps to try to make the algorithm more efficient. One dealt with limiting duplicates by not inserting a letter both before and after a letter which is identical to it. For example above, going from step 3 to step 4, only one "eeg" would be produced from "eg" and 'e'.

The other, to some extent, prevents the creation of unusable letter permutations when the final letter is being inserted into the substrings of smaller length: it stops building permutations when the substring before the letter to be inserted is not the prefix of a word in the dictionary. For example given the word "crack", suppose we have the four-letter substring "rkac" and are about to insert 'c' into various positions in it. There is no point in inserting 'c' after any of the letters after the 'r' in "rkac" (assuming no words in the dictionary start with "rk").

The final step attempts to amplify the effects of the second step by re-ordering the letters of the given word to bring the consonants to the front and the vowels to the back before permutations start being built. It seems more likely to get to substrings that are not prefixes if all the consonants are bunched up at the front of the substring (e.g. "snck" from "snack", instead of "sank"), so the program tries to insert the vowels last.

##### Searching the dictionary file

My version of this program allows the user to ask for anagrams of words that can be found in a dictionary file repeatedly. For this reason, it seemed like it might be worth doing a little pre-processing of the file to make finding out whether a word is in it reasonably fast. 

The program puts the dictionary file (the default file or the file provided by the user) into a trie. Putting a single word into the trie takes time relative to the length of the word (e.g. for "slam", the program would go down four levels, for "consciousness", 13). This happens for every word in the file. 

Once the trie contains the words in the file, the program can find out whether a certain word is in the file in time also relative to the length of the the word.

An alternative could be to handle the file only when the program needs to check if a word is in it. No pre-processing costs here, but searching the file for each word seems inefficient - it seems as though the program could end up searching through most of the file for each permutation of each word's letters (though it may not be that bad if the program is only used to find anagrams for one or two short words). 

An option between the two could be to read the file once and keep the words in a list in sorted order. Figuring out whether a word is in that list would take O(lgn) time, where n is the number of words in the dictionary (binary search). 

Creating the trie takes a little longer than creating the list (assuming the words in the file are sorted), but this could be worth it considering that it is quite a bit faster to figure out if a word is in the file, especially for shorter words.

Or the program could use a hashset instead. If the word is in the hashset, it's in the dictionary file. This would take about the same amount to create as the list but figuring out if a word is in the set would take constant time. 

So a hashset seems like the most efficient structure to use to figure out if words are in the file. 

However, a trie is also extremely helpful because it allows the program to figure out whether a string is the prefix of another word in the trie quickly (in time relative to the length of the prefix). This helps significantly with searching, especially using the graph-based option described above. The longer the words given as input, the more important it is to eliminate possibilities that won't work.* The value of finding out whether a string is also the prefix of another word can be seen in comparing the behavior of the graph-based search and the more iterative version for a single nine-letter word input. The latter takes noticeably longer than the former. The difference is that the former can continuously take advantage of the trie's ability to confirm whether a string is a prefix, whereas the latter can only use it in some cases as it's building its final letter permutations. 

*An example: consider the word 'misfortune'. There are 10! ways to order its letters (10 characters, no repeating letters). Suppose the path so far is {n,f} and that our dictionary contains no words beginning with "nf". If we can take advantage of figuring out whether a string is a prefix, this means we can ignore 8! possible anagrams. This is just one elimination. We can also probably eliminate 5 x 7! possibilities (nothing starts with any arrangement of the three letter 'm', 'n', 'f', but 'nfm' was covered by "nf" previously) as well as other possibilities. 

A hashset cannot really tell the program if a string is the prefix of any other string, (unless it goes through every single element in the set which is much more inefficient than the trie strategy). 

So even though a hashset may be faster for one role (is a certain word in the dictionary?), the trie does not perform badly in that same role, and offers quite an advantage in the other main role of the program (finding potential anagrams). This is why I chose it. 


----

### While it's running:

An outline of how it should go:

1. The program asks you if you would like to use your own dictionary file.

  1. Scenario 1 - You want to use your own dictionary file.

    1. Input 'y' or 'yes'.

    1. The program asks you for the path to that dictionary file.

    1. Input the path to it. e.g.:

           ../../tests/resources/TestSmallDict.txt

  1. Scenario 2 - You don't want to use your own dictionary file.

    1. Input 'n' or 'no'.

1. The program asks you to enter the word or words you want to find anagrams of (or quit).

1. Assuming you don't want to quit just yet, input one or more words, separating them by spaces, e.g.

       snag three pods

1. The program asks you if you would like to stick to the default algorithm used to find anagrams (a graph based search), or switch to the other option (a more iterative version). Note: the default is recommended for longer words (~ 9+ characters).

  1. Scenario 1 - You want to switch to the more iterative search option.

    1. Input 'y' or 'yes'.

  1. Scenario 2 - You want to stick to the graph based search option.

    1. Input 'n' or 'no'.

1. The program prints out the anagrams of the given words that are in the dictionary file.

1. Steps 2 - 5 repeat until you enter 'Quit!' after being prompted for the words you want to find anagrams of. e.g.


       PROGRAM: "Please enter the word or words you'd like to find anagrams of. Otherwise, to quit please enter 'Quit!'"

       USER: Quit!
       

----

### Some Notes:
Default Dictionary File Found at:
http://www-01.sil.org/linguistics/wordlists/english/

Note: I added the word 'I' to it.
