package com.example.vnotes.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vnotes.R;
import com.example.vnotes.cache.AppCache;
import com.example.vnotes.common.AppCommons;
import com.example.vnotes.objects.NoteStorageStructure;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class EditNoteActivity extends AppCompatActivity {
    private Integer noteKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            noteKey = extras.getInt("key");
            NoteStorageStructure note = AppCache.NOTES.get(noteKey);
            initTitle(note);
            initContent(note);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EditText title = findViewById(R.id.title);
        EditText content = findViewById(R.id.content);
        NoteStorageStructure note = AppCache.NOTES.get(noteKey);

        note.setTitle(title.getText().toString());
        note.setContent(content.getText().toString());

        AppCache.NOTES.put(noteKey, note);
        AppCache.updateOrderedNotes(note);
        writeNoteToFile(note);
    }

    private void initTitle(NoteStorageStructure note) {
        EditText title = findViewById(R.id.title);
        title.setLayoutParams(new LinearLayout.LayoutParams(AppCommons.WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
        title.setText(note.getTitle());
        title.setTextSize(40);
        title.setFocusable(true);
        title.setEnabled(true);
        title.setClickable(true);
        title.setFocusableInTouchMode(true);
        title.setCursorVisible(true);
    }

    private void initContent(NoteStorageStructure note) {
        EditText content = findViewById(R.id.content);
        content.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        content.setText(note.getContent());
        content.setTextSize(20);
    }

    private void writeNoteToFile(NoteStorageStructure note) {
        ObjectMapper jsonMapper = new ObjectMapper();

        try {
            jsonMapper.writeValue(new File(AppCommons.APP_ROOT_PATH
                                            + "/"
                                            + noteKey.toString()
                                            + ".json"), note);
        } catch(IOException error) {
            Log.e("ERROR", error.getMessage());
        }
    }
}