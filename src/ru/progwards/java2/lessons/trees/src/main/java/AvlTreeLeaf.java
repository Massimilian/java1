import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Avl tree leaf
 *
 * @param <K> key for get the value
 * @param <V> value that the leaf stores
 */
public class AvlTreeLeaf<K extends Comparable<K>, V> {
    private K key;
    private V value;
    AvlTreeLeaf<K, V> parent;
    AvlTreeLeaf<K, V> right;
    AvlTreeLeaf<K, V> left;
    private int balance = 0;
    private int rightLength = 0;
    private int leftLength = 0;

    /**
     * Main constructor
     *
     * @param key   is key
     * @param value is value
     */
    public AvlTreeLeaf(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public AvlTreeLeaf<K, V> getParent() {
        return parent;
    }

    public void setParent(AvlTreeLeaf<K, V> parent) {
        this.parent = parent;
    }

    public AvlTreeLeaf<K, V> getRight() {
        return right;
    }

    public void setRight(AvlTreeLeaf<K, V> right) {
        this.right = right;
    }

    public AvlTreeLeaf<K, V> getLeft() {
        return left;
    }

    public void setLeft(AvlTreeLeaf<K, V> left) {
        this.left = left;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Method for find the leaf
     *
     * @param added is a key for find the leaf
     * @return
     */
    public AvlTreeLeaf<K, V> find(K added) {
        int cmp = this.key.compareTo(added);
        if (cmp > 0) {
            if (this.left != null) {
                return this.left.find(added);
            } else {
                return this;
            }
        } else if (cmp < 0) {
            if (this.right != null) {
                return this.right.find(added);
            } else {
                return this;
            }
        }
        return this;
    }

    /**
     * Method for place the leaf and put its relations
     *
     * @param added is a leaf for add
     * @throws Exception when is something's going wrong
     */
    public void add(AvlTreeLeaf<K, V> added) throws Exception {
        int cmp = this.key.compareTo(added.getKey());
        if (cmp == 0) {
            throw new Exception("Tree exception");
        } else if (cmp > 0) {
            left = added;
            added.parent = this;
        } else {
            right = added;
            added.parent = this;
        }
    }

    /**
     * Method for delete leaf and for keep all relations clear
     *
     * @throws Exception if something's wrong
     */
    public void delete() throws Exception {
        if (parent.right == this) {
            parent.right = this.right;
            if (right != null) {
                this.right.parent = this.parent;
            }
            if (left != null) {
                parent.find(left.getKey()).add(left);
            }
        }
        if (parent.left == this) {
            parent.left = this.left;
            if (left != null) {
                this.left.parent = this.parent;
            }
            if (right != null) {
                parent.find(right.getKey()).add(right);
            }
        }
    }

    public String toString() {
        return "Value = " + value + "; key = " + key + ";";
    }

    /**
     * This method was in Binary tree
     *
     * @param consumer
     */
    @Deprecated
    public void process(Consumer<AvlTreeLeaf<K, V>> consumer) {
        if (this.left != null) {
            this.left.process(consumer);
        }
        consumer.accept(this);
        if (this.right != null) {
            this.right.process(consumer);
        }
    }

    /**
     * Method for checkBalance
     *
     * @return truu if correct, false if needs to be rebalanced
     */
    public boolean reCheckBalance() {
        return Math.abs(this.getBalance()) <= 1;
    }

    /**
     * Method for rebalance this leaf
     */
    public void renovateBalance() {
        int right = 0;
        int left = 0;
        ArrayList<Integer> numbers;
        if (this.right != null) {
            numbers = new ArrayList<>();
            right = findDepth(this.right, numbers, right + 1);
        }
        if (this.left != null) {
            numbers = new ArrayList<>();
            left = findDepth(this.left, numbers, left + 1);
        }
        this.rightLength = right;
        this.leftLength = left;
        this.setBalance(this.rightLength - this.leftLength);
    }

    /**
     * Method for find the depth of the branch
     *
     * @param leaf  the branch
     * @param list  is a number of depths
     * @param depth is current depth
     * @return most deep depth
     */
    private int findDepth(AvlTreeLeaf<K, V> leaf, ArrayList<Integer> list, int depth) {
        list.add(depth);
        if (leaf.getRight() != null) {
            findDepth(leaf.getRight(), list, depth + 1);
        }
        if (leaf.getLeft() != null) {
            findDepth(leaf.getLeft(), list, depth + 1);
        }
        return list.stream().max(Integer::compareTo).get();
    }
}