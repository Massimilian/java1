import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Main {
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Way> ways = new ArrayList<>();

    public static void main(String[] args) {
        Main m = new Main();
        Point one = new Point("1");
        Point two = new Point("2");
        Point three = new Point("3");
        Point four = new Point("4");
        Point five = new Point("5");

        Collections.addAll(m.points, one, two, three, four, five);

        Way a = new Way(one, two);
        Way b = new Way(one, three);
        Way c = new Way(one, four);
        Way d = new Way(two, four);
        Way e = new Way(two, five);
        Way f = new Way(three, four);
        Way g = new Way(four, five);

        Collections.addAll(m.ways, a, b, c, d, e, f, g);

        for (int i = 0; i < m.points.size(); i++) {
            System.out.print(m.points.get(i).name + " ");
            for (int j = 0; j < m.points.size(); j++) {
                if (i != j) {
                    Way temp = new Way(m.points.get(i), m.points.get(j));
                    if (m.ways.contains(temp)) {
                        System.out.print(temp.second.name + " ");
                    } else {
                        System.out.print("0 ");
                    }
                }
            }
            System.out.println();
        }
    }
}

// класс вершин
class Point {
    public Point(String name) {
        this.name = name;
    }

    String name;
}

// класс ребра
class Way {
    public Way(Point first, Point second) {
        this.first = first;
        this.second = second;
    }
    Point first;
    Point second;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Way way = (Way) o;
        return (Objects.equals(first, way.first) && Objects.equals(second, way.second)) ||
                (Objects.equals(second, way.first) && Objects.equals(first, way.second));
    }
}