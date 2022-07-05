package com.bytevalue.books;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bytevalue.BookSorter;
import com.bytevalue.service.TextureService;

public class Background  {

    private final TextureRegion backgroundTexture;
    private final TextureRegion topTexture;
    private final BookDisposer bookDisposer;

    Background(BookDisposer bookDisposer){
        this.bookDisposer = bookDisposer;
        backgroundTexture = TextureService.getBackgroundTexture();
        topTexture= TextureService.getTopTexture();
    }

    public void draw(Batch batch) {
        batch.draw(backgroundTexture,0,bookDisposer.getBias()%getH());
        batch.draw(backgroundTexture,0,bookDisposer.getBias()%getH()+ backgroundTexture.getRegionHeight());
        batch.draw(topTexture,0, BookSorter.SCREEN_HEIGHT-topTexture.getRegionHeight());
    }
    public int getH(){
        return backgroundTexture.getRegionHeight();
    }

}
