package com.example.vnotes.objects;

import java.util.Comparator;

public class NoteStorageStructureComparator implements Comparator<NoteStorageStructure> {
    @Override
    public int compare(NoteStorageStructure o1, NoteStorageStructure o2) {
        String time1 = o1.getDate().concat(" ").concat(o1.getHour());
        String time2 = o2.getDate().concat(" ").concat(o2.getHour());

        return time1.compareTo(time2);
    }
}
