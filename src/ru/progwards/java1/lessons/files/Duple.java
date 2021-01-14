package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Class of possible duplicate
 */
public class Duple {
    private final String name;
    private final String lastModifiedTime;
    private final String size;
    private final String content;
    public final String fullName;

    public Duple(Path path) throws IOException {
        this.name = path.getFileName().toString();
        this.lastModifiedTime = Files.getLastModifiedTime(path).toString();
        this.size = Files.getAttribute(path, "basic:size").toString();
        this.content = Files.readString(path);
        this.fullName = path.toAbsolutePath().toString();
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getContent() {
        return content;
    }

    public String getFullName() {
        return fullName;
    }

    private boolean nameEqual(Duple duple) {
        return this.shortName(this.name).equals(duple.shortName(duple.name));
    }

    public String shortName(String name) {
        int index = name.lastIndexOf('/');
        return name.substring(index + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duple duple = (Duple) o;
        return nameEqual(duple) &&
                Objects.equals(lastModifiedTime, duple.lastModifiedTime) &&
                Objects.equals(size, duple.size) &&
                Objects.equals(content, duple.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastModifiedTime, size, content);
    }
}
