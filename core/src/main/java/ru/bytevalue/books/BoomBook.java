package ru.bytevalue.books;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.bytevalue.service.*;

public class BoomBook extends Book{

    private float interval=3;
    private float time=0;

    private int frame =0;

    BoomBook(int id, int positionNumber, BookHandler bookHandler, BookContainer bookContainer) {
        super(id, positionNumber, bookHandler, bookContainer);
        setZIndex(150);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(TextureService.getBoomTextureById( frame),getRealX(),getRealY(),
                getOriginX(),getOriginY(),
                TextureService.getBoomTextureById( frame).getRegionWidth(),
                TextureService.getBoomTextureById( frame).getRegionHeight(),
                getScaleX(),getScaleY(),getRotation());
    }

    private boolean peeps=true;
    private float peepTime;
    private boolean animatePeeps =false;



    public void animatePeep(float delta){
        peepTime+=delta;
        if (peepTime>0.05){
            peepTime=0;
            if (animatePeeps){
                frame++;
            } else if (frame>0){
                frame--;
            }
            if (frame>4){
                frame=4;
                SoundService.playPeepSound();
                animatePeeps=false;
            }
        }
    }

    @Override
    public void act(float delta) {
        animatePeep(delta);
        if (peeps) {
            time += delta;
            if (time > interval) {
                interval -= 0.23;
                if (interval <= 0) {
                    SoundService.playBoomSound();
                    disappear();
                    interval=10000;
                    for (Book book : getTwoNeighbours()) book.disappear();
                }
                time = 0;
                animatePeeps = true;
            }
        }
        super.act(delta);
    }

    @Override
    public void onRelease() {
        peeps =true;
        super.onRelease();
    }

    @Override
    public void onTouch() {
        peeps =false;
        super.onTouch();
    }

    @Override
    public boolean isBoomBook() {
        return true;
    }
}
