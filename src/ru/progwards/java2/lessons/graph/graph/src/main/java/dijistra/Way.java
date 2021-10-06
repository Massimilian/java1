package dijistra;

/**
 * Class of edge of the graph
 */
public class Way implements Comparable<Way>{
    private int weight;
    private int first;
    private int second;

    public Way(int first, int second, int weight) {
        this.first = first;
        this.second = second;
        this.weight = weight;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Way o) {
        return Integer.compare(this.weight, o.weight);
    }
}
