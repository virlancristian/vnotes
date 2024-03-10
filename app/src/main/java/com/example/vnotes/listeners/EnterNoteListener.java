package com.example.vnotes.listeners;

import android.content.Intent;
import android.view.View;

import com.example.vnotes.activities.EditNoteActivity;

public class EnterNoteListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), EditNoteActivity.class);
        int key = v.getId();

        intent.putExtra("key", key);
        v.getContext().startActivity(intent);
    }
}
