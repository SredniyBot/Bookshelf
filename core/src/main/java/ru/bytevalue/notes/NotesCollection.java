package ru.bytevalue.notes;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.*;

import ru.bytevalue.service.FontService;

public class NotesCollection extends Group {

    private static Array<Note> notes;
    private int numberOfList = 0;
    private final GlyphLayout countLayout;
    private final GlyphLayout notesLayout;


    public NotesCollection() {

        Preferences pr = Gdx.app.getPreferences("main");
        boolean ru = pr.getBoolean("ru", false);

        notes = new Array<>();
        String in = Gdx.files.internal("utilities/notes.txt").readString("UTF-8");

        while (in.contains("{")) {
            in = in.substring(in.indexOf("{") + 1);
            notes.add(new Note(in.substring(0, in.indexOf("}")), ru));
        }
        countLayout = new GlyphLayout();
        notesLayout = new GlyphLayout();
        countLayout.setText(FontService.getFont(), String.valueOf(numberOfList + 1),
            FontService.getScoreColor(), 1280, Align.center, false);
        notesLayout.setText(FontService.getFont(), String.valueOf(numberOfList),
            FontService.getNotesColor(), 430, Align.center, false);
    }

    public static Note newRandomNote() {
        Array<Note> cn = new Array<>(notes);
        cn.shuffle();
        for (int i = 0; i < cn.size; i++) {
            if (!cn.get(i).isFound()) {
                cn.get(i).setFound(true);
                return cn.get(i);
            }
        }
        return null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        countLayout.setText(FontService.getFont(), String.valueOf(numberOfList + 1),
            FontService.getScoreColor(), 1280, Align.center, false);

        FontService.getFont().draw(batch, countLayout, 0, 1015);
        for (int i = 0; i < 13; i++) {
            int k = i + 13 * numberOfList;
            if (notes.get(k).isFound()) {
                notesLayout.setText(FontService.getFont(), (k + 1) + ". " + notes.get(k).getAuthor(),
                    FontService.getNotesColor(), 430, Align.left, false);
            } else {
                notesLayout.setText(FontService.getFont(), (k + 1) + ". ???",
                    FontService.getNotesColor(), 430, Align.left, false);
            }
            FontService.getFont().draw(batch, notesLayout, 435, 943 - 60 * i);
        }
    }

    public void right() {
        numberOfList++;
        if (13 * (numberOfList + 1) > notes.size) numberOfList = 0;

    }

    public void left() {
        numberOfList--;
        if (numberOfList < 0) numberOfList = notes.size / 13 - 1;
    }

    public Note getChosenNote(int i) {
        if (notes.get(numberOfList * 13 + i).isFound())
            return notes.get(numberOfList * 13 + i);
        return null;
    }

    public static void changeLanguage(boolean ru) {
        if (notes == null) return;
        for (Note note : notes) note.setRu(ru);
    }
}
