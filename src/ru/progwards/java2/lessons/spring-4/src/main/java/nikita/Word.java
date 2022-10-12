package nikita;

import java.util.Objects;

public class Word{
    private String word;
    int count = 1;

    public Word(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return word.equals(word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    @Override
    public String toString() {
        return this.word + " - " + count;
    }

    public void incr() {
        this.count++;
    }
}