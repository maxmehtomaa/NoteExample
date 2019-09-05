package com.example.notepadexample;

import java.io.Serializable;

public class Note implements Serializable {

    private String title;
    private String content;
    private String timeStampToString;

    public Note(String noteTitle, String noteContent, String noteTimestamp) {
        this.title = noteTitle;
        this.content = noteContent;
        this.timeStampToString = noteTimestamp;
    }

    public void changeTitle(String text) {
        title = text;
    }

    public void changeTimestamp(String text) {
        timeStampToString = text;
    }

    public void changeContent(String text) {
        content = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStampToString() {
        return timeStampToString;
    }

    public void setTimeStampToString(String timeStampToString) {
        this.timeStampToString = timeStampToString;
    }


}
