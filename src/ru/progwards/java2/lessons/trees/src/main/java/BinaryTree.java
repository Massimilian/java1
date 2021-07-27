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
        return (V) this.findLeaf(key).getValue();
    }


    public TreeLeaf findLeaf(K key) {
        if (this.root == null) {
            return null;
        } else {
            TreeLeaf<K, V> found = root.find(key);
            return found.getKey().compareTo(key) == 0 ? found : null;
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
            root.setParent(null); // new code(???)
            if (found.left == null && found.right == null) {
                root = null;
            }
        } else {
            found.delete();
        }
        return found;
    }

    public TreeIteratorTwo getIterator() {
        return new TreeIteratorTwo(root);
    }

}
