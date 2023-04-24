package com.example.todoapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity (tableName = "note")
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo (name = "note_title")
    private String noteTitle;
    @ColumnInfo (name = "note_description")
    private String noteDescription;


    public Note() {
    }

    public Note( String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteDescription='" + noteDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return getId().equals(note.getId()) && getNoteTitle().equals(note.getNoteTitle()) && getNoteDescription().equals(note.getNoteDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNoteTitle(), getNoteDescription());
    }
}
