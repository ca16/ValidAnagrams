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


Note: the order that the anagrams of the given words appear in may be different depending on which algorithm is chosen for finding anagrams.

----

### To run:

1. Clone the repository.

1. Using the terminal, navigate to the src folder.

1. run 'javac vaf/*.java'

1. run 'java vaf/ProgramStartVAF'

1. The program itself will ask you for input, and give you the instructions you need. For an outline, see section 'While it's running below'. 

----

### A little on the strategy

----

There were two main problems to tackle: finding possible anagrams for words, and figuring out which of those anagrams appear in the dictionary file.

##### Finding anagrams

I considered two options for finding anagrams.

The first was constructing a graph out of the word, and using a search to find anagrams.

Each letter is a node, and there are edges between all nodes (any letter from a word can follow any other letter from that word in an anagram). Moving from one
node to another represents adding the letter corresponding to the second node to the substring ending in the letter corresponding to the first.

The search is similar to depth-first search, only instead of adding nodes to a visited set, and not visiting them again if I've ever seen them before, I
visit all the neighbors of a node, unless they are on the path I've taken to get there (visiting them even then would be the equivalent of using a letter more
times than it appears in a word, e.g. making "lada" from "lad", and would mean the search would never end)*.
Each neighbor of a given node that I visit gets it's own copy of the path taken to get to its parent that it can add itself to. This allows me to differentiate
between the different orders in which I can visit all the nodes, and allows me to reconstruct the word associated with a given order.
I collect the result from starting this search at every node in the graph, to ensure that I find anagrams starting with any letter in the word. When I accumulate
the results, I make sure not to add an anagram to the final list if it is already there.

*To make this more efficient, instead of passing the search along to all of a node's neighbors, I only do so when the path ending in the current node corresponds
to a string that is the prefix of some word in the dictionary (which I can check using the trie structure that keeps track of the words in the dictionary list).


The second option I considered was about building on all the anagrams of a substring of a given word to construct anagrams of the full word.

For example, suppose one word is given: 'gee'.

Step 1: Substring of length 0 = {""}.

Step 2: Using the first character of the word, make substrings of length 1 from substrings of length 0: {"g"}.

Step 3: Using the next character of the word, make substrings of length 2 from substrings of length 1: {"eg, "ge"}.

Step 4: Using the next character of the word, make substrings of length 3 from substrings of length 2: {"eeg", "eeg", "ege", "ege", "gee", "gee"}

Step 5: We're out of characters, we have our permutations.

I added a couple of checks to try to make the algorithm more efficient.

The first: try to cut down on duplicate anagrams. For example "gee" has two 'e's. So the list of anagrams include the same word multiple times
to account for different arrangements within the same letters. Instead of always inserting a letter in every possible position in a string, I only insert it
before a letter that is identical to it. So, if I'm looking for places to build on the subtring "eg" by inserting the letter "e", I'll add "e" to the front of
"eg", resulting in "eeg", but I will not also add a second "eeg" (where I insert the "e" between the two letters). Instead, I move straight to adding the "e"
after the "g", resulting in "ege". This means that the list of anagrams becomes:  {"eeg", "ege", "ege", "gee"}. Unfortunately, this strategy doesn't help when
the identical letters are seperated by another letter (so "ege" still appears in the list twice.).

The second: when we get to inserting the final letter, we know there is no need to consider anagrams where the substring before the letter we're about to insert
is not the prefix of a word (which we can check using the trie structure in charge of keeping track of dictionary entries - see the searching the dictionary file
below). For example, if we were using the word "crack" and were about to use the letter 'k' to make five-letter anagrams based on four-letter anagrams, we know
there is no point in adding 'k' after any of the letters after the second 'c' in 'ccra', because no words in our dictionary start with 'ccr'.
How usful this is also depends on which order the letters are inserted in (it seems more likely to get to substrings that are not prefixes if all the consonants
are bunched up at the front of the substring - e.g. "snck" from "snack", instead of "sank"). For this reason a reorder the word before I start looking for anagrams:
consonants to the front, vowels to the back.



##### Searching the dictionary file

----

### Some Notes:
Default Dictionary File Found at:
http://www-01.sil.org/linguistics/wordlists/english/

Note: I added the word 'I' to it.

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
