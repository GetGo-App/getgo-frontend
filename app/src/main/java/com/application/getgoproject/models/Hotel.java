package com.application.getgoproject.models;

public class Hotel {
    private int imgHotel;
    private String nameHotel;
    private String commentHotel;
    private String money;

    public Hotel(int imgHotel, String nameHotel, String commentHotel, String money) {
        this.imgHotel = imgHotel;
        this.nameHotel = nameHotel;
        this.commentHotel = commentHotel;
        this.money = money;
    }

    public int getImgHotel() {
        return imgHotel;
    }

    public void setImgHotel(int imgHotel) {
        this.imgHotel = imgHotel;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public void setNameHotel(String nameHotel) {
        this.nameHotel = nameHotel;
    }

    public String getCommentHotel() {
        return commentHotel;
    }

    public void setCommentHotel(String commentHotel) {
        this.commentHotel = commentHotel;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
