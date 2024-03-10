package com.example.vnotes.cache;

import android.util.Log;

import com.example.vnotes.common.AppCommons;
import com.example.vnotes.objects.NoteStorageStructure;
import com.example.vnotes.objects.NoteStorageStructureComparator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class AppCache {
    public static HashMap<Integer, NoteStorageStructure> NOTES = readNotesFromStorage();
    public static NoteStorageStructure[] NOTES_ORDERED = getOrderedNotes();

    private static HashMap readNotesFromStorage() {
        File appRoot = new File(AppCommons.APP_ROOT_PATH);
        File[] files = appRoot.listFiles();
        ObjectMapper jsonMapper = new ObjectMapper();
        HashMap<Integer, NoteStorageStructure> notes = new HashMap<>();

        if(files != null) {
            for(File file:files) {
                Log.i("INFO", file.getName());
                try {
                    NoteStorageStructure note = jsonMapper.readValue(file, NoteStorageStructure.class);
                    notes.put(note.getKey(), note);
                } catch (IOException error) {
                    Log.e("ERROR", error.getMessage());
                }
            }
        }

        return notes;
    }

    private static NoteStorageStructure[] getOrderedNotes() {
        NoteStorageStructure[] notesArray = new NoteStorageStructure[]{};

        for(NoteStorageStructure note: NOTES.values()) {
            notesArray = Arrays.copyOf(notesArray, notesArray.length + 1);
            notesArray[notesArray.length - 1] = note;
        }

        Arrays.sort(notesArray, new NoteStorageStructureComparator());

        return notesArray;
    }

    public static void updateOrderedNotes(NoteStorageStructure note) {
        int left = 0;
        int right = NOTES_ORDERED.length - 1;
        NoteStorageStructureComparator comparator = new NoteStorageStructureComparator();

        if(right == -1 || comparator.compare(NOTES_ORDERED[right], note) <= 0) {
            NOTES_ORDERED = Arrays.copyOf(NOTES_ORDERED, NOTES_ORDERED.length + 1);
            NOTES_ORDERED[NOTES_ORDERED.length - 1] = note;
            return;
        }

        while(left < right) {
            int middle = (left + right) / 2;

            if(NOTES_ORDERED[middle].equals(note)) {
                NOTES_ORDERED[middle] = note;
            } else {
                if(comparator.compare(NOTES_ORDERED[middle], note) < 0) {
                    right = middle;
                } else {
                    left = middle + 1;
                }
            }
        }
    }
}