package boruvka;

/**
 * Special class for one Node and one Edge
 * @param <N>
 * @param <E>
 */
public class NodeWithEdge<N, E> {
    private Node<N, E> node;
    private Edge<N, E> edge;

    public NodeWithEdge(Node<N, E> node) {
        this.node = node;
        this.edge = findMin(this.node);
    }

    /**
     * Method for find the minimum edge in current node
     * @param node
     * @return
     */
    private Edge<N,E> findMin(Node<N,E> node) {
        Edge result = null;
        if (node.out != null) {
            result = node.out.get(0);
            for (int i = 1; i < node.out.size(); i++) {
                if (result.weight > node.out.get(i).weight) {
                    result = node.out.get(i);
                }
            }
        }
        return result;
    }

    public Node<N, E> getNode() {
        return node;
    }

    public Edge<N, E> getEdge() {
        return edge;
    }
}
