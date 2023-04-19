package com.example.todoapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "note")
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo (name = "note_title")
    private String noteTitle;
    @ColumnInfo (name = "note_description")
    private String noteDescription;


    public Note() {
    }

    public Note(int id, String noteTitle, String noteDescription) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
