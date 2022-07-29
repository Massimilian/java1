package classes;

import java.util.ArrayList;

public class Text {
    private int maxNumber = 10;
    private int numberOfPosts = 0;
    private ArrayList<String> posts = new ArrayList<>();

    public int getNum() {
        if (numberOfPosts == 10) {
            numberOfPosts = 0;
        }
        return numberOfPosts++;
    }

    public void makePost(String text) {
        posts.add(getNum(), text);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxNumber; i++) {
            sb.append(posts.get(getNum())).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
