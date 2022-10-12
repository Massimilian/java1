package nikita;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Wspp {
    MyScanner ms = new MyScanner();

    public void work (String from) throws IOException {
        ArrayList<Word> words = this.setWords(ms.readLine(from));
        ms.writeFile(prepareInfo(words), "src/main/resources/statistics.txt");
    }

    private String prepareInfo(ArrayList<Word> words) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            sb.append(words.get(i)).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public ArrayList<Word> setWords(String string) {
        string = string.toLowerCase(Locale.ROOT);
        ArrayList<String> words = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char temp = string.charAt(i);
            if (Character.isAlphabetic(temp) || temp == '-' || temp == '\'') {
                sb.append(temp);
            } else {
                if (!sb.isEmpty()) {
                    words.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
        }
        if (!sb.isEmpty()) {
            words.add(sb.toString());
        }
        return prepareWords(words);

    }

    private ArrayList<Word> prepareWords(ArrayList<String> words) {
        ArrayList<Word> result = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            Word word = new Word(words.get(i));
            if (result.contains(word)) {
                Word temp = result.remove(result.indexOf(word));
                temp.incr();
                result.add(temp);
            } else {
                result.add(word);
            }
        }
        return result;
    }
}