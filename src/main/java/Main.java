public class Main {
    public static void main(String[] args) {
        WordCounter wc = new WordCounter();
        for(int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-c" -> wc.isCaseSensitive = true;
                case "-d" -> {
                    wc.dictPath += args[i + 1];
                    wc.dictProvided = true;
                }
                case "-i" ->{
                    wc.inputPath += args[i + 1];
                    wc.inPathProvided = true;
                }
                default -> {
                }
            }
        }
        System.out.println("Total words found in the string: " + wc.countOccurrences());
    }
}
