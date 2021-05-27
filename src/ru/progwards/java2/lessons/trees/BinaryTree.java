package ru.progwards.java2.lessons.trees;

public class BinaryTree<K extends Comparable<K>, V> {
    TreeLeaf<K, V> root;

    public void add(TreeLeaf<K, V> leaf) throws Exception {
        if (root == null) {
            this.root = leaf;
        } else {
            root.find(leaf.getKey()).add(leaf);
        }
    }

    public V find(K key) {
        if (this.root == null) {
            return null;
        } else {
            TreeLeaf<K, V> found = root.find(key);
            return found.getKey().compareTo(key) == 0 ? found.getValue() : null;
        }
    }

    public V delete(K key) throws Exception {
        TreeLeaf<K, V> treeLeaf = this.internalDelete(key);
        return treeLeaf.getValue();
    }

    private TreeLeaf<K, V> internalDelete(K key) throws Exception {
        if (root == null) {
            throw new Exception("Root is null");
        }
        TreeLeaf<K, V> found = root.find(key);
        int cmp = found.getKey().compareTo(key);
        if (cmp != 0) {
            throw new Exception("Not exists");
        }
        if (found.parent == null) {
            if (found.right != null) {
                root = found.right;
                if (found.left != null) {
                    this.add(found.left);
                }
            } else if (found.left != null) {
                root = found.left;
            }
        } else {
            found.delete();
        }
        return found;
    }

    public TreeIterator<K, V> getIterator() {
        return new TreeIterator<>(root);
    }

    public static void main(String[] args) throws Exception {
        BinaryTree<Integer, Integer> tree = new BinaryTree<>();
        tree.add(new TreeLeaf<>(0, 0));
        tree.add(new TreeLeaf<>(1, 1));
        tree.add(new TreeLeaf<>(2, 2));
        tree.add(new TreeLeaf<>(-1, -1));
        TreeIterator<Integer, Integer> iterator = tree.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
