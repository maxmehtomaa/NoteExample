package com.example.notepadexample.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String title;
    private String content;
    private String lastEditTime;

    public Note(String noteTitle, String noteContent, String lastEditTime) {
        this.title = noteTitle;
        this.content = noteContent;
        this.lastEditTime = lastEditTime;
    }

    protected Note(Parcel in) {
        title = in.readString();
        content = in.readString();
        lastEditTime = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeLastEditTime(String lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public void changeContent(String content) {
        this.content = content;
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

    public String getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(lastEditTime);
    }
}
