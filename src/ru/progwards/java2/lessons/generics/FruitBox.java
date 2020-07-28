package ru.progwards.java2.lessons.generics;

import java.util.ArrayList;

public class FruitBox {
    private ArrayList<Fruit> list = new ArrayList();

    public boolean add(ArrayList<? extends Fruit> news) {
        boolean added;
        if (list.isEmpty()) {
            list.addAll(news);
            added = true;
        } else {
            int x = 0;
            while (list.get(x) == null) {
                x++;
            }
            String[] temp = list.get(x).getClass().toString().split("\\Q.\\E");
            String test = temp[temp.length - 1];
            if (news.get(0).getClass().toString().matches(".*(" + test + ")")) {
                list.addAll(news);
                added = true;
            } else {
                added = false;
            }
        }
        return added;
    }

    public boolean add(Fruit fruit) {
        boolean added = false;
        if (list.isEmpty()) {
            added = true;
        } else {
            int x = 0;
            while (list.get(x) == null) {
                x++;
            }
            if (list.get(x).getClass().isInstance(fruit.getClass())) {
                added = true;
            }
        }
        if (added) {
            list.add(fruit);
        }
        return added;
    }


    public float getWeight() {
        return list.stream().map(o -> o.getWeight()).reduce(0f, (o1, o2) -> o1 + o2);
    }

    public int getCount() {
        return list.size();
    }

    public boolean moveTo(FruitBox fb) {
        boolean added = false;
        if (fb.add(this.list)) {
            added = true;
            this.list.clear();
        }
        return added;
    }
}
