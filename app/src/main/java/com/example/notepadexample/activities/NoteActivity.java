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
    private TextView titleView;
    private TextView contentView;
    private TextView lastEditDateView;

    private String currentTitle;
    private String currentContent;
    private String lastEditTime;

    private int notePosition;
    private static final String LAST_EDIT = "Last edit: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent = getIntent();
        if (intent != null) {
            Note note = intent.getParcelableExtra("note_example");
            notePosition = intent.getIntExtra("position", 0);

            Log.i(TAG, "Title: " + note.getTitle());
            Log.i(TAG, "Content: " + note.getContent());
            Log.i(TAG, "Last edit time: " + note.getDateToString());

            currentTitle = note.getTitle();
            currentContent = note.getContent();
            lastEditTime = note.getDateToString();
        }

        titleView = findViewById(R.id.activity_note_title_text_view);
        contentView = findViewById(R.id.activity_note_content_text_view);
        lastEditDateView = findViewById(R.id.activity_note_date_text_view);
        titleEditText = findViewById(R.id.activity_note_title_edit_text);
        contentEditText = findViewById(R.id.activity_note_content_edit_text);

        initTextViews();
        setupButtons();
    }

    private void initTextViews() {
        titleView.setText(currentTitle);
        contentView.setText(currentContent);
        lastEditDateView.setText(LAST_EDIT + lastEditTime);
        titleEditText.setText(currentTitle);
        contentEditText.setText(currentContent);
    }

    private void setupButtons() {

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
        Toast.makeText(getApplicationContext(), "Note has been saved", Toast.LENGTH_SHORT).show();
//        finish();
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
        titleView.setText("Empty");
        contentView.setText("Empty");
        lastEditDateView.setText(LAST_EDIT + getDateTime());
        Toast.makeText(this, "Cleared text fields", Toast.LENGTH_SHORT).show();
    }

    private String getDateTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault());
        return sdf.format(c);
    }

    @Override
    public void onBackPressed() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        String lastEdit = getDateTime();

        Note resultNote = new Note(title, content, lastEdit);
        editNote(title, content, lastEdit);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("resultNote", resultNote);
        resultIntent.putExtra("position", notePosition);
        setResult(RESULT_OK, resultIntent);
        Toast.makeText(NoteActivity.this, "Note has been saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
