package pack;

import java.util.Objects;

public class Word {
    private String name;
    private String translate;
    private String note;
    private int count = 10;

    public Word(String name, String translate, String note) {
        this.name = name;
        this.translate = translate;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return translate.equals(word.translate) || name.equals(word.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(translate);
    }
}
