import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhoneWordsTest {

  private Trie trie;
  private Dictionary dict;

  @Before
  public void setup() {
    trie = getTrie();
    dict = new Dictionary("test");
  }

  @Test
  public void canary() {
    assertTrue(1 == 1);
  }

  @Test
  public void nonDigitCharMappingIsEmpty() {
    assertTrue(PhonePad.get('#').isEmpty());
    assertTrue(PhonePad.get('A').isEmpty());
  }

  @Test
  public void digitToCharMapping() {
    assertTrue(PhonePad.get('0').isEmpty());
    assertTrue(PhonePad.get('1').isEmpty());
    assertEquals(Arrays.asList('A', 'B', 'C'), PhonePad.get('2'));
    assertEquals(Arrays.asList('D', 'E', 'F'), PhonePad.get('3'));
    assertEquals(Arrays.asList('G', 'H', 'I'), PhonePad.get('4'));
    assertEquals(Arrays.asList('J', 'K', 'L'), PhonePad.get('5'));
    assertEquals(Arrays.asList('M', 'N', 'O'), PhonePad.get('6'));
    assertEquals(Arrays.asList('P', 'Q', 'R', 'S'), PhonePad.get('7'));
    assertEquals(Arrays.asList('T', 'U', 'V'), PhonePad.get('8'));
    assertEquals(Arrays.asList('W', 'X', 'Y', 'Z'), PhonePad.get('9'));
  }

  private Trie getTrie() {
    Trie aTrie = new Trie();
    aTrie.put("earth");
    aTrie.put("rocket");
    aTrie.put("star");
    aTrie.put("starlet");
    aTrie.put("starch");
    aTrie.put("stardom");
    aTrie.put("ROCKET"); // duplicate
    return aTrie;
  }

  @Test
  public void trieHasUpperCaseWords() {
    assertTrue(trie.contains("EARTH"));
    assertTrue(!trie.contains("earth"));
  }

  @Test
  public void trieHandlesDuplicates() {
    assertTrue(trie.contains("ROCKET"));
  }

  @Test
  public void trieHasRightWords() {
    assertTrue(trie.contains("EARTH"));
    assertTrue(trie.contains("ROCKET"));
    assertTrue(!trie.contains("MOON"));
  }

  @Test
  public void trieDoesNotHaveSmallerOrLongerWords() {
    assertTrue(trie.contains("EARTH"));
    assertTrue(!trie.contains("EAR"));
    assertTrue(!trie.contains("EARTHEN"));
  }

  @Test
  public void trieDoesNotHaveVarients() {
    assertTrue(trie.contains("STAR"));
    assertTrue(trie.contains("STARCH"));
    assertTrue(!trie.contains("STARDUST"));
    assertTrue(!trie.contains("STARLING"));
  }

  @Test
  public void dictionaryHasRightWords() {
    assertTrue(dict.contains("ABASH"));
    assertTrue(!dict.contains("ABSHA"));
  }

  private Set<String> toSet(List<String> list) {
    return new HashSet<String>(list);
  }

  @Test
  public void invalidPhoneWords() {
    assertTrue(dict.phoneWords("0").isEmpty());
    assertTrue(dict.phoneWords("1").isEmpty());
    assertTrue(dict.phoneWords("10").isEmpty());
  }

  @Test
  public void singleDigitPhoneWords() {
    assertTrue(dict.phoneWords("5").isEmpty());
    assertEquals(toSet(Arrays.asList("A")), toSet(dict.phoneWords("2")));
    assertEquals(toSet(Arrays.asList("I")), toSet(dict.phoneWords("4")));
  }

  @Test
  public void twoDigitPhoneWords() {
    assertTrue(dict.phoneWords("92").isEmpty());
    assertEquals(toSet(Arrays.asList("AD", "BE")), toSet(dict.phoneWords("23")));
    assertEquals(toSet(Arrays.asList("IF", "HE")), toSet(dict.phoneWords("43")));
  }

  @Test
  public void threeDigitPhoneWords() {
    assertEquals(toSet(Arrays.asList("ACT", "BAT", "CAT")), toSet(dict.phoneWords("228")));
  }

  @Test
  public void subWordBoundaries() {
    assertEquals(toSet(Arrays.asList("CALL-ME")), toSet(dict.phoneWords("225563")));
    assertEquals(toSet(Arrays.asList("A-BE", "A-AD", "BAD", "ACE")), toSet(dict.phoneWords("223")));
  }

  @Test
  public void oneDigitSkip() {
    // assertEquals(toSet(Arrays.asList("CALL-9-ME")), toSet(dict.phoneWords("2255963")));
    // assertEquals(toSet(Arrays.asList("CALL-9")), toSet(dict.phoneWords("22559")));
    // assertEquals(toSet(Arrays.asList("9-CALL")), toSet(dict.phoneWords("92255")));
  }

}