package interfaces;

import java.util.List;

public interface Store<E> {
    void write(E item);
    List<E> read();
}
