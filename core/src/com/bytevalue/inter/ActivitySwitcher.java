package com.bytevalue.inter;

import com.bytevalue.books.Score;
import com.bytevalue.notes.Note;

public interface ActivitySwitcher {
    void switchActivity(Activity activity);
    void startGame();
    void openNote(Note note,float x,float y);
    void loseGame(Score score);
}
