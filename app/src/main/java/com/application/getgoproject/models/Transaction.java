package com.application.getgoproject.models;

public class Transaction {
    private String title;
    private String time;
    private String price;

    public Transaction(String title, String time, String price) {
        this.title = title;
        this.time = time;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
