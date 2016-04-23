//1つのセルにあるデータを保存するためだけのデータクラスです。継承なし。
package com.spica_travel.taku.gsapp;

public class MessageRecord {
    //保存するデータ全てを変数で定義します。
    private String imageUrl;
    private String comment;
    private String title;
    private String author;

    //データを１つ作成する関数です。項目が増えたら増やしましょう。
    public MessageRecord(String imageUrl, String title, String author, String comment) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.comment = comment;
    }
    //それぞれの項目を返す関数です。項目が増えたら増やしましょう。
    public String getComment() {
        return comment;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
}
