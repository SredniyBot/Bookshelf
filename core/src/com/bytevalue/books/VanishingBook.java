package com.bytevalue.books;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.bytevalue.service.TextureService;

public class VanishingBook extends Book{

    private int frame =0;
    private float interval=1;
    private float time=0;



    VanishingBook(int id, int positionNumber, BookHandler bookHandler, BookContainer bookContainer) {
        super(id, positionNumber, bookHandler, bookContainer);
        setZIndex(150);
    }

    VanishingBook(Book book){
        super(book.getId(), book.getPositionNumber(), book.getBookHandler(), book.getBookContainer());
        setCanBeLifted(!book.isStone(),false,book.isStone());
        setZIndex(150);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(TextureService.getTimerTextureById(frame),getRealX(),getRealY(),
                getOriginX(),getOriginY(),
                TextureService.getTimerTextureById( frame).getRegionWidth(),
                TextureService.getTimerTextureById( frame).getRegionHeight(),
                getScaleX(),getScaleY(),getRotation());

    }

    private float vanishTime;
    private boolean vanishes =true;

    public void animateVanish(float delta){
        if (!vanishes)return;
        vanishTime +=delta;
        if (vanishTime >0.3){
            vanishTime =0;
            frame++;
            if (frame>159){
                frame=159;
                disappear();
                for (Book book:getBookHandler().getTwoRandomBooks()){
                    book.startVanish();
                }
                vanishes =false;
            }
        }
    }

    @Override
    public void act(float delta) {
        animateVanish(delta);
//        time+=delta;
//        if (time>interval){
//            if (interval<= 0)disappear();
//            time=0;
//            vanishes =true;
//        }

        super.act(delta);
    }

    @Override
    public boolean isVanishBook() {
        return true;
    }


    @Override
    public void onTouch() {
        vanishes=false;
        super.onTouch();
    }

    @Override
    public void onRelease() {
        vanishes=true;
        super.onRelease();
    }
}
