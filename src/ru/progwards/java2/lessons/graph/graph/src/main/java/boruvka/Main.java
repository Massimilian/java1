package boruvka;

import java.util.*;

/**
 * This class is for work with boruvka method of finding the minimum spanning tree
 * @param <N>
 * @param <E>
 */
public class Main<N, E> {

    /**
     * Method for prepare graph for test
     * @return prepared graph
     */
    public static Graph<String, Integer> getGraph() {
        Node<String, Integer> a = new Node<>("a");
        Node<String, Integer> b = new Node<>("b");
        Node<String, Integer> c = new Node<>("c");
        Node<String, Integer> d = new Node<>("d");
        Node<String, Integer> e = new Node<>("e");
        Node<String, Integer> f = new Node<>("f");
        Edge<String, Integer> one = new Edge<>(1, 1);
        Edge<String, Integer> two = new Edge<>(2, 2);
        Edge<String, Integer> three = new Edge<>(3, 3);
        Edge<String, Integer> four = new Edge<>(4, 4);
        Edge<String, Integer> five = new Edge<>(5, 5);
        Edge<String, Integer> six = new Edge<>(6, 6);
        Edge<String, Integer> fourteen = new Edge<>(14, 14);
        a.setOut(List.of(one, three));
        b.setIn(List.of(one));
        b.setOut(List.of(six, fourteen));
        c.setIn(List.of(three));
        c.setOut(List.of(two));
        d.setIn(List.of(two, six));
        d.setOut(List.of(five));
        e.setIn(List.of(five));
        e.setOut(List.of(four));
        f.setIn(List.of(four, fourteen));
        one.setIn(b);
        one.setOut(a);
        two.setIn(d);
        two.setOut(c);
        three.setIn(c);
        three.setOut(a);
        four.setIn(f);
        four.setOut(e);
        five.setIn(e);
        five.setOut(d);
        six.setIn(d);
        six.setOut(b);
        fourteen.setIn(f);
        fourteen.setOut(b);
        Graph<String, Integer> graph = new Graph<>();
        graph.nodes = List.of(a, b, c, d, e, f);
        graph.edges = List.of(one, two, three, four, five, six, fourteen);
        return graph;
    }

    /**
     * static method for prepare the minimum spanning tree
     * @param graph graph for prepare
     * @return edges of minimum tree
     */
    static List minTree(Graph graph) {
        Main main = new Main();
        return main.minTree(graph, false);
    }

    /**
     * non-static method for prepare the minimum spanning tree.
     * Works with two algorhytms:
     * 1. True - the algorhythm with "my understanding" from lecture (works, but not always);
     * 2. False - the algorhythm with "my understanding" from other resources (but in lecture this is a "Kraskall method")
     * @param graph graph for prepare
     * @return edges of minimum tree
     */
    private List minTree(Graph graph, boolean wrongMethod) {
        if (wrongMethod) {
            return minTreeFromLectureAlgorhythm(graph);
        } else {
            return minTreeInternetAlgorhythm(graph);
        }
    }

    /**
     * Method to prepare the minimum spanning tree
     * @param graph for work
     * @return edges of minimum spanning tree
     */
    private List minTreeFromLectureAlgorhythm(Graph graph) {
        ArrayList<NodeWithEdge> nodesWithEdges = new ArrayList<>();
        for (int i = 0; i < graph.nodes.size(); i++) {
            nodesWithEdges.add(new NodeWithEdge((Node) graph.nodes.get(i)));
        }
        ArrayList<Edge> result = new ArrayList<>();
        ArrayList<Node> passed = new ArrayList<>();
        passed.add(nodesWithEdges.get(0).getNode());
        for (int i = 0; i < nodesWithEdges.size(); i++) {
            Edge edge = nodesWithEdges.get(i).getEdge();
            if (edge != null && !passed.contains(edge.in)) {
                result.add(edge);
            }
        }
        return result;
    }

    /**
     * Method to prepare the minimum spanning tree
     * @param graph for work
     * @return edges of minimum spanning tree
     */
    private List minTreeInternetAlgorhythm(Graph graph) {
        List<Node> nodes = new ArrayList<>();
        List <Edge> edges = new ArrayList<>();
        Edge minEdge = findMinEdge(graph);
        nodes.add(minEdge.out);
        nodes.add(minEdge.in);
        edges.add(minEdge);
        while(nodes.size() < graph.nodes.size()) {
            step(nodes, edges);
        }
        return edges;
    }

    /**
     * Method for finding new minimum Edge and Node (the end of that Edge's way)
     * @param nodes prepared nodes
     * @param edges edges for not duplicate
     */
    private void step(List<Node> nodes, List<Edge> edges) {
        Edge minEdge = findMinEdge(nodes);
        edges.add(minEdge);
        nodes.add(minEdge.in);
    }

    /**
     * Method for find minimum edge
     * @param nodes where we try to find minimum edge
     * @return minimum edge
     */
    private Edge findMinEdge(List<Node> nodes) {
        Edge result = new Edge(null, Double.MAX_VALUE);
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.get(i).out.size(); j++) {
                Edge temp = (Edge) nodes.get(i).out.get(j);
                if (!nodes.contains(temp.in) && result.weight > temp.weight) {
                    result = temp;
                }
            }
        }
        return result;
    }

    /**
     * Method for find the minimum edge of all graph
     * @param graph for find minimum edge
     * @return minimum edge
     */
    private Edge findMinEdge(Graph graph) {
        Edge result = new Edge(null, Double.MAX_VALUE);
        for (int i = 0; i < graph.edges.size(); i++) {
            if (result.weight > ((Edge)graph.edges.get(i)).weight) {
                result = (Edge) graph.edges.get(i);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        Graph<String, Integer> graph = Main.getGraph();
        List<Edge> edges = Main.minTree(graph);
        assert edges.size() == 5;
    }
}
