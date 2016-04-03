import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhoneWords {

  private static final String DEFAULT_DICTIONARY = "small";

  private static class Parameters {
    private String dictionaryFile;
    private List<String> inputFiles = new ArrayList<String>();
  }

  public static void main(String[] args) {
    try {
      Parameters params = getParameters(args);
      process(params, getDictionary(params));
    } catch (Exception e) {
      System.out.println("Error processing input.");
      e.printStackTrace();
    }
  }

  private static void process(Parameters params, Dictionary dictionary) {
    if (params.inputFiles.isEmpty()) {
      System.out.println("No input files specified, enter numbers directly, type exit to stop.");
      fromStdIn(dictionary);
    } else {
      for (String file : params.inputFiles) {
        fromFile(file, dictionary);
      }
    }
  }

  private static Dictionary getDictionary(Parameters params) {
    if (params.dictionaryFile == null) {
      return new Dictionary(DEFAULT_DICTIONARY);
    }
    return new Dictionary(new File(params.dictionaryFile));
  }

  private static void fromStdIn(Dictionary dictionary) {
    try {
      while (!StdIn.isEmpty()) {
        String number = format(StdIn.readString());
        if ("exit".equalsIgnoreCase(number)) break;

        List<String> result = dictionary.phoneWords(number);
        System.out.println(number + " > " + join(result));
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid entry");
    }
  }

  private static void fromFile(String fileName, Dictionary dictionary) {
    try {
      In in = new In(fileName);
      System.out.println("[" + fileName + "]");
      while (!in.isEmpty()) {
        String number = format(in.readString());
        List<String> result = dictionary.phoneWords(number);
        System.out.println(number + " > " + join(result));
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid file or format");
    }
  }

  private static String join(List<String> list) {
    StringBuilder result = new StringBuilder("");
    for (String str : list) {
      result.append(", ");
      result.append(str);
    }
    return result.toString().replaceFirst(", ", "");
  }

  private static String format(String key) {
    if (key == null) 
      throw new IllegalArgumentException("Cannot insert null");
    if ("exit".equalsIgnoreCase(key)) return key;

    String formatKey = key.replaceAll("[^0-9]", "");
    if ("".equals(formatKey)) 
      throw new IllegalArgumentException("Invalid key insertion");
    return formatKey;
  }

  private static Parameters getParameters(String[] args) {
    Parameters params = new Parameters();
    boolean skipNext = false;
    for (int i = 0; i < args.length; i++) {
      if (skipNext) {
        skipNext = false;
        continue;
      }
      if ("-d".equals(args[i])) {
        if (i+1 < args.length) {
          params.dictionaryFile = args[i+1];
          skipNext = true;
        } else {
          printHelp();
          System.out.println("Dictionary file not specified, using default");
        }
      } else {
        params.inputFiles.add(args[i]);
      }
    }
    return params;
  }

  private static void printParams(Parameters params) {
    System.out.println(params.dictionaryFile);
    for (String f : params.inputFiles) {
      System.out.println(f);
    }
  }

  private static void printHelp() {
    System.out.println("Usage:");
    System.out.println("Parameters:");
    System.out.println("\t-d dictionary: optional dictionary file");
    System.out.println("\tfiles: optional list of files with numbers for conversion to Phonewords");
    System.out.println("\tnone: input numbers via STDIN for conversion to Phonewords");
  }
}