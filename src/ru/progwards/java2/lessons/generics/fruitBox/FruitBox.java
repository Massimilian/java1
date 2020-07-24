package ru.progwards.java2.lessons.generics.fruitBox;

import java.util.ArrayList;

public class FruitBox {
    private ArrayList<Fruit> list = new ArrayList();

    public boolean add(ArrayList<? extends Fruit> news) {
        boolean added;
        if (list.isEmpty()) {
            list.addAll(news);
            added = true;
        } else {
            String[] temp = list.get(0).getClass().toString().split("\\Q.\\E");
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
