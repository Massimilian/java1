package boruvka;

class Edge<N, E> {
    E info; // информация о ребре
    Node<N, E> out; // вершина, из которой исходит ребро
    Node<N, E> in; // вершина, в которую можно попасть по этому ребру
    double weight; // стоимость перехода

    public Edge(E info, double weight) {
        this.info = info;
        this.weight = weight;
    }

    public void setOut(Node<N, E> out) {
        this.out = out;
    }

    public void setIn(Node<N, E> in) {
        this.in = in;
    }
}
