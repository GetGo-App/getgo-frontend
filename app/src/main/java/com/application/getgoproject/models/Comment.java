package com.application.getgoproject.models;

public class Comment {
    private int avatar;
    private String name;
    private String time;
    private String comment;
    private int star;

    public Comment(int avatar, String name, String time, String comment, int star) {
        this.avatar = avatar;
        this.name = name;
        this.time = time;
        this.comment = comment;
        this.star = star;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
