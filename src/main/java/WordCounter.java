import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class WordCounter
{
    public boolean isCaseSensitive;
    public boolean dictProvided;
    public boolean inPathProvided;
    public String inputPath;
    public String dictPath;

    public WordCounter(){
        isCaseSensitive = false; // By default, is not case-sensitive
        dictProvided = false;
        inPathProvided = false;
        inputPath = System.getProperty("user.dir");
        dictPath = System.getProperty("user.dir");
    }

    public static int countOccurrences(String wholeString, String subString){
        int occ = 0;
        int fullLen = wholeString.length();
        int subLen = subString.length();
        if ((subLen < fullLen) && (subLen != 0)) {
            // Preprocess the string
            Map<Character, Integer> last = new HashMap<>();
            for (int i = 0; i < fullLen; i++) // set all chars, by default, to -1
                last.put(wholeString.charAt(i), -1);
            for (int i = 0; i < subLen; i++) // update last seen positions
                last.put(subString.charAt(i), i);
            int idxFull = subLen - 1;
            int idxSub = subLen - 1;
            while (idxFull < fullLen) {
                if (wholeString.charAt(idxFull) == subString.charAt(idxSub)) { // Matching symbol found
                    if (idxSub == 0) {
                        occ++; // Found a full match
                        idxFull += subLen - Math.min(idxSub, 1 + last.get(wholeString.charAt(idxFull)));
                        idxSub = subLen - 1;
                    } else { // Not a full match -> keep iterating through
                        idxSub--;
                        idxFull--;
                    }
                } else {
                    idxFull += subLen - Math.min(idxSub, 1 + last.get(wholeString.charAt(idxFull)));
                    idxSub = subLen - 1;
                }
            }
        }
        return occ;
    }

    public int countOccurrences(){
        AtomicInteger totalCount = new AtomicInteger();
        if (this.dictProvided){
            List<String> dictList = new ArrayList<>();
            try (BufferedReader br = Files.newBufferedReader(Paths.get(this.dictPath))) {
                Stream<String> stringStream = br.lines();
                if (!this.isCaseSensitive) stringStream = stringStream.map(s ->  s.toUpperCase());
                dictList =  stringStream.collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!this.inPathProvided){
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please provide the text to search the dictionary words in:");
                String inputString = scanner.nextLine();
                dictList.forEach((word) -> totalCount.addAndGet(countOccurrences(inputString, word)));
            } else {
                try (BufferedReader br = Files.newBufferedReader(Paths.get(this.inputPath))) {
                    List<String> finalDictList = dictList;
                    Stream<String> stringStream = br.lines();
                    if (!this.isCaseSensitive) stringStream = stringStream.map(s ->  s.toUpperCase());
                    stringStream.forEach((line) ->
                            finalDictList.forEach((word) ->
                                    totalCount.addAndGet(countOccurrences(line, word))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Dictionary not provided, please provide a valid path! ");
        }
        return totalCount.intValue();
    }


}