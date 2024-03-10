package com.example.vnotes.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vnotes.R;
import com.example.vnotes.cache.AppCache;
import com.example.vnotes.common.AppCommons;
import com.example.vnotes.listeners.CreateNoteListener;
import com.example.vnotes.listeners.EnterNoteListener;
import com.example.vnotes.objects.NoteStorageStructure;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.button).setOnClickListener(new CreateNoteListener());
        onFirstInitialization(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNoteListUI();
    }

    private void onFirstInitialization(Context context) {
        createDirs(context);
    }

    private void createDirs(Context context) {
        File externalFilesDir = context.getExternalFilesDir(null);
        File appRoot = new File(externalFilesDir, "storage");

        if(!appRoot.exists()) {
            if(appRoot.mkdirs()) {
                Log.d("STATUS", "SUCCESS");
            } else {
                Log.d("STATUS", "FAIL");
            }
        }
    }

    private void updateNoteListUI() {
        LinearLayout content = findViewById(R.id.content);

        for(int i = AppCache.NOTES_ORDERED.length - 1; i >= 0; i--) {
            int key = AppCache.NOTES_ORDERED[i].getKey();
            LinearLayout noteWindow = findViewById(key);

            if(noteWindow != null) {
                ((ViewGroup) noteWindow.getParent()).removeView(noteWindow);
            }

            content.addView(createView(AppCache.NOTES_ORDERED[i]));
        }

        content.invalidate();
    }

    private View createView(NoteStorageStructure note) {
        LinearLayout noteViewTemplate = findViewById(R.id.note_template);
        LinearLayout noteView = new LinearLayout(this);
        LinearLayout contentView = new LinearLayout(this);
        TextView noteContent = new TextView(this);
        TextView noteDate = new TextView(this);
        TextView noteTitle = new TextView(this);

        noteContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                ViewGroup.LayoutParams.MATCH_PARENT));
        noteContent.setText(note.getContent());

        noteDate.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                ViewGroup.LayoutParams.WRAP_CONTENT));
        noteDate.setText(note
                        .getDate()
                        .concat(" ")
                        .concat(note.getHour()));

        noteTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                ViewGroup.LayoutParams.WRAP_CONTENT));
        noteTitle.setText(note.getTitle());

        contentView.setLayoutParams(new LinearLayout.LayoutParams((AppCommons.WIDTH * 3) / 4,
                                                            AppCommons.HEIGHT / 5));
        contentView.setPadding(AppCommons.WIDTH / 40, AppCommons.HEIGHT / 200, 0 ,0);
        contentView.setBackground(noteViewTemplate.getBackground());
        contentView.addView(noteContent);

        noteView.setOrientation(LinearLayout.VERTICAL);
        noteView.setGravity(1);
        noteView.setPadding(0, AppCommons.HEIGHT / 20, 0, 0);
        noteView.setOnClickListener(new EnterNoteListener());
        noteView.setId(note.getKey());
        noteView.addView(contentView);
        noteView.addView(noteDate);
        noteView.addView(noteTitle);
        noteView.invalidate();

        return noteView;
    }
}