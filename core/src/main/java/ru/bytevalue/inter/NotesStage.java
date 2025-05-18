package ru.bytevalue.inter;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.bytevalue.notes.*;
import ru.bytevalue.service.*;

public class NotesStage extends Stage {

    NotesStage(Viewport viewport,final ActivitySwitcher activitySwitcher){
        setViewport(viewport);
        final NotesCollection collection = new NotesCollection();
        Button back = new Button(viewport){
            @Override
            public void action() {
                activitySwitcher.switchActivity(Activity.MAIN_MENU);
                SoundService.playMenuSound();                       //TODO
                VibrationService.vibrate(20);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(484,15,313,77);
            }
        };

        Button right = new Button(viewport){
            @Override
            public void action() {
                collection.right();
                SoundService.playMenuSound();
                VibrationService.vibrate(20);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(810,120,50,50);
            }
        };
        Button left = new Button(viewport){
            @Override
            public void action() {
                collection.left();
                SoundService.playMenuSound();
                VibrationService.vibrate(20);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(422,120,50,50);
            }
        };



        Actor background = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(TextureService.getNotesMenuTexture(),0,0);
            }
        };

        addActor(background);
        addActor(back);
        addActor(left);
        addActor(right);
        addActor(collection);
        for (int i=0;i<13;i++) addActor(noteButton(viewport,activitySwitcher,i,collection));

    }

    private Button noteButton(Viewport viewport, final ActivitySwitcher activitySwitcher, final int i, final NotesCollection collection){
        return new Button(viewport){
            @Override
            public void action() {
                Note n= collection.getChosenNote(i);
                if (n!=null){
                    activitySwitcher.openNote(n,640,879-60*i);
                    SoundService.playMenuSound();
                }else {
                    SoundService.playBadMenuSound();
                }
                VibrationService.vibrate(20);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(425,905-60*i,429,52);
            }
        };
    }


}
