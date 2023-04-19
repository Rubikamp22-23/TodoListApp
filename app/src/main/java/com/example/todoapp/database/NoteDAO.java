package com.example.todoapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Note note);


}
