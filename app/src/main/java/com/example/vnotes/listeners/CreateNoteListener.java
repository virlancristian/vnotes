package com.example.vnotes.listeners;

import android.content.Intent;
import android.view.View;

import com.example.vnotes.activities.EditNoteActivity;;
import com.example.vnotes.cache.AppCache;
import com.example.vnotes.objects.NoteStorageStructure;

public class CreateNoteListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), EditNoteActivity.class);
        NoteStorageStructure newNote = new NoteStorageStructure();

        AppCache.NOTES.put(newNote.getKey(), newNote);
        intent.putExtra("key", newNote.getKey());

        v.getContext().startActivity(intent);
    }
}
