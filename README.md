# ValidAnagrams

A program that takes one or more words as input, and prints the anagrams of those words that appear in a dictionary file. The user can provide their own dictionary file if they wish. 

For example, if the dictionary file contains all English words:

Given


    snag three pond


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


Note: the order that the anagrams of the given words appear in may be different depending on which algorithm is chosen for finding anagrams (see 'a little on strategy section' below).

----

### To run:

1. Clone the repository.

1. Using the terminal, navigate to the src folder.

1. run 'javac vaf/*.java'

1. run 'java vaf/ProgramStartVAF'

1. The program itself will ask you for input, and give you the instructions you need. For an outline, see section 'While it's running below'. 

Note: vaf as in 'valid anagram finder'.

----

### A little on the strategy

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

*To make this more efficient, the search is not always passed along to all of a node's neighbors. This only happens when the path ending in the current node corresponds to a string that is the prefix of some word in the dictionary (which can be checked using the trie structure that keeps track of the words in the dictionary list - see the 'searching the dictionary file section' below). For example, if my path so far is {g,r,z} there is no need to build the path further by exploring z's neighbors (assuming no words in the dictionary start with "grz"), so the program can return to searching 'r''s other neighbors, and building paths starting with "gr".

##### Option 2

The second option I considered was about building on all the letter pemutations of a substring of the given word to construct potential anagrams of the full word.

I ended up favoring the graph option above instead. Especially for larger words, using the graph-based search described above is much faster than the option described briefly below. If you're curious, you can choose to have the program conduct its search using this algorithm instead: after asking you to provide a list of words, it will ask you whether you want to change from the default search algorithm (graph-based), to the other option (a more iterative version). If you respond 'yes' or 'y', it will honor your wishes. To really see the difference, input a eight-letter word first (e.g. "throwing"), have the program run the search, and then input a nine-letter word (e.g. "throbbing"). The graph-based algorithm finishes almost immediately in both cases. The other option takes a moment for the eight-letter word, and takes much longer for the nine-letter word.

A brief description of the second option:

It is probably best understood through an example. Suppose one word is given: "gee".

1. Substring of length 0 = {""}.

1. Using the first character of the word, make substrings of length 1 from substrings of length 0: {"g"}.

1. Using the next character of the word, make substrings of length 2 from substrings of length 1: {"eg, "ge"} - insert 'e' before and after 'g'.

1. Using the next character of the word, make substrings of length 3 from substrings of length 2: {"eeg", "eeg", "ege", "ege", "gee", "gee"} - insert 'e' before the first character of the first string, between the two characters and after the two characters, do the same for the second string.

1. We're out of characters, we have our permutations.

I added a few more steps to try to make the algorithm more efficient. One dealt with limiting duplicates by not inserting a letter both before and after a letter which is identical to it. For example above, going from step 3 to step 4, only one "eeg" would be produced from "eg" and 'e'.

The other, to some extent, prevents the creation of unusable letter permutations when the final letter is being inserted into the substrngs of smaller length: it stops building permutations when the substring before the letter to be inserted is not the prefix of a word in the dictionary. For example given the word "crack", suppose we have the four-letter substirng "ccra" and are about to insert 'k' into various positions in it. There is no point in inserting 'k' after any of the letters after the second 'c' in "ccra'" (assuming no words in the dictionary start with "ccr").

The final step tried to maximize the effects of the second step by re-ordering the letters of the given word to bring the consonants to the front and the vowels to the back. It seems more likely to get to substrings that are not prefixes if all the consonants are bunched up at the front of the substring - e.g. "snck" from "snack", instead of "sank").

##### Searching the dictionary file


----

### While it's running:

An outline of how it should go:

1. The program asks you if you would like to use your own dictionary file.

  1. Scenario 1 - You want to use your own dictionary file.

    1. Input 'y' or 'yes'.

    1. The program asks you for the path to that dictionary file.

    1. Input the path to it. e.g.:

           ../TestSmallDict.txt

  1. Scenario 2 - You don't want to use your own dictionary file.

    1. Input 'n' or 'no'.

1. The program asks you to enter the word or words you want to find anagrams of (or quit).

1. Assuming you don't want to quit just yet, input one or more words, separating them by spaces, e.g.

       snag three pods

1. The program asks you if you would like to stick to the default algorithm used to find anagrams (it proceeds iteratively), or switch to the other option (using a graph).

  1. Scenario 1 - You want to switch to the graph search option (recommended if any of the words are quite long, where long is about 9 characters and above).

    1. Input 'y' or 'yes'.

  1. Scenario 2 - You want to tick to the iterative search option.

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
