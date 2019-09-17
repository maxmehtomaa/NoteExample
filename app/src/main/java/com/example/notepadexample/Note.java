package com.example.notepadexample;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String title;
    private String content;
    private String date;

    public Note(String noteTitle, String noteContent, String date) {
        this.title = noteTitle;
        this.content = noteContent;
        this.date = date;
    }

    protected Note(Parcel in) {
        title = in.readString();
        content = in.readString();
        date = in.readString();
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

    public void changeTitle(String text) {
        title = text;
    }

    public void changeTimestamp(String text) {
        date = text;
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

    public String getDateToString() {
        return date;
    }

    public void setDateToString(String timeStampToString) {
        this.date = timeStampToString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(date);
    }
}
