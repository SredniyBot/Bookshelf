package ru.bytevalue.notes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.bytevalue.inter.*;

public class NoteStage extends Stage {

    private Note note;

    public NoteStage(Viewport viewport, final ActivitySwitcher activitySwitcher){
        setViewport(viewport);
        Button b = new Button(viewport) {
            @Override
            public void action() {
                if (!note.isAnim()){
                    activitySwitcher.switchActivity(Activity.CLOSE_NOTE);
                    note.remove();
                }
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(0,0,1280,1280);
            }
        };
        addActor(b);
    }

    public void setNote(Note note,float x,float y){
        this.note=note;
        note.startAnim(x,y);
        addActor(note);
    }

}
