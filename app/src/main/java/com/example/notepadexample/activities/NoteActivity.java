package com.example.notepadexample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notepadexample.model.Note;
import com.example.notepadexample.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = ".NoteActivity";

    private EditText titleEditText;
    private EditText contentEditText;
    private Button editNoteBtn;
    private TextView titleView;
    private TextView contentView;
    private TextView lastEditDateView;

    private String currentContent;
    private String currentTitle;
    private String lastEditTime;

    private Note n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent = getIntent();
        Note note = intent.getParcelableExtra("note_example");

        currentTitle = note.getTitle();
        currentContent = note.getContent();
        lastEditTime = note.getDateToString();

        titleView = findViewById(R.id.activity_note_title_text_view);
        contentView = findViewById(R.id.activity_note_content_text_view);
        lastEditDateView = findViewById(R.id.activity_note_date_text_view);
        titleEditText = findViewById(R.id.activity_note_title_edit_text);
        contentEditText = findViewById(R.id.activity_note_content_edit_text);
        editNoteBtn = findViewById(R.id.activity_note_edit_button);

//        titleEditText.addTextChangedListener(new TextViewListener() {
//            @Override
//            protected void onTextChanged(String before, String old, String aNew, String after) {
//                String completeOldText = before + old + after;
//                String completeNewText = before + aNew + after;
//
//                startUpdates();
//                returnedNote.setTitle(completeNewText);
//                titleEditText.setText(completeNewText);
//                endUpdates();
//
//            }
//        });
//
//        contentEditText.addTextChangedListener(new TextViewListener() {
//            @Override
//            protected void onTextChanged(String before, String old, String aNew, String after) {
//                String completeOldText = before + old + after;
//                String completeNewText = before + aNew + after;
//
//                startUpdates();
//                returnedNote.setTitle(completeNewText);
//                contentEditText.setText(completeNewText);
//                endUpdates();
//
//            }
//        });

        Log.i(TAG, "Title: " + currentTitle);
        Log.i(TAG, "Content: " + currentContent);
        Log.i(TAG, "Last edit time: " + lastEditTime);

        initTextViews();
        setupButtons();
    }

    private void initTextViews() {
        titleView.setText(currentTitle);
        contentView.setText(currentContent);
        lastEditDateView.setText("Last edit: " + lastEditTime);
        titleEditText.setText(currentTitle);
        contentEditText.setText(currentTitle);
    }


    private String getDateTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault());
        return sdf.format(c);
    }

    private void setupButtons() {
        editNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                editNote(title, content, getDateTime());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNote();
                break;
            case R.id.action_clear:
                clearTextFields();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        String lastEdit = getDateTime();

        Intent resultIntent = new Intent();
        Note resultNote = new Note(title, content, lastEdit);

        resultIntent.putExtra("resultNote", resultNote);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void editNote(String title, String content, String date) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, "Title/currentContent is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        titleView.setText(title);
        contentView.setText(content);
        lastEditDateView.setText(date);
        Toast.makeText(this, "Note has been edited", Toast.LENGTH_SHORT).show();
    }

    private void clearTextFields() {
        titleEditText.getText().clear();
        contentEditText.getText().clear();
        Toast.makeText(this, "Cleared text fields", Toast.LENGTH_SHORT).show();
    }
}
