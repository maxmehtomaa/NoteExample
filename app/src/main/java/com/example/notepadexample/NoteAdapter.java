package com.example.notepadexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> noteArrayList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClicked(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public NoteAdapter(ArrayList<Note> noteList) {
        noteArrayList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        NoteViewHolder viewHolder = new NoteViewHolder(v, listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = noteArrayList.get(position);

        holder.titleTextView.setText(currentNote.getTitle());
        holder.contentTextView.setText(currentNote.getContent());
        holder.timestampTextView.setText(currentNote.getTimeStampToString());

    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView contentTextView;
        private TextView timestampTextView;
        private ImageView removeImageView;

        public NoteViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.note_list_item_text_view_title);
            contentTextView = itemView.findViewById(R.id.note_list_item_text_view_content);
            timestampTextView = itemView.findViewById(R.id.note_list_item_text_view_timestamp);
            removeImageView = itemView.findViewById(R.id.note_list_item_image_view_remove);

            removeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClicked(position);
                        }
                    }
                }
            });
        }
    }
}
