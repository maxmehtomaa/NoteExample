package com.example.notepadexample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notepadexample.model.Note;
import com.example.notepadexample.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class NoteActivity extends AppCompatActivity {

    public interface OnNoteClickListener {
        void onRemove(int position);
    }

    private static final String TAG = ".NoteActivity";

    public static final String EXTRA_TITLE = "com.example.notepadexample.EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "com.example.notepadexample.EXTRA_CONTENT";
    public static final String EXTRA_POSITION = "com.example.notepadexample.EXTRA_POSITION";
    public static final String EXTRA_NOTE = "com.example.notepadexample.EXTRA_NOTE";

    private EditText titleEditText;
    private EditText contentEditText;
    private TextView titleView;
    private TextView contentView;
    private TextView lastEditDateView;

    private String currentTitle;
    private String currentContent;
    private String lastEditTime;

    private int notePositionInList;
    private static final String LAST_EDIT = "Last edit: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        setTitle("Edit Note");

        titleView = findViewById(R.id.activity_note_title_text_view);
        contentView = findViewById(R.id.activity_note_content_text_view);
        lastEditDateView = findViewById(R.id.activity_note_date_text_view);
        titleEditText = findViewById(R.id.activity_note_title_edit_text);
        contentEditText = findViewById(R.id.activity_note_content_edit_text);

        Intent intent = getIntent();

        if (intent != null) {
            Note note = intent.getParcelableExtra(EXTRA_NOTE);
            notePositionInList = intent.getIntExtra(EXTRA_POSITION, 0);

            currentTitle = note.getTitle();
            currentContent = note.getContent();
            lastEditTime = note.getLastEditTime();

            Log.i(TAG, "Title: " + currentTitle);
            Log.i(TAG, "Content: " + currentContent);
            Log.i(TAG, "Last edit time: " + lastEditTime);
        } else {
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        initTextViews();
    }

    private void initTextViews() {
        titleView.setText(currentTitle);
        contentView.setText(currentContent);
        lastEditDateView.setText(lastEditTime);
        titleEditText.setText(currentTitle);
        contentEditText.setText(currentContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNote();
                return true;
            case R.id.action_clear:
                clearNote();
                return true;
            case R.id.action_remove:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        String lastEdit = getDateTime();

        if (title.trim().isEmpty() || content.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and content", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent resultIntent = new Intent();
        Note resultNote = new Note(title, content, lastEdit);

        resultIntent.putExtra(EXTRA_NOTE, resultNote);
        resultIntent.putExtra(EXTRA_POSITION, notePositionInList);
        setResult(RESULT_OK, resultIntent);
        Toast.makeText(getApplicationContext(), "Note has been saved", Toast.LENGTH_SHORT).show();
        finish();
        editTextViews(title, content, lastEdit);
    }

    private void editTextViews(String title, String content, String lastEdit) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, "Title/content is missing", Toast.LENGTH_SHORT).show();
            return;
        }
        titleView.setText(title);
        contentView.setText(content);
        lastEditDateView.setText(lastEdit);
        Toast.makeText(this, "Text views has been edited", Toast.LENGTH_SHORT).show();
    }

    private void clearNote() {
        titleEditText.getText().clear();
        contentEditText.getText().clear();

        titleView.setText("Untitled");
        contentView.setText("Content");
        lastEditDateView.setText(getDateTime());
        Toast.makeText(this, "Cleared text fields", Toast.LENGTH_SHORT).show();
    }

    private String getDateTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
        return sdf.format(c);
    }

    @Override
    public void onBackPressed() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        String lastEdit = getDateTime();

        if (title.trim().isEmpty() || content.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and content", Toast.LENGTH_SHORT).show();
            return;
        }

        Note resultNote = new Note(title, content, lastEdit);
        editTextViews(title, content, lastEdit);

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_NOTE, resultNote);
        resultIntent.putExtra(EXTRA_POSITION, notePositionInList);
        setResult(RESULT_OK, resultIntent);
        Toast.makeText(NoteActivity.this, "Note has been saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
