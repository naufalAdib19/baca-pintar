package com.example.baca_pintar.recycler_view;

import android.widget.ImageView;
import android.widget.TextView;

public class Item {

    String thumbnail;
    String title;
    String authors;

    public Item(String thumbnail, String title, String authors) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.authors = authors;
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

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}
