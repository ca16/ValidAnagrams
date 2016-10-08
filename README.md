# ValidAnagrams

A program that takes one or more words, and prints the anagrams of those words that appear in a dictionary file. The user can provide their own dictionary file if they wish. 

----

### To run:

1. Clone the repository.

1. Using the terminal, navigate to the src folder.

1. do 'javac *.java'

1. do 'java ProgramStart'

1. The program itself will ask you for input, and give you the instructions you need. For an outline, see section 'While it's running below'. 

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

    1. ../TestSmallDict.txt

  1. Scenario 2 - You don't want to use your own dictionary file.

    1. Input 'n' or 'no'.

1. The program asks you to enter the word or words you want to find anagrams of (or quit).

1. Assuming you don't want to quit just yet, input one or more words, separating them by spaces, e.g.

  three stab jump

1. The program asks you if you would like to stick to the default algorithm used to find anagrams (it proceeds iteratively), or switch to the other option (using a graph).

  1. Scenario 1 - You want to switch to the graph search option.

    1. Input 'y' or 'yes'.

  1. Scenario 2 - You want to tick to the iterative search option.

    1. Input 'n' or 'no'.

1. The program prints out the anagrams of the given words that are in the dictionary file.

1. Steps 2 - 5 repeat until you enter 'Quit!' after being prompted for the words you want to find anagrams of. e.g.

  PROGRAM: "Please enter the word or words you'd like to find anagrams of. Otherwise, to quit please enter 'Quit!'"

  USER: Quit!
