package ru.progwards.java2.lessons.gc;

import java.util.ArrayList;

public class PriorQueue <T extends CounterForCompare> {
    ArrayList<ArrayList<T>> queue = new ArrayList<>();

    public boolean add(T t) {
        boolean added = false;
        int pos = 0;
        while(!added && queue.size() != 0 && queue.size() != pos) {
            T temp = queue.get(pos).get(0);
            if (temp.compareTo(t) == 1) {
                added = addNew(t, pos);
            } else if (temp.compareTo(t) == 0){
                queue.get(pos).add(t);
                added = true;
            } else {
                pos++;
            }
        }
        if (!added) {
            added = addNew(t, pos);
        }
        return added;
    }

    public boolean addNew(T t, int pos) {
        ArrayList<T> list = new ArrayList<>();
        list.add(t);
        queue.add(pos, list);
        return true;
    }

    public T delete(int count) {
        T t = null;
        if (queue.size() != 0) {
            for (int i = 0; i < queue.size(); i++) {
                if (queue.get(i).get(0).getCounter() == count) {
                    t = queue.get(i).remove(0);
                    if (queue.get(i).size() == 0) {
                        queue.remove(i);
                    }
                }
            }
        }
        return t;
    }

    public void clear() {
        this.queue = new ArrayList<>();
    }

    public int size() {
        return queue.size();
    }

    public ArrayList<T> get(int pos) {
        return queue.get(pos);
    }
}
