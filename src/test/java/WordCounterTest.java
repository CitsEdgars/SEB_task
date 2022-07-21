import junit.framework.TestCase;
import org.junit.Test;

public class WordCounterTest extends TestCase {

    @Test
    public void testEmptyInput(){
        // countOccurrences() with "" "a" -> 0
        assertEquals(0, WordCounter.countOccurrences("", "a"));
    }

    @Test
    public void testEmptySubString(){ // Kind of the same as testing empty dict
        // countOccurrences() with "asdasccas" "" -> 0
        assertEquals(0, WordCounter.countOccurrences("asdasccas", ""));
    }

    @Test
    public void testNoOccurences() {
        // countOccurrences() with "bbbbbb" "a" -> 0
        assertEquals(0, WordCounter.countOccurrences("bbbbbb", "a"));
    }

    @Test
    public void testEmptyDict(){ // Kind of the same as testing empty dict
        // countOccurrences() with "asdasccas" "" -> 0
        WordCounter wc = new WordCounter();
        wc.isCaseSensitive = true;
        wc.dictPath += "/src/test/java/emptyDict.txt";
        wc.dictProvided = true;
        wc.inputPath += "/src/test/java/testInput.txt";
        wc.inPathProvided = true;
        assertEquals(0, wc.countOccurrences());
    }

    @Test
    public void testCaseSensitive() {
        // with "baAbbbb" "a" -> 1
        // A little "hacky" way of doing it, but couldn't quickly think of a better way other than to rewrite implementation
        WordCounter wc = new WordCounter();
        wc.isCaseSensitive = true;
        wc.dictPath += "/src/test/java/testDict.txt";
        wc.dictProvided = true;
        wc.inputPath += "/src/test/java/testInput.txt";
        wc.inPathProvided = true;
        assertEquals(1, wc.countOccurrences());
    }

    @Test
    public void testNotCaseSensitive() {
        // with "baAbbbb" "a" -> 2
        WordCounter wc = new WordCounter();
        wc.isCaseSensitive = false;
        wc.dictPath += "/src/test/java/testDict.txt";
        wc.dictProvided = true;
        wc.inputPath += "/src/test/java/testInput.txt";
        wc.inPathProvided = true;
        assertEquals(2, wc.countOccurrences());
    }
 }