package com.example.notepadexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;

    private Button saveNote;
    private Button removeNote;
    private String timestampToString;

    public interface OnSavedClickListener {
        void onSaved();
        void onFailed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        titleEditText = findViewById(R.id.activity_note_edittext_title);
        contentEditText = findViewById(R.id.activity_note_edittext_content);
        saveNote = findViewById(R.id.activity_note_save_note_btn);
        removeNote = findViewById(R.id.activity_note_remove_note_btn);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        timestampToString = dateFormat.format(new Date());

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(titleEditText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Title is missing!", Toast.LENGTH_SHORT).show();
                } else {
                    String title = titleEditText.getText().toString();
                    String content = contentEditText.getText().toString();

                    Note note = createNote(title, content, timestampToString);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("title", note.getTitle());
                    intent.putExtra("content", note.getContent());
                    intent.putExtra("timestamp", note.getTimeStampToString());
                    startActivity(intent);
                }
            }
        });
    }

    public Note createNote(String title, String content, String timestamp) {
        Note note = new Note(title, content, timestamp);

        return note;
    }

    public void updateNote(Note note) {
        note.changeTitle(note.getTitle());
        note.changeContent(note.getContent());
        note.changeTimestamp(note.getTimeStampToString());
    }
}
