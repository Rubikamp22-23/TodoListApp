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

    @Query("SELECT * FROM note")
    List<NoteEntity> getAll();

    @Insert
    void insert(NoteEntity noteEntity);

    @Delete
    void delete(NoteEntity noteEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(NoteEntity noteEntity);


}
