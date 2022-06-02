package org.example.servlets.testTask.classes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Place {
    private String header;
    private String body;
    private String fotoLocation;
    private String author;
    private long numberOfLooks = -1L;
    private Date date;
    private int likes = 0;
    private ArrayList<String> theyHaveSaid = new ArrayList<>();

    public Place(String header, String body, String fotoLocation, String author) {
        this.header = header;
        this.body = body;
        this.fotoLocation = fotoLocation;
        this.author = author;
        date = Date.from(Instant.now());
    }
    public long getNumberOfLooks() {
        return numberOfLooks;
    }

    public ArrayList<String> getTheyHaveSaid() {
        return theyHaveSaid;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFotoLocation() {
        return fotoLocation;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void incrLooks() {
        numberOfLooks++;
    }

}
