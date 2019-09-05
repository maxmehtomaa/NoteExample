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
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button removeNoteBtn;
    private Button insertNoteBtn;

    private EditText insertEditText;
    private EditText removeEditText;


    private ArrayList<Note> notes;
    private long timestamp;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String timestampToString;

    private String savedTitle;
    private String savedContent;
    private String savedTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        timestampToString = dateFormat.format(new Date());

        Intent intent = getIntent();

        if (intent != null) {
            savedTitle = intent.getStringExtra("title");
            savedContent = intent.getStringExtra("content");
            savedTimestamp = intent.getStringExtra("timestamp");


            int position = ;
            changeItem(notes.get(0).setTitle(savedTitle));
        }

        createNoteList();
        setupRecyclerView();
        setButtons();

    }


    public void setButtons() {
        removeNoteBtn = findViewById(R.id.remove_button);
        insertNoteBtn = findViewById(R.id.insert_button);
        insertEditText = findViewById(R.id.edittext_insert);
        removeEditText = findViewById(R.id.edittext_remove);

        insertNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(insertEditText.getText().toString());
                insertItem(position);
            }
        });

        removeNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(removeEditText.getText().toString());
                removeItem(position);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem(0);
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void insertItem(int position) {
        notes.add(position, new Note("Example title", "Example desc", timestampToString));
        adapter.notifyItemInserted(position);
    }

    public void insertNoteItem(int position, Note note) {
        notes.add(position, new Note(note.getTitle(), note.getContent(), note.getTimeStampToString()));
        adapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        notes.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void createNoteList() {
        notes = new ArrayList<>();

        //notes.add(new Note("Ruokalista", "Mehuu, ruokaa", timestampToString));

//        notes.add(new Note("Velkalista", "Mäsks: 20€", timestampToString));
//        notes.add(new Note("Yoyoyoyo", "ohohoho", timestampToString));
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new NoteAdapter(notes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Note note = notes.get(position);
                changeItem(position, note.getTitle(), note.getContent(), note.getTimeStampToString());
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(intent);

            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
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

    public void changeItem(int position, String title, String content, String timestamp) {
        Note n = notes.get(position);

        n.changeTitle(title);
        n.changeContent(content);
        n.changeTimestamp(timestamp);
        adapter.notifyItemChanged(position);
    }

    public Note getUpdatedNoteObject(int position, String title, String content, String timestamp) {
        Note note = notes.get(position);
        note.changeTitle(title);
        note.changeContent(content);
        note.changeTimestamp(timestamp);

        return note;
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
