package Renata;

public class Node<V extends Comparable<V>> {
    public static void main(String[] args) {
        Node<Integer> node = new Node<>();
        System.out.println(node.add(3));
        System.out.println(node.add(1));
        System.out.println(node.add(5));
        System.out.println(node.add(2));
        System.out.println(node.add(4));
        System.out.println(node.add(0));
        System.out.println(node.add(1));
        System.out.println();
        System.out.println(node.get(3));
        System.out.println(node.get(1));
        System.out.println(node.get(5));
        System.out.println(node.get(2));
        System.out.println(node.get(4));
        System.out.println(node.get(0));
        System.out.println(node.get(11));
    }

    V value = null;
    Node<V> right = null;
    Node<V> left = null;

    public Node(V value) {
        this.value = value;
    }

    public Node() {
    }

    public boolean add(V v) {
        boolean result = false;
        if (value == null) {
            value = v;
            result = true;
        } else {
            if (v.compareTo(value) > 0) {
                if (right == null) {
                    Node<V> node = new Node<>(v);
                    this.right = node;
                    result = true;
                } else {
                    result = right.add(v);
                }
            }
            if (v.compareTo(value) < 0) {
                if (left == null) {
                    this.left = new Node<>(v);
                    result = true;
                } else {
                    result = left.add(v);
                }
            }
        }
        return result;
    }


    public boolean get(V v) {
        boolean result = false;
        if (this.value.compareTo(v) == 0) {
            result = true;
        } else if (this.value.compareTo(v) < 0) {
            if (this.right != null) {
                result = right.get(v);
            }
        } else if (this.value.compareTo(v) > 0) {
            if (this.left != null) {
                result = left.get(v);
            }
        }
        return result;
    }
}
