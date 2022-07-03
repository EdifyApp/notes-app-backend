package com.notes.api.responses;

import java.util.Date;

public interface NoteInfo {
    long getId();
    String getNoteName();
    Date getLastSaved();
}
