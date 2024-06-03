package com.application.getgoproject.models;

import java.io.Serializable;

public class ListPackage implements Serializable {
    private String title;
    private String price;

    public ListPackage(String title, String price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
