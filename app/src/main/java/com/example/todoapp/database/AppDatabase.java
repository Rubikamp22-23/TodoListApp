package com.example.todoapp.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NoteEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final Object LOCK = new Object();
    public static final String DATABASE_NAME = "todo_list";
    private static final String TAG = "AppDatabase";
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "creating new database");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(TAG, "getting the database instance");
        return sInstance;

    }

    public abstract NoteDAO noteDAO();
}
