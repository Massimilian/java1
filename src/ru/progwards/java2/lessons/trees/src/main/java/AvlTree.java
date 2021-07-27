import java.util.function.Consumer;

/**
 * Avl tree for work with different types of data
 *
 * @param <K> is key
 * @param <V> is value (data)
 */
public class AvlTree<K extends Comparable<K>, V> {
    AvlTreeLeaf<K, V> root;

    /**
     * method for find the place for data
     *
     * @param leaf is the prepared AvlTreeLeaf for add
     * @throws Exception is something's going wrong
     */
    private void easyAdd(AvlTreeLeaf<K, V> leaf) throws Exception {
        if (root == null) {
            this.root = leaf;
        } else {
            root.find(leaf.getKey()).add(leaf);
        }
    }

    /**
     * Method for add the prepared AvlTreeLeaf
     *
     * @param atl is a AvlTreeLeaf for add
     * @throws Exception if something's gping wrong
     */
    public void put(AvlTreeLeaf<K, V> atl) throws Exception {
        this.put(atl.getKey(), atl.getValue());
    }

    /**
     * Main method for add the prepared AvlTreeLeaf
     *
     * @param key   Key for leaf
     * @param value Value for leaf
     * @throws Exception if something is wrong
     */
    public void put(K key, V value) throws Exception {
        AvlTreeLeaf atl = new AvlTreeLeaf(key, value);
        this.easyAdd(atl);
        recheckRightLeftLengths(atl);
        if ((atl = balanceIncorrect(atl)) != null) {
            balance(atl);
            reNewRoot();
        }
    }

    /**
     * Method for get the value of a leaf with the same key
     *
     * @param key is a key of leaf for search
     * @return Value of found leaf
     */
    public V find(K key) {
        return (V) this.findLeaf(key).getValue();
    }

    /**
     * Method that returns the leaf with the key
     *
     * @param key that the searched leaf has
     * @return AvlTreeLeaf with that key
     */
    public AvlTreeLeaf findLeaf(K key) {
        if (this.root == null) {
            return null;
        } else {
            AvlTreeLeaf<K, V> found = root.find(key);
            return found.getKey().compareTo(key) == 0 ? found : null;
        }
    }

    /**
     * Method for change the key of the leaf
     * @param oldKey old key value
     * @param newKey new key value
     */
    public void change(K oldKey, K newKey) {
        AvlTreeLeaf leaf = this.findLeaf(oldKey);
        if (leaf != null) {
            leaf.setKey(newKey);
            try {
                this.put(leaf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void process(Consumer<TreeLeaf<K,V>> consumer) {
        
    }

    /**
     * Main method for delete the leaf by key
     *
     * @param key for delete
     * @return value of deleted leaf
     */
    public V delete(K key) {
        V result;
        AvlTreeLeaf forDelete = findLeaf(key);
        AvlTreeLeaf changer = null;
        boolean isRootDeleting = this.root.getKey().equals(key);
        if (isRootDeleting) {
            if (root.getRight() == null && root.getLeft() == null) { // корень - последнее, что осталось в дереве
                this.root = null;
            } else if (root.getRight() == null && root.getLeft() != null) { // осталbсь только корень и левое поддерево, удаляем корень
                this.root = root.getLeft();
                changer = this.root;
            } else if (root.getRight() != null && root.getLeft() == null) { // осталbсь только корень и правое поддерево, удаляем корень
                this.root = root.getRight();
                changer = this.root;
            } else { // просто удаляем корень
                AvlTreeLeaf deletedRoot = this.root;
                deleteRoot(root);
                if (deletedRoot.getKey().compareTo(this.root.getKey()) > 0) {
                    changer = findSomeSmaller(deletedRoot);
                } else {
                    changer = findSomeBigger(deletedRoot);
                }
            }
        } else { // удаляем простой элемент
            if (forDelete.getRight() == null && forDelete.getLeft() == null) { // если у удаляемого листа нет потомков
                changer = forDelete.getParent();
                if (forDelete.getParent().getRight() == forDelete) { // обнуляем значение и устанавливаем changer на предыдущее значение
                    changer.setRight(null);
                } else {
                    changer.setLeft(null);
                }
            } else {
                changer = deleteRoot(forDelete);
            }
        }
        if (this.root != null) {
            renewTreeBalanceByParents(changer, true);
            if ((changer = balanceIncorrect(changer)) != null) {
                balance(changer);
                reNewRoot();
            }
        }
        result = (V) forDelete.getValue();
        return result;
    }

    private AvlTreeLeaf deleteRoot(AvlTreeLeaf root) {
        AvlTreeLeaf checkable;
        boolean isSmaller = false;
        if (root.getRight() == null) { // проверяем значение правой ветки на ноль
            checkable = findSomeSmaller(root); // если правой ветки нет - значит берём максимальное значение из левой ветки (у которого правого потомка быть не может)
            isSmaller = true;
        } else {
            checkable = findSomeBigger(root); // иначе берём минимальное значение из правой вправоетки (левой ветки быть не может)
        }
        AvlTreeLeaf result = checkable.getParent();
        if (isSmaller) { // выписываем checkable, при необходимости подставляем туда ту ветку, которая есть (одной из веток быть не может)
            if (result.getLeft() == checkable) {
                result.setLeft(checkable.getLeft());
                if (checkable.getLeft() != null) {
                    checkable.getLeft().setParent(result);
                }
            } else {
                result.setRight(checkable.getLeft());
                if (checkable.getLeft() != null) {
                    checkable.getLeft().setParent(result);
                }
            }
        } else {
            if (result.getLeft() == checkable) {
                result.setLeft(checkable.getRight());
                if (checkable.getRight() != null) {
                    checkable.getRight().setParent(result);
                }
            } else {
                result.setRight(checkable.getRight());
                if (checkable.getRight() != null) {
                    checkable.getRight().setParent(result);
                }
            }
        }
        replace(checkable, root);
        if (result == root) {
            result = checkable;
        }
        changeRootIfNeed(root, checkable);
        return result;
    }

    private void changeRootIfNeed(AvlTreeLeaf root, AvlTreeLeaf checkable) {
        if (this.root == root) {
            this.root = checkable;
        }
    }

    private void replace(AvlTreeLeaf toPut, AvlTreeLeaf<K, V> toDelete) {
        toPut.setRight(toDelete.getRight());
        if (toDelete.getRight() != null) {
            toDelete.getRight().setParent(toPut);
        }
        toPut.setLeft(toDelete.getLeft());
        if (toDelete.getLeft() != null) {
            toDelete.getLeft().setParent(toPut);
        }
        toPut.setParent(toDelete.getParent());
        if (toDelete.getParent() != null) {
            if (toDelete.getParent().getRight() == toDelete) {
                toDelete.getParent().setRight(toPut);
            } else {
                toDelete.getParent().setLeft(toPut);
            }
        }
    }

    /**
     * Method for renew root if need
     */
    private void reNewRoot() {
        if (this.root.getParent() != null) {
            this.root = root.getParent();
            reNewRoot();
        }
    }

    /**
     * Method for renovate all parents balance
     *
     * @param checkParent AvlTreeLeaf for rebalance
     * @param firstStep   is this the first leaf
     */
    private void renewTreeBalanceByParents(AvlTreeLeaf checkParent, boolean firstStep) {
        int cpBalance = checkParent.getBalance();
        checkParent.renovateBalance();
        boolean cont = true;
        if (!firstStep) {
            cont = cpBalance != checkParent.getBalance();
        }
        if (checkParent.getParent() != null && cont) {
            renewTreeBalanceByParents(checkParent.getParent(), false);
        }
    }

    /**
     * Method for check all tree balance
     *
     * @param atl root of the tree
     */
    private void renewAllTreeBalance(AvlTreeLeaf atl) {
        atl.renovateBalance();
        if (atl.getRight() != null) {
            renewAllTreeBalance(atl.getRight());
        }
        if (atl.getLeft() != null) {
            renewAllTreeBalance(atl.getLeft());
        }
    }

    /**
     * Method for find some smaller (and more down) leaf
     *
     * @param forDelete is a leaf from what we search
     * @return the leaf with some smaller value
     */
    private AvlTreeLeaf findSomeBigger(AvlTreeLeaf forDelete) {
        AvlTreeLeaf result = forDelete;
        if (forDelete.getRight() != null) {
            result = forDelete.getRight();
        }
        while (result.getLeft() != null) {
            result = result.getLeft();
        }
        return result;
    }

    /**
     * Method for find some bigger (amd more down) leaf
     *
     * @param forDelete is a leaf from what we search
     * @return the leaf with some bigger value
     */
    private AvlTreeLeaf findSomeSmaller(AvlTreeLeaf forDelete) {
        AvlTreeLeaf result = forDelete;
        if (forDelete.getLeft() != null) {
            result = forDelete.getLeft();
        }
        while (result.getRight() != null) {
            result = result.getRight();
        }
        return result;
    }

    /**
     * Method for balance the tree
     *
     * @param atl is a root of balanced tree
     */
    private void balance(AvlTreeLeaf atl) {
        boolean passed = false;
        AvlTreeLeaf father = atl.getParent();
        if (atl.getBalance() == 2) {
            AvlTreeLeaf x = atl.getRight();
            if (x.getBalance() >= 0) {
                AvlTreeLeaf b = x.getLeft();
                smallLeftTurn(father, b, x, atl);
                passed = true;
            }
            if (!passed && x.getBalance() == -1) {
                AvlTreeLeaf q = x;
                AvlTreeLeaf s = q.getLeft();
                AvlTreeLeaf b = s.getLeft();
                AvlTreeLeaf c = s.getRight();
                smallRightTurn(atl, c, s, q);
                smallLeftTurn(father, b, s, atl);
                passed = true;
            }
        }
        if (!passed && atl.getBalance() == -2) {
            AvlTreeLeaf x = atl.getLeft();
            if (x.getBalance() <= -1) {
                AvlTreeLeaf b = x.getRight();
                smallRightTurn(father, b, x, atl);
                passed = true;
            }
            if (!passed && x.getBalance() == 1) {
                AvlTreeLeaf q = x;
                AvlTreeLeaf s = q.getRight();
                AvlTreeLeaf b = s.getRight();
                AvlTreeLeaf c = s.getLeft();
                smallLeftTurn(atl, c, s, q);
                smallRightTurn(father, b, s, atl);
                passed = true;
            }
        }
        if (passed) {
            if (father == null) {
                father = atl.getParent();
            }
            renewAllTreeBalance(father);
            renewTreeBalanceByParents(father, true);
        }
    }

    /**
     * Small left turn
     *
     * @param father root father
     * @param b      "right-left" grandson of root
     * @param x      "right" son of root
     * @param y      root
     */
    private void smallLeftTurn(AvlTreeLeaf father, AvlTreeLeaf b, AvlTreeLeaf x, AvlTreeLeaf y) {
        AvlTreeLeaf nb = b;
        x.setLeft(y);
        y.setRight(nb);
        if (nb != null) {
            nb.setParent(y);
        }
        y.setParent(x);
        x.setParent(father);
        if (father == null) {
            root = x;
            x.parent = null;
        } else {
            if (y == father.getLeft()) {
                father.setLeft(x);
            } else {
                father.setRight(x);
            }
            x.parent = father;
        }
    }

    /**
     * small right tirn
     *
     * @param father is a root father
     * @param b      "left-right" grandson of root
     * @param x      "left" son of root
     * @param y      root
     */
    private void smallRightTurn(AvlTreeLeaf father, AvlTreeLeaf b, AvlTreeLeaf x, AvlTreeLeaf y) {
        AvlTreeLeaf nb = b;
        x.setRight(y);
        y.setLeft(nb);
        if (nb != null) {
            nb.setParent(y);
        }
        y.setParent(x);
        x.setParent(father);
        if (father == null) {
            root = x;
            x.parent = null;
        } else {
            if (y == father.getRight()) {
                father.setRight(x);
            } else {
                father.setLeft(x);
            }
            x.parent = father;
        }
    }

    /**
     * Method for recheck balance by parents
     *
     * @param atl for recheck
     */
    private void recheckRightLeftLengths(AvlTreeLeaf atl) {
        if (atl.getParent() != null) {
            atl.getParent().renovateBalance();
            recheckRightLeftLengths(atl.getParent());
        }
    }

    /**
     * Method for find leaf with incorrect balance
     *
     * @param atl for check
     * @return leaf with incorrect balance or null
     */
    private AvlTreeLeaf balanceIncorrect(AvlTreeLeaf atl) {
        if (atl == null || !atl.reCheckBalance()) {
            return atl;
        } else {
            return balanceIncorrect(atl.getParent());
        }
    }
}
