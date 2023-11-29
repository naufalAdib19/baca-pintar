package com.example.baca_pintar.recycler_view_user;

import java.util.List;

public class UserItem {
    String thumbnail;
    String title;
    List<String> authors;
    String id;

    public UserItem(String thumbnail, String title, List<String> authors, String id) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.authors = authors;
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List <String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
