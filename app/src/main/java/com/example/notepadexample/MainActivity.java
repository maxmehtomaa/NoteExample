package com.example.notepadexample;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int NOTE_ACTIVITY_REQUEST_CODE = 0;

    private Button removeNoteBtn;
    private Button insertNoteBtn;

    private EditText insertEditText;
    private EditText removeEditText;

    private FloatingActionButton fab;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

    private ArrayList<Note> notes;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertEditText = findViewById(R.id.activity_main_insert_edit_text);
        removeEditText = findViewById(R.id.activity_main_remove_edit_text);
        insertNoteBtn = findViewById(R.id.activity_main_insert_button);
        removeNoteBtn = findViewById(R.id.activity_main_remove_button);

        fab = findViewById(R.id.fab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createNoteList();
        setupRecyclerView();
        setupButtons();
    }

    private String getDateTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(c);
    }

    public void setupButtons() {
        insertNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(insertEditText.getText().toString());
                insertItemInList(position);
            }
        });

        removeNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(removeEditText.getText().toString());
                removeNoteFromList(position);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItemInList(0);
            }
        });
    }

    public void insertItemInList(int position) {
        notes.add(position, new Note("Example title", "Example content", getDateTime()));
        adapter.notifyItemInserted(position);
    }

    public void removeNoteFromList(int position) {
        notes.remove(position);
        adapter.notifyItemRemoved(position);
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
//                changeItem(position, "Clicked");
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("note_example", notes.get(position));
                startActivity(intent);
            }

            @Override
            public void onNoteRemoved(int position) {
                removeNoteFromList(position);
                Toast.makeText(getApplicationContext(), "Note removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void changeItem(int position, String text) {
        notes.get(position).changeTitle(text);

        adapter.notifyItemChanged(position);
    }

    public String create(String fileName) {
        return null;
    }

    public String remove(String fileName) {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
