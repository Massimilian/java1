package ru.progwards.java1.lessons.io2;

/**
 * Class for changing words in text
 */
public class Translator {
    private final String[] inLang;
    private final String[] outLang;

    /**
     * Constructor with parameters inLang (entering words) and outLang (escaping words)
     * @param inLang words for enter
     * @param outLang words for escape
     */
    public Translator(String[] inLang, String[] outLang) {
        this.inLang = inLang;
        this.outLang = outLang;
    }

    /**
     * Method for preparing the array of words and putting into the final sentence
     * @param sentence for translate
     * @return changed sentence (String)
     */
    public String translate(String sentence) {
        String[] words = prepareWords(sentence, true);
        return constructAll(sentence, changeWords(words), words, prepareWords(sentence, false));
    }

    /**
     * Method for construct final sentence
     * @param sentence for change
     * @param changedWords is array with prepared (changed if need) words
     * @param words is array with only words of sentence
     * @param punctuations is array with punctuation gifts
     * @return finally prepared String
     */
    private String constructAll(String sentence, String[] changedWords, String[] words, String[] punctuations) {
        int wordPos = 0;
        int punctPos = 0;
        StringBuilder sb = new StringBuilder();
        if (!Character.isAlphabetic(sentence.charAt(0))) {
            sb.append(punctuations[punctPos++]);
        }
        boolean addWord = true;
        while (punctPos != punctuations.length || wordPos != changedWords.length) {
            if (addWord) {
                sb.append(checkUppers(changedWords, words, wordPos++));
            } else {
                sb.append(punctuations[punctPos++]);
            }
            addWord = !addWord;
        }
        return sb.toString();
    }

    /**
     * The method for change the array with words on @changed@ words
     * @param words old words
     * @return changed words
     */
    private String[] changeWords(String[] words) {
        String[] newWords = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            newWords[i] = changeWord(words[i]);
        }
        return newWords;
    }

    /**
     * Method for change the word if it need
     * @param word for change
     * @return changed (maybe) word
     */
    private String changeWord(String word) {
        String result = word.toLowerCase();
        for (int i = 0; i < inLang.length; i++) {
            if (word.toLowerCase().equals(inLang[i])) {
                result = outLang[i];
                break;
            }
        }
        return result;
    }

    /**
     * Method to prepare the words and punctuation arrays
     * @param sentence with words (of punctuation) for prepare
     * @param isWords true = words; false = punctuation
     * @return prepared array with words or punctuation
     */
    private String[] prepareWords(String sentence, boolean isWords) {
        int pos = 0;
        String[] buffer = new String[sentence.length()];
        for (int i = 0; i < sentence.length(); i++) {
            String word = fillValues(sentence, i, isWords);
            if (word.length() != 0) {
                buffer[pos++] = word;
                i += word.length();
            }
        }
        String[] result = new String[pos];
        System.arraycopy(buffer, 0, result, 0, pos);
        return result;
    }

    /**
     * Method for prepare words and punctuation
     *
     * @param sentence for work
     * @param i position into sentence
     * @param isAlphabetic true if is letter; false if is not.
     * @return prepared group of symbols for add into arrays
     */
    private String fillValues(String sentence, int i, boolean isAlphabetic) {
        StringBuilder sb = new StringBuilder();
        if (isAlphabetic) {
            while (i < sentence.length() && Character.isAlphabetic(sentence.charAt(i))) {
                sb.append(sentence.charAt(i++));
            }
        } else {
            while (i < sentence.length() && !Character.isAlphabetic(sentence.charAt(i))) {
                sb.append(sentence.charAt(i++));
            }
        }
        return sb.toString();
    }

    /**
     * Method for check all words with upper letters
     * @param changedWords array with new words
     * @param words array with words of sentence
     * @param wordPos number of position into the sentence
     * @return
     */
    private String checkUppers(String[] changedWords, String[] words, int wordPos) {
        String word = words[wordPos];
        String changed = changedWords[wordPos];
        if (Character.isUpperCase(word.charAt(0))) {
            changed = changed.substring(0, 1).toUpperCase() + changed.substring(1);
        }
        return changed;
    }

    public static void main(String[] args) {
        Translator translator = new Translator(new String[]{"мглою", "буря", "кроет", "небо"}, new String[]{"cрочно", "надо", "Java", "учить"});
        assert translator.translate("...Буря мглою небо кроет...").equals("...Надо срочно учить Java...");
        assert translator.translate("...Буря мглою(!) небо кроет...").equals("...Надо срочно(!) учить Java...");


    }
}
