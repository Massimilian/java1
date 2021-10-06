package pathLoader;

import java.util.Objects;

public class ClassKeeper {
    private String className;
    private String folder;

    public ClassKeeper(String className, String folder) {
        this.className = className;
        this.folder = folder;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassKeeper that = (ClassKeeper) o;
        return className.equals(that.className) && folder.equals(that.folder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, folder);
    }
}
