import java.util.Iterator;

/**
 * Tree iterator with step-by-step work
 *
 * @param <K> key
 * @param <V> value
 */
public class TreeIteratorTwo<K extends Comparable<K>, V> implements Iterator {
    private final TreeLeaf root;
    private TreeLeaf current;
    private TreeLeaf previous = null;
    private int twoSteps;

    /**
     * Constructor with root preparing
     *
     * @param tree is Binary tree for searching elements
     */
    public TreeIteratorTwo(BinaryTree tree) {
        this.root = tree.root;
        current = root;
        twoSteps = root.getRight() != null ? 3 : 2;
    }
    /**
     * Constructor with root preparing
     *
     * @param root is a leaf for searching elements
     */
    public <V, K extends Comparable<K>> TreeIteratorTwo(TreeLeaf<K, V> root) {
        this.root = root;
        current = root;
        twoSteps = root.getRight() != null ? 3 : 2;
    }

    /**
     * Main method to take next leaf
     *
     * @return next leaf in the tree, if have
     */
    @Override
    public TreeLeaf next() {
        change();
        return current;
    }

    /**
     * Method for take next leaf, that is more close to previous leaf
     */
    private void change() {
        boolean step = false;

        if (!step && previous == null && current.getLeft() != null) {
            current = mostLeft(current);
            previous = current.getParent();
            step = true;
        }

        if (!step && previous == null && current.getRight() == null && current.getLeft() == null) {
            step = true;
        }

        if (!step && current.getLeft() == null) {
            if (previous != null) {
                if (current.getRight() == null) {
                    if (!step && previous.getRight() == current) {
                        previous = mostRightFather(current);
                        current = previous.getParent();
                        step = true;
                    }
                    if (!step && previous.getLeft() == current) {
                        previous = current;
                        current = current.getParent();
                        step = true;
                    }
                } else if (!step && (previous == current.getParent() || current == previous)) {
                    current = current.getRight();
                    current = mostLeft(current);
                    previous = current.getParent();
                    step = true;
                }
            } else {
                previous = current;
                step = true;
            }
        }

        if (!step && current.getRight() == null) {
            if (current.getLeft() != null) {
                if (!step && previous == current.getLeft() && current.getParent().getLeft() == current) {
                    previous = current;
                    current = current.getParent();
                    step = true;
                }
                if (!step && previous == current.getLeft() && current.getParent().getRight() == current) {
                    previous = mostRightFather(current);
                    current = previous.getParent();
                    step = true;
                }
            }
            if (!step && current.getRight() == previous) {
                previous = mostRightFather(current);
                current = previous.getParent();
                step = true;
            }
        }
        if (!step && current.getRight() != null && current.getLeft() != null && previous == current.getLeft()) {
            current = current.getRight();
            current = mostLeft(current);
            previous = current.getParent();
        }
    }

    /**
     * Method to find most left position
     *
     * @param curr is a leaf, from what we start the searching
     * @return most leaf leaf of the tree
     */
    private TreeLeaf mostLeft(TreeLeaf curr) {
        while (curr.getLeft() != null) {
            curr = curr.getLeft();
        }
        return curr;
    }

    /**
     * Method to find more right father of the tree (the root of the subtree)
     *
     * @param curr if a leaf
     * @return most right father-leaf
     */
    private TreeLeaf mostRightFather(TreeLeaf curr) {
        while (curr.getParent().getRight() == curr) {
            curr = curr.getParent();
        }
        return curr;
    }

    /**
     * Main method to take information if has more leafs
     *
     * @return is has next value
     */
    @Override
    public boolean hasNext() {
        boolean reallyHas = true;
        if (current != root && current.getParent().getRight() == current && current.getRight() == null) {
            reallyHas = !tryRights(current);
        }
        if (current == root) {
            twoSteps--;
            reallyHas = !(twoSteps == 0);
        }
        return reallyHas;
    }

    /**
     * Method to try to take right parent, if have
     *
     * @param parent leaf, who has right parent
     * @return has right parent
     */
    private boolean tryRights(TreeLeaf<K, V> parent) {
        while (parent.getParent() != null && parent.getParent().getRight() == parent) {
            parent = parent.getParent();
        }
        return parent == this.root;
    }
}
