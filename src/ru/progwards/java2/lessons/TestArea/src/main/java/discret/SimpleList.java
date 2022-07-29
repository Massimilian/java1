package discret;

public class SimpleList {
    private static class Node {
        int info;
        Node next;
    }

    private Node head = null;

    /**
     * Меьлж для перераспределения позиций (Node) - в начале отрицательные, потом положительные с сохранением внутреннего порядка
     */
    public void separate() {
        stepByStep(head);
        System.out.println();
    }

    /**
     * Метод создаёт два списка - последвоательность отрицательных и положительных Node
     * @param head - головной Node списка
     */
    private void stepByStep(Node head) {
        Node minus = null; // будущая голова отрицательного списка
        Node plus = null; // будущая голова положительного списка
        Node starterMinus = findFirstMinus(head); // самый первый отрицательный Node списка
        Node starterPlus = findFirstPlus(head);// самый первый полоительный Node списка
        while (head != null) {
            if (head.info < 0) {
                if (minus == null) {
                    minus = head;
                } else {
                    minus.next = head;
                    minus = head;
                }
            } else {
                if (plus == null) {
                    plus = head;
                } else {
                    plus.next = head;
                    plus = head;
                }
            }
            head = head.next;
        }
        if (plus != null) {
            plus.next = null;
        }
        finalChange(starterMinus, minus, starterPlus);
    }

    /**
     * соединение полученных списков через "хвотс" отрицательного Node и первого Node положительного списка
     * @param head - голова списка
     * @param minus - последнее отрицательное значение
     * @param plus - первое полодительное значение
     */
    private void finalChange(Node head, Node minus, Node plus) {
        if (minus != null) {
            minus.next = plus;
        }
        if (head != null) {
            this.head = head;
        } else {
            this.head = plus;
        }
    }

    /**
     * поиск первого полодительного Node
     * @param head - голова списка
     * @return - первый полоительный Node
     */
    private Node findFirstPlus(Node head) {
        if (head != null && head.info < 0) {
            while (head != null &&head.info < 0) {
                head = head.next;
            }
        }
        return head;
    }

    /**
     * поиск первого отрицательного Node
     * @param head - голова списка
     * @return - первый отрицательный Node
     */
    private Node findFirstMinus(Node head) {
        while (head != null && head.info >= 0) {
            head = head.next;
        }
        return head;
    }


    public static void main(String[] args) {
        int val = 0;
        int[] ints = {8, 2, 1};
        SimpleList list = new SimpleList();
        Node node = new Node();
        node.info = ints[val++];
        list.head = node;
        for (int i = 0; i < ints.length - 1; i++) {
            Node temp = new Node();
            temp.info = ints[val++];
            node.next = temp;
            node = temp;
        }
        list.separate();
        System.out.println();
    }
}
