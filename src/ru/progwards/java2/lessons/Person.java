package ru.progwards.java2.lessons;

import java.util.List;

class OList<Integer> {
    class ListItem<Integer> {
        private int item;
        private ListItem<Integer> next;

        ListItem (Integer item) {
            this.item = (int) item;
        }

        int getItem() {
            return item;
        }

        void setNext(ListItem<Integer> item) {
            next = item;
        }

        ListItem<Integer> getNext() {
            return next;
        }
    }

    private ListItem<Integer> head;
    private ListItem<Integer> tail;

    void add(Integer item) {
        ListItem<Integer> li = new ListItem<>(item);
        if (head == null) {
            head = li;
            tail = li;
        } else {
            tail.setNext(li);
            tail = li;
        }
    }

    public static void main(String[] args) {
        OList ol = new OList();
        ol.add(1);
        ol.add(2);
        ol.add(3);
        ol.add(4);
    }
}