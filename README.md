# ValidAnagrams

----

### To run:

1. Clone the repository.

1. Using the terminal, navigate to the src folder.

1. do 'javac *.java'

1. do 'java ProgramStart'

1. The program itself should ask you for input, and give you the instructions you need. 

----

#### While it's running:

Here's how it should go:

> The program asks you if you would like to use your own dictionary file.

>> Scenario 1 - You want to use your own dictionary file.

>>> Input 'y' or 'yes'.

>>> The program asks you for the path to that dictionary file.

>>> Input the path to it. e.g.:

>>> ../TestSmallDict.txt

>> Scenario 2 - You don't want to use your own dictionary file.

>>> Input 'n' or 'no'.

> The program asks you to enter the word or words you want to find anagrams of (or quit).

> Assuming you don't want to quit just yet, input one or more words, seperating them by spaces, e.g.

> three stab jump

> The program asks you if you would like to stick to the default algorithm used to find anagrams (it proceeds iteratively), or switch to the other option (using a graph).

>> Scenario 1 - You want to switch to the graph search option.

>>> Input 'y' or 'yes'.

>> Scenario 2 - You want to tick to the iterative search option.

>>> Input 'n' or 'no'.

> The program prints out the anagrams of the given words that are in the dictionary file.

> Steps 5.ii - 5.v repeat until you enter 'Quit!' after being prompted for the words you want to find anagrams of. e.g.

> PROGRAM: "Please enter the word or words you'd like to find anagrams of. Otherwise, to quit please enter 'Quit!'"

> USER: Quit!

----

### Other Notes:
Default Dictionary File Found at:
http://www-01.sil.org/linguistics/wordlists/english/

Note: I added the word 'I' to it.
