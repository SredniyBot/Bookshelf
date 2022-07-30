package com.bytevalue.inter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.notes.NotesCollection;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.TextureService;
import com.bytevalue.service.VibrationService;

public class LanguageButton extends Group {

    private boolean ru;
    private float x;
    private float bias;

    public LanguageButton(Viewport viewport) {
        Preferences preferences = Gdx.app.getPreferences("main");
        ru=preferences.getBoolean("ru",false);
        if (ru)x=560;
        else x=685;
        Button eng = new Button(viewport) {
            @Override
            public void action() {
                changeLanguage(false);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(663,449,78,78);
            }
        };
        Button rus = new Button(viewport)  {
            @Override
            public void action() {
                changeLanguage(true);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(541,449,78,78);
            }
        };
        addActor(rus);
        addActor(eng);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(TextureService.getCheckMarkTexture(),x,403);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (bias==0)return;
        float v=delta*bias*10;
        bias-=v;
        x-=v;
        if (Math.abs(bias)<1){
            x-=bias;
            bias=0;
        }

    }

    private void changeLanguage(boolean ru){
        if (ru==this.ru)return;
        this.ru=ru;
        TextureService.changeLanguage(ru);
        NotesCollection.changeLanguage(ru);
        SoundService.playMenuSound();
        VibrationService.vibrate(20);
        Preferences preferences=Gdx.app.getPreferences("main");
        preferences.putBoolean("ru",ru);
        preferences.flush();
        if (!ru) bias=685-x;
        else bias=560-x;
        bias=-bias;

    }


}
