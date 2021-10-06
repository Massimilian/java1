package dijistra;

import java.util.*;

/**
 * Class for work with Dijkstra method of finding the most short ways
 */
public class Dijkstra {
    private int[][] graph;
    private HashSet<Way> ways;
    private HashMap<Integer, Node> nodes;

    Dijkstra(int[][] graph) {
        this.graph = graph;
    }

    public void setWays(HashSet<Way> ways) {
        this.ways = ways;
    }

    /**
     * Method for prepare hashmap of Nodes by ways
     */
    public void setNodes() {
        HashMap<Integer, Node> nodes = new HashMap<>();
        Way[] arrWays = wayToArray();
        for (int i = 0; i < graph.length; i++) {
            nodes.put(i + 1, new Node(i + 1, arrWays));
        }
        setNodes(nodes);
    }

    /**
     * Method for transport hashset of ways to array of ways
     * @return array with all ways
     */
    private Way[] wayToArray() {
        Way[] result = new Way[ways.size()];
        Iterator<Way> iterator = ways.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            result[count++] = iterator.next();
        }
        return result;
    }

    /**
     * Method for prepare hashmap<node count, node> from hashset<node>
     * @param nodes for prepare
     */
    public void setNodes(HashSet<Node> nodes) {
        HashMap<Integer, Node> separated = new HashMap<>();
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            Node temp = iterator.next();
            separated.put(temp.getCount(), temp);
        }
        this.setNodes(separated);
    }

    public void setNodes(HashMap<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Method for prepare all ways from node n to other nodes
     * @param n is a node place
     * @return all ways from node
     */
    public int[][] find(int n) {
        Node start = this.nodes.get(n);
        HashMap<Node, Integer> wayTime = prepareNodes(start, nodes);
        HashSet<Node> passed = new HashSet<>();
        this.step(wayTime, start, passed);
        return prepareFinalView(wayTime);
    }

    /**
     * Method for prepare final array with node-to-way times
     * @param wayTime is a hashmap with nodes and there most quick times of ways
     * @return prepared array with all ways
     */
    private int[][] prepareFinalView(HashMap<Node, Integer> wayTime) {
        int[][] result = new int[1][wayTime.size()];
        int count = 0;
        for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            result[0][count++] = wayTime.get(entry.getValue());
        }
        return result;
    }

    /**
     * step of algorhythm, that gets at list one time of node ways
     * @param wayTime hashmap with nodes and times to go
     * @param start start node, from where we're going
     * @param passed nodes, where we was
     */
    public void step(HashMap<Node, Integer> wayTime, Node start, HashSet<Node> passed) {
        if (!passed.contains(start)) {
            Set<Node> neighbours = new LinkedHashSet<>();
            for (int i = 0; i < start.getWays().size(); i++) {
                Way way = start.getWays().get(i);
                int destiny = way.getFirst() == start.getCount() ? way.getSecond() : way.getFirst();
                Node dest = nodes.get(destiny);
                neighbours.add(dest);
                int expectedTimeOfPath = wayTime.get(start) + way.getWeight();
                if (wayTime.get(dest) != Integer.MAX_VALUE) {
                    int currentCountOfDest = wayTime.get(dest);
                    if (currentCountOfDest > expectedTimeOfPath) {
                        wayTime.put(dest, expectedTimeOfPath);
                    }
                } else {
                    wayTime.put(dest, expectedTimeOfPath);
                }
            }
            passed.add(start);
            Iterator<Node> iterator = neighbours.iterator();
            while (iterator.hasNext()) {
                Node temp = iterator.next();
                this.step(wayTime, temp, passed);
            }
        }
    }

    /**
     * Method for prepare all nodes to try-to-find-the-way action
     * @param start is a Node from we we're going to start
     * @param nodes all nodes
     * @return HashMap with <node, max-possible-time to walk>
     */
    private HashMap<Node, Integer> prepareNodes(Node start, HashMap<Integer, Node> nodes) {
        HashMap<Node, Integer> result = new HashMap<>();
        result.put(start, 0);
        for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            Node temp = entry.getValue();
            if (temp.getCount() != start.getCount()) {
                result.put(temp, Integer.MAX_VALUE);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Way way12 = new Way(1, 2, 7);
        Way way13 = new Way(3, 1, 9);
        Way way16 = new Way(6, 1, 14);
        Way way23 = new Way(2, 3, 10);
        Way way24 = new Way(4, 2, 15);
        Way way34 = new Way(3, 4, 11);
        Way way36 = new Way(3, 6, 2);
        Way way45 = new Way(5, 4, 6);
        Way way56 = new Way(5, 6, 9);
//        Node node1 = new Node(1, way12, way23, way13, way16, way24, way34, way36, way45, way56);
//        Node node2 = new Node(2, way12, way23, way13, way16, way24, way34, way36, way45, way56);
//        Node node3 = new Node(3, way12, way23, way13, way16, way24, way34, way36, way45, way56);
//        Node node4 = new Node(4, way12, way23, way13, way16, way24, way34, way36, way45, way56);
//        Node node5 = new Node(5, way12, way23, way13, way16, way24, way34, way36, way45, way56);
//        Node node6 = new Node(6, way12, way23, way13, way16, way24, way34, way36, way45, way56);
        int[][] parameter = new int[][]{{0, 1, 1, 0, 0, 1}, {1, 0, 1, 1, 0, 0}, {1, 1, 0, 1, 0, 1}, {0, 1, 1, 0, 1, 0}, {0, 0, 0, 1, 0, 1}, {1, 0, 1, 0, 1, 0}};
        Dijkstra dij = new Dijkstra(parameter);
//        dij.setNodes(new HashSet<>(Arrays.asList(node1, node2, node3, node4, node5, node6)));
        dij.setWays(new HashSet<>(Arrays.asList(way12, way23, way13, way16, way24, way34, way36, way45, way56)));
        dij.setNodes();
        int[][] res = dij.find(1);
        HashSet<Integer> results = new HashSet<>();
        for (int i = 0; i < res[0].length; i++) {
            results.add(res[0][i]);
        }
        assert results.contains(0);
        assert results.contains(7);
        assert results.contains(9);
        assert results.contains(11);
        assert results.contains(20);
    }
}
