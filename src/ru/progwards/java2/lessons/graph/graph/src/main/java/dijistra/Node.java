package dijistra;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class of Node of the graph
 */
public class Node {
    private int count;
    private ArrayList<Way> ways = new ArrayList<>();

    public Node(int count, Way... ways) {
        this.count = count;
        this.checkAndAdd(ways, this.count);
    }

    private void checkAndAdd(Way[] ways, int count) {
        for (int i = 0; i < ways.length; i++) {
            if (ways[i].getFirst() == count || ways[i].getSecond() == count) {
                this.ways.add(ways[i]);
            }
        }
        Collections.sort(this.ways);
    }

    public int getCount() {
        return count;
    }

    public ArrayList<Way> getWays() {
        return ways;
    }


}
