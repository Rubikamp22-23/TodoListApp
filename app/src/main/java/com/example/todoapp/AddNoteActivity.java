package com.example.todoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.Note;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.todoapp.databinding.ActivityAddNoteBinding;

import java.lang.ref.WeakReference;

public class AddNoteActivity extends AppCompatActivity {
    private ActivityAddNoteBinding binding;
    private AppDatabase appDatabase;

    private Note note;

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appDatabase = AppDatabase.getsInstance(AddNoteActivity.this);

        init();

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update) {
                    note.setNoteDescription(binding.edittextDescription.getText().toString());
                    note.setNoteTitle(binding.edittextTitle.getText().toString());
                    appDatabase.noteDAO().update(note);
                    setResult(note, 2);
                } else {
                    note = new Note(binding.edittextTitle.getText().toString(), binding.edittextDescription.getText().toString());
                    new InsertTask(AddNoteActivity.this, note).execute();
                }

            }
        });
    }

    private void init() {
        if ((note = (Note) getIntent().getSerializableExtra(Const.NOTE_KEY)) != null) {
            update = true;
            binding.buttonSave.setText("Update");
            binding.edittextTitle.setText(note.getNoteTitle());
            binding.edittextDescription.setText(note.getNoteDescription());
        }
    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<AddNoteActivity> activityReference;
        private Note note;

        InsertTask(AddNoteActivity context, Note note) {
            activityReference = new WeakReference<>(context);
            this.note = note;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Long id = activityReference.get().appDatabase.noteDAO().insert(note);
            note.setId(id);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                activityReference.get().setResult(note, 1);
                activityReference.get().finish();
            }
        }
    }

    private void setResult(Note note, int flag) {
        setResult(flag, new Intent().putExtra(Const.NOTE_KEY, note));
        finish();
    }
}