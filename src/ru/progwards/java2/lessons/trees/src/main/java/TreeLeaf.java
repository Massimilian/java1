import java.util.function.Consumer;

public class TreeLeaf<K extends Comparable<K>, V> {
    private K key;
    private V value;
    TreeLeaf<K, V> parent;
    TreeLeaf<K, V> right;
    TreeLeaf<K, V> left;

    public TreeLeaf(K key, V value) {
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

    public TreeLeaf<K, V> getParent() {
        return parent;
    }

    public void setParent(TreeLeaf<K, V> parent) {
        this.parent = parent;
    }

    public TreeLeaf<K, V> getRight() {
        return right;
    }

    public void setRight(TreeLeaf<K, V> right) {
        this.right = right;
    }

    public TreeLeaf<K, V> getLeft() {
        return left;
    }

    public void setLeft(TreeLeaf<K, V> left) {
        this.left = left;
    }

    public TreeLeaf<K, V> find(K added) {
        int cmp = this.key.compareTo(added);
        if (cmp > 0) {
            if (this.left != null) {
                return this.left.find(added);
            } else {
                return this;
            }
        } else if (cmp < 0){
            if (this.right != null) {
                return this.right.find(added);
            } else {
                return this;
            }
        }
        return this;
    }

    public void add(TreeLeaf<K, V> added) throws Exception {
        int cmp = this.key.compareTo(added.getKey());
        if (cmp == 0) {
            throw new Exception("Tree exception");
        } else if (cmp > 0){
            left = added;
            added.parent = this;
        } else {
            right = added;
            added.parent = this;
        }
    }

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
                parent.find (right.getKey()).add(right);
            }
        }
    }

    public String toString() {
        return "Value = " + value + "; key = " + key + ";";
    }

    public void process(Consumer<TreeLeaf<K, V>> consumer) {
        if (this.left != null) {
            this.left.process(consumer);
        }
        consumer.accept(this);
        if (this.right != null) {
            this.right.process(consumer);
        }
    }
}
