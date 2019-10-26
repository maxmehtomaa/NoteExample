package com.example.notepadexample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepadexample.model.Note;
import com.example.notepadexample.R;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> noteList;
    private OnNoteClickListener listener;

    public interface OnNoteClickListener {
        void onNoteClicked(int position);
        void onNoteRemoved(int position);
    }

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.listener = onNoteClickListener;
    }

    public NoteAdapter(ArrayList<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item, parent, false);
        return new NoteViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);

        holder.titleTextView.setText(note.getTitle());
        holder.contentTextView.setText(note.getContent());
        holder.dateTextView.setText(note.getLastEditTime());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public Note getNoteAt(int position) {
        return noteList.get(position);
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView contentTextView;
        private TextView dateTextView;
        private ImageView removeImageView;

        public NoteViewHolder(@NonNull View itemView, final OnNoteClickListener listener) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.note_list_item_title_text_view);
            contentTextView = itemView.findViewById(R.id.note_list_item_content_text_view);
            dateTextView = itemView.findViewById(R.id.note_list_item_date_text_view);
            removeImageView = itemView.findViewById(R.id.note_list_item_remove_image_view);

            removeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onNoteRemoved(position);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onNoteClicked(position);
                    }
                }
            });
        }
    }
}
