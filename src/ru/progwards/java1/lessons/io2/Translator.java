package ru.progwards.java1.lessons.io2;

public class Translator {
    private final String[] inLang;
    private final String[] outLang;

    public Translator(String[] inLang, String[] outLang) {
        this.inLang = inLang;
        this.outLang = outLang;
    }

    public String translate(String sentence) {
        String[] words = prepareWords(sentence, true);
        return constructAll(sentence, changeWords(words), words, prepareWords(sentence, false));
    }

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

    private String[] changeWords(String[] words) {
        String[] newWords = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            newWords[i] = changeWord(words[i]);
        }
        return newWords;
    }

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
