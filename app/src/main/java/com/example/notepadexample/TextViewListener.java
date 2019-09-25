package com.example.notepadexample;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextViewListener implements TextWatcher {

    private boolean ignoreLoop = false;
    private String _before;
    private String _after;
    private String _old;
    private String _new;


    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        _before = charSequence.subSequence(0, start).toString();
        _old = charSequence.subSequence(start, start + count).toString();
        _after = charSequence.subSequence(start + count, charSequence.length()).toString();

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
        _new = charSequence.subSequence(start, start + count).toString();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (ignoreLoop) {
            return;
        }

        onTextChanged(_before, _old, _new, _after);
    }

    protected abstract void onTextChanged(String before, String old, String aNew, String after);

    protected void startUpdates() {
        ignoreLoop = true;
    }

    protected void endUpdates() {
        ignoreLoop = false;
    }
}
