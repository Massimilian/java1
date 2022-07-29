package discret;

public class BinTree {
    private static class Node {
        int info;
        Node left, right;
    }

    Node root = null;

    /**
     * Метод по подсчёт суммы значений info в каждом Node, которое лежит в диапазоне от from до to
     * @param from минимальное значение диапазона
     * @param to максимальное значение диапазона
     * @return итоговая сумма
     */
    public int sum(int from, int to) {
        return sumIt(root, 0, from, to);
    }

    /**
     * Метод рекурсивно проверяет значение на соответствие фильтру, если надо - прибавуляет значение, затем вызывает аналогичную функцию у дочерних Node
     * @param root - основной Node
     * @param result - текущая сумма подсчёта
     * @param from - минимальное значение фильтра
     * @param to - максимальное значение фильтра
     * @return - результат подсчёта
     */
    private int sumIt(Node root, int result, int from, int to) {
        if (root != null) {
            if (check(root.info, from, to)) {
                result += root.info;
            }
            result = sumIt(root.left, result, from, to);
            result = sumIt(root.right, result, from, to);
        }
        return result;
    }

    /**
     * Фильтр, проверяющий значение, что оно не выходит за пределы от from до to
     * @param forCheck - значение для проверки
     * @param from - минимальное значение
     * @param to - максимальное значение
     * @return - подходит или не подходит
     */
    private boolean check(int forCheck, int from, int to) {
        return forCheck >= from && forCheck <= to;
    }

    public static void main(String[] args) {
        BinTree bt = new BinTree();
        bt.root = new Node();
        bt.root.info = 5;
        bt.root.right = new Node();
        bt.root.right.info = 3;
        bt.root.left = new Node();
        bt.root.left.info = 7;
        bt.root.right.right = new Node();
        bt.root.right.right.info = 4;
        bt.root.right.left = new Node();
        bt.root.right.left.info = 8;
        bt.root.left.right = new Node();
        bt.root.left.right.info = 6;
        bt.root.left.right.left = new Node();
        System.out.println(bt.sum(3, 8) == 33);
        System.out.println(bt.sum(4, 8) == 30);
        System.out.println(bt.sum(3, 7) == 25);
    }
}
