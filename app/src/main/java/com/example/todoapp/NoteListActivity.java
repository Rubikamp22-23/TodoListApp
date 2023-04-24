package com.example.todoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.todoapp.database.AppDatabase;
import com.example.todoapp.database.Note;
import com.example.todoapp.databinding.ActivityNoteListBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity implements NoteAdapter.OnNoteItemClick {

    private ActivityNoteListBinding binding;
    private NoteAdapter noteAdapter;
    private List<Note> notes;

    private AppDatabase appDatabase;

    private Note note;

    private int position;

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appDatabase = AppDatabase.getsInstance(NoteListActivity.this);
        initView();
        displayList();

    }

    private void displayList() {
        new RetrieveTask(this).execute();
    }

    @Override
    public void onNoteClick(int position) {
        new AlertDialog.Builder(NoteListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                appDatabase.noteDAO().delete(notes.get(position));
                                notes.remove(position);
                                listVisibility();
                                break;
                            case 1:
                                NoteListActivity.this.position = position;
                                startActivityForResult(
                                        new Intent(NoteListActivity.this,
                                                AddNoteActivity.class).putExtra(Const.NOTE_KEY, notes.get(position)),
                                        REQUEST_CODE);

                                break;
                        }
                    }
                }).show();
    }

    private static class RetrieveTask extends AsyncTask<Void, Void, List<Note>> {
        private WeakReference<NoteListActivity> activityReference;

        RetrieveTask(NoteListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().appDatabase.noteDAO().getAll();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            if (notes != null && notes.size() > 0) {
                activityReference.get().notes.clear();
                activityReference.get().notes.addAll(notes);
                // hides empty text view
                activityReference.get().binding.textviewMesg.setVisibility(View.GONE);
                activityReference.get().noteAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initView() {
        notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(notes, this);
        binding.recyclerviewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerviewNotes.setHasFixedSize(true);
        binding.recyclerviewNotes.setAdapter(noteAdapter);

        binding.fabAddNote.setOnClickListener(view -> startActivityForResult(new Intent(NoteListActivity.this, AddNoteActivity.class), REQUEST_CODE));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode > 0) {
            if (resultCode == 1) {
                notes.add((Note) data.getSerializableExtra(Const.NOTE_KEY));
            } else if (resultCode == 2) {
                notes.set(position, (Note) data.getSerializableExtra(Const.NOTE_KEY));
            }
            listVisibility();
        }
    }

    private void listVisibility() {
        int emptyMsgVisibility = View.GONE;
        if (notes.size() == 0) { // no item to display
            if (binding.textviewMesg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        binding.textviewMesg.setVisibility(emptyMsgVisibility);
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        appDatabase.cleanUp();
        super.onDestroy();
    }
}