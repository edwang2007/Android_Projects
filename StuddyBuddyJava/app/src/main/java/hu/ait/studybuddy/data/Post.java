package hu.ait.studybuddy.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Jana on 7/2/2017.
 */

public class Post {
    private String uid;
    private String author;
    private String title;
    private String body;
    private String date;
    private List<String> listPeople;

    public Post(){}

    public Post(String uid, String author, String title, String body, String date, List list) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.date = date;
        this.listPeople = list;
        listPeople.add(0,"User's Joined");
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getListPeople() {
        return listPeople;
    }

    public void setListPeople(List<String> listPeople) {
        this.listPeople = listPeople;
    }
}
