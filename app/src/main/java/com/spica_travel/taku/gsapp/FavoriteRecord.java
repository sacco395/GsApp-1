//1つのセルにあるデータを保存するためのデータクラスです。
package com.spica_travel.taku.gsapp;

import java.util.ArrayList;


public class FavoriteRecord {
    //Data Variables
    private String imageUrl;
    private String title;
    private String author;
    private String comment;


    //Getters and Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() { return title;}

    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getComment() { return comment;}

    public void setComment(String comment) {this.comment = comment;}
}
