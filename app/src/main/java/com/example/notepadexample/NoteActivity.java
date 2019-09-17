package com.example.notepadexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private Button saveNoteBtn;
    private Button clearNoteBtn;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);




    public interface OnSavedClickListener {
        void onSaved(String title, String content, String timestamp);
        void onFailed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent = getIntent();
        Note note = intent.getParcelableExtra("note_example");

        String title = note.getTitle();
        String content = note.getContent();
        String date = note.getDateToString();

        TextView titleView = findViewById(R.id.activity_note_title_text_view);
        TextView contentView = findViewById(R.id.activity_note_content_text_view);
        TextView dateView = findViewById(R.id.activity_note_date_text_view);

//        titleEditText = findViewById(R.id.activity_note_title_edit_text);
//        contentEditText = findViewById(R.id.activity_note_content_edit_text);
        saveNoteBtn = findViewById(R.id.activity_note_save_button);
        clearNoteBtn = findViewById(R.id.activity_note_clear_button);

        setupButtons();
    }

    private String getDateTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(c);
    }

    private void setupButtons() {
        clearNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearNote();
            }
        });
    }

    public void clearNote() {
        titleEditText.getText().clear();
        contentEditText.getText().clear();
    }

}
