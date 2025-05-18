package ru.bytevalue.inter;

import ru.bytevalue.books.Score;
import ru.bytevalue.notes.Note;

public interface ActivitySwitcher {
    void switchActivity(Activity activity);
    void startGame();
    void openNote(Note note,float x,float y);
    void loseGame(Score score);
}
