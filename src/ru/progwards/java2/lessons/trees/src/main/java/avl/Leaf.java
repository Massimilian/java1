package avl;

public class Leaf<V extends Comparable<V>> {
    private V v = null; // само значение
    private Leaf<V> right = null; // правый лист
    private Leaf<V> left = null; // левый лист
    private Leaf<V> parent = null; // родительский лист
    private int balance = 0; // баланс
    private int rightDepth = 0; // глубина правого поддерева
    private int leftDepth = 0; // глубина левого поддерева

    public int getRightDepth() {
        return rightDepth;
    }

    public void setRightDepth(int rightDepth) {
        this.rightDepth = rightDepth;
    }

    public int getLeftDepth() {
        return leftDepth;
    }

    public void setLeftDepth(int leftDepth) {
        this.leftDepth = leftDepth;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    public Leaf<V> getRight() {
        return right;
    }

    public void setRight(Leaf<V> right) {
        this.right = right;
    }

    public Leaf<V> getLeft() {
        return left;
    }

    public void setLeft(Leaf<V> left) {
        this.left = left;
    }

    public Leaf<V> getParent() {
        return parent;
    }

    public void setParent(Leaf<V> parent) {
        this.parent = parent;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public V add(V v) {
        if (this.v == null) { // работа с новым листом
            this.v = v;
        } else {
            if (v.compareTo(this.v) < 0) {
                if (this.getLeft() == null) {
                    this.setLeft(new Leaf<>()); // создаём новый лист
                    this.getLeft().setParent(this); // устанавливаем родителя
                    this.renewBalance(-1); // перебалансируем
                }
                this.getLeft().add(v); // устанавливаем значение
            }
            if (v.compareTo(this.v) > 0) {
                if (this.getRight() == null) {
                    this.setRight(new Leaf<>());
                    this.getRight().setParent(this);
                    this.renewBalance(1);
                }
                this.getRight().add(v);
            }
        }
        return v;
    }

    public Leaf<V> find(V v) {
        Leaf result = null;
        if (this.getV().equals(v)) {
            result = this;
        } else if (this.getLeft() != null && this.getV().compareTo(v) > 0) {
            result = this.getLeft().find(v);
        } else if (this.getRight() != null && this.getV().compareTo(v) < 0) {
            result = this.getRight().find(v);
        }
        return result;
    }

    private void renewBalance(int i) {
        if (i == -1) {
            this.setLeftDepth(this.getLeftDepth() + 1);
        } else {
            this.setRightDepth(this.getRightDepth() + 1);
        }
        this.setBalance(this.getRightDepth() - this.getLeftDepth());
        if (this.getParent() != null) {
            if (this.getParent().getLeft() != null && this.getParent().getLeft().equals(this) && (this.getRight() == null || this.getRightDepth() - this.getLeftDepth() != 0)) {
                this.getParent().renewBalance(-1);
            }
            if (this.getParent().getRight() != null && this.getParent().getRight().equals(this) && (this.getLeft() == null || this.getRightDepth() - this.getLeftDepth() != 0)) {
                this.getParent().renewBalance(1);
            }
        }
    }

    public void rebalance(Leaf<V> l) {
        if (l.getBalance() < -1 || l.getBalance() > 1) {
            boolean isChanged = false;
            if (l.getBalance() <= -2 && l.getLeft().getBalance() < 0) {
                l.smallRightTurn(l, l.getLeft(), l.getLeft().getRight());
                isChanged = true;
            }
            if (l.getBalance() >= 2 && l.getRight().getBalance() > 0) {
                l.smallLeftTurn(l, l.getRight(), l.getRight().getLeft());
                isChanged = true;
            }
            if (l.getBalance() <= -2 && l.getLeft().getBalance() > 0) {
                this.bigRightTurn(l, l.getLeft(), l.getLeft().getRight(), l.getLeft().getRight().getRight(), l.getLeft().getRight().getLeft());
                isChanged = true;
            }
            if (l.getBalance() >= 2 && l.getRight().getBalance() < 0) {
                l.bigLeftTurn(l, l.getRight(), l.getRight().getLeft(), l.getRight().getLeft().getLeft(), l.getRight().getLeft().getRight());
                isChanged = true;
            }
            if (isChanged) {
                l.balanceUp(l);
                l = l.getParent();
            }
        }
        if (l.getParent() != null) {
            this.rebalance(l.getParent());
        }
    }

    private void bigLeftTurn(Leaf p, Leaf q, Leaf s, Leaf b, Leaf c) {
        this.smallRightTurn(q, s, c);
        this.smallLeftTurn(p, s, b);
    }

    private void bigRightTurn(Leaf p, Leaf q, Leaf s, Leaf b, Leaf c) {
        this.smallLeftTurn(q, s, c);
        this.smallRightTurn(p, s, b);
    }

    private void smallLeftTurn(Leaf y, Leaf x, Leaf b) {
        Leaf<V> bigParent = y.getParent();
        x.setLeft(y);
        y.setParent(x);
        y.setRight(b);
        if (b != null) {
            b.setParent(y);
        }
        if (bigParent != null) {
            if (bigParent.getRight() != null && bigParent.getRight().equals(y)) {
                bigParent.setRight(x);
            } else {
                bigParent.setLeft(x);
            }
            x.setParent(bigParent);
        } else {
            x.setParent(null);
        }
        y.setRightDepth(b == null ? 0 : Math.max(b.leftDepth, b.rightDepth) + 1);
        y.balancing();
        x.setLeftDepth(Math.max(y.getLeftDepth(), y.getRightDepth()) + 1);
        x.balancing();
    }

    private void smallRightTurn(Leaf y, Leaf x, Leaf b) {
        Leaf<V> bigParent = y.getParent();
        x.setRight(y);
        y.setParent(x);
        y.setLeft(b);
        if (b != null) {
            b.setParent(y);
        }
        if (bigParent != null) {
            if (bigParent.getRight().equals(y)) {
                bigParent.setRight(x);
            } else {
                bigParent.setLeft(x);
            }
            x.setParent(bigParent);
        } else {
            x.setParent(null);
        }
        y.setLeftDepth(b == null ? 0 : Math.max(b.getLeftDepth(), b.getRightDepth()) + 1);
        y.balancing();
        x.setRightDepth(Math.max(y.getLeftDepth(), y.getRightDepth()) + 1);
        x.balancing();
    }

    public void balanceUp(Leaf bal) {
        if (bal.getParent() != null) {
            Leaf balance = bal.getParent();
            balance.setLeftDepth(balance.getLeft() == null ? 0 : (Math.max(balance.getLeft().getRightDepth(), balance.getLeft().getLeftDepth()) + 1));
            balance.setRightDepth(balance.getRight() == null ? 0 : (Math.max(balance.getRight().getRightDepth(), balance.getRight().getLeftDepth()) + 1));
            balance.balancing();
            this.balanceUp(balance);
        }
    }

    private void balancing() {
        this.setBalance(this.getRightDepth() - this.getLeftDepth());
    }
}
