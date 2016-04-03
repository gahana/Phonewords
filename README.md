# Phonewords
Phonewords are mnemonic phrases represented as alphanumeric equivalents of a telephone number.
For more details see [Wikipedia](https://en.wikipedia.org/wiki/Phoneword).
Phonewords is at the core an algorithmic problem and involves the use of Tries for efficient implementation. Tries are rarely used compared to other data structures and hence presents an interesting challenge to solve.
The following cases are handled:

- Single complete word
  + Example: 228 is ACT or BAT or CAT
- Multi word
  + Example: 225563 is CALL-ME
- Skip at max 1 digit
  + Example: 2255963 is CALL-9-ME

# References
The Phonewords implementation makes use of Ternary Search Trie implementation from [algs4](http://algs4.cs.princeton.edu/home/). 
It also uses StdIn and In classes from [algs4](http://algs4.cs.princeton.edu/home/) to support command line execution.

# Dependencies
- Java 7+
- Gradle 2.12 (Dev)

# Setup

```
$ git clone https://github.com/gahana/Phonewords.git
$ cd Phonewords
$ gradle build
```
# Running
First build the jar

```
$ gradle build
```
To input numbers from STDIN

```
$ java -jar build/libs/Phonewords.jar
```
To input numbers from files

```
$ java -jar build/libs/Phonewords.jar data/input/three.txt data/input/subwords.txt
```
To specify a custom dictionary file (every line has a word)

```
$ java -jar build/libs/Phonewords.jar -d data/dictionary/test data/input/three.txt data/input/subwords.txt
```

# Implementation
- The implementation makes use of a Ternary Search Trie to store the dictionary. 
- Given a phone word, the possible combinations are determined using PhonePad class. 
- The resulting combinations are reduced by traversing the Trie to filter out valid combinations.
- The Trie traversal takes care to branch out for multi-word combinations

## Running Time Complexity
- Terms
  + N is the number of words in the dictonary used.
  + L is the lenth of a word/number under consideration.
- The Ternery Search Trie itself has 
  + Insertion has a time complexity of (L + ln N)
  + Search has a time complexity of (L + ln N)
- Each valid number searched has at least 3 combinations (7 and 9 have four combinations)
- The possible word combinations is 3^L where L is the length of the number.
- The Trie is used to filter out invalid combinations and greatly reduces the solution space.

