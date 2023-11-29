package com.example.baca_pintar;

import java.util.Map;

public class UserData {

    private String username;
    private Map<String, String> booksList;

    public UserData(String username, Map<String, String> booksList) {
        this.username = username;
        this.booksList = booksList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getBooksList() {
        return booksList;
    }

    public void setBooksList(Map<String, String> booksList) {
        this.booksList = booksList;
    }
}
