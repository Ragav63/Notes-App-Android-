package com.example.notesapproom_java.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String noteTitle;

    private String noteDesc;

    // Constructor
    public Note(int id, String noteTitle, String noteDesc) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteDesc = noteDesc;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteDesc() {
        return noteDesc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteDesc(String noteDesc) {
        this.noteDesc = noteDesc;
    }

    // Parcelable implementation
    protected Note(Parcel in) {
        id = in.readInt();
        noteTitle = in.readString();
        noteDesc = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(noteTitle);
        parcel.writeString(noteDesc);
    }
}