import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Dictionary {

  private Trie words = new Trie();
  
  public Dictionary(String defaultDictionary) {
    loadTrie(
      loadFile(
        Dictionary.class.getResourceAsStream(defaultDictionary)), 
      this.words);
  }

  public Dictionary(File file) {
    try {
      loadTrie(
        loadFile(
          new FileInputStream(file)), 
        this.words);
    } catch (Exception e) {
      throw new IllegalArgumentException("Could not open dictionary file");
    }
  }

  private static Scanner loadFile(InputStream is) {
    try {
      Scanner scanner = new Scanner(new BufferedInputStream(is), "UTF-8");
      scanner.useLocale(Locale.US);
      return scanner;
    } catch (Exception e) {
      throw new IllegalArgumentException("Could not open dictionary file");
    }
  }

  private static void loadTrie(Scanner scanner, Trie trie) {
    try {
      while (scanner.hasNext()) {
        trie.put(scanner.next());
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid file format");
    }
  }

  public boolean contains(String word) {
    return this.words.contains(word);
  }

  public List<String> phoneWords(String number) {
    return this.words.phoneWords(number);
  }

}