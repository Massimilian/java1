package boruvka;

import java.util.List;

class Node<N, E> {
    N info; // информация об узле
    List<Edge<N, E>> in; // массив входящих ребер
    List<Edge<N, E>> out; // массив исходящих ребер

    public Node(N info) {
        this.info = info;
    }

    public void setIn(List<Edge<N, E>> in) {
        this.in = in;
    }

    public void setOut(List<Edge<N, E>> out) {
        this.out = out;
    }
}
