package com.example.notepadexample.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.notepadexample.model.Note;
import com.example.notepadexample.R;
import com.example.notepadexample.adapter.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static androidx.recyclerview.widget.ItemTouchHelper.*;
import static com.example.notepadexample.activities.NoteActivity.EXTRA_NOTE;
import static com.example.notepadexample.activities.NoteActivity.EXTRA_POSITION;

public class NoteListActivity extends AppCompatActivity {

    private static final int NOTE_ACTIVITY_REQUEST_CODE = 0;
    private static final int EDIT_NOTE_REQUEST_CODE = 1;
    private static final int NEW_NOTE_POSITION = 0;

    private static final String TAG = ".NoteListActivity";

    private FloatingActionButton fab;
    private ArrayList<Note> notes;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        setTitle("Notepad Example");

        fab = findViewById(R.id.fab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createNoteList();
        setupRecyclerView();
        setupButtons();
        setupItemTouchHelper();
    }

    private void createNoteList() {
        notes = new ArrayList<>();
        notes.add(new Note("Untitled", "untitled", getDateTime()));
        notes.add(new Note("Example title", "example", getDateTime()));
        notes.add(new Note("Khiel", "qwerty", getDateTime()));
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new NoteAdapter(notes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClicked(int position) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra(EXTRA_NOTE, notes.get(position));
                intent.putExtra(EXTRA_POSITION, position);
                startActivityForResult(intent, NOTE_ACTIVITY_REQUEST_CODE);
            }

            @Override
            public void onNoteRemoved(int position) {
                removeNoteFromList(position);
                Toast.makeText(getApplicationContext(), "Note removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupButtons() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertNoteInList(NEW_NOTE_POSITION);
            }
        });
    }

    private void setupItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleCallback(0,
                RIGHT | LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeNoteFromList(viewHolder.getAdapterPosition());
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private String getDateTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(c);
    }

    public void insertNoteInList(int position) {
        notes.add(position, new Note("Untitled", "Example content", getDateTime()));
        adapter.notifyItemInserted(position);
    }

    public void removeNoteFromList(int position) {
        notes.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Note resultNote = data.getParcelableExtra(EXTRA_NOTE);
                int position = data.getIntExtra(EXTRA_POSITION, 0);

                String resultTitle = resultNote.getTitle();
                String resultContent = resultNote.getContent();
                String resultLastEditTime = resultNote.getLastEditTime();

                Log.i(TAG, "Title: " + resultTitle);
                Log.i(TAG, "Content: " + resultContent);
                Log.i(TAG, "Last edit: " + resultLastEditTime);
                Log.i(TAG, "This note position: " + position);

                changeNoteTitle(position, resultTitle);
                changeNoteContent(position, resultContent);
                changeNoteEditDate(position, resultLastEditTime);

                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Note isn't saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeNote(int position, String text) {
        notes.get(position).changeTitle(text);
        adapter.notifyItemChanged(position);
    }

    public void changeNoteTitle(int position, String text) {
        notes.get(position).changeTitle(text);
        adapter.notifyItemChanged(position);
    }

    public void changeNoteContent(int position, String text) {
        notes.get(position).changeContent(text);
        adapter.notifyItemChanged(position);
    }

    public void changeNoteEditDate(int position, String text) {
        notes.get(position).changeLastEditTime(text);
        adapter.notifyItemChanged(position);
    }


    public String create(String fileName) {
        //TODO: Creates a note file
        return null;
    }

    public String remove(String fileName) {
        //TODO: Removes a note file
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_delete_all_notes:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
