package ru.progwards.java1.lessons.io2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Special class for censoring the text.
 */
public class Censor {

    /**
     * Main static method for censurer's work
     *
     * @param inoutFileName the file place
     * @param obscene       words for censurer
     * @throws CensorException if something's going wrong
     */
    public static void censorFile(String inoutFileName, String[] obscene) throws CensorException {
        Censor censor = new Censor();
        censor.censor(inoutFileName, obscene);
    }

    /**
     * Main non-static method for censurer's work
     *
     * @param inoutFileName the file place
     * @param obscene       words for censore
     * @throws CensorException if something's going wrong
     */
    private void censor(String inoutFileName, String[] obscene) throws CensorException {
        try {
            writeFile(inoutFileName, cens(writeFromFile(inoutFileName), obscene, inoutFileName));
        } catch (IOException e) {
            throw new CensorException(String.format("%s: %s", inoutFileName, e.getMessage()));
        }
    }

    /**
     * Method to take the text from the file
     *
     * @param inoutFileName file place
     * @return text from file
     * @throws IOException if something's going wrong
     */
    private String writeFromFile(String inoutFileName) throws IOException {
        FileReader fr = new FileReader(inoutFileName);
        Scanner scanner = new Scanner(fr);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            if (scanner.hasNext()) {
                sb.append(System.lineSeparator());
            }
        }
        fr.close();
        return sb.toString();
    }

    /**
     * Main method to check words for censore
     *
     * @param info    String for censore
     * @param obscene censoring words
     * @return censored String
     */
    private String cens(String info, String[] obscene, String inOutFileName) throws CensorException {
        String result = info;
        for (String s : obscene) {
            result = replaceInText(result, s, inOutFileName);
        }
        return result;
    }

    /**
     * Method to censurer word-by-word (with special check at the begining and the end of the String)
     *
     * @param result String fo check
     * @param word   String for change
     * @return changed String
     */
    private String replaceInText(String result, String word, String inOutFileName) throws CensorException {
        if (word.equals("")) {
            throw new CensorException(String.format("%s: Impossible word!", inOutFileName));
        }
        int point = -1;
        do {
            point = result.toLowerCase().indexOf(String.format("%s", word.toLowerCase()), point + 1);

            if (point == 0 && result.substring(point, word.length() + 1).toLowerCase().matches(String.format("%s[\\W]", word.toLowerCase()))) {
                result = String.format("%s%s", prepareWord(word), result.substring(word.length()));
            }
            if (point < result.length() - word.length() && point > 0 && result.substring(point - 1, point + word.length() + 1).toLowerCase().matches(String.format("[\\W]%s[\\W]", word.toLowerCase()))) {
                result = String.format("%s%s%s", result.substring(0, point), prepareWord(word), result.substring(point + word.length()));
            }
            if (point == result.length() - word.length() && result.substring(point - 1).toLowerCase().matches(String.format("[\\W]%s", word.toLowerCase()))) {
                result = String.format("%s%s", result.substring(0, result.length() - word.length()), prepareWord(word));
            }
        } while (point != -1);
        return result;
    }

    /**
     * Method for preparing impossible word and putting '*' in it
     *
     * @param word to change
     * @return stars
     */
    private String prepareWord(String word) {
        return "*".repeat(word.length());
    }

    /**
     * Method for write prepared text into the file
     *
     * @param inoutFileName file place
     * @param cleaned       text for write
     * @throws IOException if something's going wrong
     */
    private void writeFile(String inoutFileName, String cleaned) throws IOException {
        FileWriter fw = new FileWriter(inoutFileName);
        fw.write(cleaned);
        fw.close();
    }

    /**
     * Exception class if something's going wrong
     */
    private static class CensorException extends Exception {
        public CensorException(String s) {
            super(s);
        }
    }

    /**
     * Delete and create method, specoal for comfort test
     *
     * @param file       for create
     * @param realCreate necessity of creating new file
     * @throws IOException if something's going wrong
     */
    private void forTestDeleteCreate(String file, boolean realCreate) throws IOException {
        File f = new File(file);
        if (f.exists()) {
            f.delete();
        }
        if (realCreate) {
            f.createNewFile();
        }
    }

    public static void main(String[] args) throws CensorException, IOException {
        String file = "text.txt";
        Censor cens = new Censor();
        cens.forTestDeleteCreate(file, true);
        cens.writeFile(file, "Delete dElete DELETE delete! edelete deletee");
        censorFile(file, new String[]{"delete"});
        assert cens.writeFromFile(file).equals("****** ****** ****** ******! edelete deletee");
        cens.forTestDeleteCreate(file, true);
        cens.writeFile(file, "This is a lISt of \"is\".");
        censorFile(file, new String[]{"a", "is"});
        assert cens.writeFromFile(file).equals("This ** * lISt of \"**\".");
        cens.forTestDeleteCreate(file, true);
        cens.writeFile(file, "Java — строго типизированный объектно-ориентированный язык программирования, разработанный компанией Sun Microsystems (в последующем приобретённой компанией Oracle).");
        censorFile(file, new String[]{"Java", "Oracle", "Sun", "Microsystems"});
        assert cens.writeFromFile(file).equals("**** — строго типизированный объектно-ориентированный язык программирования, разработанный компанией *** ************ (в последующем приобретённой компанией ******).");
        cens.forTestDeleteCreate(file, false);
    }
}

