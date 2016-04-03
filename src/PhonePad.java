import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhonePad {

  private static PhonePad instance = new PhonePad();

  private Map<Character, List<Character>> map;

  private PhonePad() {
    this.map = new HashMap<Character, List<Character>>(8);
    this.map.put('2', Arrays.asList('A', 'B', 'C'));
    this.map.put('3', Arrays.asList('D', 'E', 'F'));
    this.map.put('4', Arrays.asList('G', 'H', 'I'));
    this.map.put('5', Arrays.asList('J', 'K', 'L'));
    this.map.put('6', Arrays.asList('M', 'N', 'O'));
    this.map.put('7', Arrays.asList('P', 'Q', 'R', 'S'));
    this.map.put('8', Arrays.asList('T', 'U', 'V'));
    this.map.put('9', Arrays.asList('W', 'X', 'Y', 'Z'));
  }

  public static List<Character> get(char ch) {
    if (!Character.isDigit(ch)) {
      return new ArrayList<Character>();
    } else if (instance.map.containsKey(ch)) {
      return instance.map.get(ch);
    } else {
      return new ArrayList<Character>();
    }
  }
}