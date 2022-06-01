package com.bytevalue.pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.ActivitySwitcher;
import com.bytevalue.TextureIds;

import javax.print.DocFlavor;

public class Pause extends Actor {

    private TextureRegion textureRegion;
    private Viewport viewport;
    private float pressureTime=0;
    private Rectangle rectangle;
    private ActivitySwitcher activitySwitcher;


    Pause(Viewport viewport, ActivitySwitcher activitySwitcher){
        textureRegion= TextureIds.getTextureByStr("");
        this.viewport=viewport;
        rectangle=new Rectangle(400,400,100,100);
        this.activitySwitcher=activitySwitcher;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureRegion,400,400);
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isTouched()) {
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if(rectangle.contains(touch)) {
                pressureTime+=delta;
            }else {
                pressureTime=0;
            }
        }else {
            if (pressureTime>0){
                pressureTime=0;
                activitySwitcher.switchActivity();
            }
        }
    }
}
