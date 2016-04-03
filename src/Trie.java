import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *  Trie represents a set of Strings with operations like 
 *  put, get and Phonewords search
 *
 *  This implementation uses a ternary search trie derived from 
 *  implementation by Robert Sedgewick and Kevin Wayne
 *
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/52trie">Section 5.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Trie {
  private int N;       // size
  private Node root;   // root of TST

  private static class Node {
    private char c;                 // character
    private Node left, mid, right;  // left, middle, and right subtries
    private boolean isWord;         // Flag to denote word ending

    Node(char ch) {
      this.c = ch;
      this.isWord = false;
    }
  }

  /**
   * Initializes an empty string symbol table.
   */
  public Trie() {
  }

  /**
   * Returns the number of key-value pairs in this symbol table.
   * @return the number of key-value pairs in this symbol table
   */
  public int size() {
    return N;
  }

  /**
   * Does this symbol table contain the given key?
   * @param key the key
   * @return <tt>true</tt> if this symbol table contains <tt>key</tt> and
   *   <tt>false</tt> otherwise
   * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
   */
  public boolean contains(String key) {
    if (key == null) throw new NullPointerException();
    if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
    Node x = get(root, key, 0);
    if (x == null) return false;
    return x.isWord;
  }

  // return subtrie corresponding to given key
  private Node get(Node x, String key, int d) {
    if (key == null) throw new NullPointerException();
    if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
    if (x == null) return null;
    char c = key.charAt(d);
    if    (c < x.c)                return get(x.left,  key, d);
    else if (c > x.c)              return get(x.right, key, d);
    else if (d < key.length() - 1) return get(x.mid,   key, d+1);
    else                           return x;
  }

  /**
   * Inserts a key into the symbol table, overwriting the old entry
   * if the key is already in the symbol table.
   * @param key the key
   * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
   */
  public void put(String key) {
    String formatKey = format(key);
    if (!contains(formatKey)) N++;
    root = put(root, formatKey, 0);
  }

  private Node put(Node x, String key, int d) {
    char c = key.charAt(d);
    if (x == null) { x = new Node(c); }

    if    (c < x.c)                 x.left   = put(x.left,  key, d);
    else if (c > x.c)               x.right  = put(x.right, key, d);
    else if (d < key.length() - 1)  x.mid  = put(x.mid,   key, d+1);
    else                            x.isWord = true;

    return x;
  }

  private String format(String key) {
    if (key == null) 
      throw new IllegalArgumentException("Cannot insert null");
    String formatKey = key.replaceAll("[^a-zA-Z]", "").toUpperCase();
    if ("".equals(formatKey)) 
      throw new IllegalArgumentException("Invalid key insertion");
    return formatKey;
  }

  public List<String> phoneWords(String number) {
    new BigInteger(number);
    List<String> result = new ArrayList<String>();
    collectNumber(root, number, 0, "", result);
    return result;
  }

  private void collectNumber(Node x, String number, int index, String prefix, List<String> result) {
    if (x == null) return;
    char digit = number.charAt(index);
    List<Character> options = PhonePad.get(digit);
    for (char ch : options) {
      collectChar(x, ch, number, index, prefix, result);
    }
  }

  private void collectChar(Node x, char ch, String number, int index, String prefix, List<String> result) {
    if (x == null) return;
    if (ch < x.c) collectChar(x.left, ch, number, index, prefix, result);
    if (ch > x.c) collectChar(x.right, ch, number, index, prefix, result);
    if (ch == x.c) {
      if (index < number.length() - 1) {
        // Continue to look for longer words
        collectNumber(x.mid, number, index + 1, prefix + x.c, result);
        // Restart from root for new sub words
        if (x.isWord) collectNumber(root, number, index + 1, prefix + x.c + "-", result);
      }
      if (index == number.length() - 1) {
        if (x.isWord) result.add(prefix + x.c);
      }
    }
  }

}

/******************************************************************************
 *  Copyright 2002-2015, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *    Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *    Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *    http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
